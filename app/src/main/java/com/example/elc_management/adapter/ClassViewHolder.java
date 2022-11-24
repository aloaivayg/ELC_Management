package com.example.elc_management.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elc_management.R;

public class ClassViewHolder extends RecyclerView.ViewHolder{
    public TextView cid, cquantity;

    public ClassViewHolder(@NonNull View itemView) {
        super(itemView);
        cid = itemView.findViewById(R.id.tv_class_id);
        cquantity = itemView.findViewById(R.id.tv_class_quantity);
    }

}
