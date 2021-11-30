package com.example.gymstats.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.gymstats.Data.Scale.scaleDetails;
import com.example.gymstats.R;
import java.util.List;

public class adapterScale extends BaseAdapter {
    private Context context;
    private List<scaleDetails> detailslist;
    static int i,counter=0;
    String date2;

    public adapterScale(Context context,List<scaleDetails> detailslist){
        this.context = context;
        this.detailslist = detailslist;
    }

    @Override
    public int getCount() {
        return detailslist.size();
    }

    @Override
    public scaleDetails getItem(int position) {
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
            customadapter = LayoutInflater.from(context).inflate(R.layout.adapter_scale,null,false);
        }

        TextView date = customadapter.findViewById(R.id.date);
        TextView datetext = customadapter.findViewById(R.id.textView3);
        TextView set = customadapter.findViewById(R.id.set);
        TextView weight = customadapter.findViewById(R.id.weight);


        if(i==0){
            date2 = detailslist.get(position).getDate();
            date.setText(date2);
            set.setText(String.valueOf(++counter));
            weight.setText(String.valueOf(detailslist.get(position).getWeight()));
            i++;
        }else if(i>0 && date2.equals(detailslist.get(position).getDate())){
            date.setVisibility(View.GONE);
            datetext.setVisibility(View.GONE);
            set.setText(String.valueOf(++counter));
            weight.setText(String.valueOf(detailslist.get(position).getWeight()));
            i++;
        }else{
            counter=0;
            date2 = detailslist.get(position).getDate();
            date.setText(date2);
            set.setText(String.valueOf(++counter));
            weight.setText(String.valueOf(detailslist.get(position).getWeight()));
            i++;
        }

        if(i==detailslist.size()){
            counter=0;
            i=0;
        }

        return customadapter;
    }
}
