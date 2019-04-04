package com.cavalerie.vlc123.gogogo.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cavalerie.vlc123.gogogo.R;
import com.cavalerie.vlc123.gogogo.Adapters.History.HistoryCell;
import com.cavalerie.vlc123.gogogo.model.Reservation;
import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleRecyclerView;
import com.jaychang.srv.decoration.SectionHeaderProvider;
import com.jaychang.srv.decoration.SimpleSectionHeaderProvider;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    SimpleRecyclerView simpleRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        simpleRecyclerView = findViewById(R.id.SimplerecylcerViewHistory);
        this.addRecyclerHeaders();
        this.bindData();
    }

    /**
     * create de recyclerViewHeaders
     */
    private void addRecyclerHeaders()
    {
        SectionHeaderProvider<Reservation> sectionHeaderProvider = new SimpleSectionHeaderProvider<Reservation>() {
            @NonNull
            @Override
            public View getSectionHeaderView(@NonNull Reservation item, int position) {
                View view = LayoutInflater.from(HistoryActivity.this).inflate(R.layout.headerloading, null, false);
                TextView headerDate = view.findViewById(R.id.headertxt);
                headerDate.setText(item.getDatevoyage());
                return view;
            }

            @Override
            public boolean isSameSection(@NonNull Reservation item, @NonNull Reservation nextItem) {
                return item.getDatevoyage() == nextItem.getDatevoyage();
            }

            // Optional, whether the header is sticky, default false
            @Override
            public boolean isSticky() {
                return true;
            }
        };
        simpleRecyclerView.setSectionHeader(sectionHeaderProvider);
    }

    /**
     * Bind data to our RecyclerView
     */
    private void bindData()
    {
        List<Reservation> reservation = getData();

        // Custom sort according to categories
        /*Collections.sort(reservation, new Comparator<Reservation>() {
            @Override
            public int compare(Reservation reservation, Reservation t1) {
                return reservation.getDatevoyage() - t1.getDatevoyage();
            }
        });*/

        List<HistoryCell>  loadingCells = new ArrayList<>();

        for (Reservation reservation1 : reservation)
        {
            HistoryCell loadingCell = new HistoryCell(reservation1);

            loadingCell.setOnCellClickListener(new SimpleCell.OnCellClickListener<Reservation>() {
                @Override
                public void onCellClicked(@NonNull Reservation item) {
                    Toast.makeText(HistoryActivity.this, item.getTxtprice(), Toast.LENGTH_SHORT).show();
                }
            });

            loadingCells.add(loadingCell);
        }

        simpleRecyclerView.addCells(loadingCells);
    }


    /**
     * Data Source
     * returns an arrayList of galaxies.
     */
    private ArrayList<Reservation> getData()
    {
        ArrayList<Reservation> reservations = new ArrayList<>();

        //intance reservation load and add them to reservation list
        reservations.add(new Reservation("10/10/2018", "Logbessou - carrefour", "bonaberi - ndobo", "10:00", "11:00", "3", "1500"));
        reservations.add(new Reservation("12/10/2018", "Logbessou - carrefour", "Akwa - boulangerie", "09:00", "09:40", "2", "800"));
        reservations.add(new Reservation("14/10/2018", "mbassong - carrefour", "bepanda - laverie", "18:00", "18:30", "4", "500"));

        return reservations;
    }
}
