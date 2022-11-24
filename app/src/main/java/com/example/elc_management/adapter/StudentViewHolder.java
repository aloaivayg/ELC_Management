package com.example.elc_management.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elc_management.R;

public class StudentViewHolder extends RecyclerView.ViewHolder{
    public TextView sPhone, sclass, sname;

    public StudentViewHolder(@NonNull View itemView) {
        super(itemView);

        sclass = itemView.findViewById(R.id.tv_class);
        sname = itemView.findViewById(R.id.tv_name);
        sPhone = itemView.findViewById(R.id.tv_phone_number);
    }

}
