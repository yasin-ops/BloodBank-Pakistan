package com.muhammadyaseenFatima.blood_bank_pakistan;

import android.os.Parcel;
import android.os.Parcelable;

public class UserRegisterData implements Parcelable {
    public String userName;
    public String userEmail;
    public String userNumber;
    public String userCity;
    public String userCountry;
    public  String userBloodGroup;

    public UserRegisterData() {
    }

    protected UserRegisterData(Parcel in) {
        userName = in.readString();
        userEmail = in.readString();
        userNumber = in.readString();
        userCity = in.readString();
        userCountry = in.readString();
        userBloodGroup = in.readString();
    }

    public UserRegisterData(String userName, String userEmail, String userNumber, String userCity, String userCountry, String userBloodGroup) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userNumber = userNumber;
        this.userCity = userCity;
        this.userCountry = userCountry;
        this.userBloodGroup = userBloodGroup;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public String getUserBloodGroup() {
        return userBloodGroup;
    }

    public void setUserBloodGroup(String userBloodGroup) {
        this.userBloodGroup = userBloodGroup;
    }

    public static final Creator<UserRegisterData> CREATOR = new Creator<UserRegisterData>() {
        @Override
        public UserRegisterData createFromParcel(Parcel in) {
            return new UserRegisterData(in);
        }

        @Override
        public UserRegisterData[] newArray(int size) {
            return new UserRegisterData[size];
        }
    };

    @Override
    public String toString() {
        return "UserRegisterData{" +
                "userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userNumber='" + userNumber + '\'' +
                ", userCity='" + userCity + '\'' +
                ", userCountry='" + userCountry + '\'' +
                ", userBloodGroup='" + userBloodGroup + '\'' +
                '}';
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userName);
        parcel.writeString(userEmail);
        parcel.writeString(userNumber);
        parcel.writeString(userCity);
        parcel.writeString(userCountry);
        parcel.writeString(userBloodGroup);
    }
}

