package com.tech.rotobash.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sachinarora on 12/1/18.
 */

public class MatchContestsResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("response")
    @Expose
    private ArrayList<MatchContestsData> contestData;

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

    public ArrayList<MatchContestsData> getContestModel() {
        return contestData;
    }

    public void setContestModel(ArrayList<MatchContestsData> contestData) {
        this.contestData = contestData;
    }
}
