package com.example.elc_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.SearchView;

import com.example.elc_management.adapter.ClassAdapter;
import com.example.elc_management.object.Classs;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewClassListActivity extends AppCompatActivity {
    private List<Classs> classList;

    private DatabaseReference mDatabase;

    private SearchView searchView;

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_class_list);

        recyclerView = findViewById(R.id.rv_class_list);
        searchView = findViewById(R.id.sv_class_searchbar);

        mDatabase = FirebaseDatabase.getInstance("https://elc-management-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("classes");
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
                public boolean onQueryTextChange(String c) {
                    search(ViewClassListActivity.this, c);
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
                    classList = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        classList.add(snapshot.getValue(Classs.class));
                    }
                }
                //set recyclerview
                ClassAdapter adapter = new ClassAdapter(context, classList);
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

    private void search(Context context, String str) {
        List<Classs> resultList = new ArrayList<>();
        for (Classs classs: classList) {
            if (classs.getcId().contains(str.toLowerCase())) {
                resultList.add(classs);
            }
        }
        ClassAdapter myAdapter = new ClassAdapter(context, resultList);
        recyclerView.setAdapter(myAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
    }

}