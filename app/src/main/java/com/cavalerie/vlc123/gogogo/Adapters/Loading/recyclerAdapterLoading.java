package com.cavalerie.vlc123.gogogo.Adapters.Loading;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cavalerie.vlc123.gogogo.R;
import com.cavalerie.vlc123.gogogo.model.Reservation;

import java.util.List;

public class recyclerAdapterLoading extends RecyclerView.Adapter<LoadingViewHolder>{
    private List<Reservation> list;
    public static OnItemClickListenerL lListener;


    public interface OnItemClickListenerL {
        void onItemClickL(int position);
    }

    public void setOnItemClickListenerL (OnItemClickListenerL listener) {
        lListener = listener;
    }

    public recyclerAdapterLoading(List<Reservation> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public LoadingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_loading_model, parent,false);
        return new LoadingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoadingViewHolder holder, int position) {
        Reservation myObject = list.get(position);
        holder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
