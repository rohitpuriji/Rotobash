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

    @SerializedName("series_name")
    @Expose
    private String seriesName;

    @SerializedName("series_short_name")
    @Expose
    private String seriesShortName;



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

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getMatchKey() {
        return matchKey;
    }

    public void setMatchKey(String matchKey) {
        this.matchKey = matchKey;
    }

    public String getMatchSeriesId() {
        return matchSeriesId;
    }

    public void setMatchSeriesId(String matchSeriesId) {
        this.matchSeriesId = matchSeriesId;
    }

    public String getMatchStartDate() {
        return matchStartDate;
    }

    public void setMatchStartDate(String matchStartDate) {
        this.matchStartDate = matchStartDate;
    }

    public String getMatchVanue() {
        return matchVanue;
    }

    public void setMatchVanue(String matchVanue) {
        this.matchVanue = matchVanue;
    }

    public String getMatchCreateAt() {
        return matchCreateAt;
    }

    public void setMatchCreateAt(String matchCreateAt) {
        this.matchCreateAt = matchCreateAt;
    }

    public String getTeam1Name() {
        return team1Name;
    }

    public void setTeam1Name(String team1Name) {
        this.team1Name = team1Name;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public void setTeam2Name(String team2Name) {
        this.team2Name = team2Name;
    }

    public MatchesData() {
    }

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
        dest.writeString(this.seriesName);
        dest.writeString(this.seriesShortName);
        dest.writeString(this.team2Name);
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
        this.seriesName = in.readString();
        this.seriesShortName = in.readString();
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