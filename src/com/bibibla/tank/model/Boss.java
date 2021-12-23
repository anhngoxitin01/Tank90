package com.bibibla.tank.model;

import com.bibibla.tank.manager.BossManager;
import com.bibibla.tank.manager.MapManager;
import com.bibibla.tank.manager.ImageMgr;
import com.bibibla.tank.util.Const;

public class Boss extends TankObject {
    private int id;
    private int orient;
    private float speed;
    private int level;
    private int hp;
    private boolean isCanFire;
    private MapManager mMapManager;
    private Bird mBird;
    private TankPlayer mtankPlayer;
    private BossManager mBossManager;

    public Boss(int x, int y, int level, MapManager mapManager, Bird bird, TankPlayer tankPlayer, BossManager bossManager) {
        super(x, y);
        this.level = level;
        this.id = Const.BOSS_ID;
        this.orient = Const.DOWN_ORIENT;
        this.hp = level;
        this.speed = Const.STANDARD_SPEED / 4;
        this.width = Const.TANK_SIZE;
        this.height = Const.TANK_SIZE;
        this.isCanFire = true;

        this.mMapManager = mapManager;
        this.mBird = bird;
        this.mtankPlayer = tankPlayer;
        this.mBossManager = bossManager;

        updateIcon();
    }

    private void updateIcon() {
        switch (level) {
            case 1:
                this.icon = ImageMgr.arrBoss1Images.get(orient);
                break;
            case 2:
                if (hp == 2) this.icon = ImageMgr.arrBoss22Images.get(orient);
                if (hp == 1) this.icon = ImageMgr.arrBoss21Images.get(orient);
                break;
            case 3:
                if (hp == 3) this.icon = ImageMgr.arrBoss33Images.get(orient);
                if (hp == 2) this.icon = ImageMgr.arrBoss32Images.get(orient);
                if (hp == 1) this.icon = ImageMgr.arrBoss31Images.get(orient);
                break;
        }
    }

    public void moveBoss(int orient, int index) {
        if (this.orient != orient) {
            this.orient = orient;
            updateIcon();
        }

        switch (orient) {
            case Const.UP_ORIENT:
                if (y > 0) {
                    this.y -= speed;
                    if (intersectWithMapAndBird() || intersectWithTankPlayer() || intersectWithOtherBoss(index)) {
                        y += speed;
                    }
                }
                break;
            case Const.DOWN_ORIENT:
                if (y < Const.MAP_SIZE - Const.TANK_SIZE) {
                    this.y += speed;
                    if (intersectWithMapAndBird() || intersectWithTankPlayer() || intersectWithOtherBoss(index)) {
                        y -= speed;
                    }
                }
                break;
            case Const.RIGHT_ORIENT:
                if (x < Const.MAP_SIZE - Const.TANK_SIZE) {
                    this.x += speed;
                    if (intersectWithMapAndBird() || intersectWithTankPlayer() || intersectWithOtherBoss(index)) {
                        x -= speed;
                    }
                }
                break;
            case Const.LEFT_ORIENT:
                if (x > 0) {
                    this.x -= speed;
                    if (intersectWithMapAndBird() || intersectWithTankPlayer() || intersectWithOtherBoss(index)) {
                        x += speed;
                    }
                }
                break;
        }
        if (intersectWithMapBorders()) {   // viền map
            updateIcon();
        }
    }

    private boolean intersectWithMapBorders() {
        if (x == 0 && y == 0 && (orient == Const.UP_ORIENT || orient == Const.LEFT_ORIENT)) {
            if (orient == Const.LEFT_ORIENT) {
                orient = Const.DOWN_ORIENT;
            } else if (orient == Const.UP_ORIENT) {
                orient = Const.RIGHT_ORIENT;
            }
            return true;
        } else if (x == 672 && y == 0 && (orient == Const.RIGHT_ORIENT || orient == Const.UP_ORIENT)) {
            if (orient == Const.UP_ORIENT) {
                orient = Const.LEFT_ORIENT;
            } else if (orient == Const.RIGHT_ORIENT) {
                orient = Const.DOWN_ORIENT;
            }
            return true;
        } else if (x == 0 && y == 672 && (orient == Const.DOWN_ORIENT || orient == Const.LEFT_ORIENT)) {
            if (orient == Const.DOWN_ORIENT) {
                orient = Const.RIGHT_ORIENT;
            } else if (orient == Const.LEFT_ORIENT) {
                orient = Const.UP_ORIENT;
            }
            return true;
        } else if (x == 672 && y == 672 && (orient == Const.RIGHT_ORIENT || orient == Const.DOWN_ORIENT)) {
            if (orient == Const.DOWN_ORIENT) {
                orient = Const.LEFT_ORIENT;
            } else if (orient == Const.RIGHT_ORIENT) {
                orient = Const.UP_ORIENT;
            }
            return true;
        }

        return false;
    }

    private boolean intersectWithOtherBoss(int index) {
        for (int i = 0; i < mBossManager.getmArrBoss().size(); i++) {
            if (i != index) {
                Boss boss = mBossManager.getmArrBoss().get(i);
                if (getRect().intersects(boss.getRect())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean intersectWithTankPlayer() {
        if (getRect().intersects(mtankPlayer.getRect()))
            return true;
        return false;
    }

    private boolean intersectWithMapAndBird() {
        for (int i = 0; i < mMapManager.getArrMaps().size(); i++) {
            if (getRect().intersects(mMapManager.getArrMaps().get(i).getRect()) &&
                    !mMapManager.getArrMaps().get(i).isAllowTankPass()) {
                return true;
            }
        }
        if (getRect().intersects(mBird.getRect())) {
            return true;
        }
        return false;
    }

    public boolean bossBeDie() {
        hp--;
        updateIcon();
        return hp == 0;
    }


    public Bullet shout() {
        return new Bullet(Const.BOSS_ID,
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isCanFire() {
        return isCanFire;
    }

    public void setCanFire(boolean canFire) {
        isCanFire = canFire;
    }

}
