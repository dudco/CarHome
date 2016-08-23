package kr.edcan.lumihana.carhome.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.squareup.okhttp.ResponseBody;

import kr.edcan.lumihana.carhome.Data.OnOffData;
import kr.edcan.lumihana.carhome.Data.SecurityData;
import kr.edcan.lumihana.carhome.Data.TemperatureData;
import kr.edcan.lumihana.carhome.Data.TurnData;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by kimok_000 on 2016-08-22.
 */
public class ShareManager {
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Retrofit retrofit;
    private Call<TurnData> shareTurnGet;
    private Call<SecurityData> shareEGGet;
    private Call<TemperatureData> shareGet;
    private Call<OnOffData> shareOnOffGet;
    private Call<ResponseBody> shareSet;
    private IShareService shareService;

    public ShareManager() {
    }

    public ShareManager(Context context) {
        this.context = context;

        initRetrofit();
        sharedPreferences = context.getSharedPreferences("carHome", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://yyc.applepi.kr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        shareService = retrofit.create(IShareService.class);
    }

    public void getTemperature() {
        shareGet = shareService.getTemperature();
        shareGet.enqueue(new Callback<TemperatureData>() {
            @Override
            public void onResponse(Response<TemperatureData> response, Retrofit retrofit) {
                if (response.code() == 200) {
                    String temp = response.body().getDegree();
                    editor.putString("CarTemperature", temp);
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("ShareManager", "Failed to get temperature");
                Log.e("ShareManager", t.getMessage() + "");
            }
        });
    }

    public void setOn() {
        shareSet = shareService.setOn();
        shareSet.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                if (response.code() == 200) {
                    Log.e("ShareManager", "Successfully set on");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("ShareManager", "Failed to set temperature");
                Log.e("ShareManager", t.getMessage() + "");
            }
        });
    }

    public void setOff() {
        shareSet = shareService.setOff();
        shareSet.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                if (response.code() == 200) {
                    Log.e("ShareManager", "Successfully set off");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("ShareManager", "Failed to set temperature");
                Log.e("ShareManager", t.getMessage() + "");
            }
        });
    }

    public void setShareGet() {
        shareOnOffGet = shareService.getWork();
        shareOnOffGet.enqueue(new Callback<OnOffData>() {
            @Override
            public void onResponse(Response<OnOffData> response, Retrofit retrofit) {
                if (response.code() == 200) {
                    boolean temp = response.body().isWorking();
                    editor.putBoolean("Working", temp);
                    editor.commit();
                    Log.e("Service status", temp + "");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("ShareManager", "Failed to set temperature");
                Log.e("ShareManager", t.getMessage() + "");
            }
        });
    }

    public void setTemperature(String temperature) {
        shareSet = shareService.setTemperature(temperature);
        shareSet.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                if (response.code() == 200) {
                    Log.e("ShareManager", "Successfully set");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("ShareManager", "Failed to set temperature");
                Log.e("ShareManager", t.getMessage() + "");
            }
        });
    }

    public void setData(boolean isGas, boolean isElect) {
        shareSet = shareService.setData(isGas, isElect);
        shareSet.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                if (response.code() == 200) {
                    Log.e("ShareManager", "Successfully set");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("ShareManager", "Failed to set temperature");
                Log.e("ShareManager", t.getMessage() + "");
            }
        });
    }

    public void egUpdate(boolean isGas, boolean isElect) {
        shareSet = shareService.egUpdate(isGas, isElect);
        shareSet.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                if (response.code() == 200) {
                    Log.e("ShareManager", "Successfully set");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("ShareManager", "Failed to set temperature");
                Log.e("ShareManager", t.getMessage() + "");
            }
        });
    }

    public void getEg() {
        shareEGGet = shareService.getEg();
        shareEGGet.enqueue(new Callback<SecurityData>() {
            @Override
            public void onResponse(Response<SecurityData> response, Retrofit retrofit) {
                if (response.code() == 200) {
                    boolean isGas = response.body().isGas();
                    boolean isElect = response.body().isElect();

                    editor.putBoolean("isGas", isGas);
                    editor.putBoolean("isElect", isElect);
                    editor.commit();
                    Log.e("ShareManager", "Gas : " + isGas + ", Elect : " + isElect);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("ShareManager", "Failed to set temperature");
                Log.e("ShareManager", t.getMessage() + "");
            }
        });
    }

    public void turnOn() {
        shareSet = shareService.turnOn();
        shareSet.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                if (response.code() == 200) {
                    Log.e("ShareManager", "Successfully turn on");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("ShareManager", "Failed to set temperature");
                Log.e("ShareManager", t.getMessage() + "");
            }
        });
    }

    public void turnOff() {
        shareSet = shareService.turnOff();
        shareSet.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                if (response.code() == 200) {
                    Log.e("ShareManager", "Successfully turn off");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("ShareManager", "Failed to set temperature");
                Log.e("ShareManager", t.getMessage() + "");
            }
        });
    }

    public void getTurn() {
        shareTurnGet = shareService.getTurn();
        shareTurnGet.enqueue(new Callback<TurnData>() {
            @Override
            public void onResponse(Response<TurnData> response, Retrofit retrofit) {
                if (response.code() == 200) {
                    boolean temp = response.body().isTurn();

                    editor.putBoolean("turn", temp);
                    editor.commit();
                    Log.e("ShareManager", "Turn : " + temp);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("ShareManager", "Failed to set temperature");
                Log.e("ShareManager", t.getMessage() + "");
            }
        });
    }
}
