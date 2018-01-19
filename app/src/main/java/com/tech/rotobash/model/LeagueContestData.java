package com.tech.rotobash.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sachinarora on 15/1/18.
 */

public class LeagueContestData {

    @SerializedName("default")
    @Expose
    private String mDefault;

    private String id;
    private String code;

    private String name;
    private String status;
    private String created;

    public String getmDefault() {
        return mDefault;
    }

    public void setmDefault(String mDefault) {
        this.mDefault = mDefault;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
