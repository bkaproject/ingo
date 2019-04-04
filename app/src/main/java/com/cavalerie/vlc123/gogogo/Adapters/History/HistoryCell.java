package com.cavalerie.vlc123.gogogo.Adapters.History;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cavalerie.vlc123.gogogo.R;
import com.cavalerie.vlc123.gogogo.model.Reservation;
import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleViewHolder;

public class HistoryCell extends SimpleCell<Reservation, HistoryCell.ViewHolder>{


    public HistoryCell(@NonNull Reservation item)
    {
        super(item);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_history_model;
    }

    /**
     - Return a ViewHolder instance
     **/
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, @NonNull View cellView) {
        return new ViewHolder(cellView);
    }

    /**
     * bind data to widgets in our viewholder
     */
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int position, @NonNull Context context, Object payload) {
        viewHolder.txtdepart.setText(getItem().getTxtdepart());
        viewHolder.txtarrive.setText(getItem().getTxtarrive());
        viewHolder.txthdepart.setText(getItem().getTxthdepart());
        viewHolder.txtharrive.setText(getItem().getTxtharrive());
        viewHolder.txtdatevoyage.setText(getItem().getDatevoyage());
        viewHolder.txtplace.setText(getItem().getTxtplace());
        viewHolder.txtprice.setText(getItem().getTxtprice());
    }

    /**
     * Our viewHolder class.
     * Inner static class
     * define your view holder, which must extend SimpleViewHolder.
     **/
    static class ViewHolder extends SimpleViewHolder
    {
        private TextView txtdepart, txtarrive, txthdepart, txtharrive, txtdatevoyage, txtplace, txtprice;

        ViewHolder(View itemView)
        {
            super(itemView);
            txtdepart = (TextView) itemView.findViewById(R.id.txtdepart4);
            txtarrive = (TextView) itemView.findViewById(R.id.txtarrive4);
            txthdepart = (TextView) itemView.findViewById(R.id.txthdepart4);
            txtharrive = (TextView) itemView.findViewById(R.id.txtharrive4);
            txtdatevoyage = (TextView) itemView.findViewById(R.id.datevoyage4);
            txtplace = (TextView) itemView.findViewById(R.id.txtplace4);
            txtprice = (TextView) itemView.findViewById(R.id.txtprice4);
        }
    }

}
