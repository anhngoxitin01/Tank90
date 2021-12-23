package com.bibibla.tank.manager;

import com.bibibla.tank.model.Effect;

import java.awt.*;
import java.util.ArrayList;

public class EffectManager {
    private ArrayList<Effect> arrEffect;

    public EffectManager() {
        arrEffect = new ArrayList<>();
    }

    public void addEffect(Effect effect) {
        arrEffect.add(effect);
    }

    public void drawAllEffect(Graphics2D g2d) {
        for (int i = 0; i < arrEffect.size(); i++) {
            if (!arrEffect.get(i).draw(g2d)) {
                arrEffect.remove(i);
            }
        }
    }
}
