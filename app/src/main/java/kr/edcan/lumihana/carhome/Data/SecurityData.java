package kr.edcan.lumihana.carhome.Data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kimok_000 on 2016-08-23.
 */
public class SecurityData {

    /**
     * Gas : false
     * Elect : false
     */

    @SerializedName("Gas")
    private boolean Gas;
    @SerializedName("Elect")
    private boolean Elect;

    public boolean isGas() {
        return Gas;
    }

    public boolean isElect() {
        return Elect;
    }

    public void setGas(boolean gas) {
        Gas = gas;
    }

    public void setElect(boolean elect) {
        Elect = elect;
    }
}
