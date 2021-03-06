package com.example.mygallery;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import java.util.HashMap;

public class PermissionManager {
  static final String KEY_ALBUM= "album_name";
  static final String KEY_PATH= "path";
  static final String KEY_TIMESTAMP= "timestamp";

  public static boolean hasPermission(Context context, String... permissions) {
    if ((Build.VERSION.SDK_INT>= Build.VERSION_CODES.M)&& (context!=null)&& (permissions!=null)) {
      for (String permission: permissions) {
        if (ActivityCompat.checkSelfPermission(context, permission)!= PackageManager.PERMISSION_DENIED) {
          return false;
        }
      }
    }
    return true;
  }

  public static HashMap<String, String> mappingAlbum(String album, String path, String timestamp) {
    HashMap<String, String> map= new HashMap<>();
    map.put(KEY_ALBUM, album);
    map.put(KEY_PATH, path);
    map.put(KEY_TIMESTAMP, timestamp);
    return map;
  }
}
