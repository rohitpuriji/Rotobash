package com.tech.rotobash.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 @Module class/module		:	SeriesResponse
 @Author Name			    :	Rohit Puri
 @Date					    :	Jan 15 , 2018
 @Purpose				    :	This class defines the pojo for series filter showing on dashboard
 */
public class SeriesResponse implements Parcelable {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("response")
    @Expose
    private ArrayList<SeriesData> seriesModel;

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

    public ArrayList<SeriesData> getSeriesModel() {
        return seriesModel;
    }

    public void setSeriesModel(ArrayList<SeriesData> seriesModel) {
        this.seriesModel = seriesModel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeString(this.message);
        dest.writeList(this.seriesModel);
    }

    public SeriesResponse() {
    }

    protected SeriesResponse(Parcel in) {
        this.status = in.readString();
        this.message = in.readString();
        this.seriesModel = new ArrayList<SeriesData>();
        in.readList(this.seriesModel, SeriesData.class.getClassLoader());
    }

    public static final Parcelable.Creator<SeriesResponse> CREATOR = new Parcelable.Creator<SeriesResponse>() {
        @Override
        public SeriesResponse createFromParcel(Parcel source) {
            return new SeriesResponse(source);
        }

        @Override
        public SeriesResponse[] newArray(int size) {
            return new SeriesResponse[size];
        }
    };
}
