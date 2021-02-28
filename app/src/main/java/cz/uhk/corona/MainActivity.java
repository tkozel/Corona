package cz.uhk.corona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import java.util.List;
import java.util.concurrent.Executors;

import cz.uhk.corona.db.CovidDatabase;
import cz.uhk.corona.db.DayStatsDao;
import cz.uhk.corona.model.DayStats;
import cz.uhk.corona.ui.StatsViewModel;
import cz.uhk.corona.ui.CovidDataAdapter;

/**
 * Hlavni (a jedina) aktivita aplikace.
 * Obsahuje OptionsMenu a RecyclerView (seznam) pro zobrazeni covidovych statistik
 */
public class MainActivity extends AppCompatActivity {
    // reference na komponenty UI - viz res/layout/activity_main
    Button button;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeLayout;
    /**
     * stav/data drzena ve ViewModelu s live daty
     */
    private StatsViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvItems);

        /*
         * Ziskani reference na View model, diky pouziti predka AndroidViewModel je trochu
         * slozitejsi ziskani instance. U ViewModel je vyrazne jednodussi
         */
        model = new ViewModelProvider(this,
                new ViewModelProvider.Factory() {
                    @NonNull
                    @Override
                    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                        return (T) new StatsViewModel(getApplication());
                    }
                }).get(StatsViewModel.class);

        //Zaregistrovani observeru live dat, ktery updatuje pri zmene UI/RecyclerView
        model.getCurrentData().observe(this, (covidData)->
                recyclerView.setAdapter(new CovidDataAdapter(covidData)));

        //zajisteni reloadu dat z webu - vyzaduje zabaleni do SwipeRefreshLayoutu
        swipeLayout = findViewById(R.id.swipe_layout);
        swipeLayout.setOnRefreshListener(() -> downloadData(null));

        // uvodni nacteni dat z DB (cache), pokud tam neco je
        loadData();
    }

    /**
     * Nacteni dat z DB - musi jit mimo hlavni thread (tj. na pozadi)
     * @see Executors
     */
    private void loadData() {
        Executors.newSingleThreadExecutor().execute(()-> model.loadDbData());
    }

    /**
     * Download dat z webu (ws), vola se i z menu
     * @param item reference na menuI|tem
     */
    public void downloadData(MenuItem item) {
        swipeLayout.setRefreshing(true);
        Executors.newSingleThreadExecutor().execute(()->{
            model.downloadData();
            swipeLayout.setRefreshing(false);
        });
    }

    public void showChart(MenuItem item) {
        Intent intent = new Intent(this, ChartActivity.class);
        startActivity(intent);
    }

    /**
     * Vytvoreni menu
     * @param menu menu
     * @return splneno
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }
}