package com.tech.rotobash.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sombirbisht on 19/1/18.
 */
public class TeamCombinationResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private TeamCombinationData response;

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

    public TeamCombinationData getResponse() {
        return response;
    }

    public void setResponse(TeamCombinationData response) {
        this.response = response;
    }

}