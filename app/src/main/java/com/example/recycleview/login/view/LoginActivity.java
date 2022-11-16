package com.example.recycleview.login.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.recycleview.post.view.MainActivity;
import com.example.recycleview.R;
import com.example.recycleview.login.presenter.LoginPresenter;
import com.example.recycleview.login.presenter.LoginPresenterImpl;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements LoginView{

    private TextInputEditText username, password;
    private Button login;
    private ProgressBar progressBarLogin;
    private LoginPresenter presenter;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private static final String TAG = "LoginRepositoryImpl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

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

        username = (TextInputEditText) findViewById(R.id.username);
        password = (TextInputEditText) findViewById(R.id.password);
        login = (Button)  findViewById(R.id.login);
        progressBarLogin = (ProgressBar) findViewById(R.id.progressbarlogin);
        hideProgressBar();

        presenter = new LoginPresenterImpl(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(username.getText().toString(),
                        password.getText().toString());
                //presenter.signIn(username.getText().toString(),
                 //       password.getText().toString(),this);

            }
        });
    }

    private void signIn(String username, String password) {
        presenter.signIn(username,password,this ,firebaseAuth);
    }

    public void goCreateAccount(View view){
        goCreateAccount();
    }


    @Override
    public void enableInputs() {
        username.setEnabled(true);
        password.setEnabled(true);
        login.setEnabled(true);
    }

    @Override
    public void disableInputs() {
        username.setEnabled(false);
        password.setEnabled(false);
        login.setEnabled(false);
    }

    @Override
    public void showProgressBar() {
        progressBarLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBarLogin.setVisibility(View.GONE);
    }

    @Override
    public void loginError(String error) {
        Toast.makeText(this, "Ocurrio este error: "+error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goCreateAccount() {
        Intent intent = new Intent(this,CreateAccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void goHome() {
        //  Intent intent = new Intent(this,ContainerActivity.class);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Log.d(TAG, "goHome: abrirr");

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