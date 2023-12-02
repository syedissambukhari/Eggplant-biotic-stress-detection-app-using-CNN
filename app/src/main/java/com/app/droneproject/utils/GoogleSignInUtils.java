package com.app.droneproject.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.app.droneproject.R;
import com.app.droneproject.activities.DashboardActivity;
import com.app.droneproject.info.Info;
import com.app.droneproject.mvvm.pojos.response.RegResponse;
import com.app.droneproject.mvvm.mapping_utils.GenericResponse;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;


public class GoogleSignInUtils implements Info {
    /**
     * STEP 1: Implement Success Listener in activity
     * STEP 2: Configure in onCreate
     * STEP 3:  Put this in onActivityResult
     * GoogleSignInUtils.handleSignInResult(requestCode, data);
     * <p>
     * <p>
     * STEP 4: initialize on click using - GoogleSignInUtils.continueWithGoogle(this);
     */


    public static String TAG = "GoogleSignIn";
    public static int GOOGLE_SIGN_IN = 99;
    static GoogleSignInOptions gso;
    static SuccessListener successListener;
    static Intent intent;
    static GoogleApiClient mGoogleApiClient;

    public static void configureGoogleSignIn(Context context) {
        successListener = (SuccessListener) context;
        String serverClientId = context.getString(R.string.default_web_client_id);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
                .requestIdToken(serverClientId)
                .requestServerAuthCode(serverClientId)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public static void continueWithGoogle(Context context) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        ((Activity) context).startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    public static void handleSignInResult(int requestCode, Intent data) {
        if (requestCode != GoogleSignInUtils.GOOGLE_SIGN_IN) {
            return;
        }
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

        if (result != null) {
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                String authCode;
                String idToken;
                if (acct != null) {
                    authCode = acct.getServerAuthCode();
                    idToken = acct.getIdToken();
                    Log.i(TAG, "onActivityResult: authCode : " + authCode);
                    Log.i(TAG, "onActivityResult: idToken : " + idToken);
                    initLogin(authCode, idToken);
                }
            } else {
                Log.i(TAG, "onActivityResult: " + result.getStatus());

            }
        }
    }

    private static void initLogin(String authCode, String idToken) {
        Log.i(TAG, "initLogin: " + idToken);
        Log.i(TAG, "initLogin: " + authCode);

        successListener.onSuccess(authCode, idToken);
    }

    public static void initSignIn(Activity context, GenericResponse<RegResponse> regResponseGenericResponse) {
        try {
            Toast.makeText(context, regResponseGenericResponse.getResponse().getSuccess(),
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String key = regResponseGenericResponse.getResponse().getKey();
        if (key.isEmpty()) {
            Toast.makeText(context, "Key not found please try again later", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPrefUtils.putStringSharedPrefs(context, key, TOKEN);
        context.startActivity(new Intent(context, DashboardActivity.class));
        context.finish();
    }

    public interface SuccessListener {
        void onSuccess(String authCode, String idToken);
    }

}
