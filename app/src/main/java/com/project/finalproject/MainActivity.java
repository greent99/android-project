package com.project.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
    EditText mUsername;
    EditText mPassword;
    Button mLogin;
    Button mSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUsername = (EditText) findViewById(R.id.edt1);
        mPassword = (EditText) findViewById(R.id.edt2);
        mLogin = (Button) findViewById(R.id.btn1);
        mSignup = (Button) findViewById(R.id.btn2);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                myData myData = new myData();
                Log.d("AAA",username);
                if (myData.checkAccount(username,password) == 0)
                {
                    Log.d("AAA",username);
                    Intent intent1 = new Intent(MainActivity.this,Menu.class);
                    intent1.putExtra("username",username);
                    intent1.putExtra("password",password);
                    MainActivity.this.startActivity(intent1);
                }
                else
                {
                    Toast toast = Toast.makeText(MainActivity.this,"Tên đăng nhập hoặc mật khẩu không đúng",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SignUp.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}
