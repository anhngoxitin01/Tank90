package com.bibibla.tank.gui;

import com.bibibla.tank.manager.SoundManager;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private SettingsPanel mSettingsPanel;
    private MainPanel mMainPanel;
    private SoundManager mSoundManager;

    public GameFrame() {
        mSoundManager = new SoundManager();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setLayout(new CardLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        mMainPanel = new MainPanel(this,mSoundManager);
        mSettingsPanel = new SettingsPanel(this,mSoundManager);
        add(mSettingsPanel);
        add(mMainPanel);
    }

    public void showGame() {
        mMainPanel.getMapPanel().setRunning(true);
        mMainPanel.setVisible(true);
        mSettingsPanel.setVisible(false);
    }

    public void showSettings() {
        mMainPanel.setVisible(false);
        mSettingsPanel.setVisible(true);
    }
}
