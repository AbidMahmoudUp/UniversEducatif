package com.example.javafxprojectusertask.Services;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
public class ServiceSms {
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "AC4abfa5e3a174c9b8ea76372e3b98c670";
    public static final String AUTH_TOKEN = "7238ee3410f608006b8a482af0aae61a";

    public static void SendSMS(String msg) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+21625630222"),
                        new com.twilio.type.PhoneNumber("+14433643140"),
                        msg)

                .create();

        System.out.println(message.getSid());
    }
}
