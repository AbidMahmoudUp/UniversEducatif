package com.example.javafxprojectusertask.Interfaces;

public interface AudioService {

    /**
     * Converts a given audio path into Speech as a String.
     *
     * @param audioFilePath
     * @return String
     */
    String audioToText(String audioFilePath);

    /**
     * records the audio via machine micro and returns the path to a wav file.
     *
     * @return String.
     */
    String recordAudio();
    void textToAudio(String fileString,String fileName);
}
