package com.projects.nyinyihtunlwin.routeplanner.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.projects.nyinyihtunlwin.routeplanner.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    public static final Intent newIntent(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        return intent;
    }

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;

    @BindView(R.id.btn_sign_up)
    Button btnSignUp;

    @BindView(R.id.tv_sign_in)
    TextView tvSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this, this);

        btnSignUp.setOnClickListener(this);
        tvSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_up:
                break;
            case R.id.tv_sign_in:
                Intent intentToLogin = LoginActivity.newIntent(RegisterActivity.this);
                startActivity(intentToLogin);
                break;
        }
    }
}
