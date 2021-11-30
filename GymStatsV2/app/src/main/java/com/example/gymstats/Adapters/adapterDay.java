package com.example.gymstats.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.gymstats.Data.Gym.Excercises;
import com.example.gymstats.R;
import java.util.List;

public class adapterDay extends BaseAdapter {
    private Context context;
    List<Excercises> currexc;

    public adapterDay(Context context,List<Excercises> currexc){
        this.context = context;
        this.currexc = currexc;
    }

    @Override
    public int getCount() {
        return currexc.size();
    }

    @Override
    public Excercises getItem(int position) {
        return currexc.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View customadapter = convertView;
        if(customadapter==null){
            customadapter = LayoutInflater.from(context).inflate(R.layout.adapter_day,null,false);

        }
        TextView name = customadapter.findViewById(R.id.excercisename);
        TextView setsreps = customadapter.findViewById(R.id.setsreps);

        name.setText(currexc.get(position).getName()+": ".toUpperCase());
        setsreps.setText(currexc.get(position).getSets()+"χ"+currexc.get(position).getReps());

        return customadapter;
    }
}
