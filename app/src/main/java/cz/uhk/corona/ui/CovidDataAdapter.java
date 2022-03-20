package cz.uhk.corona.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.List;

import cz.uhk.corona.R;
import cz.uhk.corona.model.CovidData;
import cz.uhk.corona.model.DayStats;

/**
 * Adapter poskytujici pristup k datum pro RecyclerView.
 * @see RecyclerView
 */
public class CovidDataAdapter extends RecyclerView.Adapter<CovidDataAdapter.ViewHolder> {
    /**
     * Reference na data
     */
    private List<DayStats> covidData;

    public CovidDataAdapter(List<DayStats> covidData) {
        this.covidData = covidData;
    }

    /**
     * Vytvoreni views pro jeden item seznamu (RecyclerView)
     * @param parent view vlastnika
     * @param viewType typ view
     * @return instance viewHolderu
     * @see ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stats_item, parent, false);

        return new ViewHolder(v);
    }

    /**
     * "Plni" komponenty jednoho itemu daty
     * @param holder ViewHolder
     * @param position index bindovaneho itemu
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DayStats dayStats = covidData.get(position);
        String date = DateFormat.getDateInstance().format(dayStats.getDay());
        holder.textView.setText(date);
        holder.countView.setText(String.valueOf(dayStats.getPositive()));
        holder.pcrView.setText(String.valueOf(dayStats.getPcr()));
        holder.agView.setText(String.valueOf(dayStats.getAnti()));
        //barveni sudych/lichych polozek
        if (position % 2 == 0) {
            holder.rootView.setBackgroundColor(Color.rgb(230,230,230));
        } else {
            holder.rootView.setBackgroundColor(Color.rgb(255,255,255));
        }
    }

    /**
     * Pocet prvku v RecyclerView
     * @return pocet prvku
     */
    @Override
    public int getItemCount() {
        return (covidData != null && covidData != null) ? covidData.size() : 0;
    }

    /**
     * ViewHolder - drzi reference na kompoennty pro zobrazeni jedne polozky
     * viz res/layout/stats_item
     */
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
