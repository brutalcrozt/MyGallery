package com.example.mygallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
  private final int READ_WRITE_EXTERNAL_STORAGE= 1;
  private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS= -1111;
  private GridView galleryView;
  private ArrayList<HashMap<String, String>> albumList= new ArrayList<>();

  private void askPermission() {
    String[] PERMISSIONS= {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    if (!PermissionManager.hasPermission(this, PERMISSIONS)) {
      Log.e("perm", "askPermission: ");
      ActivityCompat.requestPermissions(this, PERMISSIONS, READ_WRITE_EXTERNAL_STORAGE);
    } else {
      Log.e("perm", "where the hell this is");
    }
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    galleryView= findViewById(R.id.gridView);
//    askPermission();
    androidGuideToAskPerm(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
//    askPermission();
    androidGuideToAskPerm(this);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode) {
      case READ_WRITE_EXTERNAL_STORAGE:
        if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
          LoadAlbum loadAlbum= new LoadAlbum(this, albumList);
          loadAlbum.execute();
        } else {
          Toast.makeText(this, "App can't run without permission", Toast.LENGTH_LONG).show();
        }
      break;
    }
  }

  public void setAddapter(AlbumAdapter aa) {
    galleryView.setAdapter(aa);
  }

  public void androidGuideToAskPerm(Activity thisActivity) {
    if (ContextCompat.checkSelfPermission(thisActivity,
            Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {

      // Permission is not granted
      // Should we show an explanation?
      if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
              Manifest.permission.READ_CONTACTS)) {
        // Show an explanation to the user *asynchronously* -- don't block
        // this thread waiting for the user's response! After the user
        // sees the explanation, try again to request the permission.
        Toast.makeText(this,"explain why u need read contact", Toast.LENGTH_LONG).show();
      } else {
        // No explanation needed; request the permission
        ActivityCompat.requestPermissions(thisActivity,
                new String[]{Manifest.permission.READ_CONTACTS},
                READ_WRITE_EXTERNAL_STORAGE);

        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
        // app-defined int constant. The callback method gets the
        // result of the request.
      }
    } else {
      // Permission has already been granted
      Toast.makeText(this, "perm granted", Toast.LENGTH_LONG).show();
    }
  }
}
