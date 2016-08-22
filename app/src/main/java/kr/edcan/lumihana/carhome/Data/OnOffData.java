package kr.edcan.lumihana.carhome.Data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kimok_000 on 2016-08-22.
 */
public class OnOffData {

    /**
     * working : false
     */

    @SerializedName("working")
    private boolean working;

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }
}
