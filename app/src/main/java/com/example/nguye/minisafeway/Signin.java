package com.example.nguye.minisafeway;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nguye.minisafeway.Common.Common;
import com.example.nguye.minisafeway.Model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;


/**
     *Login class is used to get user's username and password. When user clicks login, the database will
     * check if the user account exist, it it does , it will prompt to users account page.
     */

    public class Signin extends AppCompatActivity {

        EditText username, password;
        Button btnSignIn,btnSignUp,btnGuest;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.signin);

            username = (EditText) findViewById(R.id.user_name);
            password = (EditText) findViewById(R.id.password);
            btnSignIn = (Button) findViewById(R.id.btnSignIn);
            btnSignUp = (Button) findViewById(R.id.btnSignUp);
            btnGuest = (Button) findViewById(R.id.btnGuest);

            Paper.init(this);
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference table_user = database.getReference("User");

            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Common.isConnectedToInternet(getBaseContext())) {

                        if (validate(username.getText().toString())) dialoge();
                        else {
                            final ProgressDialog mDialog = new ProgressDialog(Signin.this);
                            mDialog.setMessage("Please waiting...");
                            mDialog.show();

                            table_user.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.child(username.getText().toString()).exists()) {
                                        mDialog.dismiss();
                                        User user = dataSnapshot.child(username.getText().toString()).getValue(User.class);
                                        if (user.getPassword().equals(password.getText().toString())) {
                                            Intent homeIntent = new Intent(Signin.this, Home.class);
                                            Common.currentUser = user;
                                            startActivity(homeIntent);
                                            finish();
                                        } else {
                                            Toast.makeText(Signin.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        mDialog.dismiss();
                                        Toast.makeText(Signin.this, "Please Sign Up first", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    } else {
                        Toast.makeText(Signin.this, "Please check your connection!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Signin.this, Signup.class);
                    startActivity(i);
                }
            });
            btnGuest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Signin.this, Guest.class);
                    startActivity(i);
                }
            });
        }

    public void dialoge() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Username/Password not found!");
        alertBuilder.setMessage("Please enter Username or Password");
        alertBuilder.create().show();

    }

    public boolean validate(String username) {
            return username.equals("");
    }

}

