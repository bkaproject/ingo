package com.cavalerie.vlc123.gogogo.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cavalerie.vlc123.gogogo.Data.UserProfileManager;
import com.cavalerie.vlc123.gogogo.model.CountryData;
import com.cavalerie.vlc123.gogogo.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView logo;
    private Button btnconnexion;
    private ProgressBar progressBar;
    private TextView txtTelephone, txtfacebookCon, txtgoogleplusCon;
    private Spinner spcountry;
    private RelativeLayout corps;
    public static Animation appear, uptodown, downtoup, rotate;
    public static FirebaseUser user;
    UserProfileManager userProfileManager;
    public static boolean byFacebook = false, byGoogle = false;
    /*private boolean dispatch = true;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return dispatch && super.dispatchTouchEvent(ev);
    }*/

    private FirebaseAuth mAuth;

    //for facebook authentification
    private CallbackManager mCallbackManager;

    //for Google authentification
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        userProfileManager = new UserProfileManager(getApplicationContext());

        spcountry = (Spinner) findViewById(R.id.spcountry);
        txtTelephone = (TextView) findViewById(R.id.txtTelephone);
        btnconnexion = (Button) findViewById(R.id.btnconnexion);
        logo = (ImageView) findViewById(R.id.logo);
        corps = (RelativeLayout) findViewById(R.id.corps);
        txtgoogleplusCon = (TextView) findViewById(R.id.txtgoogleplusCon);
        txtfacebookCon = (TextView) findViewById(R.id.txtfacebookCon);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //array adapter used to bind spfonction values in the spinner
        spcountry.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        //animation
        appear = AnimationUtils.loadAnimation(this, R.anim.appear);
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);

        //firebase get current user
        user = FirebaseAuth.getInstance().getCurrentUser();

        corps.startAnimation(appear);
        logo.startAnimation(uptodown);
        btnconnexion.startAnimation(downtoup);

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();

        btnconnexion.setOnClickListener(this);
        txtfacebookCon.setOnClickListener(this);
        txtgoogleplusCon.setOnClickListener(this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (!byFacebook) {
            // Pass the activity result back to the Facebook SDK
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        else if (requestCode == RC_SIGN_IN) {
            com.google.android.gms.tasks.Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "signInWithCredential:failure", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }

                });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("TAG", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    private void updateUI() {

        FirebaseUser user = mAuth.getCurrentUser();

        if(!byFacebook) {
            if(checkfacebookUser(user.getEmail())) {
                Toast.makeText(this, "facebook user exist",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, DashboardActivity.class));
            }else {

                String displayName = user.getDisplayName();
                String fEmail = user.getEmail();

                Toast.makeText(this, "you're logged in",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, Sign2Activity.class);
                startActivity(i);

                //i.putExtra()

                byFacebook = true;
                finish();
            }
        } else if (!byGoogle) {
            if(checkfacebookUser(user.getEmail())) {
                Toast.makeText(this, "facebook user exist",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, DashboardActivity.class));
            }else {

                String displayName = user.getDisplayName();
                String fEmail = user.getEmail();

                Toast.makeText(this, "you're logged in",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, Sign2Activity.class);
                startActivity(i);

                //i.putExtra()

                byGoogle = true;
                finish();
            }
        }
    }

    private boolean checkfacebookUser(String email) {
        return false;
    }


    @Override
    public void onBackPressed() {
        //désactiver le retour au splashScreen
        moveTaskToBack(true);
    }


    public boolean validate(String telephone) {
        boolean valid = false;

        if(!telephone.isEmpty()) {
            if(telephone.length() == 9) {
                if (Pattern.matches("69\\d{7}||67\\d{7}||66\\d{7}||65\\d{7}||680\\d{6}", telephone)) {
                    valid = true;
                }else {
                    txtTelephone.setError("Le numero que vous avez saisie n'est pas valide pour cameroun");
                }
            } else {
                txtTelephone.setError("Le numero que vous avez saisie n'est pas valide");
            }
        }
        else {
            txtTelephone.setError("Le numero de téléphone n'a pas été renseigné");
        }
        return valid;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnconnexion:
                String telephone = txtTelephone.getText().toString();
                if(validate(telephone)) {
                    String code = CountryData.getCountryNamesAreaCodes[spcountry.getSelectedItemPosition()];
                    String number = txtTelephone.getText().toString().trim();
                    String phoneNumber = "+" + code + number;

                    Intent c = new Intent(LoginActivity.this, SignActivity.class);
                    c.putExtra("phoneNumber", phoneNumber);
                    startActivity(c);

                }
                break;

            case R.id.txtfacebookCon:
                Toast.makeText(LoginActivity.this, " clicked",
                        Toast.LENGTH_SHORT).show();
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("TAG", "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d("TAG", "facebook:onCancel");
                        // ...
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("TAG", "facebook:onError", error);
                        // ...
                    }
                });
                break;

            case R.id.txtgoogleplusCon:
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
                break;
        }
    }

}
