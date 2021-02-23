package cz.uhk.corona.model;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.Collections;

import cz.uhk.corona.repo.CovidRepository;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatsViewModel extends ViewModel {
    private CovidData covidData;

    public CovidData getCurrentData() {
        if (covidData == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://lide.uhk.cz/fim/ucitel/kozelto1/prog/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            CovidRepository repository = retrofit.create(CovidRepository.class);
            try {
                covidData = repository.loadCurrentData().execute().body();
                Collections.reverse(covidData.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return covidData;
    }

}
