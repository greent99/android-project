package com.project.finalproject;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class myData{
    private DatabaseReference mDatabase;
    public int checkUserSignUp(final String username, String password)
    {

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference child = mDatabase.child("users");
        child.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 User temp = dataSnapshot.getValue(User.class);
                 String u = temp.getmUsername();
                 if (u.equals(username))
                 {
                     Intent intent = new Intent()
                 }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                  /*User temp = new User();
                  listUser.add(temp);  */
            }
        });
        if (username.length()<6)
            return 2;
        if (password.length()<6)
            return 3;
        return 1;
    }
   
    public void addUser(User myUser)
    {
        Map<String,Object> userInfo = new HashMap<>();
        userInfo.put("username",myUser.getmUsername());
        userInfo.put("password",myUser.getmPassword());
        userInfo.put("name",myUser.getmName());
        userInfo.put("address",myUser.getmAddress());
        userInfo.put("phone number",myUser.getmPhone());
        userInfo.put("gender",myUser.getmGender());
        userInfo.put("birthdate",myUser.getmBirthdate());
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(myUser.getmUsername()).setValue(userInfo);
    }
    int checkAccount(String username, final String password)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               /* String value = dataSnapshot
                if (value.equals(password))
                {
                    //add(a);
                }   */
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
       
        return 0;
    }
    int add(List<User> a,User b)
    {
       a.add(b);
       return 1;
    }
}
