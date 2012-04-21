package com.gollersoft.ld23;

import com.gollersoft.jultragame.core.UGList;
import com.gollersoft.jultragame.scene.UGScene;
import com.gollersoft.jultragame.scene.UGSceneAddon;
import com.gollersoft.jultragame.scene.UGSpritePool;

/**
 * Created with IntelliJ IDEA.
 * User: neosam-code
 * Date: 4/21/12
 * Time: 5:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpriteAnimator extends UGSceneAddon {
    @Override
    public void register(final UGScene scene) {
        scene.registerPerFrameAction(new Runnable() {
            @Override
            public void run() {
                final UGSpritePool spritePool = scene.getSpritePool();
                final int length = spritePool.size();
                for (int i = 0; i < length; i++)
                    spritePool.at(i).step();
            }
        });
    }

    @Override
    public UGList getAttributes() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setAttribute(String attribute, Object value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
