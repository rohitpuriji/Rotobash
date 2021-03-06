package com.tech.rotobash.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 @Module class/module		:	MatchesResponse
 @Author Name			    :	Rohit Puri
 @Date					    :	Jan 11 , 2018
 @Purpose				    :	This class defines the pojo for match contest showing on dashboard
 */
public class MatchesResponse implements Parcelable{

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("response")
    @Expose
    private ArrayList<MatchesData> matchModel;

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

    public ArrayList<MatchesData> getMatchModel() {
        return matchModel;
    }

    public void setMatchModel(ArrayList<MatchesData> matchModel) {
        this.matchModel = matchModel;
    }



    public MatchesResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeString(this.message);
        dest.writeTypedList(this.matchModel);
    }

    protected MatchesResponse(Parcel in) {
        this.status = in.readString();
        this.message = in.readString();
        this.matchModel = in.createTypedArrayList(MatchesData.CREATOR);
    }

    public static final Creator<MatchesResponse> CREATOR = new Creator<MatchesResponse>() {
        @Override
        public MatchesResponse createFromParcel(Parcel source) {
            return new MatchesResponse(source);
        }

        @Override
        public MatchesResponse[] newArray(int size) {
            return new MatchesResponse[size];
        }
    };
}
