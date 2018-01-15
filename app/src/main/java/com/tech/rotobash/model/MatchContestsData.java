package com.tech.rotobash.model;

/**
 * Created by sachinarora on 12/1/18.
 */

public class MatchContestsData {

    private String id;
    private String contest_code;
    private String contest_type;
    private String contest_name;
    private String min_user;
    private String max_user;
    private String min_winning_amount;
    private String max_winning_amount;
    private String no_of_winners;
    private String entry_fee;
    private String created;
    private String league_id;
    private String league_name;
    private String total_users;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContest_code() {
        return contest_code;
    }

    public void setContest_code(String contest_code) {
        this.contest_code = contest_code;
    }

    public String getContest_type() {
        return contest_type;
    }

    public void setContest_type(String contest_type) {
        this.contest_type = contest_type;
    }

    public String getContest_name() {
        return contest_name;
    }

    public void setContest_name(String contest_name) {
        this.contest_name = contest_name;
    }

    public String getMin_user() {
        return min_user;
    }

    public void setMin_user(String min_user) {
        this.min_user = min_user;
    }

    public String getMax_user() {
        return max_user;
    }

    public void setMax_user(String max_user) {
        this.max_user = max_user;
    }

    public String getMin_winning_amount() {
        return min_winning_amount;
    }

    public void setMin_winning_amount(String min_winning_amount) {
        this.min_winning_amount = min_winning_amount;
    }

    public String getMax_winning_amount() {
        return max_winning_amount;
    }

    public void setMax_winning_amount(String max_winning_amount) {
        this.max_winning_amount = max_winning_amount;
    }

    public String getNo_of_winners() {
        return no_of_winners;
    }

    public void setNo_of_winners(String no_of_winners) {
        this.no_of_winners = no_of_winners;
    }

    public String getEntry_fee() {
        return entry_fee;
    }

    public void setEntry_fee(String entry_fee) {
        this.entry_fee = entry_fee;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLeague_id() {
        return league_id;
    }

    public void setLeague_id(String league_id) {
        this.league_id = league_id;
    }

    public String getLeague_name() {
        return league_name;
    }

    public void setLeague_name(String league_name) {
        this.league_name = league_name;
    }

    public String getTotal_users() {
        return total_users;
    }

    public void setTotal_users(String total_users) {
        this.total_users = total_users;
    }
}
