package com.gollersoft.ld23;

import com.gollersoft.jultragame.core.display.UGColor;
import com.gollersoft.jultragame.core.display.UGGraphics;
import com.gollersoft.jultragame.layer.UGLayer;

/**
 * Created with IntelliJ IDEA.
 * User: neosam-code
 * Date: 4/21/12
 * Time: 6:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class StateDisplayLayer implements UGLayer {
    private final GameState gameState;
    private final UGColor color = new UGColor(0, 0, 0);
    private final UGColor rectColor = new UGColor(255, 255, 255, 64);
    private final long starttime;

    public StateDisplayLayer(GameState gameState) {
        this.gameState = gameState;
        starttime = System.currentTimeMillis();
    }

    @Override
    public void draw(UGGraphics g) {
        g.fillRect(0, 0, 800, 24, rectColor);
        g.drawString("Cheese: " + gameState.getCheese(), 10, 10, color);
        g.drawString("Water: " + gameState.getWater(), 200, 10, color);
        g.drawString("Usage: " + gameState.getCheeseProduction() * 30, 10, 20, color);
        g.drawString("Usage: " + gameState.getWaterProduction() * 30, 200, 20, color);
        g.drawString("Time: " + (System.currentTimeMillis() - starttime) / 1000, 400, 10, color);
        g.drawString("Score: " + (gameState.getScore() * 25), 400, 20, color);
    }

    @Override
    public void frame() {

    }
}
