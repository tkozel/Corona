package cz.uhk.corona.repo;

import cz.uhk.corona.model.CovidData;
import retrofit2.Call;
import retrofit2.http.GET;


public interface CovidRepository {
    @GET("covid.php")
    Call<CovidData> loadCurrentData();
}
