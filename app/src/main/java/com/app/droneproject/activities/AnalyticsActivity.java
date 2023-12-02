package com.app.droneproject.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.droneproject.R;
import com.app.droneproject.mvvm.MvvmUtils;
import com.app.droneproject.mvvm.pojos.response.Disease;
import com.app.droneproject.mvvm.pojos.response.plant.Plant;
import com.app.droneproject.mvvm.mapping_utils.GenericResponse;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class AnalyticsActivity extends AppCompatActivity {
    List<DataPoint> dataPoints;
    GraphView graph;
    List<Disease> diseases;
    TextView tv11;
    TextView tv12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        tv11 = findViewById(R.id.textView11);
        tv12 = findViewById(R.id.textView12);
        graph = (GraphView) findViewById(R.id.graph);

        dataPoints = new ArrayList<>();

        initDiseases();
    }

    public void back(View view) {
        finish();
    }

    private void initDiseases() {
        MvvmUtils.getViewModelRepo(this)
                .getDisease().observe(this, this::initResponse);
    }

    private void initResponse(GenericResponse<List<Disease>> listGenericResponse) {
        if (!listGenericResponse.isSuccessful())
            return;
        diseases = listGenericResponse.getResponse();
        initPlants();
    }

    private void initPlants() {
        MvvmUtils.getViewModelRepo(this)
                .getPlants().observe(this, this::initResponse2);
    }

    private void initResponse2(GenericResponse<List<Plant>> listGenericResponse) {
        if (!listGenericResponse.isSuccessful())
            return;
        List<Plant> plantList = listGenericResponse.getResponse();
        for (int i = 0; i < plantList.size(); i++) {
            dataPoints.add(new DataPoint(i, i * i * i));
        }

        tv11.setText(String.valueOf(plantList.size()));
        tv12.setText(String.valueOf(diseases.size()));

        DataPoint[] dataPoint = new DataPoint[dataPoints.size()];
        dataPoints.toArray(dataPoint);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoint);
        graph.addSeries(series);

    }
}