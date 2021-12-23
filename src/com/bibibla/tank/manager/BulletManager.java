package com.bibibla.tank.manager;

import com.bibibla.tank.model.*;
import com.bibibla.tank.util.Const;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BulletManager {
    private List<Bullet> mBullets;
    private MapManager mapManager;
    private BossManager mBossManager;
    private TankPlayer mTankPlayer;
    private SoundManager mSoundManager;
    private EffectManager mEffectManager;
    private Bird mBird;

    public BulletManager(MapManager map, BossManager bossManager, TankPlayer tankPlayer, SoundManager soundManager, EffectManager effectManager
                            , Bird bird) {
        this.mBossManager = bossManager;
        this.mTankPlayer = tankPlayer;
        this.mapManager = map;
        this.mSoundManager = soundManager;
        this.mEffectManager = effectManager;
        this.mBird = bird;
        mBullets = new ArrayList<>();
    }

    public BulletManager() {
        mBullets = new ArrayList<>();
    }

    public void addBullet(Bullet bullet) {    // truyen id hoac vi tri
        mBullets.add(bullet);
    }

    public void moveAllBullets() {
        for (int i = 0; i < mBullets.size(); i++) {
            if (!mBullets.get(i).moveBullet()) {
                mBullets.remove(i);
                continue;
            }
            checkBulletIntersectWithObject(mBullets.get(i));

        }
    }

    private void checkBulletIntersectWithObject(Bullet bullet) {
        if (!checkBulletIntersectWithMap(bullet)) {
            if (!checkBulletIntersectWithTank(bullet)) {
                if(!checkBulletIntersectWithAnotherBullet(bullet)){
                    checkBulletIntersectWithBird(bullet);
                }
            }
        }
    }

    private boolean checkBulletIntersectWithBird(Bullet bullet) {
        if(bullet.getRect().intersects(mBird.getRect())){
            mTankPlayer.setLiveCount(0);
            return true;
        }
        return false;
    }

    private boolean checkBulletIntersectWithAnotherBullet(Bullet bullet) {
        for (int i = 0; i < mBullets.size(); i++) {
            Bullet bullet1 = mBullets.get(i);
            if (bullet1.getId() != bullet.getId()) {
                if (bullet.getRect().intersects(bullet1.getRect())) {
                    mBullets.remove(bullet);
                    mBullets.remove(bullet1);
                    mSoundManager.playSound(Const.SOUND_BUM);
                }
            }
        }
        return false;
    }

    private boolean checkBulletIntersectWithTank(Bullet bullet) {
        if (bullet.getId() == Const.TANK_ID) {
            for (int i = 0; i < mBossManager.getmArrBoss().size(); i++) {
                Boss boss = mBossManager.mArrBoss.get(i);
                if (bullet.getRect().intersects(boss.getRect())) {
                    mBullets.remove(bullet);
                    if (boss.bossBeDie()) {
                        mBossManager.mArrBoss.remove(boss);
                        mSoundManager.playSound(Const.SOUND_BUM_TANK);
                        mEffectManager.addEffect(new Effect(boss.getX(), boss.getY()));
                    }
                    return true;
                }
            }
        } else {
            if (bullet.getRect().intersects(mTankPlayer.getRect())) {
                mBullets.remove(bullet);
                mTankPlayer.setLiveCount(mTankPlayer.getLiveCount() - 1);
                mSoundManager.playSound(Const.SOUND_BUM_TANK);
                mTankPlayer.newTank();
                return true;
            }
        }
        return false;
    }


    private boolean checkBulletIntersectWithMap(Bullet bullet) {
        boolean isIntersectWithItem = false;
        for (int i = 0; i < mapManager.getArrMaps().size(); i++) {
            MapItem mapItem = mapManager.getArrMaps().get(i);
            if (bullet.getRect().intersects(mapItem.getRect())
                    && !mapItem.isAllowBulletPass()) {
                mapManager.getArrMaps().remove(mapItem);
                mBullets.remove(bullet);
                mSoundManager.playSound(Const.SOUND_BUM);
                mapManager.getArrWayMaps()[mapItem.getY() / Const.ITEM_SIZE][mapItem.getX() / Const.ITEM_SIZE] = 0;
                isIntersectWithItem = true;
            }
        }
        return isIntersectWithItem;
    }

    public void drawAllBullets(Graphics2D g2d) {        // vi ko co g2d nen ta phai truyen graphic2d vao day || ko dc tao Graphic
        for (int i = 0; i < mBullets.size(); i++) {
            mBullets.get(i).draw(g2d);
        }
    }

    public List<Bullet> getBullets() {
        return mBullets;
    }

    public void setBullets(List<Bullet> mBullets) {
        this.mBullets = mBullets;
    }
}
