package com.cavalerie.vlc123.gogogo.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cavalerie.vlc123.gogogo.Data.UserProfileManager;
import com.cavalerie.vlc123.gogogo.R;

public class UserInformationActivity extends AppCompatActivity {

    private TextView txtPrStatut, txtPrPassager, txtPrConducteur, txtPrEmail, txtPrTelephone, txtPrborn,
            txtPrAdresse, txtPrCni, txtPrOccupation, txtPrDiscution, txtPrCigarette, txtPrMusique, txtPrObject, txtModifierProfil;

    private UserProfileManager userProfileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        userProfileManager = new UserProfileManager(UserInformationActivity.this);

        //Find ID
        txtPrStatut = (TextView) findViewById(R.id.txtPrStatut);
        txtPrPassager = (TextView) findViewById(R.id.txtPrPassager);
        txtPrConducteur = (TextView) findViewById(R.id.txtPrConducteur);
        txtPrEmail = (TextView) findViewById(R.id.txtPrEmail);
        txtPrTelephone = (TextView) findViewById(R.id.txtPrTelephone);
        txtPrborn = (TextView) findViewById(R.id.txtPrDateborn);
        txtPrAdresse = (TextView) findViewById(R.id.txtPrAdresse);
        txtPrCni = (TextView) findViewById(R.id.txtPrCni);
        txtPrOccupation = (TextView) findViewById(R.id.txtPrOccupation);
        /*txtPrDiscution = (TextView) findViewById(R.id.txtPrDiscution);
        txtPrCigarette = (TextView) findViewById(R.id.txtPrCigarette);
        txtPrMusique = (TextView) findViewById(R.id.txtPrMusique);
        txtPrObject = (TextView) findViewById(R.id.txtPrObject); */

        txtModifierProfil = (TextView) findViewById(R.id.txtModifierProfil);
        txtModifierProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInformationActivity.this, user_edit_information.class));
            }
        });

        load();
    }

    private void load() {

        if(userProfileManager.getStatus() != null) {
            txtPrStatut.setText(userProfileManager.getStatus());
        }else {
            txtPrStatut.setText("Débutanr");
        }

        if(userProfileManager.getEmail() != null) {
            txtPrEmail.setText(userProfileManager.getEmail());
        }else {
            txtPrEmail.setText("Non défini");
        }

        if(userProfileManager.getTelephone() != null) {

        }else {
            txtPrEmail.setText("Non défini");
        }

        if(userProfileManager.getBorndate() != null) {
            txtPrborn.setText(userProfileManager.getBorndate());
        }else {
            txtPrborn.setText("Non défini");
        }

        if(userProfileManager.getAdresse() != null) {
            txtPrAdresse.setText(userProfileManager.getAdresse());
        }else {
            txtPrAdresse.setText("Non défini");
        }

        if(userProfileManager.getCni() != null) {
            txtPrCni.setText(userProfileManager.getCni());
        }else {
            txtPrCni.setText("Non défini");
        }

        if(userProfileManager.getOccupation() != null) {
            txtPrOccupation.setText(userProfileManager.getOccupation());
        }else {
            txtPrOccupation.setText("Non défini");
        }

        /*if(userProfileManager.getDiscussion() != null) {
            txtPrDiscution.setText(userProfileManager.getDiscussion());
        }else {
            txtPrDiscution.setText("Non défini");
        }

        if(userProfileManager.getCigarette() != null) {
            txtPrCigarette.setText(userProfileManager.getCigarette());
        }else {
            txtPrCigarette.setText("Non défini");
        }

        if(userProfileManager.getMusique() != null) {
            txtPrMusique.setText(userProfileManager.getMusique());
        }else {
            txtPrMusique.setText("Non défini");
        }

        if(userProfileManager.getObject() != null) {
            txtPrObject.setText(userProfileManager.getObject());
        }else {
            txtPrObject.setText("Non défini");
        }*/
    }
}
