package com.bibibla.tank.model;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    private String url;
    private AudioInputStream audioInputStream;
    private Clip clip;
    private File file;

    public Sound() {
    }

    public Sound(String url) {
        this.url = url;
        this.file = new File("D:/IntelliJ_workspace/T3H/temp_3/TankSwing/src/SOUNDS/" + url);
    }

    public void play(){
        try {
            clip = AudioSystem.getClip();
            audioInputStream = AudioSystem.getAudioInputStream(file);
            clip.open(audioInputStream);
            clip.start();
            while(!clip.isRunning()){
                audioInputStream.close();
            }
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playOpenGame(){
        try {
            clip = AudioSystem.getClip();
            audioInputStream = AudioSystem.getAudioInputStream(file);
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            while(!clip.isRunning()){
                audioInputStream.close();
            }
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void closeOpenGame() {
        try {
            clip.close();
            audioInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Clip getClip() {
        return clip;
    }

    public void setClip(Clip clip) {
        this.clip = clip;
    }

}
