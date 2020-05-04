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

public class RegisterActivity extends AppCompatActivity {

    EditText mName, mPassword, memail, mConfirmpassword;
    Button mRegisterbtn;

    private ProgressDialog loadingBar;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);

        mName = (EditText) findViewById(R.id.Reg_ET_name);
        memail = (EditText) findViewById(R.id.Reg_ET_email);
        mPassword = (EditText) findViewById(R.id.Reg_ET_password);
        mConfirmpassword = (EditText) findViewById(R.id.Reg_ET_confirm_password);
        mRegisterbtn = (Button) findViewById(R.id.Reg_createAcc_Btn);

        loadingBar = new ProgressDialog(this);

        mRegisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = mName.getText().toString().trim();
                String pwd = mPassword.getText().toString().trim();
                String cnf_pwd = mConfirmpassword.getText().toString().trim();
                String email = memail.getText().toString().trim();

                if (TextUtils.isEmpty(user)) {
                    Toast.makeText(RegisterActivity.this, "Please enter the username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this, "Please enter the email", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(pwd)) {
                    Toast.makeText(RegisterActivity.this, "Please enter the password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(cnf_pwd)) {
                    Toast.makeText(RegisterActivity.this, "Please enter the confirm password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pwd.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Please too short", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (pwd.equals(cnf_pwd)) {
                    long val = db.addUser(user, pwd);
                    if (val > 0) {
                        loadingBar.dismiss();
                        Toast.makeText(RegisterActivity.this, "You have registered", Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(moveToLogin);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registeration Error", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "Password is not matching", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    public void alreadyAccount(View view) {
        Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(moveToLogin);
    }
}
