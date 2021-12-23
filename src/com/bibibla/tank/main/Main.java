package com.bibibla.tank.main;

import com.bibibla.tank.gui.GameFrame;
import com.bibibla.tank.util.Const;
import com.bibibla.tank.manager.ImageMgr;


public class Main {
    public static void main(String[] args) {
        new Const();
        new ImageMgr();
        new GameFrame();
    }
}
