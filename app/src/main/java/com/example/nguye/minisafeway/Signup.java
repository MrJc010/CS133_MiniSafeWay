package com.example.nguye.minisafeway;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguye.minisafeway.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class Signup extends AppCompatActivity {

    EditText edtUsername, edtName, edtPassword;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtName = (EditText) findViewById(R.id.name);
        edtPassword = (EditText) findViewById(R.id.password);
        edtUsername = (EditText) findViewById(R.id.user_name);
        btnSignUp = (Button) findViewById(R.id.btnSignup);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                final ProgressDialog mDialog = new ProgressDialog(Signup.this);
                final AlertDialog OptionDialog = new AlertDialog.Builder(Signup.this).create();
                OptionDialog.setTitle("Username is used");
                OptionDialog.setMessage("Please enter different username");
                OptionDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.child(edtUsername.getText().toString()).exists()) {
                            OptionDialog.dismiss();
                            User user = new User(edtName.getText().toString(), edtPassword.getText().toString());
                            table_user.child(edtUsername.getText().toString()).setValue(user);
                            Toast.makeText(Signup.this, "Sign up successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                    }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        });

    }
}
