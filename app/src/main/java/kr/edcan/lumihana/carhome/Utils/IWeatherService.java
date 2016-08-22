package kr.edcan.lumihana.carhome.Utils;

import kr.edcan.lumihana.carhome.Data.WeatherData;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by kimok_000 on 2016-08-21.
 */
public interface IWeatherService {
    @GET("weather/current/minutely/")
    Call<WeatherData> weatherWithAddress(@Query("appKey") String key, @Query("city") String city, @Query("county") String county, @Query("village") String village, @Query("version") int version);

    @GET("weather/current/minutely/")
    Call<WeatherData> weatherWithLocation(@Query("appKey") String key, @Query("lat") String lat, @Query("lon") String lon, @Query("version") int version);

    @GET("weather/current/minutely/")
    Call<WeatherData> weatherWithId(@Query("appKey") String key, @Query("stnid") int stnid, @Query("version") int version);
}
