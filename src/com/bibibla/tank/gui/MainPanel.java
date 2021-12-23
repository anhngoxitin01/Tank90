package com.bibibla.tank.gui;

import com.bibibla.tank.manager.ImageMgr;
import com.bibibla.tank.manager.SoundManager;
import com.bibibla.tank.util.Const;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private GameFrame mGameFrame;
    private MapPanel mapPanel;
    private SoundManager mSoundManager;
    private int bossCount = 24;

    public MainPanel(GameFrame frame, SoundManager soundManager) {
        this.mSoundManager = soundManager;
        this.mGameFrame = frame;
        setLayout(null);
        mapPanel = new MapPanel(this,mGameFrame,mSoundManager);
        add(mapPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(ImageMgr.imageLeft,
                Const.PADDING_LEFT,
                Const.PADDING_TOP,
                Const.LEFT_RIGHT_SIZE,
                Const.MAP_SIZE,
                null);

        g2d.drawImage(ImageMgr.imageRight,
                Const.RIGHT_START_X,
                Const.PADDING_TOP,
                Const.LEFT_RIGHT_SIZE,
                Const.MAP_SIZE,
                null);

        drawBossIcon(g2d);
    }

    public void updateBossCount(){
        this.bossCount -= 3;
        repaint();
    }

    private void drawBossIcon(Graphics2D g2d) {
        for (int i = 0; i < bossCount / 3; i++) {
            g2d.drawImage(ImageMgr.imageIconBoss,
                    Const.RIGHT_START_X + 50,
                    Const.PADDING_TOP + 50 * i + 30,
                    150,
                    30,
                    null);
        }
    }

    public MapPanel getMapPanel() {
        return mapPanel;
    }

    public void setMapPanel(MapPanel mapPanel) {
        this.mapPanel = mapPanel;
    }

    public int getBossCount() {
        return bossCount;
    }

    public void setBossCount(int bossCount) {
        this.bossCount = bossCount;
    }
}
