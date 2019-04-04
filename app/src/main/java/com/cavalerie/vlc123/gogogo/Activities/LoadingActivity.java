package com.cavalerie.vlc123.gogogo.Activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cavalerie.vlc123.gogogo.Data.TravelManager;
import com.cavalerie.vlc123.gogogo.R;
import com.cavalerie.vlc123.gogogo.Adapters.Loading.recyclerAdapterLoading;
import com.cavalerie.vlc123.gogogo.model.Reservation;

import java.util.ArrayList;

public class LoadingActivity extends AppCompatActivity implements recyclerAdapterLoading.OnItemClickListenerL{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private recyclerAdapterLoading recyclerAdapterLoading;
    private ArrayList<Reservation> travelList;

    private LinearLayout detail, edit, delete;
    private BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        travelList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recylcerViewLoading);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        Load(travelList);

        recyclerAdapterLoading = new recyclerAdapterLoading(travelList);
        recyclerView.setAdapter(recyclerAdapterLoading);
        recyclerAdapterLoading.setOnItemClickListenerL(LoadingActivity.this);

        createBottomSheetDialog();
    }

    private void Load(ArrayList<Reservation> a) {

        TravelManager travelManager = new TravelManager(this);
        SQLiteDatabase sqLiteDatabase = travelManager.getReadableDatabase();

        Cursor cursor = travelManager.getInformation(sqLiteDatabase);

        cursor.moveToFirst();
        do {
            Reservation reservation = new Reservation(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
            a.add(reservation);
        }while (cursor.moveToNext());

        travelManager.close();
    }

    private void createBottomSheetDialog()
    {
        if (bottomSheetDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.bottomsheetdialog, null);

            //bottomSheet find ID
            detail = (LinearLayout) view.findViewById(R.id.llDetail);
            edit = (LinearLayout) view.findViewById(R.id.lledit);
            delete = (LinearLayout) view.findViewById(R.id.llDelete);

            bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(view);
        }
    }


    @Override
    public void onItemClickL(int position) {
        bottomSheetDialog.show();

        final Reservation reservation = travelList.get(position);

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), profilActivity.class));
                //Toast.makeText(getApplicationContext(), "detail pressed", Toast.LENGTH_SHORT).show();

                TextView date = (TextView) view.findViewById(R.id.txtdate);
                TextView start = (TextView) view.findViewById(R.id.txtstart2);
                TextView end = (TextView) view.findViewById(R.id.txtend2);
                TextView hstart = (TextView) view.findViewById(R.id.txthourD2);
                TextView hend = (TextView) view.findViewById(R.id.txthourA2);
                TextView place = (TextView) view.findViewById(R.id.txtplaceD2);
                TextView prix = (TextView) view.findViewById(R.id.txtpriceD2);



                date.setText(reservation.getDatevoyage());
                start.setText(reservation.getTxtdepart());
                end.setText(reservation.getTxtarrive());
                hstart.setText(reservation.getTxthdepart());
                hend.setText(reservation.getTxtharrive());
                place.setText(reservation.getTxtplace());
                prix.setText(reservation.getTxtprice());
                bottomSheetDialog.dismiss();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), profilActivity.class));
                Toast.makeText(getApplicationContext(), "edit pressed", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), profilActivity.class));
                Toast.makeText(getApplicationContext(), "delete pressed", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });
    }


}
