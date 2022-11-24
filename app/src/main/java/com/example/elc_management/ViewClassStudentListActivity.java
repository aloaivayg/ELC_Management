package com.example.elc_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.elc_management.adapter.StudentAdapter;
import com.example.elc_management.object.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewClassStudentListActivity extends AppCompatActivity {
    private List<Student> list;

    private DatabaseReference mDatabase;


    private RecyclerView Rv_class_student;

    private Intent intentCaller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_class_student_list);

        Rv_class_student = findViewById(R.id.rv_class_student);
        mDatabase = FirebaseDatabase.getInstance("https://elc-management-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("students");

        intentCaller = getIntent();
        String class_name = intentCaller.getStringExtra("className");
        readAllData(class_name);
    }

    private void readAllData(String className) {
        mDatabase.orderByChild("sClass").equalTo(className).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list = new ArrayList<>();
                    for (DataSnapshot sn : snapshot.getChildren()) {
                        list.add(sn.getValue(Student.class));
                    }
                }
//                set recyclerview
                StudentAdapter adapter = new StudentAdapter(ViewClassStudentListActivity.this, list);
                Rv_class_student.setAdapter(adapter);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ViewClassStudentListActivity.this);
                Rv_class_student.setLayoutManager(layoutManager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
