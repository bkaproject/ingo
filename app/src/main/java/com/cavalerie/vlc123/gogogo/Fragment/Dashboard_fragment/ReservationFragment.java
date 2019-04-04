package com.cavalerie.vlc123.gogogo.Fragment.Dashboard_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cavalerie.vlc123.gogogo.Dialog.ReservationDialog;
import com.cavalerie.vlc123.gogogo.R;
import com.cavalerie.vlc123.gogogo.Adapters.Reservation.recyclerAdapterReservation;
import com.cavalerie.vlc123.gogogo.model.Mysingleton;
import com.cavalerie.vlc123.gogogo.model.Reservation;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReservationFragment extends Fragment implements recyclerAdapterReservation.OnItemClickListener{

    public static final String EXTRA_txtdepart = "txtdepart", EXTRA_txtarrive = "txtarrive", EXTRA_txthdepart = "txthdepart",
            EXTRA_txtharrive = "txtharrive", EXTRA_datevoyage = "datevoyage", EXTRA_txtplace = "txtplace", EXTRA_txtprice = "txtprice";

    //private MaterialBetterSpinner spplace;
    private TextView datevoyage2, txtdepart2, txtarrive2, txthdepart2, txtharrive2, txtplace2, txtprice2;

    private RecyclerView recyclerView;
    private MaterialBetterSpinner spregione;
    private recyclerAdapterReservation rAdapter;

    private static final String URL_RESERVATION = "http://ongo.hosteline.com/api/voyage/view";

    //a list to store all the products
    List<Reservation> reservationList;

    public ReservationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reservation, container, false);

        //initializing the productlist
        reservationList = new ArrayList<>();

        //find ID
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewR);
        spregione = (MaterialBetterSpinner) v.findViewById(R.id.spregione);
        rAdapter = new recyclerAdapterReservation(reservationList);

        //array adapter used to bind values in the spinner
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.region, android.R.layout.simple_spinner_dropdown_item);
        spregione.setAdapter(adapter);

        //array adapter (reservation 2) used to bind values in the spinner
        //ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.place, android.R.layout.simple_spinner_dropdown_item);
        //spplace.setAdapter(arrayAdapter);


        //to display it in recyclerview
        loadProducts();

        rAdapter.setOnItemClickListener(ReservationFragment.this);

        return v;
    }

    @Override
    public void onResume() {
        loadProducts();
        super.onResume();
    }

    private void loadProducts() {
        StringRequest   stringRequest = new StringRequest(Request.Method.GET, URL_RESERVATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("les voyages");

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {
                                //getting product object from json array
                                JSONObject reservation = array.getJSONObject(i);

                                //adding the product to product list
                                reservationList.add(new Reservation(
                                        reservation.getString("lieuDepart"),
                                        reservation.getString("lieuArret"),
                                        reservation.getString("heureDepart"),
                                        reservation.getString("heureArrivee"),
                                        reservation.getString("jourVoyage"),
                                        reservation.getString("prix")
                                ));
                            }

                            //form of row, here it's vertical, same like a ListView
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            //setup adapter to recyclerview
                            recyclerView.setAdapter(rAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        Mysingleton.getInstance(getActivity()).addToRequestqueue(stringRequest);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onItemClick(int position) {
        ReservationDialog reservationDialog = new ReservationDialog();
        reservationDialog.show(getFragmentManager(), "");
    }
}
