package com.cavalerie.vlc123.gogogo.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.cavalerie.vlc123.gogogo.R;

public class user_PreferenceActivity extends AppCompatActivity {

    private com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner spDiscussion, spCigarette, spAnimaux, spMusique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preference);

        spDiscussion = (com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner) findViewById(R.id.spDiscussion);
        spCigarette = (com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner) findViewById(R.id.spCigarette);
        spAnimaux = (com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner) findViewById(R.id.spAnimal);
        spMusique = (com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner) findViewById(R.id.spMusique);


        ArrayAdapter adapterDiscussion = ArrayAdapter.createFromResource(user_PreferenceActivity.this, R.array.discussion, android.R.layout.simple_spinner_dropdown_item);
        spDiscussion.setAdapter(adapterDiscussion);

        ArrayAdapter adapterCigarette = ArrayAdapter.createFromResource(user_PreferenceActivity.this, R.array.cigarette, android.R.layout.simple_spinner_dropdown_item);
        spCigarette.setAdapter(adapterCigarette);

        ArrayAdapter adapterAnimaux = ArrayAdapter.createFromResource(user_PreferenceActivity.this, R.array.animaux, android.R.layout.simple_spinner_dropdown_item);
        spAnimaux.setAdapter(adapterAnimaux);

        ArrayAdapter adapterMusique = ArrayAdapter.createFromResource(user_PreferenceActivity.this, R.array.musique, android.R.layout.simple_spinner_dropdown_item);
        spMusique.setAdapter(adapterMusique);
    }
}
