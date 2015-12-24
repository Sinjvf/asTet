package com.example.tetris;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Properties;

/**
 * Created by sinjvf on 24.12.15.
 */
public class GameProperties implements Parcelable {

    private int type;
    private int back;
    private int complex;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getBack() {
        return back;
    }

    public void setBack(int back) {
        this.back = back;
    }

    public int getComplex() {
        return complex;
    }

    public void setComplex(int complex) {
        this.complex = complex;
    }


    public GameProperties (int t, int b, int c) {
        type=t;
        back = b;
        complex = c;
    }
    private GameProperties(Parcel parcel) {
        type = parcel.readInt();
        back = parcel.readInt();
        complex = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(type);
        parcel.writeInt(back);
        parcel.writeInt(complex);
    }

    public static final Parcelable.Creator<GameProperties> CREATOR = new Parcelable.Creator<GameProperties>() {
        public GameProperties createFromParcel(Parcel in) {
            return new GameProperties(in);
        }

        public GameProperties[] newArray(int size) {
            return new GameProperties[size];
        }
    };



}