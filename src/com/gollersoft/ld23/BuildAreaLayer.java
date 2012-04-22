package com.gollersoft.ld23;

import com.gollersoft.jultragame.core.UGFinalRect;
import com.gollersoft.jultragame.core.UGList;
import com.gollersoft.jultragame.core.UGPoint;
import com.gollersoft.jultragame.core.UGRect;
import com.gollersoft.jultragame.core.display.UGColor;
import com.gollersoft.jultragame.core.display.UGGraphics;
import com.gollersoft.jultragame.layer.UGLayer;
import com.gollersoft.jultragame.scene.UGSpritePool;
import com.gollersoft.jultragame.scene.UGSpritePoolItem;

/**
 * Created with IntelliJ IDEA.
 * User: neosam-code
 * Date: 4/21/12
 * Time: 10:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildAreaLayer implements UGLayer {
    private final UGSpritePool spritePool;
    private final UGColor color = new UGColor(128, 128, 128, 128);
    private final UGColor poreColor = new UGColor(0, 255, 0, 128);
    private final int radius = 128;
    private final int poreRadius = 64;

    public BuildAreaLayer(UGSpritePool spritePool) {
        this.spritePool = spritePool;
    }

    @Override
    public void draw(UGGraphics g) {
        final UGList<UGSpritePoolItem> hqs = spritePool.getSpritePoolItemsWithLabel("hq");
        final int size = hqs.size();
        for (int i = 0; i < size; i++) {
            final UGFinalRect position = hqs.at(i).getSprite().getSpriteRect();
            g.fillCircle(position.x + (position.width >> 1), position.y + (position.height >> 1), radius, color);
        }
        final UGList<UGSpritePoolItem> pores = spritePool.getSpritePoolItemsWithLabel("pore");
        final int size2 = pores.size();
        for (int i = 0; i < size2; i++) {
            final UGFinalRect position = pores.at(i).getSprite().getSpriteRect();
            g.fillCircle(position.x + (position.width >> 1), position.y + (position.height >> 1), poreRadius, poreColor);
        }
    }


    @Override
    public void frame() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
