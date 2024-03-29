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
    private int score = 0;

    public GameState() {
    }

    public void step() {
        cheese += cheeseProduction;
        water += waterProduction;
    }

    public void hqAdded() {
        waterProduction -= 10;
        cheeseProduction -= 10;
    }
    public void hqRemoved() {
        waterProduction += 10;
        cheeseProduction += 10;
    }

    public void transporeAdded() {
        waterProduction += 2;
    }
    public void transporeRemoved() {
        System.out.println("Removed transpore");
        waterProduction -= 2;
    }

    public void infectedTransporeAdded() {
        cheeseProduction += 2;
    }
    public void infectedTransporeRemoved() {
        System.out.println("Removed infected");
        cheeseProduction -= 2;
    }

    public void infectorAdded(int transformedTranspored) {
        waterProduction -= 2 * transformedTranspored;
        cheeseProduction += 2 * transformedTranspored;
    }
    public void infectorRemoved(int transformedTranspored) {
        waterProduction += 2 * transformedTranspored;
        cheeseProduction -= 2 * transformedTranspored;
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

    public void addScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return score;
    }
}
