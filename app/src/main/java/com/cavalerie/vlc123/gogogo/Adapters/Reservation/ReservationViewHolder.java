package com.cavalerie.vlc123.gogogo.Adapters.Reservation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cavalerie.vlc123.gogogo.R;
import com.cavalerie.vlc123.gogogo.model.Reservation;

import static com.cavalerie.vlc123.gogogo.Adapters.Reservation.recyclerAdapterReservation.mListener;

public class ReservationViewHolder extends RecyclerView.ViewHolder{
    private TextView txtdepart, txtarrive, txthdepart, txtharrive, txtdatevoyage, txtplace, txtprice;

    //itemView est la vue correspondante à 1 cellule
    public ReservationViewHolder(View itemView) {
        super(itemView);

        txtdepart = (TextView) itemView.findViewById(R.id.txtdepart);
        txtarrive = (TextView) itemView.findViewById(R.id.txtarrive);
        txthdepart = (TextView) itemView.findViewById(R.id.txthdepart);
        txtharrive = (TextView) itemView.findViewById(R.id.txtharrive);
        txtdatevoyage = (TextView) itemView.findViewById(R.id.datevoyage);
        txtplace = (TextView) itemView.findViewById(R.id.txtplace);
        txtprice = (TextView) itemView.findViewById(R.id.txtprice);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(position);
                    }
                }
            }
        });
    }

    public void bind (Reservation myObject) {
        txtdepart.setText(myObject.getTxtdepart());
        txtarrive.setText(myObject.getTxtarrive());
        txthdepart.setText(myObject.getTxthdepart());
        txtharrive.setText(myObject.getTxtharrive());
        txtdatevoyage.setText(myObject.getDatevoyage());
        txtplace.setText(myObject.getTxtplace());
        txtprice.setText(myObject.getTxtprice());
    }
}
