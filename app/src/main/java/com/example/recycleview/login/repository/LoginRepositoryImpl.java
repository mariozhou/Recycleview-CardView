package com.example.recycleview.login.repository;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.recycleview.login.presenter.LoginPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginRepositoryImpl implements LoginRepository{

    LoginPresenter presenter;

    public LoginRepositoryImpl(LoginPresenter presenter) {

        this.presenter = presenter;
    }

    @Override
    public void signIn(String username, String password,Activity activity, FirebaseAuth firebaseAuth) {

        firebaseAuth.signInWithEmailAndPassword(username,password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            FirebaseUser user = task.getResult().getUser();

                            SharedPreferences preferences = activity
                                    .getSharedPreferences("User", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("email",user.getEmail());
                            editor.commit();
                            //almacenar email con sharedpreference

                            presenter.loginSuccess();
                        }else {
                            presenter.loginError("ocurrio un error");

                        }
                    }

                });
    }
}
