package com.project.finalproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUp extends Activity {
    Button btnSignUp,btnSelect;
    EditText username,password,retype,name,address,phonenumber,birthdate;
    RadioButton maleBox;
    String birth;
    private DatabaseReference mDatabase;
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

                String mUsername = username.getText().toString().trim();
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
                User user = new User(mUsername,mPassword,mName,mAddress,mPhonenumber,mGender,birth);
                checkUserSignUp(user,mRetype);
            }
        });

    }

    public void checkUserSignUp(final User user, final String retype)
    {
        boolean kt = invalidUsername(user.getUsername());
        if (kt == false)
        {
            Toast toast = Toast.makeText(SignUp.this, "Tên đăng nhập không được có kí tự đặc biệt hoặc khoảng trắng", Toast.LENGTH_LONG);
            toast.show();
        }
        else if (user.getUsername().length() < 6) {
            Toast toast = Toast.makeText(SignUp.this, "Tên đăng nhập phải có ít nhất 6 kí tự", Toast.LENGTH_LONG);
            toast.show();
        } else if (user.getPassword().length() < 6) {
            Toast toast = Toast.makeText(SignUp.this, "Mật khẩu phải có ít nhất 6 kí tự", Toast.LENGTH_LONG);
            toast.show();
        } else if (!user.getPassword().equals(retype)) {
            Toast toast = Toast.makeText(SignUp.this, "Nhập lại mật khẩu không đúng", Toast.LENGTH_LONG);
            toast.show();
        }else {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            Query child = mDatabase.child("users");
            child.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int count = 0;
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        User temp = new User();
                        temp = userSnapshot.getValue(User.class);
                        String u = temp.getUsername();
                        if (u.equals(user.getUsername())) {
                            Toast toast = Toast.makeText(SignUp.this, "Tên đăng nhập đã được đăng kí", Toast.LENGTH_LONG);
                            toast.show();
                            count++;
                            break;
                        }
                    }
                    if (count == 0)
                    {
                        addUser(user);
                        Intent intent = new Intent(SignUp.this, MainActivity.class);
                        intent.putExtra("username",user.getUsername());
                        intent.putExtra("password",user.getUsername());
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {

                }
            });
        }

    }
    public void addUser(User myUser)
    {
        Map<String,Object> userInfo = new HashMap<>();
        userInfo.put("username",myUser.getUsername());
        userInfo.put("password",myUser.getPassword());
        userInfo.put("name",myUser.getName());
        userInfo.put("address",myUser.getAddress());
        userInfo.put("phone number",myUser.getPhone());
        userInfo.put("gender",myUser.getGender());
        userInfo.put("birthdate",myUser.getBirthdate());
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(myUser.getUsername()).setValue(userInfo);
    }

    boolean invalidUsername(String username)
    {
        for(int i = 0;i<username.length();i++)
        {
            char ch = username.charAt(i);
            if (!((ch >= '0' && ch <='9') || (ch >= 'a' && ch<='z') || (ch>='A' && ch<= 'Z')))
            {
                 return false;
            }
        }
        return true;
    }

}
