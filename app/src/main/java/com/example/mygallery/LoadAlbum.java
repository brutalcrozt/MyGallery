package com.example.mygallery;

import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashMap;

public class LoadAlbum extends AsyncTask<String, Void, String> {
  private MainActivity mainActivity;
  private ArrayList<HashMap<String, String>> albumList;

  public LoadAlbum(MainActivity ma, ArrayList<HashMap<String, String>> album) {
    mainActivity= ma;
    albumList= album;
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
    albumList.clear();
  }

  @Override
  protected String doInBackground(String... strings) {
    String r="", path=null, selections= null;
    Uri uriIntern= MediaStore.Images.Media.INTERNAL_CONTENT_URI, uriExtern= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    String [] projection= {MediaStore.MediaColumns.DATA,
    android.provider.MediaStore.MediaColumns.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.DATE_MODIFIED};

    Cursor cIntern= mainActivity.getContentResolver().query(uriIntern, projection, selections, null, null)
            ,cExtern= mainActivity.getContentResolver().query(uriExtern, projection, selections, null, null);
    Cursor cursor= new MergeCursor(new Cursor[]{cIntern, cExtern});
    while (cursor.moveToNext()) {
      path= cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
      String album= cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.BUCKET_DISPLAY_NAME));
      String timestamp= cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED));
      albumList.add(PermissionManager.mappingAlbum(album, path, timestamp));
    }
    cursor.close();
    return r;
  }

  @Override
  protected void onPostExecute(String s) {
    super.onPostExecute(s);
    AlbumAdapter albumAdapter= new AlbumAdapter(mainActivity, albumList);
    mainActivity.setAddapter(albumAdapter);
  }
}
