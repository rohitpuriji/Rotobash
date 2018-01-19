package com.tech.rotobash.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sombirbisht on 18/1/18.
 */

public class ContestRankResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private ArrayList<ContestRankData> response = null;

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

    public ArrayList<ContestRankData> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<ContestRankData> response) {
        this.response = response;
    }
}