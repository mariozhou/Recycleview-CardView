package com.example.recycleview.post.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.recycleview.Picture;
import com.example.recycleview.PictureAdapter;
import com.example.recycleview.R;
import com.example.recycleview.login.view.CreateAccountActivity;
import com.example.recycleview.login.view.LoginActivity;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.SimpleTimeZone;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMARA = 1;
    private FloatingActionButton fabCamara;
    private static final String TAG = "MainActivity";

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private String photoPahtTemp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseinitialze();
        RecyclerView picturesRecycler = (RecyclerView) findViewById(R.id.pictureRecycler);
        fabCamara = (FloatingActionButton) findViewById(R.id.fabCamara);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        picturesRecycler.setLayoutManager(linearLayoutManager);

        PictureAdapter pictureAdapter = new PictureAdapter(buidPictures(),R.layout.cardview,this);
        picturesRecycler.setAdapter(pictureAdapter);

        fabCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: btn foto");
                takePicture();
            }
        });
         

    }


    private void takePicture() {
        Intent intentTakePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Log.d(TAG, "takePicture: takepicture");
        
            File photoFile = null;

            try {
                Log.d(TAG, "takePicture: try");
                photoFile =createImageFile();
            }catch (Exception e){
                Log.d(TAG, "takePicture: error");
                e.printStackTrace();

            }

            if (photoFile != null){
                Log.d(TAG, "takePicture: subir foto  ");
                Uri photoUri = FileProvider.getUriForFile(this,
                        "com.example.recycleview",photoFile);
                intentTakePicture.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(intentTakePicture,REQUEST_CAMARA);//regresar a activity
            }
    }

    private File createImageFile() throws IOException {
        Log.d(TAG, "createImageFile: aa");
        String timeStamp = new SimpleDateFormat("yyMMdd_HH-mm-ss")
                .format(new Date());
        String imageFileName = "JPEG_"+ timeStamp+ "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File photo = File.createTempFile(imageFileName,".jpg",storageDir);

        photoPahtTemp= "file:"+ photo.getAbsolutePath();

        return photo;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if (requestCode == REQUEST_CAMARA && requestCode == this.RESULT_OK){
        if (requestCode == REQUEST_CAMARA ){
            Log.d(TAG, "onActivityResult: CAMARA OK!!");
            Intent i = new Intent(this, NewPostActivity.class);
            i.putExtra("PHOTO_PATH_TEMP",photoPahtTemp);
            startActivity(i);
        }
    }

    public ArrayList<Picture> buidPictures(){
        ArrayList<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture("https://www.novalandtours.com/images/sys_line_jp/tm_01.jpg","mario chen","10 dias","8"));
        pictures.add(new Picture("https://www.novalandtours.com/images/sys_line_jp/tm_01.jpg","mario wang","7 dias","80"));

        return pictures;
    }

    private void firebaseinitialze() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    Log.w(TAG, "onAuthStateChanged: Usuario logeado" + firebaseUser.getEmail());
                } else {
                    Log.w(TAG, "onAuthStateChanged: Usuario No logeado");

                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mSignOut:
                firebaseAuth.signOut();

                Toast.makeText(this, "Se cerro la session", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.mAbout:
                Toast.makeText(this, "App chido", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}