package com.app.droneproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.droneproject.R;
import com.app.droneproject.utils.GoogleSignInUtils;
import com.app.droneproject.utils.SharedPrefUtils;
import com.app.droneproject.info.Info;
import com.app.droneproject.mvvm.MvvmUtils;
import com.app.droneproject.mvvm.pojos.request.SocialLoginPojo;
import com.app.droneproject.mvvm.pojos.response.RegResponse;
import com.app.droneproject.mvvm.mapping_utils.GenericResponse;

public class LoginActivity extends AppCompatActivity implements GoogleSignInUtils.SuccessListener, Info {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInUtils.configureGoogleSignIn(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GoogleSignInUtils.handleSignInResult(requestCode, data);
    }

    public void goToSignIn(View view) {
        startActivity(new Intent(this, LoginUserActivity.class));
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
        if (regResponseGenericResponse.isSuccessful())
            GoogleSignInUtils.initSignIn(this, regResponseGenericResponse);
        else
            MvvmUtils.printErrors(this, regResponseGenericResponse);
    }

}