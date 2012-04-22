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

    public StateDisplayLayer(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void draw(UGGraphics g) {
        g.drawString("Cheese: " + gameState.getCheese() + "    -     Water: " + gameState.getWater(), 30, 30, color);
    }

    @Override
    public void frame() {

    }
}
