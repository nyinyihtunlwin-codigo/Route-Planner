package com.projects.nyinyihtunlwin.routeplanner.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.projects.nyinyihtunlwin.routeplanner.R;
import com.projects.nyinyihtunlwin.routeplanner.utils.CommonConstants;
import com.projects.nyinyihtunlwin.routeplanner.utils.ConfigUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    public static final Intent newIntent(Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        return intent;
    }

    @BindView(R.id.tv_cash)
    TextView tvCash;

    @BindView(R.id.tv_eload)
    TextView tvEload;

    @BindView(R.id.tv_current_user_email)
    TextView tvCurrentUserEmail;

    @BindView(R.id.tv_logout)
    TextView tvLogout;

    @BindView(R.id.tv_cash_available)
    TextView tvCashAvailable;

    @BindView(R.id.tv_cash_used)
    TextView tvCashUsed;

    @BindView(R.id.tv_eload_available)
    TextView tvEloadAvailable;

    @BindView(R.id.tv_eload_used)
    TextView tvEloadUsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Long avaiCash = ConfigUtils.getInstance().loadCurrentCashAmount();
        Long avaiEload = ConfigUtils.getInstance().loadCurrentEloadAmount();
        tvCurrentUserEmail.setText(ConfigUtils.getInstance().loadCurrentUser());
        tvCashAvailable.setText(String.valueOf(avaiCash));
        tvEloadAvailable.setText(String.valueOf(avaiEload));

        tvCash.setText(String.valueOf(CommonConstants.INI_CASH));
        tvEload.setText(String.valueOf(CommonConstants.INI_ELOAD));

        tvCashUsed.setText(String.valueOf(CommonConstants.INI_CASH - avaiCash));
        tvEloadUsed.setText(String.valueOf(CommonConstants.INI_ELOAD - avaiEload));

        tvLogout.setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_logout:
                ConfigUtils.getInstance().saveCurrentUser("");
                Intent intentToHome = LoginActivity.newIntent(ProfileActivity.this);
                startActivity(intentToHome);
                break;
        }
    }
}
