package kr.edcan.lumihana.carhome.Utils;

import com.squareup.okhttp.ResponseBody;

import kr.edcan.lumihana.carhome.Data.OnOffData;
import kr.edcan.lumihana.carhome.Data.TemperatureData;
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
}
