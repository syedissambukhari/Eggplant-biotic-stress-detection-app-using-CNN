package com.app.droneproject.utils;

import android.widget.EditText;

import com.app.droneproject.mvvm.pojos.response.UserProfile;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Utils {
    public static UserProfile userProfile;

    public static boolean validEt(EditText etUserName, String strEtUserName) {
        if (strEtUserName.isEmpty()) {
            etUserName.setError("Field Empty");
            return false;
        }
        return true;
    }

    public static MultipartBody.Part requestBody(String str, String key) {
        RequestBody requestBody = RequestBody.create(
                str, MediaType.parse("text/plain"));
        return MultipartBody.Part.createFormData(key, str);
    }

    public static RequestBody fileRequest(File file, String first_name, String last_name, String phone) {
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                file);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("username", Utils.userProfile.getUsername());
        builder.addFormDataPart("first_name", first_name);
        builder.addFormDataPart("last_name", last_name);
        builder.addFormDataPart("phone_number", phone);
        builder.addFormDataPart("profile_image", file.getName(), fileReqBody);
        return builder.build();

    }

}
