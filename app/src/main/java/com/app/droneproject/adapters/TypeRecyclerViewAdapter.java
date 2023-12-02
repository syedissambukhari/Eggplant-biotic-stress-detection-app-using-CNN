package com.app.droneproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.droneproject.R;
import com.app.droneproject.activities.DetailActivity;
import com.app.droneproject.activities.DiseaseActivity;
import com.app.droneproject.activities.PlantListActivity;
import com.app.droneproject.info.Info;
import com.app.droneproject.mvvm.pojos.Super;
import com.app.droneproject.mvvm.pojos.response.Capture;
import com.app.droneproject.mvvm.pojos.response.Disease;
import com.app.droneproject.mvvm.pojos.response.plant.Image;
import com.app.droneproject.mvvm.pojos.response.plant.Plant;

import java.util.List;


public class TypeRecyclerViewAdapter extends RecyclerView.Adapter<TypeRecyclerViewHolder> implements Info {

    private static final String TAG = "TAG";
    Context context;
    List<Super> listInstances;
    int type;

    public TypeRecyclerViewAdapter(Context context, List<Super> listInstances, int type) {
        this.context = context;
        this.listInstances = listInstances;
        this.type = type;
    }

    @NonNull
    @Override
    public TypeRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(context);
        View view;
        if (type == RV_TYPE_CAPTURES)
            view = li.inflate(R.layout.rv_captures, parent, false);
        else
            view = li.inflate(R.layout.rv_plants, parent, false);
        return new TypeRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TypeRecyclerViewHolder holder, int position) {
        if (type == RV_TYPE_CAPTURES)
            initCaptures(holder, position);
        else if (type == RV_TYPE_PLANT)
            initPlant(holder, position);
        else if (type == RV_TYPE_DISEASE)
            initDisease(holder, position);

    }

    private void initCaptures(TypeRecyclerViewHolder holder, int position) {
        Capture capture = (Capture) listInstances.get(position);
        holder.ivCapture.setImageURI(capture.getImage());
    }

    private void initDisease(TypeRecyclerViewHolder holder, int position) {
        Disease disease = (Disease) listInstances.get(position);
        Image image = null;
        for (Image image1 : disease.getImages()) {
            image = image1;
            break;
        }
        try {
            holder.ivImage.setImageURI(image != null ? image.getImage() : null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.tvPlant.setText(disease.getName());
        holder.cardView.setOnClickListener(view -> {
            DiseaseActivity.selectedDisease = disease;
            context.startActivity(new Intent(context, DetailActivity.class));
        });
    }

    private void initPlant(TypeRecyclerViewHolder holder, int position) {
        Plant plant = (Plant) listInstances.get(position);
        Image image = null;
        for (Image image1 : plant.getImages()) {
            image = image1;
            break;
        }
        try {
            assert image != null;
            holder.ivImage.setImageURI(image.getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.tvPlant.setText(plant.getName());
        holder.cardView.setOnClickListener(view -> {
            PlantListActivity.selectedPlant = plant;
            context.startActivity(new Intent(context, DetailActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return listInstances.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}
