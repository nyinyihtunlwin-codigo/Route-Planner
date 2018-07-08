package com.projects.nyinyihtunlwin.routeplanner.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.projects.nyinyihtunlwin.routeplanner.R;
import com.projects.nyinyihtunlwin.routeplanner.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BalanceRequestActivity extends BaseActivity {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, BalanceRequestActivity.class);
        return intent;
    }

    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @BindView(R.id.sp_cash_type)
    Spinner spCashType;

    @BindView(R.id.tip_amount)
    TextInputLayout tipAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_request);
        ButterKnife.bind(this, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tipAmount.getEditText() != null && !tipAmount.getEditText().getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Request Success!", Toast.LENGTH_LONG).show();
                } else {
                    ScreenUtils.getInstance().showToast(getApplicationContext(), "Enter Amount.");
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
