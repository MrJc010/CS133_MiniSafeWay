package com.example.nguye.minisafeway;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Aboutus extends AppCompatActivity {
    FloatingActionButton home3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        home3 = (FloatingActionButton) findViewById(R.id.home3);
        home3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Aboutus.this, Home.class);
                startActivity(i);
            }
        });
    }
}
