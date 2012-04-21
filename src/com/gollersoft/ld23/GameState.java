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

    public GameState(int cheese, int water) {
        this.cheese = cheese;
        this.water = water;
    }

    public GameState() {
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
}
