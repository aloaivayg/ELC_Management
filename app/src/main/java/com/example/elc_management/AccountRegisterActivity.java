package com.example.elc_management;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.example.elc_management.dao.StudentDao;
import com.example.elc_management.object.Account;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AccountRegisterActivity extends AppCompatActivity {

    EditText Edt_register_email, Edt_register_password, Edt_register_username, Edt_register_phone, Edt_register_nick;
    Button Btn_register_submit, Btn_register_clear, Btn_register_cancel;

    StudentDao studentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_register);

        mapping();

        studentDao = new StudentDao("user");


        Btn_register_submit.setOnClickListener(view -> {
            String username = Edt_register_username.getText().toString();
            String password = Edt_register_password.getText().toString();
            String phone = Edt_register_phone.getText().toString();
            String email = Edt_register_email.getText().toString();
            String nick = Edt_register_nick.getText().toString();

            if (inputValidate(username, password, email, phone)) {
                String encryptPwd = encryptThisString(password);
                saveAccountToDb(username, username, encryptPwd, phone, email, nick);
            }
        });

        Btn_register_clear.setOnClickListener(view -> {
            clear();
        });

        Btn_register_cancel.setOnClickListener(view -> finish());
    }


    private boolean inputValidate(String username, String password, String email, String phone) {
        boolean isValid = true;
        //un
        if (TextUtils.isEmpty(username)) {
            Edt_register_username.setError("Required");
            isValid = false;
        }
        //pwd
        if (TextUtils.isEmpty(password)) {
            Edt_register_password.setError("Required");
            isValid = false;
        } else {
            String regex = "^(?=.*[0-9])"
                    + "(?=.*[a-z])(?=.*[A-Z])"
                    + "(?=.*[@#$%^&+=])"
                    + "(?=\\S+$).{8,20}$";
            if (!password.matches(regex)) {
                Edt_register_password.setError("Password must be at least 8 characters, " +
                        "contains at least a lowercase character, " +
                        "an uppercase character, " +
                        "a number and a symbol ");
                isValid = false;
            }
        }
        //
        if (TextUtils.isEmpty(email)) {
            Edt_register_email.setError("Required");
            isValid = false;
        }   else {
            String regex = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";
            if (!email.matches(regex)) {
                Edt_register_email.setError("Wrong email format");
                isValid = false;
            }
        }
        //phone
        if (TextUtils.isEmpty(phone)) {
            Edt_register_phone.setError("Required");
            isValid = false;
        }   else {
            String regex = "\\d{10}";
            if (!Edt_register_phone.getText().toString().matches(regex)) {
                Edt_register_phone.setError("Wrong phone format");
                isValid = false;
            }
        }
        return isValid;
    }

    private void clear() {
        Edt_register_username.setText("");
        Edt_register_password.setText("");
        Edt_register_phone.setText("");
        Edt_register_email.setText("");
        Edt_register_nick.setText("");
        Edt_register_username.requestFocus();
    }



    private void saveAccountToDb(String aId, String aUsername, String aPassword, String aPhoneNumber, String aEmail, String aNickname) {

        Account account = new Account(aId, aUsername, aPassword, aPhoneNumber, aEmail, aNickname);
        studentDao.saveAccountToDb(account, AccountRegisterActivity.this);

    }

    public void mapping() {
        Edt_register_email = (EditText) findViewById(R.id.edt_register_mail);
        Edt_register_password = (EditText) findViewById(R.id.edt_register_password);
        Edt_register_username = (EditText) findViewById(R.id.edt_register_username);
        Edt_register_phone = (EditText) findViewById(R.id.edt_register_phone);
        Edt_register_nick = (EditText) findViewById(R.id.edt_register_nick);

        Btn_register_submit = findViewById(R.id.btn_ar_submit);
        Btn_register_clear = findViewById(R.id.btn_ar_clear);
        Btn_register_cancel = findViewById(R.id.btn_ar_cancel);

    }

    public static String encryptThisString(String input) {
        try {
            // getInstance() method is called with algorithm SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            // return the HashText
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}