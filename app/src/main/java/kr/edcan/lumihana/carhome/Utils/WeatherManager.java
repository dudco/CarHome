package kr.edcan.lumihana.carhome.Utils;

import android.content.Context;
import android.content.SharedPreferences;
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
    private Context context;
    private Retrofit retrofit;
    private IWeatherService weatherService;
    private Call<WeatherData> weather;
    public static WeatherData weatherData;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private int version = VERSION;
    private String appKey = "";
    public static final int VERSION = 1;
    public static final String SKY_A01 = "맑음";
    public static final String SKY_A02 = "구름조금";
    public static final String SKY_A03 = "구름많음";
    public static final String SKY_A04 = "구름많고 비";
    public static final String SKY_A05 = "구름많고 눈";
    public static final String SKY_A06 = "구름많고 비 또는 눈";
    public static final String SKY_A07 = "흐림";
    public static final String SKY_A08 = "흐리고 비";
    public static final String SKY_A09 = "흐리고 눈";
    public static final String SKY_A10 = "흐리고 비 또는 눈";
    public static final String SKY_A11 = "흐리고 낙뢰";
    public static final String SKY_A12 = "뇌우, 비";
    public static final String SKY_A13 = "뇌우, 눈";
    public static final String SKY_A14 = "뇌우, 비 또는 눈";

    public WeatherManager(Context context, int version, String appKey) {
        if(appKey == null) return;
        this.context = context;
        this.version = version;
        this.appKey = appKey;

        initRetrofit();
        sharedPreferences = context.getSharedPreferences("carHome", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://apis.skplanetx.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherService = retrofit.create(IWeatherService.class);
    }

    public int getVersion() {
        return version;
    }
    public String getAppKey(){ return appKey; }

    public void  getWeather(String city, String county, String village) {
        weather = weatherService.weatherWithAddress(appKey, city, county, village, version);
        weather.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Response<WeatherData> response, Retrofit retrofit) {
                if(response.code() == 200){
                    weatherData = response.body();
                    String temp = weatherData.getWeather().getMinutely().get(0).getTemperature().getTc();
                    if(temp==null) temp = "0.0f";
                    editor.putString("Temperature", temp);
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("WeatherManager", "Failed to get weatherInfo");
                Log.e("WeatherManager", t.getMessage() + "");
            }
        });
    }

    public void getWeather(String lat, String lon) {
        weather = weatherService.weatherWithLocation(appKey, lat, lon, version);
        weather.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Response<WeatherData> response, Retrofit retrofit) {
                if(response.code() == 200) weatherData = response.body();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("WeatherManager", "Failed to get weatherInfo");
                Log.e("WeatherManager", t.getMessage());
            }
        });
    }

    public void getWeather(int stnid) {
        weather = weatherService.weatherWithId(appKey, stnid, version);
        weather.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Response<WeatherData> response, Retrofit retrofit) {
                if(response.code() == 200) setWeatherData(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("WeatherManager", "Failed to get weatherInfo");
                Log.e("WeatherManager", t.getMessage());
            }
        });
    }

    private void setWeatherData(WeatherData weatherData){
        this.weatherData = weatherData;
    }

    public String getTemp(){
        return weatherData.getWeather().getMinutely().get(0).getTemperature().getTc();
    }
}
