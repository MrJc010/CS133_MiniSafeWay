package com.example.nguye.minisafeway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {

    private ImageView iv;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        iv = (ImageView) findViewById(R.id.image);
        text = (TextView) findViewById(R.id.text);
        Animation wel = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        iv.startAnimation(wel);
        text.startAnimation(wel);
        final Intent i = new Intent(this,Signin.class);
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
}
