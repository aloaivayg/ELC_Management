package com.example.elc_management.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elc_management.R;
import com.example.elc_management.ViewClassInfoActivity;
import com.example.elc_management.dao.ClassDao;
import com.example.elc_management.dao.StudentDao;
import com.example.elc_management.object.Classs;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassViewHolder> {
    private List<Classs> classList;
    private LayoutInflater layoutInflater;
    private Context context;

    StudentDao studentDao;
    ClassDao classDao;

    public ClassAdapter(Context context, List<Classs> classList) {
        this.classList = classList;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;

        classDao = new ClassDao("");
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recyclerViewItem = layoutInflater.inflate(R.layout.class_item, parent, false);
        recyclerViewItem.setOnClickListener(view -> clickHandler((RecyclerView) parent, view));
        return new ClassViewHolder(recyclerViewItem);
    }

    private void clickHandler(RecyclerView recyclerView, View view) {
        int itemPosition = recyclerView.getChildLayoutPosition(view);
        Classs classs = this.classList.get(itemPosition);

        Toast.makeText(this.context, classs.getcId(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(context, ViewClassInfoActivity.class);
        intent.putExtra("class",classs);
        context.startActivity(intent);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        Classs classs = classList.get(position);
        holder.cid.setText(classs.getcId());
        holder.cquantity.setText(String.valueOf(classs.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }
}
