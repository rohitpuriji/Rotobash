package com.tech.rotobash.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sachinarora on 15/1/18.
 */

public class LeaguesResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("response")
    @Expose
    private ArrayList<LeagueContestData> matchModel;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<LeagueContestData> getMatchModel() {
        return matchModel;
    }

    public void setMatchModel(ArrayList<LeagueContestData> matchModel) {
        this.matchModel = matchModel;
    }
}
