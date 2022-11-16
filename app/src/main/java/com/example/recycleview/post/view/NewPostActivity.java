package com.example.recycleview.post.view;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.recycleview.PlatzigramApp;
import com.example.recycleview.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class NewPostActivity extends AppCompatActivity {

    private ImageView imgPhoto;
    private Button btnCreatePost;
    private static final String TAG = "NewPostActivity";

    private String photoPath;
    private PlatzigramApp app;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        //app = (PlatzigramApp) getApplicationContext();
        //storageReference = app.getStorageReference();
        storageReference = app.getStorageReference();

        imgPhoto = (ImageView) findViewById(R.id.imageHeader);
        btnCreatePost = (Button) findViewById(R.id.btnCreatePost);
        if (getIntent().getExtras() != null){
            String photoPath = getIntent().getExtras().getString("PHOTO_PATH_TEMP");


        }
        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPhoto();
            }
        });

    }

    private void uploadPhoto() {
        imgPhoto.setDrawingCacheEnabled(true);
        imgPhoto.buildDrawingCache();

        Bitmap bitmap = imgPhoto.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);

        byte[] photobyte = baos.toByteArray();
        String photoName = photoPath.substring(photoPath
                .lastIndexOf("/")+1,photoPath.length());

        StorageReference photoReference = storageReference.child("postImages/"+photoName);
        UploadTask uploadTask = photoReference.putBytes(photobyte);

        photoReference.getDownloadUrl().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, "onFailure: ", e);
            }
        }).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Uri downloadUrl = uri;
                Log.d(TAG, "onSuccess: ");
                Toast.makeText(getBaseContext(), "Upload success! URL - " + downloadUrl.toString() , Toast.LENGTH_SHORT).show();
                finish();
            }
        });


/*
         uploadTask.addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(Exception e) {

             }
         }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
             @Override
             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                 Uri uriPhoto = taskSnapshot.getDownloadUrl();

                 String photoURL = uriPhoto.toString();
                 Log.w(TAG, "URL Photo > "+ photoURL );

             }
         });
*/

    }

    private void showPhoto(){
        Picasso.get().load(photoPath).into(imgPhoto);

    }
}