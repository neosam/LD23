package com.gollersoft.ld23;

/**
 * Created with IntelliJ IDEA.
 * User: neosam-code
 * Date: 4/21/12
 * Time: 6:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameState {
    private int cheese;
    private int water;
    private int cheeseProduction;
    private int waterProduction;

    public GameState() {
    }

    public void step() {
        cheese += cheeseProduction;
        water += waterProduction;
    }

    public void hqAdded() {
        waterProduction -= 1;
        cheeseProduction -= 1;
    }

    public void transporeAdded() {
        waterProduction += 2;
    }

    public void infectorAdded(int transformedTranspored) {
        waterProduction -= 2 * transformedTranspored;
        cheeseProduction += 2 * transformedTranspored;
    }

    public int getCheese() {
        return cheese;
    }

    public void setCheese(int cheese) {
        this.cheese = cheese;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getCheeseProduction() {
        return cheeseProduction;
    }

    public void setCheeseProduction(int cheeseProduction) {
        this.cheeseProduction = cheeseProduction;
    }

    public int getWaterProduction() {
        return waterProduction;
    }

    public void setWaterProduction(int waterProduction) {
        this.waterProduction = waterProduction;
    }
}
