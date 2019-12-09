package com.project.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText mUsername;
    EditText mPassword;
    Button mLogin;
    Button mSignup;
    public static String BUNDLE = "bundle";
    private DatabaseReference mDatabase;
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
                checkAccount(username,password);
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

    void checkAccount(String username, final String password) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User temp = dataSnapshot.getValue(User.class);
                if (temp == null) {
                    Toast toast = Toast.makeText(MainActivity.this, "Tên đăng nhập không tồn tại", Toast.LENGTH_LONG);
                    toast.show();
                }
                else
                    {
                        if (temp.getPassword().equals(password))
                        {
                                Intent intent = new Intent(MainActivity.this, Menu.class);
                                intent.putExtra("username",temp.getUsername());
                                intent.putExtra("password",temp.getPassword());
                                startActivity(intent);

                        }
                        else
                        {
                            Toast toast = Toast.makeText(MainActivity.this, "Mật khẩu không chính xác", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
