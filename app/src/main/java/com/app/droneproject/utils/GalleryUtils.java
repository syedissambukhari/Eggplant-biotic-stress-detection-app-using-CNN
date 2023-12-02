package com.app.droneproject.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.ActivityCompat;


import com.app.droneproject.info.Info;

import java.io.File;


public class GalleryUtils implements Info {

    public static int GALLERY_REQUEST_CODE = 9898;

    private static boolean isStoragePermissionGranted(Context context) {
        if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission is granted");
            return true;
        } else {
            Log.v(TAG, "Permission is revoked");
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            return false;
        }
    }

    public static void openGallery(Context context) {
        Log.i(TAG, "openGallery: ");
        if (!isStoragePermissionGranted(context))
            return;

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        ((Activity) context).startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    public static Bitmap getBitmap(Context context, int resultCode, Intent data) {
        if (data != null) {
            final Uri imageUri = data.getData();
            if (imageUri != null) {
                try {
                    return ImageProcessor.getImageFromResult(context, resultCode, data);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG, "onActivityResult: Exception : " + e.getMessage());
                }
            }
        }
        return null;
    }

    public static String getName(Intent data) {
        if (data != null)
            return new File(data.getData().getPath()).getName() + ".png";
        return "ProfileImage.png";
    }

}
