package com.sinjvf.tetris;

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
        private int[] colorSchemes;
        private int pace;
        private int prevent;

        public int getPrevent() {
            return prevent;
        }

        public void setPrevent(int prevent) {
            this.prevent = prevent;
        }

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

      public int[] getColorSchemes() {
            return colorSchemes;
        }

        public void setColorSchemes(int[] colorSchemes) {
            this.colorSchemes = colorSchemes;
        }


        public Complex (int numbers, int[] colorSchemes, int pace, int prevent) {
            this.numbers =numbers;
            this.colorSchemes = colorSchemes;
            this.pace =pace;
            this.prevent = prevent;
        }
        public Complex () {
            int []color = new int[Const.MAX_FIG];
            this.numbers =Const.DEFAULT_COMPLEX_NUMB;
            for(int i=0;i<Const.MAX_FIG;i++) {
                color[i]=Const.DEFAULT_COMPLEX_COLOR;
            }
            this.colorSchemes = color;
            this.pace =Const.DEFAULT_COMPLEX_PACE;
            this.prevent = Const.SWITCH_DISTRACT;
        }
        public Complex(Parcel parcel) {
            numbers = parcel.readInt();
            colorSchemes = parcel.createIntArray();
            pace = parcel.readInt();
            prevent = parcel.readInt();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int flags) {
            parcel.writeInt(numbers);
            parcel.writeIntArray(colorSchemes);
            parcel.writeInt(pace);
            parcel.writeInt(prevent);

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