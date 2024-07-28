package com.example.javafxprojectusertask.Interfaces;

import org.json.JSONObject;

public interface AuthCallback {
    void onLoginSuccess(JSONObject js);
    void onLoginFailure(Exception e);
}
