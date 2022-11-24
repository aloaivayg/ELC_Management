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
import com.example.elc_management.ProfileActivity;
import com.example.elc_management.dao.StudentDao;
import com.example.elc_management.object.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentViewHolder> {
    private List<Student> studentList;
    private LayoutInflater layoutInflater;
    private Context context;

    StudentDao studentDao;

    public StudentAdapter(Context context, List<Student> studentList) {
        this.studentList = studentList;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        studentDao = new StudentDao("");
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recyclerViewItem = layoutInflater.inflate(R.layout.student_item, parent, false);
        recyclerViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickHandler((RecyclerView) parent, view);

            }
        });
        return new StudentViewHolder(recyclerViewItem);
    }

    private void clickHandler(RecyclerView recyclerView, View view) {
        int itemPosition = recyclerView.getChildLayoutPosition(view);
        Student student = this.studentList.get(itemPosition);

        Toast.makeText(this.context, student.getName(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra("student",student);
        context.startActivity(intent);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.sclass.setText(student.getsClass());
        holder.sname.setText(student.getName());
        holder.sPhone.setText(student.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

}
