package com.cavalerie.vlc123.gogogo.Activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cavalerie.vlc123.gogogo.Data.UserProfileManager;
import com.cavalerie.vlc123.gogogo.R;
import com.cavalerie.vlc123.gogogo.model.Mysingleton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.roger.catloadinglibrary.CatLoadingView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.cavalerie.vlc123.gogogo.Activities.LoginActivity.rotate;
import static com.cavalerie.vlc123.gogogo.Activities.LoginActivity.uptodown;

public class SignActivity extends AppCompatActivity {

    Button btninscription;
    TextView txtcode, txtResendCode, Timer;
    ImageView imgsms, logo1;
    private String verificationId;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private FirebaseAuth mAuth;
    public static String telephone;
    //private ProgressBar pbsign;
    private CatLoadingView catLoadingView;
    UserProfileManager userProfileManager;
    private String url = "http://ongo.hosteline.com/api/auth/login";

    //timer
    private CountDownTimer countDownTimer;
    private long timerMilliSeconde = 120000; // 1 minute
    private Boolean timeRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        userProfileManager = new UserProfileManager(SignActivity.this);

        //find ID
        txtcode = (TextView) findViewById(R.id.txtCode);
        btninscription = (Button) findViewById(R.id.btninscription);
        Timer = (TextView) findViewById(R.id.Timer);
        imgsms = (ImageView) findViewById(R.id.imgsms);
        logo1 = (ImageView) findViewById(R.id.logo1);

        catLoadingView = new CatLoadingView();

        txtResendCode = (TextView) findViewById(R.id.txtResendCode);
        txtResendCode.setPaintFlags(txtResendCode.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);

        //startTimer
        startTimer();

        //implement custom progress bas
        //pbsign = (ProgressBar) findViewById(R.id.pbsign);
        //FadingCircle fadingCircle = new FadingCircle();
        //pbsign.setIndeterminateDrawable(fadingCircle);

        //Animation
        imgsms.startAnimation(rotate);
        logo1.startAnimation(uptodown);


        //code sent begin
        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("fr");

        final String phoneNumber = getIntent().getStringExtra("phoneNumber");
        sendVerificationCode(phoneNumber);

        telephone = phoneNumber;

        txtResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timeRunning == false) {
                    timerMilliSeconde = 120000;
                    sendVerificationCode(phoneNumber);
                    startTimer();
                    timeRunning =  true;
                }else {
                    Toast.makeText(SignActivity.this, "Veuillez attendre l'arrivé du code de confirmation", Toast.LENGTH_LONG).show();
                }
            }
        });

        btninscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = txtcode.getText().toString().trim();

                if(code.isEmpty()) {
                    txtcode.setError("Veuillez saisir le code de vérification");
                    txtcode.requestFocus();
                    return;
                } else if(code.length() < 6) {
                    txtcode.setError("Veuillez renseigner le code de vérification envoyé par SMS");
                    txtcode.requestFocus();
                    return;
                }
                else {
                    //pbsign.setVisibility(View.VISIBLE);
                    verifyCode(code);
                    //verify();
                    catLoadingView.show(getSupportFragmentManager(), "Chargement");
                }
            }
        });

    }

    private void verify() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(SignActivity.this, "On Response", Toast.LENGTH_SHORT).show();

                        try {

                            Toast.makeText(SignActivity.this, "Try begin", Toast.LENGTH_SHORT).show();

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");

                            Log.i("resultat", message);

                            switch (message)
                            {

                                case "success connected!":
                                    Toast.makeText(SignActivity.this, "User don't exist", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(SignActivity.this, Sign2Activity.class));
                                    finish();

                                    Log.d("Result", "connexion en cours");
                                    break;

                                case "utilisateur existe deja":
                                    Toast.makeText(SignActivity.this, "User exits", Toast.LENGTH_SHORT).show();

                                    JSONArray jsonArray = jsonObject.getJSONArray("user");
                                    JSONObject  jsonObjectUser = jsonArray.getJSONObject(0);

                                    String nom = jsonObjectUser.getString("nom");
                                    String prenom = jsonObjectUser.getString("prenom");
                                    String username = jsonObjectUser.getString("username");
                                    String email = jsonObjectUser.getString("email");
                                    String telephone = jsonObjectUser.getString("telephone");
                                    String cni = jsonObjectUser.getString("cni");
                                    String adresse = jsonObjectUser.getString("adresse");
                                    String statut = jsonObjectUser.getString("statut");
                                    String dateNaissance = jsonObjectUser.getString("dateNaissance");


                                    userProfileManager.saveUserInfo(nom,
                                            prenom,
                                            username,
                                            email,
                                            telephone,
                                            cni,
                                            adresse,
                                            statut,
                                            dateNaissance);

                                    Log.d("detail n", userProfileManager.getFirstname());
                                    Log.d("detail p", userProfileManager.getLastname());
                                    Log.d("detail user", userProfileManager.getUsername());
                                    Log.d("detail email", userProfileManager.getEmail());
                                    Log.d("detail num", userProfileManager.getTelephone());
                                    Log.d("detail cni", userProfileManager.getCni());

                                    switch (userProfileManager.getUsername())
                                    {
                                        case "null":
                                            Intent i = new Intent(SignActivity.this, Sign2Activity.class);
                                            startActivity(i);
                                            finish();
                                            break;

                                        default:
                                            userProfileManager.getExistUser();
                                            Intent j = new Intent(SignActivity.this, DashboardActivity.class);
                                            startActivity(j);
                                            finish();
                                            break;
                                    }

                                    Log.d("Result", "Connexion reussi");
                                    break;

                                default:
                                    Toast.makeText(SignActivity.this, "response not found", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (JSONException e) {
                            Toast.makeText(SignActivity.this, "catch begin", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                            //pbsign.setVisibility(View.INVISIBLE);
                            catLoadingView.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(SignActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        catLoadingView.dismiss();
                        //pbsign.setVisibility(View.INVISIBLE);
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("telephone", telephone);
                        return params;
                        }
                    };
        Mysingleton.getInstance(SignActivity.this).addToRequestqueue(stringRequest);
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timerMilliSeconde, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerMilliSeconde = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timeRunning = false;
                Log.d("Timer running value", timeRunning.toString());
            }
        }.start();
    }

    private void updateTimer() {
        int minutes = (int) timerMilliSeconde / 60000;
        int secondes = (int) timerMilliSeconde % 60000 / 1000;

        String timerLeftText;

        timerLeftText = "" + minutes;
        timerLeftText += ":";
        if(secondes < 10) timerLeftText += "0";
        timerLeftText += secondes;

        Timer.setText(timerLeftText);
    }


    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            verify();
                        } else {
                            //pbsign.setVisibility(View.INVISIBLE);
                            catLoadingView.dismiss();
                            txtcode.setError("code de vérification incorrect!");
                            txtcode.requestFocus();
                            Log.d("signInWithCredentiale", task.getException().getMessage());
                        }
                    }
                });
    }

    private void sendVerificationCode(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();

            if(code != null) {
                txtcode.setText(code);
                catLoadingView.show(getSupportFragmentManager(), "Chargement");
                //pbsign.setVisibility(View.INVISIBLE);
                verify();
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            //pbsign.setVisibility(View.INVISIBLE);
            //catLoadingView.dismiss();
            Toast.makeText(SignActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.w("TAG", "onVerificationFailed", e);
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Log.d("TAG", "onCodeSent:" + verificationId);
            verificationId = s;
            resendToken = forceResendingToken;
        }

        @Override
        public void onCodeAutoRetrievalTimeOut(String s) {
            super.onCodeAutoRetrievalTimeOut(s);
            verificationId = s;
        }
    };

}
