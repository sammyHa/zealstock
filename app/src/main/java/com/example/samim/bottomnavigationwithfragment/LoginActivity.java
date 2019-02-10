package com.example.samim.bottomnavigationwithfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.text.TextUtils.isEmpty;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    private EditText mEmail;
    private EditText mPassword;
    private Button mSigIn;
    private ProgressBar mProgressBar;
    private RelativeLayout mParentLayout;

    //firebase
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.email_id);
        mPassword = findViewById(R.id.password_id);
        mSigIn = findViewById(R.id.btn_signIn);
        mProgressBar = findViewById(R.id.progressBar_id);
        mParentLayout = findViewById(R.id.parent_layout_login);

        setupFirebaseAuth();
        mSigIn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        switch (view.getId()){

            case R.id.btn_signIn:{
                if (!isEmpty(email) && !isEmpty(password)){
                    showProgressBarDialog();

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                makeToastMessage("Signed In");
                                hideProgressBarDialog();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            makeSnackBarMessage("Authentication Failed");
                            hideProgressBarDialog();
                        }
                    });

                }else {
                    makeSnackBarMessage("Enter Email and Password");
                    hideProgressBarDialog();
                }
            }
        }
    }

    //show progressbar
    private void showProgressBarDialog(){
        mProgressBar.setVisibility(mProgressBar.VISIBLE);
    }

    //hide progressBar
    private void hideProgressBarDialog(){
        if (mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(mProgressBar.INVISIBLE);

        }
    }

    //snakebar message
    private void makeSnackBarMessage(String message){
        Snackbar.make(mParentLayout, message,Snackbar.LENGTH_LONG).show();
    }

    //toast message
    private void makeToastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /*
        **************** Setting up the Firebase Authentication ***************
     */

    private void setupFirebaseAuth(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user !=null){
                    makeToastMessage("Signed In");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }else { // else do nothing because user is not signed in
                    Log.d(TAG, "onAuthStateChanged: Signed Out");
                }
            }
        };
    }

    public void signout(){
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }
}
