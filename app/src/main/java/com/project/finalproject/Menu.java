package com.project.finalproject;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;


public class Menu extends AppCompatActivity {
    private static final String TAG = "AndroidCameraApi";
    TextView txt;
    private TextureView textureView;
    private  Button takeAPhoto;
    private final int CAMERA_PIC_REQUST = 1337;



    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        takeAPhoto = (Button) findViewById(R.id.takeapicture);
        assert takeAPhoto != null;
        textureView = (TextureView) findViewById(R.id.textureView);
        assert textureView != null;
        setSupportActionBar(tb);

        getSupportActionBar().setTitle("All Document");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.mybackground));

        takeAPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        tb.inflateMenu(R.menu.mainscreen);
        tb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        txt = (TextView) findViewById(R.id.txt);
        if (id == R.id.action_search)
        {
            txt.setText("Search...");
            return true;
        }

        if (id == R.id.action_menu)
        {
            txt.setText("Menu...");
            return true;
        }

        if (id == R.id.action_share)
        {
            txt.setText("Share...");
            return true;
        }
        return false;
    }

    /*public void takePicture(){
        ActivityCompat.requestPermissions(Menu.this,new String[]{Manifest.permission.CAMERA},CAMERA_PIC_REQUST);
    }  */
    public void takePicture(){
        ActivityCompat.requestPermissions(Menu.this,new String[]{Manifest.permission.CAMERA},CAMERA_PIC_REQUST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1337)
        {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            Intent intent = new Intent(Menu.this,EditImage.class);
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG,50,bs);
            intent.putExtra("byteArray",bs.toByteArray());
            startActivity(intent);
        }
        else
        {
            Toast toast = Toast.makeText(Menu.this,"Picture not taken",Toast.LENGTH_LONG);
            toast.show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PIC_REQUST && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent,CAMERA_PIC_REQUST);
        }
        else
        {

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}


