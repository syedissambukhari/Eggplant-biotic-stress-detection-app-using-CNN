package com.app.droneproject.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.droneproject.R;
import com.app.droneproject.utils.DialogUtils;
import com.app.droneproject.utils.GoogleSignInUtils;
import com.app.droneproject.utils.Utils;
import com.app.droneproject.info.Info;
import com.app.droneproject.mvvm.MvvmUtils;
import com.app.droneproject.mvvm.pojos.request.PostRegPojo;
import com.app.droneproject.mvvm.pojos.request.SocialLoginPojo;
import com.app.droneproject.mvvm.pojos.response.RegResponse;
import com.app.droneproject.mvvm.mapping_utils.GenericResponse;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity implements GoogleSignInUtils.SuccessListener, Info {

    EditText etFullName;
    EditText etPhoneNumber;
    EditText etEmail;
    EditText etPass;

    String strEtFullName;
    String strEtPhoneNumber;
    String strEtEmail;
    String strEtPass;
    Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initViews();
        GoogleSignInUtils.configureGoogleSignIn(this);
        loadingDialog = new Dialog(this);
        DialogUtils.initLoadingDialog(loadingDialog);

    }

    private void initViews() {
        etFullName = findViewById(R.id.et_full_name);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        etEmail = findViewById(R.id.et_email);
        etPass = findViewById(R.id.et_pass);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GoogleSignInUtils.handleSignInResult(requestCode, data);
    }

    public void signUp(View view) {
        castStrings();
        if (!isEverythingValid())
            return;

        PostRegPojo postRegPojo = new PostRegPojo(
                strEtFullName,
                "-",
                strEtFullName + System.currentTimeMillis(),
                strEtEmail,
                strEtPass,
                strEtPass,
                strEtPhoneNumber);

        loadingDialog.show();
        MvvmUtils.getViewModelRepo(this)
                .postAuth(postRegPojo).observe(this, this::initResponse);
    }

    private boolean isEverythingValid() {
        if (!Utils.validEt(etFullName, strEtFullName))
            return false;
        if (!Utils.validEt(etPhoneNumber, strEtPhoneNumber))
            return false;
        if (!Utils.validEt(etEmail, strEtEmail))
            return false;
        return Utils.validEt(etPass, strEtPass);
    }

    private void castStrings() {
        strEtEmail = Objects.requireNonNull(etEmail.getText()).toString().trim();
        strEtPass = Objects.requireNonNull(etPass.getText()).toString().trim();
        strEtFullName = Objects.requireNonNull(etFullName.getText()).toString().trim();
        strEtPhoneNumber = Objects.requireNonNull(etPhoneNumber.getText()).toString().trim();
    }


    public void goToSignIn(View view) {
        finish();
    }

    public void continueWithGoogle(View view) {
        GoogleSignInUtils.continueWithGoogle(this);
    }

    @Override
    public void onSuccess(String authCode, String idToken) {
        SocialLoginPojo socialLoginPojo = new SocialLoginPojo("", idToken, authCode);
        MvvmUtils.getViewModelRepo(this)
                .postGoogleCode(socialLoginPojo)
                .observe(this, this::initResponse);
    }

    private void initResponse(GenericResponse<RegResponse> regResponseGenericResponse) {
        Log.i(TAG, "initResponse: " + regResponseGenericResponse.getRawResponse().raw());
        if (regResponseGenericResponse.getResponseCode() == 201) {
            loadingDialog.dismiss();
            finish();
            return;
        }
        loadingDialog.dismiss();

        if (regResponseGenericResponse.isSuccessful()) {
            GoogleSignInUtils.initSignIn(this, regResponseGenericResponse);
        } else {
            MvvmUtils.printErrors(this, regResponseGenericResponse);
            finish();
        }
    }
}