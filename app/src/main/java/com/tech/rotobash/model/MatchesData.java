package com.tech.rotobash.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 @Module class/module		:	MatchesData
 @Author Name			    :	Rohit Puri
 @Date					    :	Jan 11 , 2018
 @Purpose				    :	This class defines the pojo for match contest showing on dashboard
 */
public class MatchesData implements Parcelable {

    @SerializedName("id")
    @Expose
    private String matchId;

    @SerializedName("match_name")
    @Expose
    private String matchName;

    @SerializedName("match_key")
    @Expose
    private String matchKey;

    @SerializedName("series_id")
    @Expose
    private String matchSeriesId;

    @SerializedName("start_date")
    @Expose
    private String matchStartDate;

    @SerializedName("venue")
    @Expose
    private String matchVanue;

    @SerializedName("created")
    @Expose
    private String matchCreateAt;

    @SerializedName("team1_short_name")
    @Expose
    private String team1Name;

    @SerializedName("team2_short_name")
    @Expose
    private String team2Name;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.matchId);
        dest.writeString(this.matchName);
        dest.writeString(this.matchKey);
        dest.writeString(this.matchSeriesId);
        dest.writeString(this.matchStartDate);
        dest.writeString(this.matchVanue);
        dest.writeString(this.matchCreateAt);
        dest.writeString(this.team1Name);
        dest.writeString(this.team2Name);
    }

    public MatchesData() {
    }

    protected MatchesData(Parcel in) {
        this.matchId = in.readString();
        this.matchName = in.readString();
        this.matchKey = in.readString();
        this.matchSeriesId = in.readString();
        this.matchStartDate = in.readString();
        this.matchVanue = in.readString();
        this.matchCreateAt = in.readString();
        this.team1Name = in.readString();
        this.team2Name = in.readString();
    }

    public static final Creator<MatchesData> CREATOR = new Creator<MatchesData>() {
        @Override
        public MatchesData createFromParcel(Parcel source) {
            return new MatchesData(source);
        }

        @Override
        public MatchesData[] newArray(int size) {
            return new MatchesData[size];
        }
    };
}