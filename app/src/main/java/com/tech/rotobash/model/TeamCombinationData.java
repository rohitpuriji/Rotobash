package com.tech.rotobash.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sombirbisht on 19/1/18.
 */

public class TeamCombinationData {
    @SerializedName("Criteria")
    @Expose
    private Criteria criteria;
    @SerializedName("Combination")
    @Expose
    private List<String> combination = null;

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public List<String> getCombination() {
        return combination;
    }

    public void setCombination(List<String> combination) {
        this.combination = combination;
    }
}
