package com.cavalerie.vlc123.gogogo.Fragment.Dashboard_fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.cavalerie.vlc123.gogogo.R;
import com.cavalerie.vlc123.gogogo.Adapters.Accueil.recyclerAdapter;
import com.cavalerie.vlc123.gogogo.model.Publication;

import java.util.ArrayList;
import java.util.List;

public class AccueilFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Publication> lists = new ArrayList<>();
    private FloatingActionButton fab, fabCamera, fabWrite;
    Animation fabOpen, fabClose, fabRclockwise, fabRAclockwise;
    private ImageView imgpost;
    public static final int CAMERA_REQUEST = 9999;
    private Boolean isOpen = false;
    public static String EXTRA_IMAGE;

    public AccueilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_accueil, container, false);

        //remplir les post
        ajouterPost();

        //find ID
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fabCamera = (FloatingActionButton) v.findViewById(R.id.fabcamera);
        fabWrite = (FloatingActionButton) v.findViewById(R.id.fabwrite);
        fabOpen = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        fabRclockwise = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_clockwise);
        fabRAclockwise = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_anticlockwise);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen) {
                    fabCamera.setVisibility(View.INVISIBLE);
                    fabWrite.setVisibility(View.INVISIBLE);
                    fab.startAnimation( fabRAclockwise);
                    fabCamera.setClickable(false);
                    fabWrite.setClickable(false);
                    isOpen = false;
                }else {
                    fabCamera.setVisibility(View.VISIBLE);
                    fabWrite.setVisibility(View.VISIBLE);
                    fab.startAnimation( fabRclockwise);
                    fabCamera.setClickable(true);
                    fabWrite.setClickable(true);
                    isOpen = true;
                }
            }
        });

        fabWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = getLayoutInflater().inflate(R.layout.activity_write_post, null);

                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //pour adapter en grille comme une RecyclerView, avec 2 cellules par ligne
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        //puis créer un MyAdapter, lui fournir notre liste de publication.
        //cet adapter servira à remplir notre recyclerview
        recyclerView.setAdapter(new recyclerAdapter(lists));

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void ajouterPost() {
        lists.add(new Publication("LG", "18:30", "Très beau voyage", "j'aime", "https://78.media.tumblr.com/255efd4bdade204f67cf0974bd200655/tumblr_pf78wrXC0Q1xq00c8o1_500.png"));
        lists.add(new Publication("Omoura", "18:30", "Très beau voyage", "j'aime", "/home/vlc123/Images/me/LG/travel2.png"));
        lists.add(new Publication("Mary", "18:30", "Très beau voyage", "j'aime", "/home/vlc123/Images/me/LG/travel2.png"));
    }
}

