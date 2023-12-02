package com.app.droneproject.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.droneproject.R;
import com.app.droneproject.utils.SharedPrefUtils;
import com.app.droneproject.utils.Utils;
import com.app.droneproject.mvvm.MvvmUtils;
import com.app.droneproject.mvvm.interfaces.Urls;
import com.app.droneproject.mvvm.pojos.response.UserProfile;
import com.app.droneproject.mvvm.mapping_utils.GenericResponse;
import com.facebook.drawee.view.SimpleDraweeView;

public class DashboardActivity extends AppCompatActivity {

    SimpleDraweeView ivUserProfile;
    TextView tvUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        ivUserProfile = findViewById(R.id.iv_user_profile);
        tvUserName = findViewById(R.id.textView8);

        if (SharedPrefUtils.getToken(this).isEmpty()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        initUser();

    }

    private void initUser() {
        MvvmUtils.getViewModelRepo(this)
                .getProfile(this).observe(this, this::initResponse);
    }

    private void initResponse(GenericResponse<UserProfile> listGenericResponse) {
        if (listGenericResponse.isSuccessful()) {
            Utils.userProfile = listGenericResponse.getResponse();
            ivUserProfile.setImageURI(Utils.userProfile.getProfileImage());
            String Name = "Welcome Back " + Utils.userProfile.getUsername() + " !";
            tvUserName.setText(Name);
        } else {
            Toast.makeText(this, "An error occured please login again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    public void f1(View view) {
        startActivity(new Intent(this, PlantListActivity.class));
    }

    public void f2(View view) {
        startActivity(new Intent(this, DiseaseActivity.class));
    }

    public void f3(View view) {
        startActivity(new Intent(this, AnalyticsActivity.class));
    }

    public void f5(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    public void f6(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://192.168.219.39:5000"));
        startActivity(intent);
    }

    public void f9(View view) {
        startActivity(new Intent(this, DroneShotsActivity.class));
    }
}