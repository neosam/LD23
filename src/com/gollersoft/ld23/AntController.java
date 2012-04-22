package com.gollersoft.ld23;

import com.gollersoft.jultragame.core.*;

/**
 * Created with IntelliJ IDEA.
 * User: neosam-code
 * Date: 4/22/12
 * Time: 12:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class AntController implements Runnable {
    final private UGList<Antity> antities;

    public AntController(UG ug) {
        antities = ug.createList();

    }

    @Override
    public void run() {
        final int size = antities.size();
        for (int i = 0; i < size; i++) {
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
            System.out.println(antity.antSprite.getPos());
        }
    }

    public void addAnt(Antity antity) {
        antities.add(antity);
    }
}
