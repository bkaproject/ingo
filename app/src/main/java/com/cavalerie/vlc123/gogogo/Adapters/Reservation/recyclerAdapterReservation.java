package com.cavalerie.vlc123.gogogo.Adapters.Reservation;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cavalerie.vlc123.gogogo.R;
import com.cavalerie.vlc123.gogogo.model.Reservation;

import java.util.List;

public class recyclerAdapterReservation extends RecyclerView.Adapter<ReservationViewHolder>{
    private List<Reservation> list;
    public static OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener (OnItemClickListener listener) {
        mListener = listener;
    }

    public recyclerAdapterReservation (List<Reservation> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_reservation, parent,false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        Reservation myObject = list.get(position);
        holder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
