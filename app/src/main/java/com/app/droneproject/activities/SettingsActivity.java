package com.app.droneproject.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.app.droneproject.R;
import com.app.droneproject.utils.GalleryUtils;
import com.app.droneproject.utils.ImageProcessor;
import com.app.droneproject.utils.Utils;
import com.app.droneproject.mvvm.MvvmUtils;
import com.app.droneproject.mvvm.pojos.response.UserProfile;
import com.app.droneproject.mvvm.mapping_utils.GenericResponse;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

public class SettingsActivity extends AppCompatActivity {
    public static File imageFile;
    EditText etPhone;
    EditText etUserName;
    EditText etEmail;
    EditText etFirstName;
    EditText etLastName;
    String strEtPhone;
    String strEtFirstName;
    String strEtLastName;
    CardView cvProfile;
    SimpleDraweeView iv_user_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        etPhone = findViewById(R.id.et_email3);
        etPhone.setText(Utils.userProfile.getPhoneNumber());
        etUserName = findViewById(R.id.et_username);
        etUserName.setText(Utils.userProfile.getUsername());
        etEmail = findViewById(R.id.et_email);
        etEmail.setText(Utils.userProfile.getEmail());
        etFirstName = findViewById(R.id.et_first_name);
        etFirstName.setText(Utils.userProfile.getFirstName());
        etLastName = findViewById(R.id.et_last_name);
        etLastName.setText(Utils.userProfile.getLastName());
        iv_user_profile = findViewById(R.id.iv_user_profile);
        iv_user_profile.setImageURI(Utils.userProfile.getProfileImage());
        cvProfile = findViewById(R.id.cv_profile_img);
        cvProfile.setOnClickListener(v -> GalleryUtils.openGallery(this));

    }

    public void back(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryUtils.GALLERY_REQUEST_CODE) {
            Bitmap bitmap = GalleryUtils.getBitmap(this, resultCode, data);
            imageFile = ImageProcessor.saveImageToGallery(this, bitmap);
            if (data != null)
                iv_user_profile.setImageURI(data.getData());
        }
    }

    public void save(View view) {
        strEtPhone = etPhone.getText().toString();
        strEtFirstName = etFirstName.getText().toString();
        strEtLastName = etLastName.getText().toString();

        initProfileUpdate();
    }

    private void initProfileUpdate() {
        MvvmUtils.getViewModelRepo(this).putProfile(this, imageFile,
                strEtFirstName, strEtLastName, strEtPhone)
                .observe(this, this::initPostProfileUpdate);
    }

    private void initPostProfileUpdate(GenericResponse<UserProfile> userProfileGenericResponse) {
        if (userProfileGenericResponse.getRawResponse().isSuccessful())
            Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
    }

}