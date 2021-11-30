package com.example.gymstats.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.gymstats.Data.SwimmingCycling.swimmingCyclingdetails;
import com.example.gymstats.R;
import java.util.List;

public class adapterCycling extends BaseAdapter {
    private Context context;
    private List<swimmingCyclingdetails> detailslist;
    int sets;

    public adapterCycling(Context context,List<swimmingCyclingdetails> detailslist){
        this.context = context;
        this.detailslist = detailslist;
    }

    @Override
    public int getCount() {
        return detailslist.size();
    }

    @Override
    public swimmingCyclingdetails getItem(int position) {
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
            customadapter = LayoutInflater.from(context).inflate(R.layout.adapter_cycling,null,false);
        }

        TextView date = customadapter.findViewById(R.id.date);
        TextView distance = customadapter.findViewById(R.id.distance);
        TextView set = customadapter.findViewById(R.id.set);
        TextView time = customadapter.findViewById(R.id.time);

        date.setText(detailslist.get(position).getDate());
        set.setText(String.valueOf(++sets));
        distance.setText(String.valueOf(detailslist.get(position).getDistance()));
        time.setText(String.valueOf(detailslist.get(position).getTime()));

        return customadapter;
    }
}
