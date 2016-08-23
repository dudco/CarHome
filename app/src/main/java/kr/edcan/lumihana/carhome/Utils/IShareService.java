package kr.edcan.lumihana.carhome.Utils;

import com.squareup.okhttp.ResponseBody;

import kr.edcan.lumihana.carhome.Data.OnOffData;
import kr.edcan.lumihana.carhome.Data.SecurityData;
import kr.edcan.lumihana.carhome.Data.TemperatureData;
import kr.edcan.lumihana.carhome.Data.TurnData;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by kimok_000 on 2016-08-22.
 */
public interface IShareService {
    @GET("getdegree")
    Call<TemperatureData> getTemperature();

    @GET("setdegree")
    Call<ResponseBody> setTemperature(@Query("degree") String temperature);

    @GET("setOn")
    Call<ResponseBody> setOn();

    @GET("setOff")
    Call<ResponseBody> setOff();

    @GET("getWork")
    Call<OnOffData> getWork();

    @GET("setData")
    Call<ResponseBody> setData(@Query("isGas") boolean isGas, @Query("isElect") boolean isElect);

    @GET("egUpdate")
    Call<ResponseBody> egUpdate(@Query("isGas") boolean isGas, @Query("isElect") boolean isElect);

    @GET("getEg")
    Call<SecurityData> getEg();

    @GET("turnon")
    Call<ResponseBody> turnOn();

    @GET("turnoff")
    Call<ResponseBody> turnOff();

    @GET("getturn")
    Call<TurnData> getTurn();
}
