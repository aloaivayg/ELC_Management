package com.example.elc_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.elc_management.dao.ClassDao;
import com.example.elc_management.object.Classs;

public class ViewClassInfoActivity extends AppCompatActivity {
    EditText Edt_class_info_id, Edt_class_info_schedule, Edt_class_info_time, Edt_class_info_quantity;

    Button Btn_class_info_submit, Btn_class_info_del,Btn_class_info_back, Btn_class_info_view;

    private Intent intentCaller;
    ClassDao classDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_class_info);

        mapping();
        classDao = new ClassDao("classes");

        Classs classs = getData();
        readData(classs);
        Btn_class_info_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewClassInfoActivity.this, ViewClassStudentListActivity.class);
                intent.putExtra("className", Edt_class_info_id.getText().toString());
                startActivity(intent);
            }
        });

        Btn_class_info_del.setOnClickListener(view -> {
            String id = Edt_class_info_id.getText().toString();

            finish();
        });

        Btn_class_info_back.setOnClickListener(view -> finish());
    }

    public void mapping() {

        Edt_class_info_id = (EditText) findViewById(R.id.edt_class_info_id);
        Edt_class_info_schedule = (EditText) findViewById(R.id.edt_class_info_schedule);
        Edt_class_info_time = (EditText) findViewById(R.id.edt_class_info_time);
        Edt_class_info_quantity = (EditText) findViewById(R.id.edt_class_info_quantity);

        Btn_class_info_submit = findViewById(R.id.btn_class_info_submit);
        Btn_class_info_back = findViewById(R.id.btn_class_info_back);
        Btn_class_info_del = findViewById(R.id.btn_class_info_del);
        Btn_class_info_view = findViewById(R.id.btn_class_info_view);
    }

    public void readData(Classs classs) {
        Edt_class_info_id.setText(classs.getcId());
        Edt_class_info_schedule.setText(classs.getSchedule());
        Edt_class_info_time.setText(classs.getTime());
        Edt_class_info_quantity.setText(String.valueOf(classs.getQuantity()));

    }

    private Classs getData() {
        intentCaller = getIntent();
        Classs classs = intentCaller.getParcelableExtra("class");

        return classs;
    }

    private void deleteClass(String id) {
        classDao.deleteStudent(id)
                .addOnSuccessListener(unused -> Toast.makeText(ViewClassInfoActivity.this, "Successful", Toast.LENGTH_LONG).show())
                .addOnFailureListener(e -> Toast.makeText(ViewClassInfoActivity.this, e.getMessage(), Toast.LENGTH_LONG).show());
    }
}