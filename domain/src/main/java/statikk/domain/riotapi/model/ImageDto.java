/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

import java.io.Serializable;

/**
 *
 * @author AJ
 */
public class ImageDto implements Serializable {
    private String full;
    private String group;
    private int h;
    private String sprite;
    private int w;
    private int x;
    private int y;

    public String getFull() {
        return full;
    }

    public String getGroup() {
        return group;
    }

    public int getH() {
        return h;
    }

    public String getSprite() {
        return sprite;
    }

    public int getW() {
        return w;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    
}
