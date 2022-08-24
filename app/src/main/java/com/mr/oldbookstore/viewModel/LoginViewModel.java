package com.mr.oldbookstore.viewModel;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.mr.oldbookstore.BR;
import com.mr.oldbookstore.activity.MainActivity;
import com.mr.oldbookstore.firebase.PhoneNumberAuthentication;
import com.mr.oldbookstore.model.ModelLogin;

public class LoginViewModel extends BaseObservable {
    private ModelLogin modelLogin;

    private String sucessMessage="Login Successful";
    private String errorMessage="Wrong OTP";
    private String successOTPReceived="OTP Received";
    private String failedOTPReceived="OTP Not Received";
    private String backMessageSuccess="Backed";

    PhoneNumberAuthentication phoneNumberAuthentication=new PhoneNumberAuthentication();

    @Bindable
    private String toastMessage=null;
    @Bindable
    private String otpMessage=null;
    @Bindable
    private String backMessage=null;

    public String getBackMessage() {
        return backMessage;
    }

    public void setBackMessage(String backMessage) {
        this.backMessage = backMessage;
        notifyPropertyChanged(BR.backMessage);
    }

    public String getOtpMessage() {
        return otpMessage;
    }

    public void setOtpMessage(String otpMessage) {
        this.otpMessage = otpMessage;
        notifyPropertyChanged(BR.otpMessage);
    }

    public String getToastMessage() {
        return toastMessage;
    }

    public void setToastMessage(String toastMessage) {
        this.toastMessage = toastMessage;
        notifyPropertyChanged(BR.toastMessage);
    }
    @Bindable
    public String getUserPhoneNumber(){
        return modelLogin.getPhoneNumber();
    }
    @Bindable
    public String getUserOtp_1(){
        return modelLogin.getOtp_1();
    }
    @Bindable
    public String getUserOtp_2(){
        return modelLogin.getOtp_2();
    }
    @Bindable
    public String getUserOtp_3(){
        return modelLogin.getOtp_3();
    }
    @Bindable
    public String getUserOtp_4(){
        return modelLogin.getOtp_4();
    }
    @Bindable
    public String getUserOtp_5(){
        return modelLogin.getOtp_5();
    }
    @Bindable
    public String getUserOtp_6(){
        return modelLogin.getOtp_6();
    }
    //Setter Methods

    public void setUserPhoneNumber(String phoneNumber){
        modelLogin.setPhoneNumber(phoneNumber);
        notifyPropertyChanged(BR.userPhoneNumber);
    }
    public void setUserOtp_1(String otp_1){
        modelLogin.setOtp_1(otp_1);
        notifyPropertyChanged(BR.userOtp_1);
    }
    public void setUserOtp_2(String otp_2){
        modelLogin.setOtp_2(otp_2);
        notifyPropertyChanged(BR.userOtp_2);
    }
    public void setUserOtp_3(String otp_3){
        modelLogin.setOtp_3(otp_3);
        notifyPropertyChanged(BR.userOtp_3);
    }
    public void setUserOtp_4(String otp_4){
        modelLogin.setOtp_4(otp_4);
        notifyPropertyChanged(BR.userOtp_4);
    }
    public void setUserOtp_5(String otp_5){
        modelLogin.setOtp_5(otp_5);
        notifyPropertyChanged(BR.userOtp_5);
    }
    public void setUserOtp_6(String otp_6){
        modelLogin.setOtp_6(otp_6);
        notifyPropertyChanged(BR.userOtp_6);
    }
    public LoginViewModel(){
        modelLogin=new ModelLogin("","","","","","","");
    }
    public void onSubmitButtonClicked(){
        if(TextUtils.isEmpty(getUserOtp_1()) && TextUtils.isEmpty(getUserOtp_2()) && TextUtils.isEmpty(getUserOtp_3()) && TextUtils.isEmpty(getUserOtp_4()) && TextUtils.isEmpty(getUserOtp_5()) && TextUtils.isEmpty(getUserOtp_6())){
            setToastMessage(null);
            Log.d("myApp", "submit button "+toastMessage);
        }else {
            setToastMessage(getUserOtp_1()+getUserOtp_2()+getUserOtp_3()+getUserOtp_4()+getUserOtp_5()+getUserOtp_6());
        }
    }
    public void onSendOTPButtonClicked(){
        if(TextUtils.isEmpty(getUserPhoneNumber())){
            setOtpMessage(null);
            Log.d("myApp","otp " +otpMessage);
        }else {
            setOtpMessage(getUserPhoneNumber());
        }
    }
    public void onBackButtonClicked(){

        setBackMessage(backMessageSuccess);
    }

}
