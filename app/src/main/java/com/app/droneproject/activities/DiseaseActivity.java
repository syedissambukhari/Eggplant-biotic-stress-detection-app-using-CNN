package com.app.droneproject.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.droneproject.R;
import com.app.droneproject.adapters.TypeRecyclerViewAdapter;
import com.app.droneproject.info.Info;
import com.app.droneproject.mvvm.MvvmUtils;
import com.app.droneproject.mvvm.pojos.Super;
import com.app.droneproject.mvvm.pojos.response.Disease;
import com.app.droneproject.mvvm.mapping_utils.GenericResponse;

import java.util.ArrayList;
import java.util.List;

public class DiseaseActivity extends AppCompatActivity {

    public static Disease selectedDisease;
    RecyclerView recyclerView;
    TypeRecyclerViewAdapter recyclerViewAdapter;
    List<Super> superList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease);
        initViews();
        initPlants();
        initRv();

    }

    private void initRv() {
        superList = new ArrayList<>();
        recyclerViewAdapter =
                new TypeRecyclerViewAdapter(this, superList, Info.RV_TYPE_DISEASE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.rv_disease);
    }

    private void initPlants() {
        MvvmUtils.getViewModelRepo(this)
                .getDisease().observe(this, this::initResponse);
    }

    private void initResponse(GenericResponse<List<Disease>> listGenericResponse) {
        Log.i("TAG", "initResponse: ");
        if (!listGenericResponse.isSuccessful())
            return;
        superList.clear();
        List<Disease> plantList = listGenericResponse.getResponse();
        superList.addAll(plantList);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    public void back(View view) {
        finish();
    }
}