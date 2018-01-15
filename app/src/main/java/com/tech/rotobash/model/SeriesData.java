package com.tech.rotobash.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 @Module class/module		:	SeriesData
 @Author Name			    :	Rohit Puri
 @Date					    :	Jan 15 , 2018
 @Purpose				    :	This class defines the pojo for series filter showing on dashboard
 */
public class SeriesData implements Parcelable {

    public SeriesModel getmSeriesModel() {
        return mSeriesModel;
    }

    public void setmSeriesModel(SeriesModel mSeriesModel) {
        this.mSeriesModel = mSeriesModel;
    }

    @SerializedName("Series")
    @Expose
    private SeriesModel mSeriesModel;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mSeriesModel, flags);
    }

    public SeriesData() {
    }

    protected SeriesData(Parcel in) {
        this.mSeriesModel = in.readParcelable(SeriesModel.class.getClassLoader());
    }

    public static final Creator<SeriesData> CREATOR = new Creator<SeriesData>() {
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
