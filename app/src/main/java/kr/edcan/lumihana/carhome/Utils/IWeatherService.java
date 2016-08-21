package kr.edcan.lumihana.carhome.Utils;

import kr.edcan.lumihana.carhome.Data.WeatherData;
import retrofit.Call;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by kimok_000 on 2016-08-21.
 */
public interface IWeatherService {
    @GET("/weather/current/minutely")
    @FormUrlEncoded
    Call<WeatherData> weatherWithAddress(@Query("version") int version,
                                         @Query("city") String city, @Query("county") String county, @Query("village") String village);

    @GET("/weather/current/minutely")
    @FormUrlEncoded
    Call<WeatherData> weatherWithLocation(@Query("version") int version,
                                          @Query("lat") String lat, @Query("lon") String lon);


    @GET("/weather/current/minutely")
    @FormUrlEncoded
    Call<WeatherData> weatherWithId(@Query("version") int version,
                                    @Query("stnid") int stnid);
}
