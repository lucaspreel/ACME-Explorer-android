package com.example.entregable1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button signInButtonGoogle;
    private Button signInButtonMail;
    private Button signInButtonRegister;
    private ProgressBar progressBar;
    private AutoCompleteTextView loginEmail;
    private AutoCompleteTextView loginPassword;
    private TextInputLayout loginEmailParent;
    private TextInputLayout loginPasswordParent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.login_progress);
        loginEmail = findViewById(R.id.login_email_et);
        loginPassword = findViewById(R.id.login_password_et);
        signInButtonGoogle = findViewById(R.id.login_button_google);
        signInButtonMail = findViewById(R.id.login_button_mail);
        signInButtonRegister = findViewById(R.id.login_button_register);
        loginEmailParent = findViewById(R.id.login_email);
        loginPasswordParent = findViewById(R.id.login_password);

        signInButtonMail.setOnClickListener(l -> attemptLoginEmail());

    }

    private void attemptLoginEmail() {
        loginEmail.setError(null);
        loginPassword.setError(null);

        if (loginEmail.getText().length() == 0) {
            loginEmailParent.setErrorEnabled(true);
            loginEmailParent.setError(getString(R.string.login_mail_error_1));
        } else if (loginPassword.getText().length() == 0) {
            loginPasswordParent.setErrorEnabled(true);
            loginPasswordParent.setError(getString(R.string.login_mail_error_2));
        } else {
            signInEmail();
        }
    }

    private void signInEmail() {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }

        if (mAuth != null) {
            mAuth.signInWithEmailAndPassword(
                    loginEmail.getText().toString(),
                    loginPassword.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (!task.isSuccessful() || task.getResult().getUser() == null) {
                            showErrorDialogMail();
                        } else if (!task.getResult().getUser().isEmailVerified()) {
                            showErrorEmailVerified(task.getResult().getUser());
                        } else {
                            FirebaseUser user = task.getResult().getUser();
                            checkUserDatabaseLogin(user);
                        }
                    });
        } else {
            showGooglePlayServicesError();
        }
    }

    private void showErrorDialogMail() {
        hideLogginButton(false);
        Snackbar.make(signInButtonMail, getString(R.string.login_mail_access_error), Snackbar.LENGTH_SHORT).show();
    }

    private void hideLogginButton(boolean hide) {
        TransitionSet transitionSet = new TransitionSet();
        Transition layoutFade = new AutoTransition();
        layoutFade.setDuration(1000);
        transitionSet.addTransition(layoutFade);

        if (hide) {
            TransitionManager.beginDelayedTransition(findViewById(R.id.login_layout), transitionSet);
            progressBar.setVisibility(View.VISIBLE);
            signInButtonMail.setVisibility(View.GONE);
            signInButtonGoogle.setVisibility(View.GONE);
            signInButtonRegister.setVisibility(View.GONE);
            loginPasswordParent.setEnabled(false);
            loginEmailParent.setEnabled(false);
        } else {
            TransitionManager.beginDelayedTransition(findViewById(R.id.login_layout), transitionSet);
            progressBar.setVisibility(View.GONE);
            signInButtonMail.setVisibility(View.VISIBLE);
            signInButtonGoogle.setVisibility(View.VISIBLE);
            signInButtonRegister.setVisibility(View.VISIBLE);
            loginPasswordParent.setEnabled(true);
            loginEmailParent.setEnabled(true);
        }
    }

    private void showErrorEmailVerified(FirebaseUser user) {
        hideLogginButton(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.login_verified_mail_error)
                .setPositiveButton(R.string.login_verified_mail_error_ok, ((dialog, wich) -> {
                    user.sendEmailVerification().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Snackbar.make(loginEmail, R.string.login_verified_mail_error_sent, Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(loginEmail, R.string.login_verified_mail_error_no_sent, Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }))
                .setNegativeButton(R.string.login_verified_mail_error_cancel, (dialog, wich) -> {}).show();
    }

    private void showGooglePlayServicesError() {
        Snackbar.make(signInButtonRegister, R.string.login_google_play_services_error, Snackbar.LENGTH_SHORT)
                .setAction(R.string.login_download_google_play_services, view -> {
                   try {
                       startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.google_play_services_download_url))));
                   } catch (Exception exception) {
                       startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.market_download_url))));
                   }
                });
    }

    private void checkUserDatabaseLogin(FirebaseUser user) {
        // To be implemented
    }
}