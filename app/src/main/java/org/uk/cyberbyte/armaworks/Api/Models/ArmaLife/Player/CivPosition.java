
package org.uk.cyberbyte.armaworks.Api.Models.ArmaLife.Player;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CivPosition {

    @SerializedName("x")
    @Expose
    private String x;
    @SerializedName("y")
    @Expose
    private String y;
    @SerializedName("z")
    @Expose
    private String z;

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

}
