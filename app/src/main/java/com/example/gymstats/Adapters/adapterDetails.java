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
    int sets;

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
        TextView set = customadapter.findViewById(R.id.set);
        TextView weight = customadapter.findViewById(R.id.weight);
        TextView reps = customadapter.findViewById(R.id.reps);
        TextView notestext = customadapter.findViewById(R.id.notestext);
        TextView notes = customadapter.findViewById(R.id.notes);

        date.setText(detailslist.get(position).getDate());
        set.setText(String.valueOf(++sets));
        weight.setText(String.valueOf(detailslist.get(position).getWeight()));
        reps.setText(String.valueOf(detailslist.get(position).getReps()));
        if(!detailslist.get(position).getNotes().isEmpty()){
            notes.setText(detailslist.get(position).getNotes());
        }else{
            notes.setVisibility(View.GONE);
            notestext.setVisibility(View.GONE);
        }

        return customadapter;
    }
}
