package com.example.javafxprojectusertask.Services;
import com.example.javafxprojectusertask.Entities.GoogleOauth;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.*;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Desktop;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.Arrays;

public class GoogleAuth {

    private static final String CLIENT_SECRET_FILE = "C:\\Users\\Wicked\\Desktop\\JavaFxProject\\demo2\\src\\main\\resources\\client_secret_594688753031-2sh461pgd3j5d13l01d7rvadgsfe73dc.apps.googleusercontent.com.json";
    private static final String APPLICATION_NAME = "GoogleOauth";
    private static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final HttpTransport HTTP_TRANSPORT;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    // GoogleAuthorizationCodeFlow instance
    private GoogleAuthorizationCodeFlow flow;


    public void signInWithGoogle() {
        try {
            // Load client secrets
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                    new InputStreamReader(new FileInputStream(CLIENT_SECRET_FILE)));

            // Build GoogleAuthorizationCodeFlow
            flow = new GoogleAuthorizationCodeFlow.Builder(
                    HTTP_TRANSPORT, JSON_FACTORY, clientSecrets,
                    Arrays.asList("https://www.googleapis.com/auth/userinfo.profile", "https://www.googleapis.com/auth/userinfo.email"))
                    .build();

            // Build authorization URL
            AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI);

            // Open browser for user to authorize
            Desktop.getDesktop().browse(new URI(authorizationUrl.build()));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to handle the authorization code received from Google
    public GoogleOauth handleAuthorizationCode(String authorizationCode) {
       GoogleOauth googleOauth = new GoogleOauth();
        try {
            // Exchange authorization code for access token
            GoogleTokenResponse tokenResponse = flow.newTokenRequest(authorizationCode)
                    .setRedirectUri(REDIRECT_URI)
                    .execute();

            // Use access token to fetch user information
            String accessToken = tokenResponse.getAccessToken();
            String userInfoEndpoint = "https://www.googleapis.com/oauth2/v2/userinfo";

            HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory();
            GenericUrl url = new GenericUrl(userInfoEndpoint);
            HttpRequest request = requestFactory.buildGetRequest(url);
            request.getHeaders().setAuthorization("Bearer " + accessToken);
            HttpResponse response = request.execute();

            // Parse response and extract user information
            String content = response.parseAsString();
            System.out.println(content);

// Now you can parse the content JSON and extract user information as needed
            JsonObject jsonObject = new JsonParser().parse(content).getAsJsonObject();
            String email = jsonObject.has("email") ? jsonObject.get("email").getAsString() : "Email non disponible";
            String name = jsonObject.has("name") ? jsonObject.get("name").getAsString() : "name non disponible";
            String id = jsonObject.has("id") ? jsonObject.get("id").getAsString() : "-1";
            Boolean verified_Email = jsonObject.has("verified_email") ? jsonObject.get("verified_email").getAsBoolean() : false;
            String family_name = jsonObject.has("family_name") ? jsonObject.get("family_name").getAsString() : "family_name non disponible";
            String given_name = jsonObject.has("given_name") ? jsonObject.get("given_name").getAsString() : "given_name non disponible";
            String picture = jsonObject.has("picture") ? jsonObject.get("picture").getAsString() : "picture non disponible";
            String locale = jsonObject.has("locale") ? jsonObject.get("locale").getAsString() : "locale non disponible";
            System.out.println("id:" + id);
            System.out.println("Email: " + email);
            googleOauth.setLocale(locale);
            googleOauth.setPicture(picture);
            googleOauth.setVerfied_Email(verified_Email);
            googleOauth.setEmail(email);
            googleOauth.setName(name);
            googleOauth.setGiven_name(given_name);
            googleOauth.setId(id);
            googleOauth.setFamily_name(family_name);

        } catch (TokenResponseException e) {
            // Handle token exchange errors
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googleOauth;
    }
}
