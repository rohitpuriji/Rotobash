package com.tech.rotobash.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 @Module class/module		:	UserResponse
 @Author Name			    :	Rohit Puri
 @Date					    :	Jan 11 , 2018
 @Purpose				    :	This class defines the pojo for user related data coming in login, registration api
 */
public class UserResponse implements Parcelable{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("response")
    @Expose
    private UserData userModel;

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

    public UserData getResponse() {
        return userModel;
    }

    public void setResponse(UserData userModel) {
        this.userModel = userModel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeString(this.message);
        dest.writeParcelable(this.userModel, flags);
    }

    public UserResponse() {
    }

    protected UserResponse(Parcel in) {
        this.status = in.readString();
        this.message = in.readString();
        this.userModel = in.readParcelable(UserData.class.getClassLoader());
    }

    public static final Creator<UserResponse> CREATOR = new Creator<UserResponse>() {
        @Override
        public UserResponse createFromParcel(Parcel source) {
            return new UserResponse(source);
        }

        @Override
        public UserResponse[] newArray(int size) {
            return new UserResponse[size];
        }
    };
}
