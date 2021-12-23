package com.bibibla.tank.manager;

import com.bibibla.tank.model.MapItem;
import com.bibibla.tank.util.Const;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class MapManager {
    private List<MapItem> arrMaps;
    private int[][] arrWayMaps;
    private int level = 1;

    public MapManager() {
        arrMaps = new ArrayList<>();
        arrWayMaps = new int[26][26];
        loadMap();
    }

    public void drawMap(Graphics2D g2d) {
        for (int i = 0; i < arrMaps.size(); i++) {
            arrMaps.get(i).draw(g2d);
        }
    }

    public void loadMap() {
        String path = "src/MAPS/map" + level;
        File file = new File(path);
        try {
            RandomAccessFile rd = new RandomAccessFile(file, "r");
            int row = 0;
            String line = rd.readLine();
            while (line != null) {
                for (int col = 0; col < line.length(); col++) {
                    int type = line.charAt(col) - '0';
                    //init arrWayMaps
                    if (type == 0 || type == 3) arrWayMaps[row][col] = 0;
                    else arrWayMaps[row][col] = 1;
                    //init arrMaps
                    arrMaps.add(new MapItem(
                            col * Const.ITEM_SIZE,
                            row * Const.ITEM_SIZE,
                            Const.ITEM_SIZE,
                            Const.ITEM_SIZE,
                            type));
                }
                row++;
                line = rd.readLine();
            }
            rd.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newMapGame() {
        if (level != 9) {
            level += 1;

            loadMap();
        } else {
            System.out.println("end Game");
            System.exit(0);
        }
    }

    public List<MapItem> getArrMaps() {
        return arrMaps;
    }

    public void setArrMaps(List<MapItem> arrMaps) {
        this.arrMaps = arrMaps;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int[][] getArrWayMaps() {
        return arrWayMaps;
    }

    public void setArrWayMaps(int[][] arrWayMaps) {
        this.arrWayMaps = arrWayMaps;
    }
}
