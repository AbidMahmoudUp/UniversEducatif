package com.example.javafxprojectusertask.Services;

import ai.picovoice.leopard.Leopard;
import ai.picovoice.leopard.LeopardException;
import ai.picovoice.leopard.LeopardTranscript;
import com.example.javafxprojectusertask.Interfaces.AudioService;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;
import org.apache.commons.cli.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class ServiceAudio implements AudioService {
    private static final String ACCESS_KEY = "B+UBeKRWGmFgEVx0/5uMox/Bn/MuR47tzxJ9OrpEaV26hmiCgqqIJw==";
    static final long RECORD_TIME = 10000;  // 10 s
    File wavFile = new File("C:\\Users\\sboui\\Documents\\RecordAudio.wav");
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    TargetDataLine line;

    @Override
    public String audioToText(String audioFilePath) {
        final Leopard leopard = this.generateLeopard();
        return this.generateTranscriptionFromLeopard(leopard, audioFilePath);
    }

    @Override
    public String recordAudio() {

        // creates a new thread that waits for a specified
        // of time before stopping
        Thread stopper = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(RECORD_TIME);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
               finish();
            }
        });
        stopper.start();
        // start recording
        this.start();
        return wavFile.getAbsolutePath();
    }

    @Override
    public void textToAudio(String fileString,String fileName) {
        System.setProperty(
                "freetts.voices",
                "com.sun.speech.freetts.en.us"
                        + ".cmu_us_kal.KevinVoiceDirectory");
        Voice voice;
        voice = VoiceManager.getInstance().getVoice("kevin16");//Getting voice
        SingleFileAudioPlayer audioPlayer = new SingleFileAudioPlayer("C:/xampp/htdocs/files/"+fileName, AudioFileFormat.Type.WAVE);


        if (voice != null) {
            voice.allocate();
            voice.setAudioPlayer(audioPlayer);

        }
        try {
            voice.setRate(190); // Réglage du débit de la voix
            voice.setPitch(150); // Réglage de la hauteur de la voix
            voice.setVolume(5); // Réglage du volume de la voix
            voice.speak(fileString);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (voice != null) {
                voice.deallocate();
            }
            if (audioPlayer != null) {
                audioPlayer.close();
            }
        }
    }

    /**
     * Defines an audio format
     */
    private AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                channels, signed, bigEndian);
        return format;
    }
    /**
     * Captures the sound and record into a WAV file
     */
    private void start() {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();   // start capturing

            System.out.println("Start capturing...");

            AudioInputStream ais = new AudioInputStream(line);

            System.out.println("Start recording...");

            // start recording
            AudioSystem.write(ais, fileType, wavFile);

        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Closes the target data line to finish capturing and recording
     */
    private void finish() {
        line.stop();
        line.close();
        System.out.println("Finished");
        AudioService audioService = new ServiceAudio();
        System.out.println(audioService.audioToText("C:\\Users\\sboui\\Documents\\RecordAudio.wav"));
    }

    /**
     * generates a String from a Leopard Object and an audio file path.
     *
     * @param leopard
     * @param audioFilePath
     * @return
     */
    private String generateTranscriptionFromLeopard(Leopard leopard, String audioFilePath) {
        LeopardTranscript transcript = null;
        try {
            transcript = leopard.processFile(audioFilePath);
        } catch (LeopardException e) {
            throw new RuntimeException(e);
        }
        return transcript.getTranscriptString();
    }

    /**
     * Generates a Leopard object with its params.
     *
     * @return Leopard.
     */
    private Leopard generateLeopard() {
        CommandLine cmd;
        Options options = buildCommandLineOptions();
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        try {
            cmd = parser.parse(options, new String[0]);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("leopardfiledemo", options);
            System.exit(1);
            return null;
        }
        Leopard result = null;
        try {
            result = new Leopard.Builder()
                    .setAccessKey(ACCESS_KEY)
                    .setLibraryPath(cmd.getOptionValue("library_path"))
                    .setModelPath(cmd.getOptionValue("model_path"))
                    .setEnableAutomaticPunctuation(Boolean.TRUE)
                    .setEnableDiarization(Boolean.TRUE)
                    .build();
        } catch (LeopardException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * Static method to get command line Options and their current build.
     *
     * @return
     */
    private static Options buildCommandLineOptions() {
        Options options = new Options();

        options.addOption(Option.builder("a")
                .longOpt("access_key")
                .hasArg(true)
                .desc("AccessKey obtained from Picovoice Console (https://console.picovoice.ai/).")
                .build());

        options.addOption(Option.builder("m")
                .longOpt("model_path")
                .hasArg(true)
                .desc("Absolute path to the file containing model parameters.")
                .build());

        options.addOption(Option.builder("l")
                .longOpt("library_path")
                .hasArg(true)
                .desc("Absolute path to the Leopard native runtime library.")
                .build());

        options.addOption(Option.builder("dp")
                .longOpt("disable_automatic_punctuation")
                .desc("Disable automatic punctuation.")
                .build());

        options.addOption(Option.builder("dd")
                .longOpt("disable_speaker_diarization")
                .desc("Disable speaker diarization.")
                .build());

        options.addOption(Option.builder("i")
                .longOpt("input_audio_path")
                .hasArg(true)
                .desc("Absolute path to input audio file.")
                .build());

        options.addOption(Option.builder("v")
                .longOpt("verbose")
                .desc("Enable verbose logging.")
                .build());

        options.addOption(new Option("h", "help", false, ""));

        return options;
    }
}
