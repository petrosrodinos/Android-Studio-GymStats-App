package com.example.gymstats.Dialogs;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.example.gymstats.R;
import androidx.appcompat.app.AppCompatDialogFragment;

public class timerDialog extends AppCompatDialogFragment {
    private EditText minutes,seconds;
    int min1,sec1;

    @SuppressLint("CutPasteId")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_addtimer,null);

        builder.setView(view)
                .setTitle("Set Timer")
                .setNegativeButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String min = minutes.getText().toString();
                        String sec = seconds.getText().toString();
                        if(!min.isEmpty()){
                            if(Integer.parseInt(min)<59){
                                min1=Integer.parseInt(min);
                            }
                        }else{
                            min1=0;
                        }
                        if(!sec.isEmpty()){
                            if(Integer.parseInt(sec)<59){
                                sec1=Integer.parseInt(sec);
                            }
                        }else{
                            sec1=0;
                        }
                        if(min1!=0 || sec1!=0){
                            countdownDialog countdown = new countdownDialog().newInstance(min1,sec1);
                            countdown.show(getFragmentManager(),"countdowndialog");
                        }

                    }
                })
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        minutes = view.findViewById(R.id.minutes);
        seconds = view.findViewById(R.id.seconds);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}

