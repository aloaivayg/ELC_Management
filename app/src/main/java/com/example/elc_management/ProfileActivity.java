package com.example.elc_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.elc_management.dao.StudentDao;
import com.example.elc_management.object.Student;

public class ProfileActivity extends AppCompatActivity {

    EditText Edt_profile_id, Edt_profile_name, Edt_profile_gender,Edt_profile_dob, Edt_profile_class, Edt_profile_phone,Edt_profile_mail;
    Button Btn_profile_submit, Btn_profile_back, Btn_profile_del;

    private Intent intentCaller;

    StudentDao studentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        studentDao = new StudentDao("students");

        mapping();

        Student student = getData();

        readData(student);

//        System.out.println(student.toString());
        Btn_profile_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = Edt_profile_name.getText().toString();
                String gender = Edt_profile_gender.getText().toString();
                String dob = Edt_profile_dob.getText().toString();
                String s_class = Edt_profile_class.getText().toString();
                String phone = Edt_profile_phone.getText().toString();
                String email = Edt_profile_mail.getText().toString();

                editStudentInfo(name, gender, dob, s_class, phone, email);
            }
        });

        Btn_profile_del.setOnClickListener(view -> {
            String id = Edt_profile_id.getText().toString();
            deleteStudent(id);
            finish();
        });

        Btn_profile_back.setOnClickListener(view -> finish());

    }

    public void mapping() {

        Edt_profile_id = (EditText) findViewById(R.id.edt_profile_id);
        Edt_profile_name = (EditText) findViewById(R.id.edt_profile_name);
        Edt_profile_gender = (EditText) findViewById(R.id.edt_profile_gender);
        Edt_profile_dob = (EditText) findViewById(R.id.edt_profile_dob);
        Edt_profile_class = (EditText) findViewById(R.id.edt_profile_class);
        Edt_profile_phone = (EditText) findViewById(R.id.edt_profile_phone);
        Edt_profile_mail = (EditText) findViewById(R.id.edt_profile_email);

        Btn_profile_submit = findViewById(R.id.btn_profile_submit);
        Btn_profile_back = findViewById(R.id.btn_profile_back);
        Btn_profile_del = findViewById(R.id.btn_profile_del);
    }

    public void readData(Student student) {
        Edt_profile_id.setText(student.getsId());
        Edt_profile_name.setText(student.getName());
        Edt_profile_gender.setText(student.getGender());
        Edt_profile_dob.setText(student.getDob());
        Edt_profile_class.setText(student.getsClass());
        Edt_profile_phone.setText(student.getPhoneNumber());
        Edt_profile_mail.setText(student.getEmail());
    }

    private Student getData() {
        intentCaller = getIntent();
        Student student = intentCaller.getParcelableExtra("student");
        System.out.println(student.getPhoneNumber());
        return student;
    }

    private void editStudentInfo(String studentName, String studentGender, String studentDob, String studentClass, String studentPnumber, String studentEmail) {

        Student student = new Student(studentName, studentGender, studentDob, studentClass, studentPnumber);
        student.setsId(Edt_profile_id.getText().toString());
        studentDao.updateStudent(student, studentEmail);

        Toast.makeText(this, "Successful", Toast.LENGTH_LONG).show();
    }

    private void deleteStudent(String id) {
        studentDao.deleteStudent(id)
                .addOnSuccessListener(unused -> Toast.makeText(ProfileActivity.this, "Successful", Toast.LENGTH_LONG).show())
                .addOnFailureListener(e -> Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show());
    }

}