package cz.uhk.corona.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;

import cz.uhk.corona.R;
import cz.uhk.corona.model.CovidData;
import cz.uhk.corona.model.DayStats;

public class CoronaAdapter extends RecyclerView.Adapter<CoronaAdapter.ViewHolder> {
    private CovidData covidData;

    public CoronaAdapter(CovidData covidData) {
        this.covidData = covidData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stats_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DayStats dayStats = covidData.getData().get(position);
        String date = DateFormat.getDateInstance().format(dayStats.getDay());
        holder.textView.setText(date);
        holder.countView.setText(String.valueOf(dayStats.getPositive()));
        holder.pcrView.setText(String.valueOf(dayStats.getPcr()));
        holder.agView.setText(String.valueOf(dayStats.getAnti()));
        if (position % 2 == 0) {
            holder.rootView.setBackgroundColor(Color.rgb(230,230,230));
        } else {
            holder.rootView.setBackgroundColor(Color.rgb(255,255,255));
        }
    }

    @Override
    public int getItemCount() {
        return (covidData != null && covidData.getData() != null) ? covidData.getData().size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        private TextView textView;
        private TextView countView;
        private TextView pcrView;
        private TextView agView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.item_date);
            countView = itemView.findViewById(R.id.item_cnt);
            pcrView = itemView.findViewById(R.id.item_pcr);
            agView = itemView.findViewById(R.id.item_ag);
            rootView = itemView.findViewById(R.id.rootView);
        }

    }
}
