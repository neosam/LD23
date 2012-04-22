package com.gollersoft.ld23;

import com.gollersoft.jultragame.core.*;
import com.gollersoft.jultragame.scene.UGSpritePoolItem;
import com.gollersoft.jultragame.sprite.UGSprite;

/**
 * Created with IntelliJ IDEA.
 * User: neosam-code
 * Date: 4/22/12
 * Time: 12:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class AntController implements Runnable {
    final private UGList<Antity> antities;
    final private GameScene gameScene;
    final long starttime;
    int levelupSpeed = 10000;
    int amountOfAnts = 500;

    public AntController(UG ug, GameScene gameScene) {
        this.gameScene = gameScene;
        antities = ug.createList();
        starttime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        for (int i = 0; i < antities.size(); i++) {
            final Antity antity = antities.at(i);
            final UGFinalRect destRect = antity.destination.getSpriteRect();
            final UGFinalPoint destPos = new UGFinalPoint(destRect.x + destRect.width / 2,
                                                            destRect.y + destRect.height / 2);
            final UGPoint antPos = antity.antSprite.getPos();
            final int diffX = destPos.x - antPos.x;
            final int diffY = destPos.y - antPos.y;
            if (diffX > 0)
                antPos.x++;
            else if (diffX < 0)
                antPos.x--;
            if (diffY > 0)
                antPos.y++;
            else if (diffY < 0)
                antPos.y--;
            if (diffX == 0 && diffY == 0) {
                gameScene.antAttack(antity);
                antities.remove(antity);
            }
        }
        maybeAddAnAnt();
    }

    public void maybeAddAnAnt() {
        long timePlayed = (System.currentTimeMillis() - starttime) / levelupSpeed;
        if (Math.pow(1.3, timePlayed) > (int)(Math.random() * amountOfAnts)) {
            String dest = "transpore";
            if (Math.random() > 0.9)
                dest = "hq";
            else if (Math.random() > 0.9)
                dest = "infector";
            UGList<UGSpritePoolItem> possibleGoals = gameScene.getSpritePool().getSpritePoolItemsWithLabel(dest);
            if (possibleGoals.size() == 0)
                return;
            UGSprite destSprite = possibleGoals.at((int) (Math.random() * possibleGoals.size())).getSprite();
            double randomVal = Math.random();
            if (randomVal < 0.25) {
                gameScene.addAnt(-20, -20, destSprite);
            }
            else if (randomVal < 0.5) {
                gameScene.addAnt(820, -20, destSprite);
            }
            else if (randomVal < 0.75) {
                gameScene.addAnt(820, 620, destSprite);
            }
            else {
                gameScene.addAnt(-20, 620, destSprite);
            }
        }
    }

    public void addAnt(Antity antity) {
        antities.add(antity);
    }
}
