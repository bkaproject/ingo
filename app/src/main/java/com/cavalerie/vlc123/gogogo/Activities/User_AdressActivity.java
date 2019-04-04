package com.cavalerie.vlc123.gogogo.Activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cavalerie.vlc123.gogogo.Dialog.AdressAdd;
import com.cavalerie.vlc123.gogogo.R;

public class User_AdressActivity extends AppCompatActivity {

    private FloatingActionButton fabAddAdress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_adress);

        //find ID
        fabAddAdress = (FloatingActionButton) findViewById(R.id.fabAddAdress);
        fabAddAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdressAdd adressAdd = new AdressAdd();
                adressAdd.show(getSupportFragmentManager(), "adress add dialog");
            }
        });
    }

}
