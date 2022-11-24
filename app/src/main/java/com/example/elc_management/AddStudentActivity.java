package com.example.elc_management;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.elc_management.dao.ClassDao;
import com.example.elc_management.dao.StudentDao;
import com.example.elc_management.object.Student;

import java.util.ArrayList;

public class AddStudentActivity extends AppCompatActivity {
    EditText Edt_name, Edt_dob, Edt_class, Edt_email, Edt_phone;
    Button Btn_add_submit, Btn_add_clear, Btn_add_cancel;
    Spinner Spin_gender;

    StudentDao studentDao;
    ClassDao classDao;

    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        mapping();

//        mDatabase = FirebaseDatabase.getInstance("https://elc-management-default-rtdb.asia-southeast1.firebasedatabase.app/")
//                                    .getReference();
        studentDao = new StudentDao("students");

        arrayAdapter = new ArrayAdapter(AddStudentActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, createGenderList());
        Spin_gender.setAdapter(arrayAdapter);

        Spin_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Btn_add_submit.setOnClickListener(view -> {

                String name = Edt_name.getText().toString();
                String gender = Spin_gender.getSelectedItem().toString();
                String dob = Edt_dob.getText().toString();
                String s_class = Edt_class.getText().toString();
                String email = Edt_email.getText().toString();
                String phone = Edt_phone.getText().toString();

                if (inputValidate(name, dob, s_class, phone)) {
                    addNewStudent(name, gender, dob, s_class,email, phone);
                }
        });

        Btn_add_clear.setOnClickListener(view -> clear());

        Btn_add_cancel.setOnClickListener(view -> finish());

    }

    public void mapping() {
        Edt_name = (EditText) findViewById(R.id.edt_std_name);
        Spin_gender = findViewById(R.id.spin_std_gender);
        Edt_dob = (EditText) findViewById(R.id.edt_std_dob);
        Edt_class = (EditText) findViewById(R.id.edt_std_class);
        Edt_email = (EditText) findViewById(R.id.edt_std_email);
        Edt_phone = (EditText) findViewById(R.id.edt_std_pnumber);

        Btn_add_submit = findViewById(R.id.btn_add_submit);
        Btn_add_clear = findViewById(R.id.btn_add_clear);
        Btn_add_cancel = findViewById(R.id.btn_add_cancel);
    }

    private boolean inputValidate(String name, String dob, String sclass, String phone) {
        boolean isValid = true;
        //name
        if (TextUtils.isEmpty(name)) {
            Edt_name.setError("Required");
            isValid = false;
        } else {
            String regex = "^[a-zA-Z]+$";
            if (!Edt_name.getText().toString().matches(regex)) {
                Edt_name.setError("Name contains only letters");
                isValid = false;
            }
        }

        //dob
        if (TextUtils.isEmpty(dob)) {
            Edt_dob.setError("Required");
            isValid = false;
        }
        //class
        if (TextUtils.isEmpty(sclass)) {
            Edt_class.setError("Required");
            isValid = false;
        }
        //phone number
        if (TextUtils.isEmpty(phone)) {
            Edt_phone.setError("Required");
            isValid = false;
        }
        return isValid;
    }

    private void addNewStudent(String studentName, String studentGender, String studentDob, String studentClass, String studentEmail, String studentPnumber) {

        Student student = new Student(studentName, studentGender, studentDob, studentClass, studentPnumber);
        student.setEmail(studentEmail);

        studentDao.saveStudentToDb(student, AddStudentActivity.this);

    }

    private ArrayList<String> createGenderList() {
        final ArrayList<String> gender = new ArrayList<>();
        gender.add("Male");
        gender.add("Female");
        gender.add("Other");

        return gender;
    }

    private void clear() {
        Edt_name.setText("");
        Edt_phone.setText("");
        Edt_class.setText("");
        Edt_dob.setText("");
        Spin_gender.setSelected(false);
        Edt_email.setText("");
        Edt_name.requestFocus();
    }

}