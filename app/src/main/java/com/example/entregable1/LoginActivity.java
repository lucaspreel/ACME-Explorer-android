package com.example.entregable1;

import androidx.annotation.Nullable;
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
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 0x152;
    private FirebaseAuth mAuth;
    private Button signInButtonGoogle;
    private Button signInButtonMail;
    private Button buttonRegister;
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
        buttonRegister = findViewById(R.id.login_button_register);
        loginEmailParent = findViewById(R.id.login_email);
        loginPasswordParent = findViewById(R.id.login_password);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_client_id))
                .requestEmail()
                .build();

        signInButtonGoogle.setOnClickListener(l -> attemptLoginGoogle(googleSignInOptions));

        signInButtonMail.setOnClickListener(l -> attemptLoginEmail());

        buttonRegister.setOnClickListener(l -> redirectRegisterActivity());

    }

    private void redirectRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra(RegisterActivity.EMAIL_PARAM, loginEmail.getText().toString());
        startActivity(intent);
    }

    private void attemptLoginGoogle(GoogleSignInOptions googleSignInOptions) {
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> result = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = result.getResult(ApiException.class);
                assert account != null;
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                if (mAuth == null) {
                    mAuth = FirebaseAuth.getInstance();
                }
                if (mAuth != null) {
                    mAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            checkUserDatabaseLogin(user);
                        } else {
                            showErrorDialogMail();
                        }
                    });
                } else {
                    showGooglePlayServicesError();
                }
            } catch (ApiException exception) {

            }
        }
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
            buttonRegister.setVisibility(View.GONE);
            loginPasswordParent.setEnabled(false);
            loginEmailParent.setEnabled(false);
        } else {
            TransitionManager.beginDelayedTransition(findViewById(R.id.login_layout), transitionSet);
            progressBar.setVisibility(View.GONE);
            signInButtonMail.setVisibility(View.VISIBLE);
            signInButtonGoogle.setVisibility(View.VISIBLE);
            buttonRegister.setVisibility(View.VISIBLE);
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
        Snackbar.make(buttonRegister, R.string.login_google_play_services_error, Snackbar.LENGTH_SHORT)
                .setAction(R.string.login_download_google_play_services, view -> {
                   try {
                       startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.google_play_services_download_url))));
                   } catch (Exception exception) {
                       startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.market_download_url))));
                   }
                });
    }

    private void checkUserDatabaseLogin(FirebaseUser user) {
        Toast.makeText(this, String.format(getString(R.string.login_completed)), Toast.LENGTH_SHORT).show();
    }
}