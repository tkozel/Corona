package cz.uhk.corona.model;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.Executors;

import cz.uhk.corona.repo.CovidRepository;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatsViewModel extends ViewModel {
    private MutableLiveData<CovidData> data;

    public LiveData<CovidData> getCurrentData() {
        if (data == null) {
            data = new MutableLiveData<>();
            loadData();
        }
        return data;
    }
    public void loadData() {
        Executors.newSingleThreadExecutor().submit(()->{
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://lide.uhk.cz/fim/ucitel/kozelto1/prog/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            CovidRepository repository = retrofit.create(CovidRepository.class);
            try {
                CovidData covidData = repository.loadCurrentData().execute().body();
                Collections.reverse(covidData.getData());
                data.postValue(covidData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
