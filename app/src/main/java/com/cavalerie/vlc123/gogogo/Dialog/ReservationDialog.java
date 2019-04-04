package com.cavalerie.vlc123.gogogo.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;

import com.cavalerie.vlc123.gogogo.R;

public class ReservationDialog extends AppCompatDialogFragment {

    private com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner spplace;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.model_reservation2, null);

        builder.setView(view);

        spplace = (com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner) view.findViewById(R.id.spplace);

        //array adapter used to bind spfonction values in the spinner
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this.getActivity(), R.array.place, android.R.layout.simple_spinner_dropdown_item);
        spplace.setAdapter(adapter1);

        return builder.create();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
