package com.example.entregable1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    public static final String EMAIL_PARAM = "email_parameter";

    private AutoCompleteTextView login_email_et;
    private AutoCompleteTextView login_password_et;
    private AutoCompleteTextView login_password_confirm_et;

    private TextInputLayout login_email;
    private TextInputLayout login_password;
    private TextInputLayout login_password_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        String emailParam = getIntent().getStringExtra(EMAIL_PARAM);

        login_email_et = findViewById(R.id.login_email_et);
        login_password_et = findViewById(R.id.login_password_et);
        login_password_confirm_et = findViewById(R.id.login_password_confirm_et);

        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_password_confirm = findViewById(R.id.login_password_confirm);

        login_email_et.setText(emailParam);

        findViewById(R.id.register_button).setOnClickListener(l -> {
            if (login_email_et.getText().length() == 0) {
                login_email.setErrorEnabled(true);
                login_email.setError(getString(R.string.register_error_user));
            } else if (login_password_et.getText().length() == 0) {
                login_password.setErrorEnabled(true);
                login_password.setError(getString(R.string.register_error_password));
            } else if (login_password_confirm_et.getText().length() == 0) {
                login_password_confirm.setErrorEnabled(true);
                login_password_confirm.setError(getString(R.string.register_error_password));
            } else if (!login_password_confirm_et.getText().toString().equals(login_password_et.getText().toString())) {
                login_password_confirm.setErrorEnabled(true);
                login_password_confirm.setError(getString(R.string.register_error_password_not_match));
            } else {
                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(login_email_et.getText().toString(), login_password_et.getText().toString())
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()) {
                                Toast.makeText(this, R.string.register_ok, Toast.LENGTH_SHORT).show();
                                RegisterActivity.this.finish();
                            } else {
                                Toast.makeText(this, R.string.register_error, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}