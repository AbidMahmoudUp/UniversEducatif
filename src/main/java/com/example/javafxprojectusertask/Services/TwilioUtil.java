package com.example.javafxprojectusertask.Services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

//recovery code : SDPGRU5NG8AM4AKC1KYV4H1Y
public class TwilioUtil {
    // public static final String ACCOUNT_SID = "AC23c4a6d2f2111a8835dbd6a229d8fccb";
    // public static final String AUTH_TOKEN = "569736d3fcf6f21f887a5c9467baea62";

    public static void SendSMS(String msg) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+21698188600"),
                new com.twilio.type.PhoneNumber("+13203143787"),
                msg)

                        .create();

        System.out.println(message.getSid());
    }
        }