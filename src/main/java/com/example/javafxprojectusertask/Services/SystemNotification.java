package com.example.javafxprojectusertask.Services;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SystemNotification {

    public static void trigger(String title, String message) {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            java.net.URL imageUrl = SystemNotification.class.getResource("/Images/activeIcon.png"); // Path to your icon image
            Image image = toolkit.getImage(imageUrl);
            TrayIcon trayIcon = new TrayIcon(image, "Mail Notification");
            trayIcon.setImageAutoSize(true);

            trayIcon.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Handle action (if any) when the tray icon is clicked
                }
            });

            try {
                tray.add(trayIcon);
                trayIcon.displayMessage(title, message, MessageType.INFO);
            } catch (AWTException e) {
                System.err.println("TrayIcon could not be added.");
            }
        } else {
            System.err.println("SystemTray is not supported.");
        }
    }
}
