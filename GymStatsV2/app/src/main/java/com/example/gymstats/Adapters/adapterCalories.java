package com.example.gymstats.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.gymstats.Data.Calories.Calories;
import com.example.gymstats.R;
import java.util.List;


public class adapterCalories extends BaseAdapter {
    private Context context;
    private List<Calories> detailslist;
    int sets;

    public adapterCalories(Context context,List<Calories> detailslist){
        this.context = context;
        this.detailslist = detailslist;
    }

    @Override
    public int getCount() {
        return detailslist.size();
    }

    @Override
    public Calories getItem(int position) {
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
            customadapter = LayoutInflater.from(context).inflate(R.layout.adapter_calories,null,false);
        }

        TextView name = customadapter.findViewById(R.id.name);
        TextView calories = customadapter.findViewById(R.id.calories);
        TextView protein = customadapter.findViewById(R.id.protein);
        TextView carbs = customadapter.findViewById(R.id.carbs);
        TextView fats = customadapter.findViewById(R.id.fats);

        TextView prot = customadapter.findViewById(R.id.pro);
        TextView carb = customadapter.findViewById(R.id.car);
        TextView fato = customadapter.findViewById(R.id.fat);

        name.setText(detailslist.get(position).getName()+", ");
        calories.setText(String.valueOf(detailslist.get(position).getCalories()));
        int pro = detailslist.get(position).getProtein();
        int car = detailslist.get(position).getCarbs();
        int fat = detailslist.get(position).getFats();

        if(pro!=0 && car!=0 && fat!=0){
            protein.setText(String.valueOf(pro));
            carbs.setText(String.valueOf(car));
            fats.setText(String.valueOf(fat));
        }else{
            protein.setVisibility(View.GONE);
            carbs.setVisibility(View.GONE);
            fats.setVisibility(View.GONE);
            prot.setVisibility(View.GONE);
            carb.setVisibility(View.GONE);
            fato.setVisibility(View.GONE);
        }




        return customadapter;
    }
}
