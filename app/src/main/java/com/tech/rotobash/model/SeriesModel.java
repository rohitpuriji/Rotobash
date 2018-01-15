package com.tech.rotobash.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rohitpuri on 15/1/18.
 */

public class SeriesModel implements Parcelable{

    @SerializedName("id")
    @Expose
    private String seriesId;

    @SerializedName("series_key")
    @Expose
    private String seriesKey;

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getSeriesKey() {
        return seriesKey;
    }

    public void setSeriesKey(String seriesKey) {
        this.seriesKey = seriesKey;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getSeriesShortName() {
        return seriesShortName;
    }

    public void setSeriesShortName(String seriesShortName) {
        this.seriesShortName = seriesShortName;
    }

    public String getSeriesStartDate() {
        return seriesStartDate;
    }

    public void setSeriesStartDate(String seriesStartDate) {
        this.seriesStartDate = seriesStartDate;
    }

    public String getSeriesCreateAt() {
        return seriesCreateAt;
    }

    public void setSeriesCreateAt(String seriesCreateAt) {
        this.seriesCreateAt = seriesCreateAt;
    }

    @SerializedName("name")
    @Expose

    private String seriesName;

    @SerializedName("short_name")
    @Expose
    private String seriesShortName;

    @SerializedName("start_date")
    @Expose
    private String seriesStartDate;

    @SerializedName("created")
    @Expose
    private String seriesCreateAt;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.seriesId);
        dest.writeString(this.seriesKey);
        dest.writeString(this.seriesName);
        dest.writeString(this.seriesShortName);
        dest.writeString(this.seriesStartDate);
        dest.writeString(this.seriesCreateAt);
    }

    public SeriesModel() {
    }

    protected SeriesModel(Parcel in) {
        this.seriesId = in.readString();
        this.seriesKey = in.readString();
        this.seriesName = in.readString();
        this.seriesShortName = in.readString();
        this.seriesStartDate = in.readString();
        this.seriesCreateAt = in.readString();
    }

    public static final Parcelable.Creator<SeriesData> CREATOR = new Parcelable.Creator<SeriesData>() {
        @Override
        public SeriesData createFromParcel(Parcel source) {
            return new SeriesData(source);
        }

        @Override
        public SeriesData[] newArray(int size) {
            return new SeriesData[size];
        }
    };
}
