package com.example.elc_management.dao;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.elc_management.object.Classs;
import com.example.elc_management.object.Student;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ClassDao {
    DatabaseReference database;
    FirebaseAuth auth;

    public ClassDao(String database) {
        this.database = FirebaseDatabase.getInstance("https://elc-management-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference(database);
        this.auth = FirebaseAuth.getInstance();
    }

    public void saveClassToDb(Classs classs, Context context) {
        database.orderByChild("cId").equalTo(classs.getcId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Toast.makeText(context, "Class existed", Toast.LENGTH_SHORT).show();
                        } else {
                            database.child(classs.getcId()).setValue(classs);
                            Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void updateClass(Classs classs) {

        System.out.println(classs.getcId());
        Map<String, Object> classInfo = classs.toMap();

        database.child(classs.getcId()).updateChildren(classInfo);
    }

    public void updateClassQuantity(Student student) {
        database.child(student.getsClass())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Classs classs = snapshot.getValue(Classs.class);
                            database.child(student.getsClass()).child("quantity").setValue(classs.getQuantity()+1);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public Task<Void> deleteStudent(String id) {
        return database.child(id).removeValue();
    }
}
