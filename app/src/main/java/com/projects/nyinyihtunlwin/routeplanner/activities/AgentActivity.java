package com.projects.nyinyihtunlwin.routeplanner.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.projects.nyinyihtunlwin.routeplanner.R;
import com.projects.nyinyihtunlwin.routeplanner.utils.ConfigUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AgentActivity extends BaseActivity implements View.OnClickListener {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AgentActivity.class);
        return intent;
    }

    @BindView(R.id.btn_to_rq_balance)
    Button btnToReqBalance;

    @BindView(R.id.btn_generate_code)
    Button btnGenerateCode;

    @BindView(R.id.btn_logout)
    Button btnLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);

        ButterKnife.bind(this, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnToReqBalance.setOnClickListener(this);
        btnGenerateCode.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_to_rq_balance:
                Intent intentToBalanceRequest = BalanceRequestActivity.newIntent(AgentActivity.this);
                startActivity(intentToBalanceRequest);
                break;
            case R.id.btn_generate_code:
                Intent intentToGenerateCode = GenerateCodeActivity.newIntent(AgentActivity.this);
                startActivity(intentToGenerateCode);
                break;
            case R.id.btn_logout:
                ConfigUtils.getInstance().saveCurrentUser("");
                Intent intentToHome = LoginActivity.newIntent(AgentActivity.this);
                startActivity(intentToHome);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }
}
