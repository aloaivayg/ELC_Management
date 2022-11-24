package com.example.elc_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    EditText Edt_username, Edt_password;
    CheckBox Chb_remember_login;
    Button Btn_login;
    TextView Tv_forget_pw;
    TextView Tv_new_account;

    //firebase
    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    //remember account
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String USERNAME_KEY = "user";
    String PASSWORD_KEY = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mapping();

        mAuth = FirebaseAuth.getInstance();

        sharedPreferences = getSharedPreferences("LoginShPr", MODE_PRIVATE);
        Edt_username.setText(sharedPreferences.getString(USERNAME_KEY, ""));
        Edt_password.setText(sharedPreferences.getString(PASSWORD_KEY, ""));

        Btn_login.setOnClickListener(view -> {
            String username = Edt_username.getText().toString();
            String password = Edt_password.getText().toString();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            }
            else {
                logIn(username, password);
            }
        });

        Tv_new_account.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, AccountRegisterActivity.class);
            startActivity(intent);
        });

        Tv_forget_pw.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
            startActivity(intent);
        });
    }

    private void mapping() {
        //login_activity
        Edt_username = (EditText) findViewById(R.id.edt_username);
        Edt_password = (EditText) findViewById(R.id.edt_password);

        Btn_login = findViewById(R.id.btn_login);

        Chb_remember_login = findViewById(R.id.chb_remember_login);
        Tv_forget_pw = findViewById(R.id.tv_forget_pw);
        Tv_new_account = findViewById(R.id.tv_new_account);

    }

    private void logIn(String email, String password) {

        String user = Edt_username.getText().toString().trim();
        String pwd = Edt_password.getText().toString();

        mAuth.signInWithEmailAndPassword(email, encryptThisString(password))
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Successful", Toast.LENGTH_SHORT).show();

                            if (Chb_remember_login.isChecked()) {
                                editor = sharedPreferences.edit();
                                editor.putString(USERNAME_KEY, user);
                                editor.putString(PASSWORD_KEY, pwd);
                                editor.commit();
                            }

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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