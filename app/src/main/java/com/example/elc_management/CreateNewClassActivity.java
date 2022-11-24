package com.example.elc_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.elc_management.dao.ClassDao;
import com.example.elc_management.dao.StudentDao;
import com.example.elc_management.object.Classs;
import com.example.elc_management.object.DatePickerFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CreateNewClassActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText Edt_class_name, Edt_teacher;
    TextView Tv_class_schedule_start, Tv_class_schedule_end;
    Button Btn_class_schedule_start,Btn_class_schedule_end, Btn_class_submit, Btn_class_clear, Btn_class_cancel;
    Spinner Spin_time;


    StudentDao studentDao;
    ClassDao classDao;

    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_class);

        mapping();

        classDao = new ClassDao("classes");

        arrayAdapter = new ArrayAdapter(CreateNewClassActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, createTimeList());
        Spin_time.setAdapter(arrayAdapter);

        Btn_class_schedule_start.setOnClickListener(view -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });

        Btn_class_submit.setOnClickListener(view -> {

            String name = Edt_class_name.getText().toString();
            String schedule = Tv_class_schedule_start.getText().toString() + "-"
                    + Tv_class_schedule_end.getText().toString();
            String time = Spin_time.getSelectedItem().toString();
            createNewClass(name, schedule, time);
        });

        Btn_class_clear.setOnClickListener(view -> {
            clear();
        });

        Btn_class_cancel.setOnClickListener(view -> finish());
    }

    public void mapping() {
        Edt_class_name = (EditText) findViewById(R.id.etd_class_name);
        Tv_class_schedule_start = findViewById(R.id.tv_class_schedule_start);
        Tv_class_schedule_end = findViewById(R.id.tv_class_schedule_end);
        Spin_time = findViewById(R.id.spin_class_time);

        Btn_class_schedule_start = findViewById(R.id.btn_class_schedule_start);

        Btn_class_submit = findViewById(R.id.btn_class_submit);
        Btn_class_clear = findViewById(R.id.btn_class_clear);
        Btn_class_cancel = findViewById(R.id.btn_class_cancel);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);

        String startDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        Tv_class_schedule_start.setText(startDateString);

        c.set(Calendar.MONTH, month+3);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.DAY_OF_MONTH, day);
        String endDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        Tv_class_schedule_end.setText(endDateString);
    }

    public void createNewClass(String id, String schedule, String time) {
        Classs classs = new Classs(id, schedule, time);
        classDao.saveClassToDb(classs, CreateNewClassActivity.this);
    }

    private ArrayList<String> createTimeList() {
        final ArrayList<String> time = new ArrayList<>();
        time.add("Monday, Wednesday, Friday: 17h30-19h30");
        time.add("Monday, Wednesday, Friday: 19h40-21h10");
        time.add("Tuesday, Wednesday, Friday: 17h30-19h30");
        time.add("Tuesday, Wednesday, Friday: 19h40-21h10");
        return time;
    }
    private void clear() {
        Edt_class_name.setText("");
        Tv_class_schedule_start.setText("");
        Tv_class_schedule_end.setText("");

    }
}