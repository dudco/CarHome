package kr.edcan.lumihana.carhome.Data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kimok_000 on 2016-08-22.
 */
public class TemperatureData {

    /**
     * degree : 31.1
     */
    @SerializedName("degree")
    private String degree;

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
