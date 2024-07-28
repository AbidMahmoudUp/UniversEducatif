package com.example.javafxprojectusertask.Services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRGenerator {

    private BufferedImage qrImage;

    public QRGenerator(String data, BarcodeFormat format, int width, int height) throws WriterException {
        this.qrImage = generateQRCode(data, format, width, height);
    }

    private BufferedImage generateQRCode(String data, BarcodeFormat format, int width, int height) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, format, width, height);

        BufferedImage qrImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = qrImage.createGraphics();

        // Set white background
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        // Paint QR code on the image
        graphics.setColor(Color.BLACK);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (bitMatrix.get(x, y)) {
                    graphics.fillRect(x, y, 1, 1);
                }
            }
        }

        graphics.dispose();
        return qrImage;
    }

    public void save(String pathname, String formatName) throws IOException {
        ImageIO.write(qrImage, formatName, new File(pathname));
    }

    public BufferedImage qrImage() {
        return this.qrImage;
    }
}
