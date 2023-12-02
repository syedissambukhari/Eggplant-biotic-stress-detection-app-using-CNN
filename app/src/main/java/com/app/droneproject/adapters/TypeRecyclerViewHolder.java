package com.app.droneproject.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.droneproject.R;
import com.facebook.drawee.view.SimpleDraweeView;


public class TypeRecyclerViewHolder extends RecyclerView.ViewHolder {

    SimpleDraweeView ivImage;
    SimpleDraweeView ivCapture;
    TextView tvPlant;
    CardView cardView;

    public TypeRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        ivImage = itemView.findViewById(R.id.iv_image);
        ivCapture = itemView.findViewById(R.id.iv_capture);
        tvPlant = itemView.findViewById(R.id.tv_plant_name);
        cardView = itemView.findViewById(R.id.cv);
    }

}


