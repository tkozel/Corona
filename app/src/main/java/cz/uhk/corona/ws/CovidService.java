package cz.uhk.corona.ws;

import cz.uhk.corona.model.CovidData;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Interface webove sluzby volane pomoco Retrofit2
 * @see retrofit2.Retrofit
 */
public interface CovidService {
    @GET("covid.php")
    Call<CovidData> loadCurrentData();
}
