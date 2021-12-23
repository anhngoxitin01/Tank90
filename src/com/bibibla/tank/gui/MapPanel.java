package com.bibibla.tank.gui;

import com.bibibla.tank.manager.*;
import com.bibibla.tank.model.Bird;
import com.bibibla.tank.model.TankPlayer;
import com.bibibla.tank.util.Const;
import com.bibibla.tank.manager.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;


public class MapPanel extends JPanel implements KeyListener, Runnable {
    private EffectManager mEffectManager;
    private MapManager mapManager;
    private GameFrame mGameFrame;
    private Bird mBird;
    private TankPlayer mTankPlayer;
    private BulletManager mBulletManager;
    private BossManager mBossManager;
    private MainPanel mMainPanel;
    private SoundManager mSoundManager;
    private boolean isRunning = false;
    private final BitSet mBitSet = new BitSet(256);     // 256 ky tu tren ban phim
    private long count;
    public static int statusGame = 1;


    public MapPanel(MainPanel MainPanel,GameFrame gameFrame, SoundManager soundManager) {
        this.mGameFrame = gameFrame;
        this.mSoundManager = soundManager;
        this.mMainPanel = MainPanel;
        setBounds((Const.WIDTH_FRAME - Const.MAP_SIZE) / 2,
                Const.PADDING_TOP,
                Const.MAP_SIZE,
                Const.MAP_SIZE);
        setBackground(Color.BLACK);
        initComponents();
        addKeyListener(this);
        setFocusable(true);                                     // co add key listener thi phai co focusable
        setLayout(null);

        setVisible(true);
    }

    private void initComponents() {             // noi init
        mBitSet.clear();        // xoa toan bo
        mapManager = new MapManager();
        mBird = new Bird(12 * Const.ITEM_SIZE,
                24 * Const.ITEM_SIZE,
                Const.TANK_SIZE,
                Const.TANK_SIZE);
        mTankPlayer = new TankPlayer();
        mEffectManager = new EffectManager();
        mBossManager = new BossManager(mapManager, mBird, mTankPlayer,mSoundManager);
        mBulletManager = new BulletManager(this.mapManager, this.mBossManager, this.mTankPlayer,mSoundManager,mEffectManager,mBird);

        mTankPlayer.setReference(mapManager, mBird, mBossManager);
        new Thread(this).start();           // de chay cai runnable
    }

    @Override
    protected void paintComponent(Graphics g) {     // noi ve || thang nao ve trc hien thi trc
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        mapManager.drawMap(g2d);                                // phai co draw neu ko co draw ko ve dc
        mBird.draw(g2d);
        mBulletManager.drawAllBullets(g2d);
        mTankPlayer.draw(g2d);
        mBossManager.drawAllBoss(g2d);
        mEffectManager.drawAllEffect(g2d);
    }

    @Override
    public void keyTyped(KeyEvent e) {                          // dung getKeyChar
        if (mBitSet.get(KeyEvent.VK_P)) {
            isRunning = !isRunning;
            return;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {                        // dung getKeyCode
        mBitSet.set(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        mBitSet.clear(e.getKeyCode());                  // chi xoa mot key
    }

    @Override
    public void run() {
        while (true) {
            checkGame();

            if (isRunning && mBird.isAlive()) {
                if (mTankPlayer.getLiveCount() == 0) {               //
                    statusGame = -1;                             // game over
                }                                                    //
                count++;
                if (count % 80 == 0) {
                    mTankPlayer.setCanFire(true);
                    if(count % 160 == 0) {
                        for (int i = 0; i < mBossManager.getmArrBoss().size(); i++) {
                            mBossManager.getmArrBoss().get(i).setCanFire(true);
                        }
                    }
                }
                if (count == 100000000) {
                    count = 0;
                }
                mBulletManager.moveAllBullets();
                if (mBossManager.addBoss()) {
                    mMainPanel.updateBossCount();
                }
                mBossManager.moveAllBoss();
                if (mBossManager.isCanFire()) {
                    mBulletManager.getBullets().addAll(mBossManager.bossShout().getBullets());
                }
                if(mBossManager.getBosses() == 0 && mBossManager.getmArrBoss().size() == 0){
                    mBossManager.setBosses(24);
                    mMainPanel.setBossCount(24);
                    mapManager.newMapGame();
                    mSoundManager.playSound(Const.SOUND_LEVEL_COMPLETED);
                    repaint();
                }
            }
            try {
                Thread.sleep(7);
            } catch (InterruptedException e) {      // thread bi pha huy
                e.printStackTrace();
            }
            catchEvent();
            repaint();
        }
    }

    private void catchEvent() {
        if (!isRunning) {
            return;
        }

        if (mBitSet.get(KeyEvent.VK_UP)) {
            mTankPlayer.moveTank(Const.UP_ORIENT);
        } else if (mBitSet.get(KeyEvent.VK_DOWN)) {
            mTankPlayer.moveTank(Const.DOWN_ORIENT);
        } else if (mBitSet.get(KeyEvent.VK_LEFT)) {
            mTankPlayer.moveTank(Const.LEFT_ORIENT);
        } else if (mBitSet.get(KeyEvent.VK_RIGHT)) {
            mTankPlayer.moveTank(Const.RIGHT_ORIENT);
        }

        if (mBitSet.get(KeyEvent.VK_SPACE)) {
            if (mTankPlayer.isCanFire()) {
                mBulletManager.addBullet(mTankPlayer.fireBullet());
                mTankPlayer.setCanFire(false);
                mSoundManager.playSound(Const.SOUND_SHOOT);
            }
        }
    }

    public void checkGame(){
        if(statusGame == 1){
            return;
        } else if(statusGame == -1){
            int option = JOptionPane.showConfirmDialog(null,"Do you want to play again","Note",JOptionPane.YES_NO_OPTION);
            if(option == JOptionPane.YES_OPTION){
                mapManager.setLevel(1);
                initComponents();
                statusGame = 1;
            } else if(option == JOptionPane.NO_OPTION){
                System.exit(0);
            }
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
