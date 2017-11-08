package com.example.administrador.taskstodo;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends AppCompatActivity{

 EditText et_email;
 EditText et_password;
 Button b_login;
 SharedPreferences prefs;
 String sPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = findViewById(R.id.emailLI_ET);
        et_password = findViewById(R.id.password_LIET);
        b_login = findViewById(R.id.loginB);

        prefs = getSharedPreferences("MyPreferencies", Context.MODE_PRIVATE);
         if (!prefs.getString("userDetails","").equalsIgnoreCase("")){
             Intent intent = new Intent(LoginActivity.this, MainActivity.class);
             startActivity(intent);
         }

        b_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                sPassword = et_password.getText().toString().trim();

                if("".equalsIgnoreCase(et_email.getText().toString().trim()) ||"".equalsIgnoreCase(sPassword)){
                    MainActivity.newToast(LoginActivity.this,getString(R.string.empty_fields));
                } else if (sPassword.length() < 6){
                    MainActivity.newToast(LoginActivity.this,getString(R.string.short_password));
                } else {

                    prefs = getSharedPreferences("MyPreferencies", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("userDetails", et_email.getText().toString() + sPassword);
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}

