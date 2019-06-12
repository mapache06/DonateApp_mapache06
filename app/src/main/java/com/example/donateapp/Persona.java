package com.example.donateapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Persona implements Parcelable {

    public int id;
    public String name;
    public String lastName;
    public String eMail;
    public String password;
    public String userName;

    public Persona(){};

    protected Persona(Parcel in) {
        id = in.readInt();
        name = in.readString();
        lastName = in.readString();
        eMail = in.readString();
        password = in.readString();
        userName = in.readString();
    }

    public static final Creator<Persona> CREATOR = new Creator<Persona>() {
        @Override
        public Persona createFromParcel(Parcel in) {
            return new Persona(in);
        }

        @Override
        public Persona[] newArray(int size) {
            return new Persona[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(lastName);
        dest.writeString(eMail);
        dest.writeString(password);
        dest.writeString(userName);
    }
}
