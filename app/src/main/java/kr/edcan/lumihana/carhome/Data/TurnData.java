package kr.edcan.lumihana.carhome.Data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kimok_000 on 2016-08-23.
 */
public class TurnData {

    /**
     * Turn : true
     */
    @SerializedName("Turn")
    private boolean Turn;

    public boolean isTurn() {
        return Turn;
    }

    public void setTurn(boolean Turn) {
        this.Turn = Turn;
    }
}
