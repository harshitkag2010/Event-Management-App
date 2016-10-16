package com.example1.harshit.event;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void onClick(View view){


        Intent i=new Intent(this,PostEvent.class);
        startActivity(i);


    }

    public void onClick1(View view){

        Intent i=new Intent(this,ViewEvent.class);
        startActivity(i);

    }
}
