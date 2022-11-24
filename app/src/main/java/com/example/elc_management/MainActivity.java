package com.example.elc_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView Tv_new_student;
    TextView Tv_new_class;
    TextView Tv_view_class;
    SearchView Edt_home_searchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mapping();

        Tv_new_student.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
            startActivity(intent);
        });

        Tv_new_class.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CreateNewClassActivity.class);
            startActivity(intent);
        });

        Tv_view_class.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ViewClassListActivity.class);
            startActivity(intent);
        });

        Edt_home_searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

    }

    public void mapping() {
        Tv_new_student = findViewById(R.id.tv_new_student);
        Tv_new_class = findViewById(R.id.tv_new_class);
        Tv_view_class = findViewById(R.id.tv_view_class);

        Edt_home_searchBar = findViewById(R.id.edt_home_searchBar);
    }
}