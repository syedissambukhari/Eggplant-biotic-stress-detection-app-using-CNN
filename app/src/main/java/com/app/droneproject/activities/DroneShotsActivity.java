package com.app.droneproject.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.droneproject.R;
import com.app.droneproject.adapters.TypeRecyclerViewAdapter;
import com.app.droneproject.info.Info;
import com.app.droneproject.mvvm.MvvmUtils;
import com.app.droneproject.mvvm.pojos.Super;
import com.app.droneproject.mvvm.pojos.response.Capture;
import com.app.droneproject.mvvm.mapping_utils.GenericResponse;

import java.util.ArrayList;
import java.util.List;

public class DroneShotsActivity extends AppCompatActivity {

    RecyclerView rvDrone;

    TypeRecyclerViewAdapter recyclerViewAdapter;
    List<Super> superList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drone_shots);

        rvDrone = findViewById(R.id.rv_drone);

        initRv();
        initRvPhotos();
    }

    private void initRv() {
        superList = new ArrayList<>();
        recyclerViewAdapter =
                new TypeRecyclerViewAdapter(this, superList, Info.RV_TYPE_CAPTURES);
        rvDrone.setAdapter(recyclerViewAdapter);
    }

    private void initRvPhotos() {
        MvvmUtils.getViewModelRepo(this)
                .getCaptures(this).observe(this, this::initResponse);
    }

    private void initResponse(GenericResponse<List<Capture>> listGenericResponse) {
        if (!listGenericResponse.isSuccessful())
            return;
        superList.clear();
        List<Capture> plantList = listGenericResponse.getResponse();
        superList.addAll(plantList);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    public void back(View view) {
        finish();
    }
}