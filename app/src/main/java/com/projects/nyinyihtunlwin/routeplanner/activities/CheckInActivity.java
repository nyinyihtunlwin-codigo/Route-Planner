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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.projects.nyinyihtunlwin.routeplanner.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckInActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String KEY_LOCATION_NAME = "KEY_LOCATION_NAME";

    public static final Intent newIntent(Context context, String routerId) {
        Intent intent = new Intent(context, CheckInActivity.class);
        intent.putExtra(KEY_LOCATION_NAME, routerId);
        return intent;
    }

    @BindView(R.id.btn_scan_code)
    Button btnScanCode;

    @BindView(R.id.tip_amount)
    TextInputLayout tipAmount;

    @BindView(R.id.iv_code_check)
    ImageView ivCodeCheck;

    @BindView(R.id.btn_cancel)
    Button btnCancel;

    @BindView(R.id.btn_save)
    Button btnSave;

    @BindView(R.id.tv_name_location)
    TextView tvLocationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        ButterKnife.bind(this, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnScanCode.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_scan_code:
                new IntentIntegrator(this)
                        .setCaptureActivity(ScannerActivity.class)
                        .initiateScan();
                break;
            case R.id.btn_save:
                setResult(1000);
                super.onBackPressed();
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
            } else {
                Log.e("Result : ", result.getContents());
                ivCodeCheck.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_24dp));
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
