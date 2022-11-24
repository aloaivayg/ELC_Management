package com.example.elc_management;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.elc_management.dao.StudentDao;

public class ForgetPasswordActivity extends AppCompatActivity {
    EditText Edt_forgot_email;
    Button Btn_forgot_submit, Btn_forgot_clear, Btn_forgot_cancel;

    StudentDao studentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mapping();

        studentDao = new StudentDao("");

        Btn_forgot_submit.setOnClickListener(view -> {
            String email = Edt_forgot_email.getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(ForgetPasswordActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
            }  else {
                forgetPass(email);
            }
        });

        Btn_forgot_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                Edt_forgot_email.requestFocus();
            }
        });

        Btn_forgot_cancel.setOnClickListener(view -> finish());
    }

    public void mapping() {
        Edt_forgot_email = (EditText) findViewById(R.id.edt_forgot_email);

        Btn_forgot_submit = findViewById(R.id.btn_ar_submit);
        Btn_forgot_clear = findViewById(R.id.btn_ar_clear);
        Btn_forgot_cancel = findViewById(R.id.btn_ar_cancel);
    }

    private void clear() {
        Edt_forgot_email.setText("");
    }


    private void forgetPass(String email) {
        studentDao.forgetPass(email).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(ForgetPasswordActivity.this, "Please check your email", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(ForgetPasswordActivity.this, "Error: "+ task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}