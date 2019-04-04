package com.cavalerie.vlc123.gogogo.Fragment.Dashboard_fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cavalerie.vlc123.gogogo.Data.TravelManager;
import com.cavalerie.vlc123.gogogo.Activities.MapActivity;
import com.cavalerie.vlc123.gogogo.R;
import com.cavalerie.vlc123.gogogo.model.Mysingleton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class VoyageFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback {

    /**
     * Mapview constant
     */
    private MapView mMapView;

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    //////////////////////////////////////////////////////////////////////:

    private MaterialBetterSpinner spfonction, spdate;
    private EditText txtstart, txtend, txthourD, txthourA, price, place;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private LinearLayout layoutplace;
    private Button btnconfirm;
    private String spinner_value;

    private static final String TAG = "VoyageFragment";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    //date
    ArrayList<String> Mydatelist = new ArrayList<String>();
    private static final String TIME24HOURS_PATTERN =
            "([01]?[0-9]|2[0-3]):[0-5][0-9]";
    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    DateFormat dh = DateFormat.getDateInstance(DateFormat.FULL);
    Date dd;

    //clock;
    Calendar currentTime;
    int hour, minutes;

    String url = "http://ongo.hosteline.com/api/voyage/save";

    private void initGoogleMap(Bundle savedInstanceState) {
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_voyage, container, false);

        //find ID
        mMapView = (MapView) v.findViewById(R.id.map);
        spdate = (MaterialBetterSpinner) v.findViewById(R.id.spdate);
        spfonction = (MaterialBetterSpinner) v.findViewById(R.id.spfonction);
        txtstart = (EditText) v.findViewById(R.id.txtstart);
        txtend = (EditText) v.findViewById(R.id.txtend);
        txthourD = (EditText) v.findViewById(R.id.txthourD);
        txthourA = (EditText) v.findViewById(R.id.txthourA);
        price = (EditText) v.findViewById(R.id.price);
        place = (EditText) v.findViewById(R.id.place);
        btnconfirm = (Button) v.findViewById(R.id.btnconfirm);

        initGoogleMap(savedInstanceState);

        //spinner with many date
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date aTomorrow = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date bTomorrow = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date cTomorrow = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date dTomorrow = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date eTomorrow = calendar.getTime();

        //calendar.add(Calendar.DAY_OF_YEAR, 1);
        //Date dTomorrow = calendar.getTime();

        String tomorrowAsString = dh.format(tomorrow);
        String aTomorrowAsString = dh.format(aTomorrow);
        String bTomorrowAsString = dh.format(bTomorrow);
        String cTomorrowAsString = dh.format(cTomorrow);
        String dTomorrowAsString = dh.format(dTomorrow);
        String eTomorrowAsString = dh.format(eTomorrow);

        Mydatelist.add(tomorrowAsString);
        Mydatelist.add(aTomorrowAsString);
        Mydatelist.add(bTomorrowAsString);
        Mydatelist.add(cTomorrowAsString);
        Mydatelist.add(dTomorrowAsString);
        Mydatelist.add(eTomorrowAsString);

        //array adapter used to bind date values in the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, Mydatelist);
        spdate.setAdapter(adapter);

        //array adapter used to bind spfonction values in the spinner
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this.getActivity(), R.array.fonction, android.R.layout.simple_spinner_dropdown_item);
        spfonction.setAdapter(adapter1);

        //clock
        currentTime = Calendar.getInstance();
        hour = currentTime.get(Calendar.HOUR_OF_DAY);
        minutes = currentTime.get(Calendar.MINUTE);

        //on click
        btnconfirm.setOnClickListener(this);
        txtstart.setOnClickListener(this);
        txthourA.setOnClickListener(this);
        txthourD.setOnClickListener(this);

        spfonction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                spinner_value = adapterView.getItemAtPosition(position).toString();
                Log.d("Result :", spinner_value);
            }
        });


        if (isServicesOK()) {
            txtstart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), MapActivity.class);
                    startActivity(intent);
                }
            });

            txtend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), MapActivity.class);
                    startActivity(intent);
                }
            });
        }


        return v;
    }


    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity());

        if (available == ConnectionResult.SUCCESS) {
            //tout ce passe bien  et l'utilisateur peut executer des requetes MapActivity
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //une erreur est arrivé mais peut etre resolue
            Log.d(TAG, "isServicesOK: an error occured but you can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(getActivity(), "You can make MapActivity requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnconfirm:
                //converted date for database format
                //createTreavel();
                sqliteTravel();
                clear();
                break;

            case R.id.txthourA:
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        if (hourOfDay < 10 && minute < 10) {
                            txthourA.setText("0" + hourOfDay + ":" + "0" + minute);
                        } else if (hourOfDay < 10 && minute >= 10) {
                            txthourA.setText("0" + hourOfDay + ":" + minute);
                        } else if (hourOfDay >= 10 && minute < 10) {
                            txthourA.setText(hourOfDay + ":" + "0" + minute);
                        }
                    }
                }, hour, minutes, true);
                timePickerDialog.show();
                break;

            case R.id.txthourD:
                TimePickerDialog timePickerDialog1 = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        if (hourOfDay < 10 && minute < 10) {
                            txthourD.setText("0" + hourOfDay + ":" + "0" + minute);
                        } else if (hourOfDay < 10 && minute >= 10) {
                            txthourD.setText("0" + hourOfDay + ":" + minute);
                        } else if (hourOfDay >= 10 && minute < 10) {
                            txthourD.setText(hourOfDay + ":" + "0" + minute);
                        }
                    }
                }, hour, minutes, true);
                timePickerDialog1.show();
                break;
        }
    }

    private void sqliteTravel() {
        try {
            dd = dh.parse(spdate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final String date = df.format(dd);
        final String start = txtstart.getText().toString();
        final String end = txtend.getText().toString();
        final String hStart = txthourD.getText().toString();
        final String hEnd = txthourA.getText().toString();
        final String prix = price.getText().toString();
        final String places = place.getText().toString();

        TravelManager travelManager = new TravelManager(getContext());
        SQLiteDatabase db = travelManager.getWritableDatabase();

        TravelManager.putInformation(date, start, end, hStart, hEnd, places, prix, db);

        travelManager.close();
    }

    private void clear() {
        txtstart.setText("");
        txtend.setText("");
        txthourA.setText("");
        txthourD.setText("");
        place.setText("");
        price.setText("");
    }

    private void createTreavel() {
        try {
            dd = dh.parse(spdate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final String date = df.format(dd);
        final String start = txtstart.getText().toString();
        final String end = txtend.getText().toString();
        final String hStart = txthourD.getText().toString();
        final String hEnd = txthourA.getText().toString();
        final String prix = price.getText().toString();
        final String places = place.getText().toString();

        if (!validate()) {
            Toast.makeText(getContext(), "Verifier les informations entrées!", Toast.LENGTH_LONG).show();
            return;
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String code = jsonObject.getString("ServeurResponse");

                                if (code == "enregistrement reussi") {
                                    Toast.makeText(getContext(), "passé", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "reponse serveur différente", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "erreur 404", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("jourVoyage", date);
                    params.put("lieuDepart", start);
                    params.put("lieuArret", end);
                    params.put("heureDepart", hStart);
                    params.put("heureArrivee", hEnd);
                    params.put("prix", prix);
                    params.put("statut", spinner_value);
                    params.put("place", places);
                    return params;
                }
            };
            Mysingleton.getInstance(getActivity()).addToRequestqueue(stringRequest);
        }
    }

    private boolean validate() {
        boolean valid = true;

        String start = txtstart.getText().toString();
        String end = txtend.getText().toString();
        String hStart = txthourD.getText().toString();
        String hEnd = txthourA.getText().toString();
        String prix = price.getText().toString();
        String places = place.getText().toString();

        if (start.isEmpty()) {
            txtstart.setError("veuillez renseigner ce champ");
            valid = false;
        }

        if (end.isEmpty()) {
            txtend.setError("veuillez renseigner ce champ");
            valid = false;
        }

        if (hStart.isEmpty()) {
            txthourD.setError("veuillez renseigner ce champ");
            valid = false;
        } else if (!Pattern.matches(TIME24HOURS_PATTERN, hStart)) {
            txthourD.setError("veuillez rentrer une heure convenable");
            valid = false;
        }

        if (hEnd.isEmpty()) {
            txthourA.setError("veuillez renseigner ce champ");
            valid = false;
        } else if (!Pattern.matches(TIME24HOURS_PATTERN, hEnd)) {
            txthourA.setError("veuillez rentrer une heure convenable");
            valid = false;
        }

        if (prix.isEmpty()) {
            price.setError("veuillez renseigner ce champ");
            valid = false;
        } else if (prix.length() > 5) {
            price.setError("veuillez entrer un prix raisonnable");
            valid = false;
        }

        if (places.isEmpty()) {
            place.setError("veuillez renseigner ce champ");
            valid = false;
        } else if (places.length() > 2) {
            place.setError("veuillez entrer un nombre de place raisonnable");
            valid = false;
        }

        return valid;
    }

    private void selectedTimeFormat(int hour) {
        if (hour == 0) {
            hour += 12;
        } else if (hour > 12) {
            hour += 12;
        }
    }

    /**
     * Mapview implementation
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
