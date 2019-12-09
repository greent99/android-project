package com.project.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

public class EditImage extends Activity {
    CropImageView cropImg;
    Button btnCut;
    Button btnRL;
    Button btnRR;
    Integer t = 0;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editimage);
        btnCut = (Button) findViewById(R.id.btCut);
        btnRL = (Button) findViewById(R.id.btRotateL);
        btnRR = (Button) findViewById(R.id.btRotateR);
        cropImg = (CropImageView) findViewById(R.id.cropImageView);
        Uri image_uri = Uri.parse(getIntent().getExtras().getString("image_uri"));
        Bitmap bitmap = null;
        try {
            if(  image_uri != null   ){
                 bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver() , image_uri);
            }
        }
        catch (Exception e) {
            //handle exception
        }
        cropImg.setImageBitmap(bitmap);


        

        btnRR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cropImg.setRotation(cropImg.getRotation() + 90);
                    t++;
                }
        });
        btnRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropImg.setRotation(cropImg.getRotation()-90);
                t--;
            }
        });
        btnCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = null;
                Intent intent1 = getIntent();
                String username =  intent1.getStringExtra("username");
                Bitmap bitmap = cropImg.getCroppedImage();
                Intent intent = new Intent(EditImage.this,FinalImage.class);
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,bs);
                intent.putExtra("byteArray",bs.toByteArray());
                intent.putExtra("t", t+ "");
                intent.putExtra("username",username);
                startActivity(intent);

            }
        });
        
    }


    /*private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }   */




}
