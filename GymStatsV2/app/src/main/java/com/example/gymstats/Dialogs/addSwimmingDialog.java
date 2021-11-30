package com.example.gymstats.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.example.gymstats.R;

public class addSwimmingDialog extends AppCompatDialogFragment{
    private EditText distance,time;
    private addSwimmingDialog.ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_swimming,null);
        builder.setView(view)
                .setTitle("Add Set")
                .setNegativeButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String distance1 = distance.getText().toString();
                        String time1 = time.getText().toString();
                        if(!time1.isEmpty() && !distance1.isEmpty() && time1.length()<=4 && distance1.length()<=5) {
                            listener.addSwimming(Float.parseFloat(distance1),Float.parseFloat(time1));
                        }
                    }
                })
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        distance= view.findViewById(R.id.distance);
        time = view.findViewById(R.id.time);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (addSwimmingDialog.ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }
    public interface ExampleDialogListener {
        void addSwimming(float distance,float time);
    }
}
