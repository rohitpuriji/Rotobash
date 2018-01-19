package com.tech.rotobash.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sombirbisht on 19/1/18.
 */

public class Criteria {

    @SerializedName("OneTeam_min")
    @Expose
    private String oneTeamMin;
    @SerializedName("OneTeam_max")
    @Expose
    private String oneTeamMax;
    @SerializedName("Batsman_min")
    @Expose
    private String batsmanMin;
    @SerializedName("Batsman_max")
    @Expose
    private String batsmanMax;
    @SerializedName("Ballers_min")
    @Expose
    private String ballersMin;
    @SerializedName("Ballers_max")
    @Expose
    private String ballersMax;
    @SerializedName("AR_min")
    @Expose
    private String aRMin;
    @SerializedName("AR_max")
    @Expose
    private String aRMax;
    @SerializedName("WK_min")
    @Expose
    private String wKMin;
    @SerializedName("WK_max")
    @Expose
    private String wKMax;
    @SerializedName("sub_min")
    @Expose
    private String subMin;
    @SerializedName("sub_max")
    @Expose
    private String subMax;

    public String getOneTeamMin() {
        return oneTeamMin;
    }

    public void setOneTeamMin(String oneTeamMin) {
        this.oneTeamMin = oneTeamMin;
    }

    public String getOneTeamMax() {
        return oneTeamMax;
    }

    public void setOneTeamMax(String oneTeamMax) {
        this.oneTeamMax = oneTeamMax;
    }

    public String getBatsmanMin() {
        return batsmanMin;
    }

    public void setBatsmanMin(String batsmanMin) {
        this.batsmanMin = batsmanMin;
    }

    public String getBatsmanMax() {
        return batsmanMax;
    }

    public void setBatsmanMax(String batsmanMax) {
        this.batsmanMax = batsmanMax;
    }

    public String getBallersMin() {
        return ballersMin;
    }

    public void setBallersMin(String ballersMin) {
        this.ballersMin = ballersMin;
    }

    public String getBallersMax() {
        return ballersMax;
    }

    public void setBallersMax(String ballersMax) {
        this.ballersMax = ballersMax;
    }

    public String getARMin() {
        return aRMin;
    }

    public void setARMin(String aRMin) {
        this.aRMin = aRMin;
    }

    public String getARMax() {
        return aRMax;
    }

    public void setARMax(String aRMax) {
        this.aRMax = aRMax;
    }

    public String getWKMin() {
        return wKMin;
    }

    public void setWKMin(String wKMin) {
        this.wKMin = wKMin;
    }

    public String getWKMax() {
        return wKMax;
    }

    public void setWKMax(String wKMax) {
        this.wKMax = wKMax;
    }

    public String getSubMin() {
        return subMin;
    }

    public void setSubMin(String subMin) {
        this.subMin = subMin;
    }

    public String getSubMax() {
        return subMax;
    }

    public void setSubMax(String subMax) {
        this.subMax = subMax;
    }

}
