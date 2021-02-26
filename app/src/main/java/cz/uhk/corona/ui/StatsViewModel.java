package cz.uhk.corona.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;

import cz.uhk.corona.db.CovidDatabase;
import cz.uhk.corona.db.DayStatsDao;
import cz.uhk.corona.model.CovidData;
import cz.uhk.corona.model.DayStats;
import cz.uhk.corona.ws.CovidService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ViewModel drzici data dennich statistik. Vyuziva LiveData, ktera drzi stav v ramci ziv. cyklu.
 * To, mimo jine, resi problem rekonfigurace Aktivit napr. pri zmene orientace displaye.
 * Kvuli DB potrebuje referenci na kontext, proto je jako predek
 * misto ViewModel pouzit AndroidViewModel
 * @see CovidData
 * @see AndroidViewModel
 */
public class StatsViewModel extends AndroidViewModel {
    /**
     * Live data
     */
    private MutableLiveData<CovidData> data;

    /**
     * Povinny konstruktor pri AndroidViewModel
     * @param application reference na aplikaci
     */
    public StatsViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Pristup k drzenym live datum
     * @return data
     */
    public LiveData<CovidData> getCurrentData() {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        return data;
    }

    /**
     * Ziskani dat z Room DB
     */
    public void loadDbData() {
        DayStatsDao dao = CovidDatabase.getInstance(getApplication()).getDayStatsDao();
        CovidData covidData = new CovidData();
        covidData.setData(dao.getAll());
        data.postValue(covidData); //update live dat -> provolani observeru (viz Aktivita)
    }

    /**
     * Ziskani dat z WS pomoci Retrofit2
     * @see CovidService
     */
    public void downloadData() {
        Executors.newSingleThreadExecutor().submit(()->{
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://lide.uhk.cz/fim/ucitel/kozelto1/prog/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            CovidService repository = retrofit.create(CovidService.class);
            try {
                CovidData covidData = repository.loadCurrentData().execute().body();
                Collections.reverse(covidData.getData());
                data.postValue(covidData); // update live dat - provola obsever
                updateDb();  //nactena data zaroven kesujeme do DB
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Odlozeni/persistence live dat do DB
     */
    private void updateDb() {
        DayStatsDao dao = CovidDatabase.getInstance(getApplication()).getDayStatsDao();
        List<DayStats> lst = data.getValue().getData();
        dao.insertAll(lst.toArray(new DayStats[0]));
    }

}
