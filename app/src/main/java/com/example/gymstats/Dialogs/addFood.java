package com.example.gymstats.Dialogs;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.gymstats.R;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class addFood extends DialogFragment {
    private EditText name,calories,carbs,fats,protein;
    private Button add,cancel;
    private static final String TAG = "MyCustomDialog";
    public OnInputSelected mOnInputSelected;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addfooddialog, container, false);


        name = view.findViewById(R.id.name);
        calories = view.findViewById(R.id.calories);
        protein = view.findViewById(R.id.protein);
        carbs = view.findViewById(R.id.carbs);
        fats = view.findViewById(R.id.fats);
        add = view.findViewById(R.id.add);
        cancel = view.findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name1 = name.getText().toString();
                String calories1 = calories.getText().toString();
                String protein1 = protein.getText().toString();
                String carbs1 = carbs.getText().toString();
                String fats1 = fats.getText().toString();

                if(!name.equals("") && name.length()<=20){

                    if(calories1.isEmpty() && (!protein1.isEmpty() && !carbs1.isEmpty() && !fats1.isEmpty())){
                        if(protein1.length()<=4 && carbs1.length()<=4 && fats1.length()<=4){
                            mOnInputSelected.sendInput(name1,"",protein1,carbs1,fats1);
                        }

                    }else if(!calories1.isEmpty() && (protein1.isEmpty() && carbs1.isEmpty() && fats1.isEmpty())){
                        if(calories1.length()<=6){
                            mOnInputSelected.sendInput(name1,calories1,"","","");
                        }

                    }else if(!calories1.isEmpty() && (!protein1.isEmpty() && !carbs1.isEmpty() && !fats1.isEmpty())){
                        if(calories1.length()<=6 && protein1.length()<=4 && carbs1.length()<=4 && fats1.length()<=4){
                            mOnInputSelected.sendInput(name1,calories1,protein1,carbs1,fats1);
                        }

                    }
                }


                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputSelected = (OnInputSelected) getTargetFragment();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage() );
        }
    }

    public interface OnInputSelected{
        void sendInput(String name,String calories,String protein,String carbs,String fats);
    }
}

