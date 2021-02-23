package cz.uhk.corona;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import cz.uhk.corona.model.CovidData;
import cz.uhk.corona.model.StatsViewModel;
import cz.uhk.corona.ui.CoronaAdapter;

public class MainActivity extends AppCompatActivity {
    Button button;
    RecyclerView recyclerView;
    private StatsViewModel statsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvItems);

        statsViewModel = new ViewModelProvider(this).get(StatsViewModel.class);
        loadData();
    }

    public void loadData() {
        Handler handler = new Handler(Looper.getMainLooper());
        Executor ex = Executors.newSingleThreadExecutor();
        ex.execute(()->{
            Date date;
            CovidData covidData;
            covidData = statsViewModel.getCurrentData();
            handler.post(()->
                recyclerView.setAdapter(new CoronaAdapter(covidData))
            );
        });
    }

}