
package org.uk.cyberbyte.armaworks.Api.Models.ArmaLife.Player;

import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import auto.parcelgson.AutoParcelGson;

@AutoParcelGson
public abstract class Player implements Parcelable {

    @SerializedName("uid")
    @Expose
    private Integer uid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("aliases")
    @Expose
    private List<String> aliases = null;
    @SerializedName("playerid")
    @Expose
    private String playerid;
    @SerializedName("cash")
    @Expose
    private Integer cash;
    @SerializedName("bankacc")
    @Expose
    private Integer bankacc;
    @SerializedName("coplevel")
    @Expose
    private Integer coplevel;
    @SerializedName("mediclevel")
    @Expose
    private Integer mediclevel;
    @SerializedName("civ_licenses")
    @Expose
    private List<CivLicense> civLicenses = null;
    @SerializedName("med_licenses")
    @Expose
    private List<Object> medLicenses = null;
    @SerializedName("cop_licenses")
    @Expose
    private List<CopLicense> copLicenses = null;
    @SerializedName("civ_gear")
    @Expose
    private List<CivGear> civGear = null;
    @SerializedName("med_gear")
    @Expose
    private List<Object> medGear = null;
    @SerializedName("cop_gear")
    @Expose
    private List<CopGear> copGear = null;
    @SerializedName("civ_stats")
    @Expose
    private CivStats civStats;
    @SerializedName("med_stats")
    @Expose
    private MedStats medStats;
    @SerializedName("cop_stats")
    @Expose
    private CopStats copStats;
    @SerializedName("arrested")
    @Expose
    private Integer arrested;
    @SerializedName("adminlevel")
    @Expose
    private Integer adminlevel;
    @SerializedName("donorlevel")
    @Expose
    private Integer donorlevel;
    @SerializedName("blacklist")
    @Expose
    private Integer blacklist;
    @SerializedName("civ_alive")
    @Expose
    private Integer civAlive;
    @SerializedName("civ_position")
    @Expose
    private CivPosition civPosition;
    @SerializedName("playtime")
    @Expose
    private Playtime playtime;
    @SerializedName("insert_time")
    @Expose
    private String insertTime;
    @SerializedName("last_seen")
    @Expose
    private String lastSeen;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public String getPlayerid() {
        return playerid;
    }

    public void setPlayerid(String playerid) {
        this.playerid = playerid;
    }

    public Integer getCash() {
        return cash;
    }

    public void setCash(Integer cash) {
        this.cash = cash;
    }

    public Integer getBankacc() {
        return bankacc;
    }

    public void setBankacc(Integer bankacc) {
        this.bankacc = bankacc;
    }

    public Integer getCoplevel() {
        return coplevel;
    }

    public void setCoplevel(Integer coplevel) {
        this.coplevel = coplevel;
    }

    public Integer getMediclevel() {
        return mediclevel;
    }

    public void setMediclevel(Integer mediclevel) {
        this.mediclevel = mediclevel;
    }

    public List<CivLicense> getCivLicenses() {
        return civLicenses;
    }

    public void setCivLicenses(List<CivLicense> civLicenses) {
        this.civLicenses = civLicenses;
    }

    public List<Object> getMedLicenses() {
        return medLicenses;
    }

    public void setMedLicenses(List<Object> medLicenses) {
        this.medLicenses = medLicenses;
    }

    public List<CopLicense> getCopLicenses() {
        return copLicenses;
    }

    public void setCopLicenses(List<CopLicense> copLicenses) {
        this.copLicenses = copLicenses;
    }

    public List<CivGear> getCivGear() {
        return civGear;
    }

    public void setCivGear(List<CivGear> civGear) {
        this.civGear = civGear;
    }

    public List<Object> getMedGear() {
        return medGear;
    }

    public void setMedGear(List<Object> medGear) {
        this.medGear = medGear;
    }

    public List<CopGear> getCopGear() {
        return copGear;
    }

    public void setCopGear(List<CopGear> copGear) {
        this.copGear = copGear;
    }

    public CivStats getCivStats() {
        return civStats;
    }

    public void setCivStats(CivStats civStats) {
        this.civStats = civStats;
    }

    public MedStats getMedStats() {
        return medStats;
    }

    public void setMedStats(MedStats medStats) {
        this.medStats = medStats;
    }

    public CopStats getCopStats() {
        return copStats;
    }

    public void setCopStats(CopStats copStats) {
        this.copStats = copStats;
    }

    public Integer getArrested() {
        return arrested;
    }

    public void setArrested(Integer arrested) {
        this.arrested = arrested;
    }

    public Integer getAdminlevel() {
        return adminlevel;
    }

    public void setAdminlevel(Integer adminlevel) {
        this.adminlevel = adminlevel;
    }

    public Integer getDonorlevel() {
        return donorlevel;
    }

    public void setDonorlevel(Integer donorlevel) {
        this.donorlevel = donorlevel;
    }

    public Integer getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Integer blacklist) {
        this.blacklist = blacklist;
    }

    public Integer getCivAlive() {
        return civAlive;
    }

    public void setCivAlive(Integer civAlive) {
        this.civAlive = civAlive;
    }

    public CivPosition getCivPosition() {
        return civPosition;
    }

    public void setCivPosition(CivPosition civPosition) {
        this.civPosition = civPosition;
    }

    public Playtime getPlaytime() {
        return playtime;
    }

    public void setPlaytime(Playtime playtime) {
        this.playtime = playtime;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

}
