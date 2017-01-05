
package org.uk.cyberbyte.armaworks.Api.Models.ArmaLife.Player;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Playtime {

    @SerializedName("cop")
    @Expose
    private String cop;
    @SerializedName("med")
    @Expose
    private String med;
    @SerializedName("civ")
    @Expose
    private String civ;

    public String getCop() {
        return cop;
    }

    public void setCop(String cop) {
        this.cop = cop;
    }

    public String getMed() {
        return med;
    }

    public void setMed(String med) {
        this.med = med;
    }

    public String getCiv() {
        return civ;
    }

    public void setCiv(String civ) {
        this.civ = civ;
    }

}
