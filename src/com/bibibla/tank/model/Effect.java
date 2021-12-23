package com.bibibla.tank.model;

import com.bibibla.tank.manager.ImageMgr;

import java.awt.*;
import java.util.ArrayList;

public class Effect {
    private ArrayList<Image> arrEffect;
    private int x;
    private int y;
    public int index = 0;

    public Effect(int x, int y) {
        this.arrEffect = ImageMgr.arrEffect;
        this.x = x;
        this.y = y;
    }

    public boolean draw(Graphics2D g2d) {
        if (index < arrEffect.size()) {
            g2d.drawImage(arrEffect.get(index++), x, y, null);
            return true;
        }
        return false;
    }
}
