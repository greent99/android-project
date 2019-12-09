package com.project.finalproject;


import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;
import java.util.List;


public class Menu extends AppCompatActivity {
   

    private TextureView textureView;
    private Button takeAPhoto;
    private ListView lvImage;
    private final int PERMISSION_CODE = 122;
    private final int CAMERA_PIC_REQUEST = 1000;
    private StorageReference mStorage;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private Uri image_uri;
    protected static ArrayAdapter<String> adapter;

    private DatabaseReference mDatabase;
    protected static  List<String> data = new ArrayList<String>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        takeAPhoto = (Button) findViewById(R.id.takeapicture);
        lvImage = (ListView) findViewById(R.id.list);
        //assert takeAPhoto != null;
        //textureView = (TextureView) findViewById(R.id.textureView);
        assert textureView != null;
        setSupportActionBar(tb);

        getSupportActionBar().setTitle("All Document");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.mybackground));

        

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        setData(username);
        adapter = new ArrayAdapter<String>(Menu.this,android.R.layout.simple_list_item_1,data);
        lvImage.setAdapter(adapter);

        takeAPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!checkPermissionForCamera() || !checkPermissionForExternalStorage()) {
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    } else {
                        captureImage();
                    }
                } else {
                    captureImage();
                }
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

        if (id == R.id.action_search)
        {

            return true;
        }

        if (id == R.id.action_menu)
        {

            return true;
        }

        if (id == R.id.action_share)
        {

            return true;
        }
        return false;
    }

    
   public void captureImage() {
       Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


       ContentValues values = new ContentValues();
       values.put(MediaStore.Images.Media.TITLE,"New Picture");
       values.put(MediaStore.Images.Media.DESCRIPTION,"Image from camera");
       image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

       cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
       startActivityForResult(cameraIntent,CAMERA_PIC_REQUEST);
   }

       @Override
       protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
           super.onActivityResult(requestCode, resultCode, data);
           if (requestCode == CAMERA_PIC_REQUEST) {
               if (resultCode == Menu.this.RESULT_OK) {
                   Intent intent1 = getIntent();
                   String username = intent1.getStringExtra("username");
                   Intent intent = new Intent(Menu.this, EditImage.class);
                   intent.putExtra("image_uri", image_uri.toString());
                   intent.putExtra("username",username);
                   startActivity(intent);
               }
           }

       }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode ==  PERMISSION_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                captureImage();
            }
            else
            {
                Toast.makeText(Menu.this,"Permission is denied",Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean checkPermissionForExternalStorage () {
           int result = ContextCompat.checkSelfPermission(Menu.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
           if (result == PackageManager.PERMISSION_GRANTED) {
               return true;
           } else {
               return false;
           }
       }

       public boolean checkPermissionForCamera () {
           int result = ContextCompat.checkSelfPermission(Menu.this, Manifest.permission.CAMERA);
           if (result == PackageManager.PERMISSION_GRANTED) {
               return true;
           } else {
               return false;
           }
       }

      public void setData(String username)
       {
           mDatabase = FirebaseDatabase.getInstance().getReference();
           Query myQuery = mDatabase.child("ListImage").child(username);
           myQuery.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   for(DataSnapshot imageSnapshot : dataSnapshot.getChildren())
                   {
                       MyImage image = imageSnapshot.getValue(MyImage.class);
                       data.add(image.getName());
                       adapter.notifyDataSetChanged();
                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });
       }

   }


