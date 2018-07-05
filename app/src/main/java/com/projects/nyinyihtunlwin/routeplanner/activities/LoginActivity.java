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
import com.projects.nyinyihtunlwin.routeplanner.utils.ConfigUtils;
import com.projects.nyinyihtunlwin.routeplanner.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    public static final Intent newIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.btn_sign_in)
    Button btnSignIn;

    @BindView(R.id.tv_sign_up)
    TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (!ConfigUtils.getInstance().loadCurrentUser().equals("")) {
            Intent intentToHome = HomeActivity.newIntent(LoginActivity.this);
            startActivity(intentToHome);
        }

        // Initialize butterknife
        ButterKnife.bind(this, this);

        btnSignIn.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_in:
                String email = etEmail.getText().toString();
                String pass = etPassword.getText().toString();
                if (!email.equals("")) {
                    if (ScreenUtils.getInstance().validateEmailAddress(email)) {
                        if (!pass.equals("")) {
                            ConfigUtils.getInstance().saveCurrentUser(email);
                            Intent intentToHome = HomeActivity.newIntent(LoginActivity.this);
                            startActivity(intentToHome);
                        } else {
                            etPassword.setError("Enter password.");
                        }
                    } else {
                        etEmail.setError("Invalid email.");
                    }
                } else {
                    etEmail.setError("Enter email.");
                }
                break;
            case R.id.tv_sign_up:
                Intent intentToRegister = RegisterActivity.newIntent(LoginActivity.this);
                startActivity(intentToRegister);
                break;
        }
    }
}
