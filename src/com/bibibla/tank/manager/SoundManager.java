package com.bibibla.tank.manager;

import com.bibibla.tank.model.Sound;
import com.bibibla.tank.util.Const;

import java.util.ArrayList;

public class SoundManager {
    private ArrayList<Sound> arrSound;
    private int amountSound = 8;


    public SoundManager() {
        arrSound = new ArrayList<>();
        initArrSound();
    }

    private void initArrSound() {
        arrSound.add(new Sound("bum.wav"));
        arrSound.add(new Sound("bum_tank.wav"));
        arrSound.add(new Sound("enter_game.wav"));
        arrSound.add(new Sound("level_completed.wav"));
        arrSound.add(new Sound("life.wav"));
        arrSound.add(new Sound("move.wav"));
        arrSound.add(new Sound("moving.wav"));
        arrSound.add(new Sound("shoot.wav"));
    }

    public void playSound(int indexSound){
        if(indexSound == Const.SOUND_ENTER_GAME){
            arrSound.get(indexSound).playOpenGame();
        } else {
            Sound sound = arrSound.get(indexSound);
            sound.play();
        }
    }



    public SoundManager(ArrayList<Sound> arrSound) {
        this.arrSound = arrSound;
    }

    public ArrayList<Sound> getArrSound() {
        return arrSound;
    }

    public void setArrSound(ArrayList<Sound> arrSound) {
        this.arrSound = arrSound;
    }
}
