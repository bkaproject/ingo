package com.cavalerie.vlc123.gogogo.Adapters.Loading;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cavalerie.vlc123.gogogo.R;
import com.cavalerie.vlc123.gogogo.model.Reservation;

import static com.cavalerie.vlc123.gogogo.Adapters.Loading.recyclerAdapterLoading.lListener;

public class LoadingViewHolder extends RecyclerView.ViewHolder{
    private TextView txtdepart, txtarrive, txthdepart, txtharrive, txtdatevoyage, txtplace, txtprice;

    //itemView est la vue correspondante à 1 cellule
    public LoadingViewHolder(View itemView) {
        super(itemView);

        txtdepart = (TextView) itemView.findViewById(R.id.txtdepart3);
        txtarrive = (TextView) itemView.findViewById(R.id.txtarrive3);
        txthdepart = (TextView) itemView.findViewById(R.id.txthdepart3);
        txtharrive = (TextView) itemView.findViewById(R.id.txtharrive3);
        txtdatevoyage = (TextView) itemView.findViewById(R.id.datevoyage3);
        txtplace = (TextView) itemView.findViewById(R.id.txtplace3);
        txtprice = (TextView) itemView.findViewById(R.id.txtprice3);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lListener != null) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        lListener.onItemClickL(position);
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
