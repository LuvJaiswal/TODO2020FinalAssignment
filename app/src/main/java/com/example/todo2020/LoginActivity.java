package com.example.todo2020;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText loginusername,loginPassword;
    Button loginBtn;

    private ProgressDialog loadingBar;
    DatabaseHelper db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);


        loginusername = (EditText)findViewById(R.id.login_username);
        loginPassword = (EditText)findViewById(R.id.login_password);
        loginBtn =(Button)findViewById(R.id.loginBtn);


        loadingBar = new ProgressDialog(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = loginusername.getText().toString().trim();
                String pwd = loginPassword.getText().toString().trim();
                Boolean res = db.checkUser(user, pwd);
                if (TextUtils.isEmpty(user)) {
                    Toast.makeText(LoginActivity.this, "please enter the verified username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    Toast.makeText(LoginActivity.this, "please enter the valid password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    loadingBar.setTitle("Login Account");
                    loadingBar.setMessage("Please wait while we are checking the credentials.");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                }

                if(res == true)
                {
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Intent HomePage = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(HomePage);
                }
                else
                {
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this,"Login failed,have valid login",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
