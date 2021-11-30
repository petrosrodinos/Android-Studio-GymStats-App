package com.example.gymstats.Dialogs;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.example.gymstats.R;

import java.util.Locale;

import androidx.appcompat.app.AppCompatDialogFragment;

public class countdownDialog extends AppCompatDialogFragment {
    private CountDownTimer mCountDownTimer;
    private TextView text;
    int seconds,minutes;
    long timeleft,starttime;
    MediaPlayer player;
    Vibrator vib;

    public static countdownDialog newInstance(int min,int sec) {
        countdownDialog fragment = new countdownDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("min", min);
        bundle.putInt("sec", sec);
        fragment.setArguments(bundle);
        return fragment;
    }

    @SuppressLint("CutPasteId")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_countdown,null);

        minutes = getArguments().getInt("min");
        seconds = getArguments().getInt("sec");
        starttime = minutes*60000+(seconds+1)*1000;

        builder.setView(view)
                .setTitle("Timer")
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        vib.cancel();
                        stopPlayer();
                    }
                });

        text = view.findViewById(R.id.text);

        mCountDownTimer = new CountDownTimer(starttime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeleft = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                text.setText("FINISHED");
                startMusic();
            }
        }.start();

        return builder.create();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeleft / 1000) / 60;
        int seconds = (int) (timeleft / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        text.setText(timeLeftFormatted);
    }

    private void startMusic(){
        long[] pattern = {500,400};

        if (player == null) {
            try{
                player = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.song);
                player.start();
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopPlayer();
                    }
                });
                vib.vibrate(pattern,0);
            }catch (NullPointerException exc){
                Log.d("ggg","111");
            }


        }

    }

    private void stopPlayer() {
        try{
            if (player != null) {
                player.release();
                player = null;
            }
        }catch(NullPointerException exc){
            Log.d("ggg","222");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        stopPlayer();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}


