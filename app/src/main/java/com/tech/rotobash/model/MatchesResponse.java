package com.tech.rotobash.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    private MatchesData matchModel;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeString(this.message);
        dest.writeParcelable(this.matchModel, flags);
    }

    public MatchesResponse() {
    }

    protected MatchesResponse(Parcel in) {
        this.status = in.readString();
        this.message = in.readString();
        this.matchModel = in.readParcelable(MatchesData.class.getClassLoader());
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
