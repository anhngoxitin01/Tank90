package com.bibibla.tank.model;

import com.bibibla.tank.manager.ImageMgr;
import com.bibibla.tank.util.Const;

public class Bullet extends TankObject {
    private int id;
    private int orient;

    public Bullet() {
    }

    public Bullet(int id, int x, int y, int orient) {               // khong truyen width height vs icon vi chi co mott loai dan
        super(x, y);
        this.id = id;
        this.orient = orient;
        this.width = Const.BULLET_SIZE;
        this.height = Const.BULLET_SIZE;
        this.icon = ImageMgr.arrBulletImages.get(orient);
    }

    public boolean moveBullet() {
        switch (orient) {
            case Const.UP_ORIENT:
                if (y > 0) {
                    this.y -= Const.STANDARD_SPEED;
                    return true;
                }
                break;
            case Const.DOWN_ORIENT:
                if (y < Const.MAP_SIZE) {
                    this.y += Const.STANDARD_SPEED;
                    return  true;
                }
                break;
            case Const.LEFT_ORIENT:
                if (x > 0) {
                    this.x -= Const.STANDARD_SPEED;
                    return  true;
                }
                break;
            case Const.RIGHT_ORIENT:
                if (x < Const.MAP_SIZE) {
                    this.x += Const.STANDARD_SPEED;
                    return true;
                }
                break;
        }
        return false;
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

    @Override
    public String toString() {
        return "Bullet{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
