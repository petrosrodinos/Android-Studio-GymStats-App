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
    int sets;

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
        TextView set = customadapter.findViewById(R.id.set);
        TextView weight = customadapter.findViewById(R.id.weight);

        date.setText(detailslist.get(position).getDate());
        set.setText(String.valueOf(++sets));
        weight.setText(String.valueOf(detailslist.get(position).getWeight()));

        return customadapter;
    }
}
