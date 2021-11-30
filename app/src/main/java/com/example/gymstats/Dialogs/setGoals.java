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

public class setGoals extends AppCompatDialogFragment {
    private EditText calories,protein,carbs,fats;
    private setGoals.ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_setgoal,null);
        builder.setView(view)
                .setTitle("Set goal")
                .setNegativeButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String calories1 = calories.getText().toString();
                        String protein1 = protein.getText().toString();
                        String carbs1 = carbs.getText().toString();
                        String fats1 = fats.getText().toString();

                        if(!calories1.isEmpty() && !protein1.isEmpty() && !carbs1.isEmpty() && !fats1.isEmpty()){
                            if(calories1.length()<=5 && protein1.length()<=4 && carbs1.length()<=4 && fats1.length()<=4){
                                listener.setGoals(Integer.parseInt(calories1), Integer.parseInt(protein1),Integer.parseInt(carbs1),Integer.parseInt(fats1));
                            }

                        }
                    }
                })
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        calories= view.findViewById(R.id.calories);
        protein = view.findViewById(R.id.protein);
        carbs = view.findViewById(R.id.carbs);
        fats = view.findViewById(R.id.fats);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (setGoals.ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }
    public interface ExampleDialogListener {
        void setGoals(int calories,int protein,int carbs,int fats);
    }
}
