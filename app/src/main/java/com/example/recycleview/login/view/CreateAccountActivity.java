package com.example.recycleview.login.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.recycleview.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccountActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private static final String TAG = "CreateAccountActivity";

    private Button btnJoinUS;
    private TextInputEditText edtEmail, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        btnJoinUS = (Button) findViewById(R.id.btnJoinUS);
        edtEmail = (TextInputEditText) findViewById(R.id.email);
        edtPassword = (TextInputEditText) findViewById(R.id.password_createaccount);


        firebaseAuth =  FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    Log.w(TAG, "onAuthStateChanged: Usuario logeado"+ firebaseUser.getEmail() );
                }else {
                    Log.w(TAG, "onAuthStateChanged: Usuario No logeado" );

                }
            }
        };

        btnJoinUS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

    }

    private void createAccount() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(CreateAccountActivity.this,
                                    "Cuenta creada exitosamente",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(CreateAccountActivity.this,
                                    "Ocurrio un error",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}
