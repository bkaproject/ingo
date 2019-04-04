package com.cavalerie.vlc123.gogogo.Adapters.Accueil;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cavalerie.vlc123.gogogo.R;
import com.cavalerie.vlc123.gogogo.model.Publication;
import com.squareup.picasso.Picasso;

public class AccueilViewHolder extends RecyclerView.ViewHolder{

    private TextView txtuser;
    private TextView txthour;
    //private TextView txtcommentaire;
    private TextView txttext;
    private TextView txtlike;
    private ImageView imgimage;

    //itemView est la vue correspondante à 1 cellule
    public AccueilViewHolder(View itemView) {
        super(itemView);

        txtuser = (TextView) itemView.findViewById(R.id.txtuser);
        txthour = (TextView) itemView.findViewById(R.id.txthour);
        //txtcommentaire = (TextView) itemView.findViewById(R.id.txtcommentaire);
        txttext = (TextView) itemView.findViewById(R.id.txttext);
        txtlike = (TextView) itemView.findViewById(R.id.txtlike);
        imgimage = (ImageView) itemView.findViewById(R.id.imgimage);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un Publication
    /*public void bind(Publication myObject){
        textViewView.setText(myObject.getText());
        Picasso.with(imageView.getContext()).load(myObject.getImageUrl()).centerCrop().fit().into(imageView);
    }*/

    public void bind (Publication myObject) {
        txtuser.setText(myObject.getTxtuser());
        txtlike.setText(myObject.getTxtlike());
        txthour.setText(myObject.getTxthour());
        txttext.setText(myObject.getTxttext());

        Picasso.get().load(myObject.getImgimage())
                .placeholder(R.drawable.skimg)
                .error(R.drawable.skimg1)
                .centerCrop().fit()
                .into(imgimage);
    }
}
