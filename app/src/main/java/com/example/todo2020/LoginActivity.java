package com.example.todo2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText loginusername,loginPassword;
    Button loginBtn;

    DatabaseHelper db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);


        loginusername = (EditText)findViewById(R.id.login_username);
        loginPassword = (EditText)findViewById(R.id.login_password);
        loginBtn =(Button)findViewById(R.id.loginBtn);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = loginusername.getText().toString().trim();
                String pwd = loginPassword.getText().toString().trim();
                Boolean res = db.checkUser(user, pwd);
                if(res == true)
                {
                    Intent HomePage = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(HomePage);
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Login Error",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
