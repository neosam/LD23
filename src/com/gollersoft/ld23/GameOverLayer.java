package com.gollersoft.ld23;

import com.gollersoft.jultragame.core.display.UGColor;
import com.gollersoft.jultragame.core.display.UGGraphics;
import com.gollersoft.jultragame.layer.UGLayer;

/**
 * Created with IntelliJ IDEA.
 * User: neosam-code
 * Date: 4/22/12
 * Time: 10:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameOverLayer implements UGLayer {
    final GameState state;
    final GameScene gameScene;
    final UGColor red = new UGColor(255, 0, 0, 128);
    final UGColor black = new UGColor(0, 0, 0);

    public GameOverLayer(GameState state, GameScene gameScene) {
        this.state = state;
        this.gameScene = gameScene;
    }

    @Override
    public void draw(UGGraphics g) {
        if (state.getCheese() < 0 || state.getWater() < 0 || gameScene.getSpritePool().getSpritePoolItemsWithLabel("hq").size() == 0) {
            g.fillRect(0, 0, 800, 600, red);
            g.drawString("You're defeated", 300, 290, black);
        }
    }

    @Override
    public void frame() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
