package kr.edcan.lumihana.carhome.Utils;

import android.util.Log;

import kr.edcan.lumihana.carhome.Data.WeatherData;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by kimok_000 on 2016-08-21.
 */
public class WeatherManager {
    private Retrofit retrofit;
    private IWeatherService weatherService;
    private Call<WeatherData> weather;
    private WeatherData data;

    private int version = 1;

    public WeatherManager(int version){
        this.version = version;

        initRetrofit();
    }

    private void initRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://apis.skplanetx.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherService = retrofit.create(IWeatherService.class);
    }

    public int getVersion(){
        return version;
    }

    public WeatherData getWeather(String city, String county, String village){
        weather = weatherService.weatherWithAddress(
                  getVersion(), city, county, village);
        weather.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Response<WeatherData> response, Retrofit retrofit) {
                if(response.code() == 200){
                    data = response.body();
                }else data = null;
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("WeatherManager", "Error to get weatherInfo");
                Log.e("WeatherManager", t.getMessage());
                data = null;
            }
        });

        return data;
    }

    public WeatherData getWeather(String lat, String lon){
        weather = weatherService.weatherWithLocation(
                getVersion(), lat, lon);
        weather.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Response<WeatherData> response, Retrofit retrofit) {
                if(response.code() == 200){
                    data = response.body();
                }else data = null;
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("WeatherManager", "Error to get weatherInfo");
                Log.e("WeatherManager", t.getMessage());
                data = null;
            }
        });

        return data;
    }

    public WeatherData getWeather(int stnid){
        weather = weatherService.weatherWithId(
                getVersion(), stnid);
        weather.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Response<WeatherData> response, Retrofit retrofit) {
                if(response.code() == 200){
                    data = response.body();
                }else data = null;
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("WeatherManager", "Error to get weatherInfo");
                Log.e("WeatherManager", t.getMessage());
                data = null;
            }
        });

        return data;
    }
}
