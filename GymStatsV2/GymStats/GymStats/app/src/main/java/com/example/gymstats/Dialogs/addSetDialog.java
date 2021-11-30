package com.example.gymstats.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.gymstats.R;

import androidx.appcompat.app.AppCompatDialogFragment;

public class addSetDialog extends AppCompatDialogFragment {
    private EditText weight,reps;
    private ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog2,null);
        builder.setView(view)
                .setTitle("Add Set")
                .setNegativeButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String weight1 = weight.getText().toString();
                        String reps1 = reps.getText().toString();
                        if(!weight1.isEmpty() && !reps1.isEmpty()) {
                            listener.applyTexts(Float.valueOf(weight1), Integer.parseInt(reps1));
                        }

                    }
                })
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        weight= view.findViewById(R.id.weight);
        reps = view.findViewById(R.id.reps);
        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }
    public interface ExampleDialogListener {
        void applyTexts(float weight1,int reps1);
    }
}
