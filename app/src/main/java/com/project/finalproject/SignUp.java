package com.project.finalproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class SignUp extends Activity {
    Button btnSignUp,btnSelect;
    EditText username,password,retype,name,address,phonenumber,birthdate;
    RadioButton maleBox;
    String birth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        retype = (EditText) findViewById(R.id.retype);
        name = (EditText) findViewById(R.id.name);
        address = (EditText) findViewById(R.id.address);
        phonenumber = (EditText) findViewById(R.id.phonenumber);
        birthdate = (EditText) findViewById(R.id.birthdate);
        maleBox = (RadioButton) findViewById(R.id.maleCheckBox);
        btnSelect = (Button) findViewById(R.id.btnSelect);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year,month,day;
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUp.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String mbirthdate = day + "/" + month + "/" + year;
                        c.set(Calendar.YEAR,year);
                        c.set(Calendar.MONTH,month);
                        c.set(Calendar.DAY_OF_MONTH,day);
                        birth = day + "/" + month + "/" + year;
                        birthdate.setText(mbirthdate);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        btnSignUp = (Button) findViewById(R.id.btn);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast;

                myData myData = new myData();

                String mUsername = username.getText().toString();
                String mPassword = password.getText().toString().trim();
                String mRetype = retype.getText().toString().trim();
                String mName = name.getText().toString().trim();
                String mAddress = address.getText().toString().trim();
                String mPhonenumber = phonenumber.getText().toString().trim();
                String mGender = "";
                if (maleBox.isChecked())
                    mGender =  "Nam";
                else
                    mGender = "Nữ";
                if (myData.checkUserSignUp(mUsername,mPassword) == 0)
                {
                    toast = Toast.makeText(SignUp.this,"Tên đăng nhập đã được đăng kí",Toast.LENGTH_LONG);
                    toast.show();
                }
                else if (myData.checkUserSignUp(mUsername,mPassword) == 2)
                {
                    toast = Toast.makeText(SignUp.this,"Tên đăng nhập phải có tối thiểu 6 kí tự",Toast.LENGTH_LONG);
                    toast.show();
                }
                else if (myData.checkUserSignUp(mUsername,mPassword) == 3)
                {
                    toast = Toast.makeText(SignUp.this,"Mật khẩu phải có tối thiểu 6 kí tự",Toast.LENGTH_LONG);
                    toast.show();
                }
                else if (myData.checkUserSignUp(mUsername,mPassword) == 1)
                {
                   if(mPassword.equals(mRetype))
                   {
                           User user = new User(mUsername,mPassword,mName,mAddress,mPhonenumber,mGender,birth);
                           myData.addUser(user);
                           Intent intent = new Intent(SignUp.this, MainActivity.class);
                           intent.putExtra("username",mUsername);
                           intent.putExtra("password",mPassword);
                           startActivity(intent);
                   }
                   else
                   {
                       toast = Toast.makeText(SignUp.this,"Nhập lại mật khẩu không đúng",Toast.LENGTH_LONG);
                       toast.show();
                   }
                }
            }
        });

    }

}
