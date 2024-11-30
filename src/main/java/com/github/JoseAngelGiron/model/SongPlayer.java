package com.github.JoseAngelGiron.model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.ByteArrayInputStream;

public class SongPlayer {

    private static Clip currentClip;

    public static void playSong(byte[] songFile) {
        stopSong();
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(songFile));
            currentClip = AudioSystem.getClip();
            currentClip.open(audioStream);
            currentClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopSong() {
        if (currentClip != null && currentClip.isOpen()) {
            currentClip.stop();
            currentClip.close();
            currentClip = null;
        }
    }

}
