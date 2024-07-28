package com.example.javafxprojectusertask.Utilities;

import com.example.javafxprojectusertask.Test.Application;
import com.example.javafxprojectusertask.Entities.UserClass;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class UserUtils {

    private static final String USER_PREFERENCES_NODE = "user_preferences";
    public static UserClass ConnectedUser;
    public UserClass getConnectedUser() {
        Preferences preferences = Preferences.userNodeForPackage(Application.class);
        UserClass user = new UserClass(preferences.getInt("idConnectedUser", -1),
                preferences.get("UsernameConnectedUser", "username not found"),
                preferences.get("passwordConnectedUser", ""),
                preferences.get("StatusConnectedUser", "Status Not found"),
                preferences.get("emailConnectedUser", "Email@noFound.com"));

        return user;

    }

    public void saveObjectToPreferences(Preferences preferences, UserClass user) {
        preferences.putInt("idConnectedUser", user.getIdUser());
        preferences.put("emailConnectedUser", user.getEmail());
        preferences.put("passwordConnectedUser", user.getPassword());
        preferences.put("StatusConnectedUser", user.getStatus());
        preferences.put("UsernameConnectedUser", user.getUserName());
    }

    public UserClass clearUser() throws BackingStoreException {
        Preferences preferences = Preferences.userNodeForPackage(Application.class);
       // preferences.clear();

        // Create and return a UserClass object with default values
        UserClass user = new UserClass(-1, "username not found", "", "Status Not found", "Email@noFound.com");
        preferences.putInt("idConnectedUser", user.getIdUser());
        preferences.put("emailConnectedUser", user.getEmail());
        preferences.put("passwordConnectedUser", user.getPassword());
        preferences.put("StatusConnectedUser", user.getStatus());
        preferences.put("UsernameConnectedUser", user.getUserName());
        return user;
    }
}