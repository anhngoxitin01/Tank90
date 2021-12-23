package com.bibibla.tank.manager;

import com.bibibla.tank.model.TankPlayer;
import com.bibibla.tank.model.Bird;
import com.bibibla.tank.model.Boss;
import com.bibibla.tank.util.Const;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BossManager {
    List<Boss> mArrBoss;
    private int bosses = 24;
    private long countNumMove = 0;
    private MapManager mMapManager;
    private Bird mBird;
    private TankPlayer mTankPlayer;
    private SoundManager mSoundManager;

    public BossManager(MapManager mapManager, Bird bird, TankPlayer tankPlayer,SoundManager soundManager) {
        this.mMapManager = mapManager;
        this.mBird = bird;
        this.mTankPlayer = tankPlayer;
        this.mSoundManager = soundManager;
        mArrBoss = new ArrayList<>();
    }

    public boolean addBoss() {
        if (bosses > 0 && mArrBoss.size() == 0) {
            bosses -= 3;
            int level = bosses > 12 ? 1 : bosses > 3 ? 2 : 3;
            for (int i = 0; i < 3; i++) {
                mArrBoss.add(new Boss(i * (Const.MAP_SIZE / 2 - Const.TANK_SIZE / 2),
                        0,
                        level,
                        mMapManager,
                        mBird,
                        mTankPlayer,
                        this));
            }
            return true;
        }
        return false;
    }

    public void moveAllBoss(){
        for(int i=0 ;i<mArrBoss.size() ;i++){
            Boss boss = mArrBoss.get(i);
            int orient = boss.getOrient();
            if(countNumMove % 500 == 0){
                orient = new Random().nextInt(4);
                countNumMove = 0;
            }
            mArrBoss.get(i).moveBoss(orient,i);
            countNumMove++;
        }
    }

    public void drawAllBoss(Graphics2D g2d) {
        for (int i = 0; i < mArrBoss.size(); i++) {
            mArrBoss.get(i).draw(g2d);
        }
    }

    public BulletManager bossShout() {
        BulletManager temp = new BulletManager();
        for(int i=0 ; i<mArrBoss.size() ; i++){
            Boss boss = mArrBoss.get(i);
            if(boss.isCanFire()) {
                temp.addBullet(boss.shout());
                boss.setCanFire(false);
                mSoundManager.playSound(Const.SOUND_SHOOT);
            }
        }
        return temp;
    }



    public boolean isCanFire() {
        return true;
    }

    public List<Boss> getmArrBoss() {
        return mArrBoss;
    }

    public void setmArrBoss(List<Boss> mArrBoss) {
        this.mArrBoss = mArrBoss;
    }

    public int getBosses() {
        return bosses;
    }

    public void setBosses(int bosses) {
        this.bosses = bosses;
    }
}
