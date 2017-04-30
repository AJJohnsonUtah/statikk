/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.riotapi.model;

import java.io.Serializable;

/**
 *
 * @author AJ
 */
public class GoldDto implements Serializable {

    private int base;
    private boolean purchasable;
    private int sell;
    private int total;

    public int getBase() {
        return base;
    }

    public boolean isPurchasable() {
        return purchasable;
    }

    public int getSell() {
        return sell;
    }

    public int getTotal() {
        return total;
    }

}
