package com.example.nguye.minisafeway;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.nguye.minisafeway.Common.Common;

public class Aboutus extends AppCompatActivity {
    FloatingActionButton home3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        home3 = (FloatingActionButton) findViewById(R.id.home3);

        String name="";
        try{
            name = Common.currentUser.getName();
        }catch (NullPointerException e){
            name=null;
        }
        if(name==null){
            home3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Aboutus.this, Guest.class);
                    startActivity(i);
                }
            });
        }
        else {
            home3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Aboutus.this, Home.class);
                    startActivity(i);
                }
            });
        }
    }
}
