package com.tech.rotobash.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sombirbisht on 18/1/18.
 */

public class ContestRankData {

    @SerializedName("ranks")
    @Expose
    private String ranks;
    @SerializedName("no_of_winners")
    @Expose
    private String noOfWinners;
    @SerializedName("prize")
    @Expose
    private String prize;

    public String getRanks() {
        return ranks;
    }

    public void setRanks(String ranks) {
        this.ranks = ranks;
    }

    public String getNoOfWinners() {
        return noOfWinners;
    }

    public void setNoOfWinners(String noOfWinners) {
        this.noOfWinners = noOfWinners;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

}
