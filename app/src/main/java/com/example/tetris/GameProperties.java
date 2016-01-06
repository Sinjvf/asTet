package com.example.tetris;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * Created by sinjvf on 24.12.15.
 */
public class GameProperties implements Parcelable {

    private int type;
    private int back;
    private Complex complex;


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

    public Complex getComplex() {
        return complex;
    }

    public void setComplex(Complex complex) {
        this.complex = complex;
    }


    public GameProperties (int t, int b, Complex c) {
        type=t;
        back = b;
        complex = c;
    }
    private GameProperties(Parcel parcel) {
        type = parcel.readInt();
        back = parcel.readInt();
        complex = (Complex)parcel.readParcelable(Complex.class.getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(type);
        parcel.writeInt(back);
        parcel.writeParcelable(complex, flags);
    }

    public static final Parcelable.Creator<GameProperties> CREATOR = new Parcelable.Creator<GameProperties>() {
        public GameProperties createFromParcel(Parcel in) {
            return new GameProperties(in);
        }

        public GameProperties[] newArray(int size) {
            return new GameProperties[size];
        }
    };



    public static class Complex implements Parcelable {
        private int numbers;
        private int[] colorShemes;
        private int pace;


        public int getPace() {
            return pace;
        }

        public void setPace(int pace) {
            this.pace = pace;
        }

        public int getNumbers() {
            return numbers;
        }

        public void setNumbers(int numbers) {
            this.numbers = numbers;
        }

      public int[] getColorShemes() {
            return colorShemes;
        }

        public void setColorShemes(int[] colorShemes) {
            this.colorShemes = colorShemes;
        }


        public Complex (int numbers, int[] colorShemes, int pace) {
            this.numbers =numbers;
            this.colorShemes = colorShemes;
            this.pace =pace;
        }
        public Complex () {
            int []color = new int[Const.MAX_FIG];
            this.numbers =Const.DEFAULT_COMPLEX_NUMB;
            for(int i=0;i<Const.MAX_FIG;i++) {
                color[i]=Const.DEFAULT_COMPLEX_COLOR;
            }
            this.colorShemes = color;
            this.pace =Const.DEFAULT_COMPLEX_PACE;
        }
        public Complex(Parcel parcel) {
            numbers = parcel.readInt();
            colorShemes= parcel.createIntArray();
           // colorShemes = parcel.readInt();
            pace = parcel.readInt();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int flags) {
            parcel.writeInt(numbers);
            parcel.writeIntArray(colorShemes);
            parcel.writeInt(pace);

            //parcel.writeIntArray(colorShemes);
        }

        public static final Parcelable.Creator<Complex> CREATOR = new Parcelable.Creator<Complex>() {
            public Complex createFromParcel(Parcel in) {
                return new Complex(in);
            }

            public Complex[] newArray(int size) {
                return new Complex[size];
            }
        };
    }
}