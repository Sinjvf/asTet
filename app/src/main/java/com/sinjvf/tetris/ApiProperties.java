package com.sinjvf.tetris;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sinjvf on 24.12.15.
 */
public class ApiProperties implements Parcelable {

    private int type;
    private int score;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int back) {
        this.score = back;
    }

  public ApiProperties(int t, int b) {
        type=t;
        score = b;
    }
    private ApiProperties(Parcel parcel) {
        type = parcel.readInt();
        score = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(type);
        parcel.writeInt(score);
    }

    public static final Creator<ApiProperties> CREATOR = new Creator<ApiProperties>() {
        public ApiProperties createFromParcel(Parcel in) {
            return new ApiProperties(in);
        }

        public ApiProperties[] newArray(int size) {
            return new ApiProperties[size];
        }
    };
}