package com.example.elc_management.dao;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.elc_management.object.Account;
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

public class StudentDao {
    DatabaseReference database;
    FirebaseAuth auth;

    ClassDao classDao;
    public StudentDao(String database) {
        this.database = FirebaseDatabase.getInstance("https://elc-management-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference(database);
        this.auth = FirebaseAuth.getInstance();
    }

    public void saveStudentToDb(Student student, Context context) {
        classDao = new ClassDao("classes");

        database.orderByChild("phoneNumber").equalTo(student.getPhoneNumber())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Toast.makeText(context, "Student existed", Toast.LENGTH_SHORT).show();
                        } else {
                            String key = database.child("").push().getKey();
                            student.setsId(key);
                            database.child(key).setValue(student);
                            Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();

                            //update class's student quantity
                            classDao.updateClassQuantity(student);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    public void saveAccountToDb(Account account, Context context) {
        //if == student.phone && !exist in user -> create acc
        DatabaseReference database1 = FirebaseDatabase.getInstance("https://elc-management-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("students");

        database1.orderByChild("phoneNumber").equalTo(account.getaPhoneNumber())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            database.orderByChild("aPhoneNumber").equalTo(account.getaPhoneNumber())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                Toast.makeText(context, "Phone number already assigned to an account", Toast.LENGTH_SHORT).show();
                                            } else {
                                                String key = database.child("").push().getKey();
                                                account.setaId(key);
                                                database.child(key).setValue(account);
                                                registerUser(account.getaEmail(), account.getaPassword());
                                                Toast.makeText(context, "You have created an account", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                        } else {
                            Toast.makeText(context, "Enter registered phone number", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    public void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password);
    }

    public Task<Void> forgetPass(String email) {
        return auth.sendPasswordResetEmail(email);
    }

    public void updateStudent(Student student, String studentEmail) {

        student.setEmail(studentEmail);
        System.out.println(student.getsId());
        Map<String, Object> studentInfo = student.toMap();

        database.child(student.getsId()).updateChildren(studentInfo);

    }

    public Task<Void> deleteStudent(String id) {
        return database.child(id).removeValue();
    }

}
