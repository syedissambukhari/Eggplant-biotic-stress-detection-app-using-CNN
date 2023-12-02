package com.app.droneproject.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.droneproject.R;
import com.app.droneproject.utils.Utils;
import com.app.droneproject.info.Info;
import com.facebook.drawee.view.SimpleDraweeView;

public class DetailActivity extends AppCompatActivity {

    TextView tvName;
    TextView tvDesc;
    SimpleDraweeView ivImage;

    boolean isPlant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvName = findViewById(R.id.tv_title);
        tvDesc = findViewById(R.id.tv_description);
        ivImage = findViewById(R.id.iv_image);

        ivImage.setImageURI(Utils.userProfile.getProfileImage());

        if (PlantListActivity.selectedPlant != null)
            isPlant = true;
        else if (DiseaseActivity.selectedDisease != null)
            isPlant = false;
        else {
            finish();
            return;
        }
        initViews();


    }


    private void initViews() {
        if (isPlant) {
            tvName.setText(PlantListActivity.selectedPlant.getName());
            tvDesc.setText(PlantListActivity.selectedPlant.getDescription());
            try {
                ivImage.setImageURI(String.valueOf(PlantListActivity.selectedPlant.getImages().get(0).getImage()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            tvName.setText(DiseaseActivity.selectedDisease.getName());
            tvDesc.setText(DiseaseActivity.selectedDisease.getDescription());
            try {
                ivImage.setImageURI(String.valueOf(DiseaseActivity.selectedDisease.getImages().get(0).getImage()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        PlantListActivity.selectedPlant = null;
        DiseaseActivity.selectedDisease = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PlantListActivity.selectedPlant = null;
        DiseaseActivity.selectedDisease = null;
    }

    public void viewMore(View view) {
        Intent browserIntent;
        String image = Info.SEARCH_BASE_URL;
        if (isPlant)
            image += PlantListActivity.selectedPlant.getName();
        else
            image += DiseaseActivity.selectedDisease.getName();

        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(image));
        startActivity(browserIntent);

    }

    public void back(View view) {
        finish();
    }
}