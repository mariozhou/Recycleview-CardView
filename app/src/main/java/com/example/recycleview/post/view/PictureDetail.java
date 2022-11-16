package com.example.recycleview.post.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.recycleview.PlatzigramApp;
import com.example.recycleview.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class PictureDetail extends AppCompatActivity {

    private static final String TAG = "PictureDetail";
    private String PHOTO_NAME = "foto.png";
    private ImageView imageHeader;
    private PlatzigramApp app;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_detail);

        app = (PlatzigramApp) getApplicationContext();
        storageReference = app.getStorageReference();

        imageHeader = (ImageView) findViewById(R.id.imageHeader);

        showData();
    }

    private void showData() {
        Log.d(TAG, "showData: ");
        storageReference.child("postImages/"+PHOTO_NAME)
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d(TAG, "onSuccess: ");
                Picasso.get().load(uri.toString()).into(imageHeader);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(app, "error", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });

    }

}


