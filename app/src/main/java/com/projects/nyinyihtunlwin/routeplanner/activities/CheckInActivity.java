package com.projects.nyinyihtunlwin.routeplanner.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.projects.nyinyihtunlwin.routeplanner.R;
import com.projects.nyinyihtunlwin.routeplanner.data.vo.RouteVO;
import com.projects.nyinyihtunlwin.routeplanner.dialogs.UpdateAmountDialog;
import com.projects.nyinyihtunlwin.routeplanner.utils.CommonConstants;
import com.projects.nyinyihtunlwin.routeplanner.utils.ConfigUtils;
import com.projects.nyinyihtunlwin.routeplanner.utils.ScreenUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class CheckInActivity extends BaseActivity implements View.OnClickListener {

    private static final String KEY_ROUTE_ID = "KEY_ROUTE_ID";


    public static final Intent newIntent(Context context, String routerId) {
        Intent intent = new Intent(context, CheckInActivity.class);
        intent.putExtra(KEY_ROUTE_ID, routerId);
        return intent;
    }

    @BindView(R.id.btn_scan_code)
    Button btnScanCode;

    @BindView(R.id.tv_amount)
    TextView tvAmount;

    @BindView(R.id.iv_code_check)
    ImageView ivCodeCheck;

    @BindView(R.id.btn_cancel)
    Button btnCancel;

    @BindView(R.id.btn_save)
    Button btnSave;

    @BindView(R.id.tv_name_location)
    TextView tvLocationName;

    @BindView(R.id.sp_cash_type)
    Spinner spCashType;

    @BindView(R.id.iBtn_update_amount)
    ImageButton iBtnUpdateAmount;

    @BindView(R.id.tv_type)
    TextView tvType;

    private RouteVO mRouteVO;


    private boolean isCodeCheck = false;
    private boolean isCash = false;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        ButterKnife.bind(this, this);
        mRealm = Realm.getDefaultInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String routeId = getIntent().getStringExtra(KEY_ROUTE_ID);
        mRouteVO = mRealm.where(RouteVO.class).equalTo("id", routeId).findFirst();

        btnScanCode.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        iBtnUpdateAmount.setOnClickListener(this);

        if (mRouteVO != null) {
            switch (mRouteVO.getReqType()) {
                case CommonConstants.TYPE_CASH:
                    spCashType.setSelection(0);
                    isCash = true;
                    break;
                case CommonConstants.TYPE_ELOAD:
                    spCashType.setSelection(1);
                    isCash = false;
                    break;
            }
            tvType.setText(mRouteVO.getReqType());
            tvAmount.setText(String.valueOf(mRouteVO.getReqAmount()));
        }


        spCashType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        isCash = true;
                        break;
                    case 1:
                        isCash = false;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iBtn_update_amount:
                new UpdateAmountDialog(CheckInActivity.this, new UpdateAmountDialog.UpdateAmountDelegate() {
                    @Override
                    public void onUpdateAmount(String amount) {
                        tvAmount.setText(amount);
                    }
                });
                break;
            case R.id.btn_scan_code:
                new IntentIntegrator(this)
                        .setCaptureActivity(ScannerActivity.class)
                        .initiateScan();
                break;
            case R.id.btn_save:
                long inputAmount = 0;
                if (tvAmount.getText() != null && !tvAmount.getText().toString().equals("")
                        && Double.parseDouble(tvAmount.getText().toString()) != 0) {
                    inputAmount = Long.parseLong(tvAmount.getText().toString());
                    Intent intent = new Intent();
                    intent.putExtra("amount", inputAmount);
                    if (isCash) {
                        intent.putExtra("type", CommonConstants.TYPE_CASH);
                    } else {
                        intent.putExtra("type", CommonConstants.TYPE_ELOAD);
                    }


                    if (isCash) {
                        long currentCashAmount = ConfigUtils.getInstance().loadCurrentCashAmount();
                        if (inputAmount <= currentCashAmount) {
                            if (isCodeCheck) {
                                setResult(1000, intent);
                                ConfigUtils.getInstance().saveUsedCashAmount(ConfigUtils.getInstance().loadUsedCashAmount() + inputAmount);
                                ConfigUtils.getInstance().saveCurrentCashAmount(currentCashAmount - inputAmount);
                                ConfigUtils.getInstance().saveCurrentEloadAmount(
                                        ConfigUtils.getInstance().loadCurrentEloadAmount() + inputAmount
                                );
                                super.onBackPressed();
                            } else {
                                ScreenUtils.getInstance().showToast(this, "Scan QR code!");
                            }
                        } else {
                            ScreenUtils.getInstance().showToast(this, "Not Enough Cash!");
                        }
                    } else {
                        long currentEloadAmount = ConfigUtils.getInstance().loadCurrentEloadAmount();
                        if (inputAmount <= currentEloadAmount) {
                            if (isCodeCheck) {
                                setResult(1000, intent);
                                ConfigUtils.getInstance().saveUsedEloadAmount(ConfigUtils.getInstance().loadUsedEloadAmount() + inputAmount);
                                ConfigUtils.getInstance().saveCurrentEloadAmount(currentEloadAmount - inputAmount);
                                ConfigUtils.getInstance().saveCurrentCashAmount(
                                        ConfigUtils.getInstance().loadCurrentCashAmount() + inputAmount
                                );
                                super.onBackPressed();
                            } else {
                                ScreenUtils.getInstance().showToast(this, "Scan QR code!");
                            }
                        } else {
                            ScreenUtils.getInstance().showToast(this, "Not Enough E-load!");
                        }
                    }
                } else {
                    ScreenUtils.getInstance().showToast(this, "Enter amount!");
                }
                break;
            case R.id.btn_cancel:
                super.onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                isCodeCheck = false;
                ivCodeCheck.setImageDrawable(getResources().getDrawable(R.drawable.ic_error_24dp));
            } else {
                isCodeCheck = true;
                ivCodeCheck.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_24dp));
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
