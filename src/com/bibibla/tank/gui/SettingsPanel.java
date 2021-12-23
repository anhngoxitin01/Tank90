package com.bibibla.tank.gui;


import com.bibibla.tank.manager.SoundManager;
import com.bibibla.tank.util.Const;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SettingsPanel extends JPanel implements KeyListener {
    private GameFrame mGameFrame;
    private SoundManager mSoundManager;

    public SettingsPanel(GameFrame frame,SoundManager soundManager) {
        this.mGameFrame = frame;
        this.mSoundManager = soundManager;
        setLayout(null);
        addKeyListener(this);
        setFocusable(true);
        mSoundManager.playSound(Const.SOUND_ENTER_GAME);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Image image = new ImageIcon(getClass()
                .getResource("/IMAGES/game_start.png"))
                .getImage();
        g2d.drawImage(image, 0,0, Const.WIDTH_FRAME, Const.HEIGHT_FRAME,null);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_SPACE) {
            mSoundManager.getArrSound().get(Const.SOUND_ENTER_GAME).closeOpenGame();
            this.mGameFrame.showGame();
        } else if (e.getKeyChar() == KeyEvent.VK_P) {

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
