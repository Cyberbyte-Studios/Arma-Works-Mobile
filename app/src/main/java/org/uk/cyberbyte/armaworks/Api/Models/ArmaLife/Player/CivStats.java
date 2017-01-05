
package org.uk.cyberbyte.armaworks.Api.Models.ArmaLife.Player;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CivStats {

    @SerializedName("health")
    @Expose
    private String health;
    @SerializedName("water")
    @Expose
    private String water;
    @SerializedName("stamina")
    @Expose
    private String stamina;

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getStamina() {
        return stamina;
    }

    public void setStamina(String stamina) {
        this.stamina = stamina;
    }

}
