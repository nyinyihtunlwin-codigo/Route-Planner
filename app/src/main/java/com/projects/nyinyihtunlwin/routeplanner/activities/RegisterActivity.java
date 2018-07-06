package com.projects.nyinyihtunlwin.routeplanner.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.nyinyihtunlwin.routeplanner.R;
import com.projects.nyinyihtunlwin.routeplanner.utils.ConfigUtils;
import com.projects.nyinyihtunlwin.routeplanner.utils.ScreenUtils;

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
                String email = etEmail.getText().toString();
                String pass = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                if (!email.equals("")) {
                    if (ScreenUtils.getInstance().validateEmailAddress(email)) {
                        if (!pass.equals("")) {
                            if (!confirmPassword.equals("")) {
                                if (pass.equals(confirmPassword)) {
                                    ConfigUtils.getInstance().saveCurrentUser(email);
                                    Intent intentToHome = HomeActivity.newIntent(RegisterActivity.this);
                                    startActivity(intentToHome);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Passwords don't match.", Toast.LENGTH_SHORT).show();
                                    etConfirmPassword.setText("");
                                }
                            } else {
                                etConfirmPassword.setError("Confirm Password.");
                            }

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
            case R.id.tv_sign_in:
                Intent intentToLogin = LoginActivity.newIntent(RegisterActivity.this);
                startActivity(intentToLogin);
                break;
        }
    }
}
