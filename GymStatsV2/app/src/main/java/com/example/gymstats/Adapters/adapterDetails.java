package com.example.gymstats.Adapters;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.gymstats.Data.Gym.Details;
import com.example.gymstats.R;
import java.util.List;

public class adapterDetails extends BaseAdapter {
    private Context context;
    private List<Details> detailslist;
    static int i,counter=0;
    String date2;

    public adapterDetails(Context context,List<Details> detailslist){
        this.context = context;
        this.detailslist = detailslist;
    }

    @Override
    public int getCount() {
        return detailslist.size();
    }

    @Override
    public Details getItem(int position) {
        return detailslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View customadapter = convertView;
        if(customadapter==null){
            customadapter = LayoutInflater.from(context).inflate(R.layout.adapter_details,null,false);
        }

        TextView date = customadapter.findViewById(R.id.date);
        TextView datetext = customadapter.findViewById(R.id.textView3);
        TextView set = customadapter.findViewById(R.id.set);
        TextView weight = customadapter.findViewById(R.id.weight);
        TextView reps = customadapter.findViewById(R.id.reps);
        TextView notestext = customadapter.findViewById(R.id.notestext);
        TextView notes = customadapter.findViewById(R.id.notes);

        if(i==0){
            date2 = detailslist.get(position).getDate();
            date.setText(date2);
            set.setText(String.valueOf(++counter));
            weight.setText(String.valueOf(detailslist.get(position).getWeight()));
            reps.setText(String.valueOf(detailslist.get(position).getReps()));
            if(!detailslist.get(position).getNotes().isEmpty()){
                notes.setText(detailslist.get(position).getNotes());
            }else{
                notes.setVisibility(View.GONE);
                notestext.setVisibility(View.GONE);
            }
            i++;
        }else if(i>0 && detailslist.get(position).getDate().equals(date2)){
            date.setVisibility(View.GONE);
            datetext.setVisibility(View.GONE);
            set.setText(String.valueOf(++counter));
            weight.setText(String.valueOf(detailslist.get(position).getWeight()));
            reps.setText(String.valueOf(detailslist.get(position).getReps()));
            if(!detailslist.get(position).getNotes().isEmpty()){
                notes.setText(detailslist.get(position).getNotes());
            }else{
                notes.setVisibility(View.GONE);
                notestext.setVisibility(View.GONE);
            }
            i++;
        }else{
            counter=0;
            date2 = detailslist.get(position).getDate();
            date.setText(date2);
            set.setText(String.valueOf(++counter));
            weight.setText(String.valueOf(detailslist.get(position).getWeight()));
            reps.setText(String.valueOf(detailslist.get(position).getReps()));
            if(!detailslist.get(position).getNotes().isEmpty()){
                notes.setText(detailslist.get(position).getNotes());
            }else{
                notes.setVisibility(View.GONE);
                notestext.setVisibility(View.GONE);
            }
            i++;
        }

        if(i==detailslist.size()){
            i=0;
            counter=0;
        }


        return customadapter;
    }
}
