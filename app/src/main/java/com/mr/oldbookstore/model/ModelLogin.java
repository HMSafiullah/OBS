package com.mr.oldbookstore.model;

import androidx.annotation.Nullable;

public class ModelLogin {
    @Nullable
    private String phoneNumber;
    @Nullable
    private String otp_1;
    @Nullable
    private String otp_2;
    @Nullable
    private String otp_3;
    @Nullable
    private String otp_4;
    @Nullable
    private String otp_5;
    @Nullable
    private String otp_6;

    public ModelLogin(String phoneNumber,String otp_1, String otp_2, String otp_3, String otp_4,String otp_5,String otp_6){
        this.phoneNumber=phoneNumber;
        this.otp_1=otp_1;
        this.otp_2=otp_2;
        this.otp_3=otp_3;
        this.otp_4=otp_4;
        this.otp_5=otp_5;
        this.otp_6=otp_6;
    }



    @Nullable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Nullable
    public String getOtp_1() {
        return otp_1;
    }

    @Nullable
    public String getOtp_2() {
        return otp_2;
    }

    @Nullable
    public String getOtp_3() {
        return otp_3;
    }

    @Nullable
    public String getOtp_4() {
        return otp_4;
    }

    @Nullable
    public String getOtp_5() {
        return otp_5;
    }

    @Nullable
    public String getOtp_6() {
        return otp_6;
    }

    public void setPhoneNumber(@Nullable String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setOtp_1(@Nullable String otp_1) {
        this.otp_1 = otp_1;
    }

    public void setOtp_2(@Nullable String otp_2) {
        this.otp_2 = otp_2;
    }

    public void setOtp_3(@Nullable String otp_3) {
        this.otp_3 = otp_3;
    }

    public void setOtp_4(@Nullable String otp_4) {
        this.otp_4 = otp_4;
    }

    public void setOtp_5(@Nullable String otp_5) {
        this.otp_5 = otp_5;
    }

    public void setOtp_6(@Nullable String otp_6) {
        this.otp_6 = otp_6;
    }
}
