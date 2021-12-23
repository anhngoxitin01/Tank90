package com.bibibla.tank.model;

import com.bibibla.tank.manager.ImageMgr;

public class MapItem extends TankObject {
    private int type;

    public MapItem(int x, int y, int width, int height, int type) {
        super(x, y, width, height);
        this.type = type;
        this.icon = ImageMgr.arrItemsImages.get(type);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isAllowTankPass() {
        return type == 0 || type == 3 || type == 4;
    }

    public boolean isAllowBulletPass() {
        return type == 0 || type == 3;
    }

}
