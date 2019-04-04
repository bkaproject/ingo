package com.cavalerie.vlc123.gogogo.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cavalerie.vlc123.gogogo.R;
import com.cavalerie.vlc123.gogogo.Data.UserProfileManager;
import com.cavalerie.vlc123.gogogo.model.Mysingleton;
import com.roger.catloadinglibrary.CatLoadingView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import static com.cavalerie.vlc123.gogogo.Activities.LoginActivity.byFacebook;
import static com.cavalerie.vlc123.gogogo.Activities.LoginActivity.uptodown;

public class Sign2Activity extends AppCompatActivity {

    private TextView txtfirstname, txtlastname, txtusername, txtdateNaissance;
    private ImageView logo2;
    private Button btninscription;
    private DatePickerDialog mDateSetListener;
    final Calendar myCalendar = Calendar.getInstance();
    private CatLoadingView catLoadingView;
    UserProfileManager userProfileManager;

    static String url = "http://ongo.hosteline.com/api/user/update";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign2);

        //find id
        txtfirstname = (TextView) findViewById(R.id.txtfirstname);
        txtlastname = (TextView) findViewById(R.id.txtlastname);
        txtusername = (TextView) findViewById(R.id.txtusername);
        txtdateNaissance = (TextView) findViewById(R.id.txtdateNaissance);
        logo2 = (ImageView) findViewById(R.id.logo2);
        btninscription = (Button) findViewById(R.id.btninscription);

        catLoadingView = new CatLoadingView();

        //animation
        logo2.startAnimation(uptodown);

        //start activities
        btninscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate(txtfirstname.getText().toString().trim(), txtlastname.getText().toString().trim(), txtusername.getText().toString().trim())) {
                    catLoadingView.show(getSupportFragmentManager(), "Chargement");
                    storedata();
                }
            }
        });

        txtdateNaissance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Sign2Activity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        if (byFacebook) {
            loadFacebookInfos();
        }
    }

    private void loadFacebookInfos() {

    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        txtdateNaissance.setText(sdf.format(myCalendar.getTime()));
    }


    private void storedata() {

        Toast.makeText(Sign2Activity.this, "in the method", Toast.LENGTH_SHORT).show();

        final String nom = txtfirstname.getText().toString().trim();
        final String prenom = txtlastname.getText().toString().trim();
        final String username = txtusername.getText().toString().trim();
        final String dateNaissance = txtdateNaissance.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(Sign2Activity.this, "try begin", Toast.LENGTH_SHORT).show();

                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("message");

                            Toast.makeText(Sign2Activity.this, "try continue", Toast.LENGTH_SHORT).show();

                            switch (code)
                            {

                                case "saved perfect":
                                    Toast.makeText(Sign2Activity.this, "in the condition", Toast.LENGTH_SHORT).show();

                                    userProfileManager = new UserProfileManager(Sign2Activity.this);

                                    userProfileManager.saveUserInfo
                                            (
                                                    nom,
                                                    prenom,
                                                    username,
                                                    "non defini",
                                                    SignActivity.telephone,
                                                    "non defini",
                                                    "non defini",
                                                    "non defini",
                                                    dateNaissance
                                            );

                                    startActivity(new Intent(Sign2Activity.this, DashboardActivity.class));
                                    finish();

                                    //make user login in the shared preference
                                    userProfileManager.getExistUser();

                                    Log.d("Result", "Connexion reussi");
                                    break;
                            }

                        } catch (JSONException e) {
                            Toast.makeText(Sign2Activity.this, "catch exception", Toast.LENGTH_SHORT).show();
                            catLoadingView.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Sign2Activity.this, "VolleyError error", Toast.LENGTH_SHORT).show();
                        catLoadingView.dismiss();
                        error.printStackTrace();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("telephone", SignActivity.telephone);
                params.put("nom", nom);
                params.put("prenom", prenom);
                params.put("username", username);
                params.put("dateNaissance", dateNaissance);
                return params;
            }
        };
        Mysingleton.getInstance(Sign2Activity.this).addToRequestqueue(stringRequest);
    }

    private boolean validate(String nom, String prenom, String username) {
        boolean valid = true;

        if (nom.trim().isEmpty()) {
            txtfirstname.setError("veuillez renseigner votre nom");
            valid = false;
        } else if (!Pattern.matches("[a-zA-Z]{3,10}", nom)) {
            txtfirstname.setError("Le nom que vous avez saisie n'est pas valide");
            valid = false;
        } else{
            txtfirstname.setError(null);
        }

        if (prenom.trim().isEmpty()) {
            txtlastname.setError("veuillez renseigner votre prenom");
            valid = false;
        } else if (!Pattern.matches("[a-zA-Z]{3,25}", prenom)) {
            txtlastname.setError("Le prenom que vous avez saisie n'est pas valide");
            valid = false;
        } else {
            txtlastname.setError(null);
        }

        if (username.trim().isEmpty()) {
            txtusername.setError("veuillez renseigner votre nom d'utilisateur");
            valid = false;
        } else if (!Pattern.matches("[a-zA-Z0-9]{3,25}", username)) {
            txtusername.setError("le nom d'utilisateur que vous avez saisie n'est pas valide");
            valid = false;
        } else {
            txtusername.setError(null);
        }

        return valid;
    }

    /*@Override
    public void onBackPressed() {
        //désactiver le retour au splashScreen
        moveTaskToBack(true);
    }*/

}
