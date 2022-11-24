package com.example.elc_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.SearchView;

import com.example.elc_management.adapter.StudentAdapter;
import com.example.elc_management.object.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private List<Student> list;

    private DatabaseReference mDatabase;

    private SearchView searchView;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recycler_view);
        searchView = findViewById(R.id.sv_search_searchBar);

        mDatabase = FirebaseDatabase.getInstance("https://elc-management-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("students");

    }

    @Override
    protected void onStart() {
        super.onStart();

        readAllData(this);

        if (searchView!=null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(SearchActivity.this, s);
                    return true;
                }
            });
        }
    }

    private void readAllData(Context context) {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.exists()) {
                    list = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        list.add(snapshot.getValue(Student.class));
                    }
                }
                //set recyclerview
                StudentAdapter adapter = new StudentAdapter(context, list);
                recyclerView.setAdapter(adapter);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(layoutManager);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        };
        mDatabase.addValueEventListener(postListener);
    }

    //Doesnt find the first letter of the str for some reason??
    private void search(Context context, String str) {
        List<Student> resultList = new ArrayList<>();
        for (Student student: list) {
            if (student.getName().contains(str.toLowerCase())) {
                resultList.add(student);
            } else if (student.getsClass().contains(str.toLowerCase())) {
                System.out.println(student.getsClass());
                resultList.add(student);
            }
        }
        StudentAdapter myAdapter = new StudentAdapter(context, resultList);
        recyclerView.setAdapter(myAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
    }
}