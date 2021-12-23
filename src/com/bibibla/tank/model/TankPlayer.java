package com.bibibla.tank.model;

import com.bibibla.tank.manager.BossManager;
import com.bibibla.tank.manager.ImageMgr;
import com.bibibla.tank.manager.MapManager;
import com.bibibla.tank.util.Const;

public class TankPlayer extends TankObject {
    private int id;
    private int orient;
    private float speed;
    private int liveCount = 3;
    private boolean canFire;
    private MapManager mMapManager;
    private Bird mBird;
    private BossManager mBossManager;

    public TankPlayer() {
        id = Const.TANK_ID;
        orient = Const.UP_ORIENT;
        speed = Const.STANDARD_SPEED / 2;
        icon = ImageMgr.arrPlayerImages.get(orient);

        x = 9 * Const.ITEM_SIZE;
        y = 24 * Const.ITEM_SIZE;
        width = Const.TANK_SIZE;
        height = Const.TANK_SIZE;

        canFire = true;
    }

    public void newTank() {
        x = 9 * Const.ITEM_SIZE;
        y = 24 * Const.ITEM_SIZE;
        orient = Const.UP_ORIENT;
        this.icon = ImageMgr.arrPlayerImages.get(orient);
    }

    public void setReference(MapManager mapManager, Bird bird, BossManager bossManager) {
        this.mMapManager = mapManager;
        this.mBird = bird;
        this.mBossManager = bossManager;
    }

    public TankPlayer(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void moveTank(int orient) {
        if (this.orient != orient) {
            x = (x + Const.ITEM_SIZE/2)/Const.ITEM_SIZE * Const.ITEM_SIZE;
            y = (y + Const.ITEM_SIZE/2)/Const.ITEM_SIZE * Const.ITEM_SIZE;
            this.orient = orient;
            this.icon = ImageMgr.arrPlayerImages.get(orient);
        }

        switch (orient) {
            case Const.UP_ORIENT:
                if (y > 0) {
                    this.y -= speed;
                    if (intersectWithMapAndBird() || intersectWithBoss()) {
                        y += speed;
                    }
                }
                break;
            case Const.DOWN_ORIENT:
                if (y < Const.MAP_SIZE - Const.TANK_SIZE) {
                    this.y += speed;
                    if (intersectWithMapAndBird() || intersectWithBoss()) {
                        y -= speed;
                    }
                }
                break;
            case Const.RIGHT_ORIENT:
                if (x < Const.MAP_SIZE - Const.TANK_SIZE) {
                    this.x += speed;
                    if (intersectWithMapAndBird() || intersectWithBoss()) {
                        x -= speed;
                    }
                }
                break;
            case Const.LEFT_ORIENT:
                if (x > 0) {
                    this.x -= speed;
                    if (intersectWithMapAndBird() || intersectWithBoss()) {
                        x += speed;
                    }
                }
                break;
        }
    }

    private boolean intersectWithBoss() {
        for(int i=0 ;i<mBossManager.getmArrBoss().size(); i++){
            Boss boss = mBossManager.getmArrBoss().get(i);
            if(getRect().intersects(boss.getRect())){
                return true;
            }
        }
        return false;
    }


    private boolean intersectWithMapAndBird() {
        for (int i = 0; i < mMapManager.getArrMaps().size(); i++) {
            if (getRect().intersects(mMapManager.getArrMaps().get(i).getRect())
                    && !mMapManager.getArrMaps().get(i).isAllowTankPass()) {
                    return true;
            }
        }
        if(getRect().intersects(mBird.getRect())){
            return true;
        }
        return false;
    }

    public Bullet fireBullet() {
        return new Bullet(Const.TANK_ID,
                x + (Const.TANK_SIZE - Const.BULLET_SIZE) / 2,
                y + (Const.TANK_SIZE - Const.BULLET_SIZE) / 2,
                orient);
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrient() {
        return orient;
    }

    public void setOrient(int orient) {
        this.orient = orient;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getLiveCount() {
        return liveCount;
    }

    public void setLiveCount(int liveCount) {
        this.liveCount = liveCount;
    }

    public boolean isCanFire() {
        return canFire;
    }

    public void setCanFire(boolean canFire) {
        this.canFire = canFire;
    }


}
