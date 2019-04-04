package com.cavalerie.vlc123.gogogo.Data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.cavalerie.vlc123.gogogo.Activities.DashboardActivity;
import com.cavalerie.vlc123.gogogo.Activities.LoginActivity;

public class UserProfileManager {


    Context Ctx;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    private static final String PREFERENCE_NAME = "auth_user_profile";
    private static final String PROFIL = "profil";
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String TELEPHONE = "telephone";
    private static final String CNI = "cni";
    private static final String ADRESSE = "address";
    private static final String STATUS = "status";
    private static final String BORNDATE = "born";
    private static final String OCCUPATION = "occupation";
    private static final String DISCUSSION = "discussion";
    private static final String CIGARETTE = "cigarette";
    private static final String MUSIQUE = "musique";
    private static final String OBJECT = "object";

    private static final String IS_LOGIN = "IsLoggedIn";

    public UserProfileManager(Context context)
    {
        this.Ctx = context;
        sp = Ctx.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void saveUserInfo(String nom, String prenom, String username, String email, String telephone, String cni, String address, String status, String bornDate)
    {
        editor.putString(FIRSTNAME , nom);
        editor.putString(LASTNAME , prenom);
        editor.putString(USERNAME , username);
        editor.putString(EMAIL , email);
        editor.putString(TELEPHONE , telephone);
        editor.putString(CNI , cni);
        editor.putString(ADRESSE , address);
        editor.putString(STATUS, status);
        editor.putString(BORNDATE , bornDate);
        editor.commit();
    }

    public void saveImgProfil(String profil) {
        editor.putString(PROFIL, profil);
        editor.commit();
    }

    public void saveUserPref(String discussion, String cigarette, String musique, String object) {
        editor.putString(DISCUSSION, discussion);
        editor.putString(CIGARETTE, discussion);
        editor.putString(MUSIQUE, discussion);
        editor.putString(OBJECT, discussion);
        editor.commit();
    }

    public String getProfil() {
        return sp.getString(PROFIL, "");
    }

    public String getFirstname() {
        return sp.getString(FIRSTNAME, "");
    }

    public String getLastname() {
        return sp.getString(LASTNAME, "");
    }

    public String getUsername() {
        return sp.getString(USERNAME, "");
    }

    public String getEmail() {
        return sp.getString(EMAIL, "");
    }

    public String getTelephone() {
        return sp.getString(TELEPHONE, "");
    }

    public String getCni() {
        return sp.getString(CNI, "");
    }

    public String getAdresse() {
        return sp.getString(ADRESSE, "");
    }

    public String getStatus() {
        return sp.getString(STATUS, "");
    }

    public String getBorndate() {
        return sp.getString(BORNDATE, "");
    }

    public String getDiscussion() {
        return sp.getString(DISCUSSION, "");
    }

    public String getCigarette() {
        return sp.getString(CIGARETTE, "");
    }

    public String getMusique() {
        return sp.getString(MUSIQUE, "");
    }

    public String getObject() {
        return sp.getString(OBJECT, "");
    }

    public String getOccupation() {
        return sp.getString(OCCUPATION, "");
    }

    public boolean isUserLogedOut() {
        boolean isPhoneEmpty = sp.getString(TELEPHONE, "").isEmpty();
        return isPhoneEmpty;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(Ctx, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        Ctx.startActivity(i);
        editor.putBoolean(IS_LOGIN, false);
    }

    /**
     * Quick check for login
     * **/
    public void getExistUser()
    {
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    public void checkUser()
    {
        if (!login())
        {
            // if IS_LOGIN is false then jump to Dashboard
            Ctx.startActivity(new Intent(Ctx, LoginActivity.class));
        }
        else
        {
            Ctx.startActivity(new Intent(Ctx, DashboardActivity.class));
        }
    }

    //to get the default value as false
    private boolean login()
    {
        return sp.getBoolean(IS_LOGIN, false);
    }
}
