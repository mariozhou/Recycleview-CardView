package com.example.recycleview;

import android.app.Application;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.concurrent.RecursiveTask;

public class PlatzigramApp extends Application {
    private static final String TAG = "PlatzigramApp";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseStorage firebaseStorage;
    @Override
    public void onCreate() {
        super.onCreate();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                if(firebaseUser !=null){
                    Log.w("PlatzigramApp", " Usuario logeado" + firebaseUser.getEmail());
                }else{
                    Log.w("PlatzigramApp", " Usuario No logeado");
                }
            }
        };

        firebaseStorage = FirebaseStorage.getInstance();
            }

        public StorageReference getStorageReference(){
            Log.d(TAG, "getStorageReference: ");
            return  firebaseStorage.getReference();
        }
}
