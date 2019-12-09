package com.project.finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FinalImage extends AppCompatActivity {
    ImageView finalImg;
    EditText nameEdt;
    Button saveBtn;
    Button dontSaveBtn;
    int t=0;
    
    MyImage myImage = new MyImage();
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://my-final-project-fdec7.appspot.com");
    private StorageReference storageRef = storage.getReference();
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finalimage);

        finalImg = (ImageView) findViewById(R.id.finalImage);
        nameEdt = (EditText) findViewById(R.id.name);
        saveBtn = (Button) findViewById(R.id.save);
        dontSaveBtn = (Button) findViewById(R.id.dontSave);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        final int height = displayMetrics.heightPixels;
        final int width = displayMetrics.widthPixels;
        final Intent callEditImage = getIntent();

        Bitmap bitmap;
        Bitmap result;

        if (callEditImage.hasExtra("byteArray")) {

            bitmap  = BitmapFactory.decodeByteArray(
                    callEditImage.getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
            String k = callEditImage.getStringExtra("t");
            t = Integer.parseInt(k);
            Calendar c = Calendar.getInstance();
            String date = c.get(Calendar.DAY_OF_MONTH) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.YEAR);
            String time = c.get(Calendar.HOUR) + "." + c.get(Calendar.MINUTE) + "." + c.get(Calendar.SECOND);
            String yourName = "new doc " + date + " " + time;
            nameEdt.setText(yourName);

            finalImg.getLayoutParams().height = 2*height/3;
            finalImg.getLayoutParams().width = width;
            Matrix matrix = new Matrix();
            float degrees = (float)t*90;
            matrix.setRotate(degrees);


            result = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
            finalImg.setImageBitmap(result);
        }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filaname =  nameEdt.getText().toString();
                myImage.setName(filaname);
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String mId =  timeStamp;
                myImage.setId(mId);


                String username = callEditImage.getStringExtra("username");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();


                Bitmap bmp =  ((BitmapDrawable)finalImg.getDrawable()).getBitmap();
                
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("ListImage").child(username).child(mId).setValue(myImage);


                 
                StorageReference ref = storageRef.child("images/" + username + "/" + mId);
                UploadTask uploadTask = ref.putBytes(data);
                
                uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        Log.d("VanTai","Upload success");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("VanTai","Upload fail");
                    }
                });
                Menu.data.add(filaname);
                Menu.adapter.notifyDataSetChanged();
                finish();
                
            }
        });

        dontSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
