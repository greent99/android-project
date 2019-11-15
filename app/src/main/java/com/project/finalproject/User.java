package com.project.finalproject;


public class User {
    private String mUsername;
    private String mName;
    private String mPassword;
    private String mBirthdate;
    private String mGender;
    private String mAddress;
    private String mPhone;

    public User(String mUsername,String mPassword , String mName, String mAddress,String mPhone,String mGender, String mBirthdate) {
        this.mUsername = mUsername;
        this.mPassword = mPassword;
        this.mBirthdate = mBirthdate;
        this.mGender = mGender;
        this.mAddress = mAddress;
        this.mPhone = mPhone;
        this.mName = mName;
    }
    public User()
    {
        this.mName = "";
        this.mPassword = "";
        this.mBirthdate = "";
        this.mGender = "";
        this.mAddress = "";
       this.mPhone = "";
    }

    public String getmUsername() {
        return mUsername;
    }

    public String getmName() {
        return mName;
    }

    public String getmPassword() {
        return mPassword;
    }

    public String getmBirthdate() {
        return mBirthdate;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmGender() {
        return mGender;
    }

    public String getmAddress() {
        return mAddress;
    }

    public String getmPhone() {
        return mPhone;
    }
}
