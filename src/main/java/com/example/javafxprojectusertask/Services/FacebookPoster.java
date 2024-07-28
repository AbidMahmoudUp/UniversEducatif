package com.example.javafxprojectusertask.Services;

import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class FacebookPoster {
    private WebView webView;
    private WebEngine webEngine;

    public FacebookPoster() {
        webView = new WebView();
        webEngine = webView.getEngine();
        webEngine.load(getClass().getResource("/fbApi/apiPartage.html").toExternalForm());

        // Execute the JavaScript function after the page loads
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                System.out.println("Page loaded successfully.");
            }
        });
    }

    public void postToFacebook(String message) {
        // Call the JavaScript function after the page loads
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                // Call the JavaScript function with the message
                webEngine.executeScript("updatePostField('" + message + "')");
            }
        });
    }

    public WebView getWebView() {
        return webView;
    }
}
