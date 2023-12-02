package com.app.droneproject.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.app.droneproject.R;
import com.app.droneproject.utils.DialogUtils;
import com.app.droneproject.utils.GoogleSignInUtils;
import com.app.droneproject.utils.Utils;
import com.app.droneproject.info.Info;
import com.app.droneproject.mvvm.MvvmUtils;
import com.app.droneproject.mvvm.pojos.request.PostLoginPojo;
import com.app.droneproject.mvvm.pojos.response.RegResponse;
import com.app.droneproject.mvvm.mapping_utils.GenericResponse;

import java.util.Objects;

public class LoginUserActivity extends AppCompatActivity implements Info {

    EditText etEmail;
    EditText etPass;
    String strEtEmail;
    String strEtPass;

    Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        castViews();
        loadingDialog = new Dialog(this);
        DialogUtils.initLoadingDialog(loadingDialog);

    }

    private void castViews() {
        etEmail = findViewById(R.id.et_email);
        etPass = findViewById(R.id.et_pass);
    }

    public void signUp(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    public void signIn(View view) {
        castStrings();
        if (!isEverythingValid())
            return;
        PostLoginPojo postLoginPojo = new PostLoginPojo(strEtEmail, strEtPass);
        loadingDialog.show();
        MvvmUtils.getViewModelRepo(this)
                .postLogin(postLoginPojo).observe(this, this::initResponse);
    }

    private void initResponse(GenericResponse<RegResponse> regResponseGenericResponse) {
        loadingDialog.cancel();
        if (regResponseGenericResponse.isSuccessful())
            GoogleSignInUtils.initSignIn(this, regResponseGenericResponse);
        else
            MvvmUtils.printErrors(this, regResponseGenericResponse);
    }


    private boolean isEverythingValid() {
        if (!Utils.validEt(etEmail, strEtEmail))
            return false;
        return Utils.validEt(etPass, strEtPass);
    }

    private void castStrings() {
        strEtEmail = Objects.requireNonNull(etEmail.getText()).toString().trim();
        strEtPass = Objects.requireNonNull(etPass.getText()).toString().trim();
    }

}