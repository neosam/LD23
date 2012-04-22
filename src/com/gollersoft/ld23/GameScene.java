package com.gollersoft.ld23;

import com.gollersoft.jultragame.collision.Collision;
import com.gollersoft.jultragame.core.UG;
import com.gollersoft.jultragame.core.UGFinalPoint;
import com.gollersoft.jultragame.core.UGFinalRect;
import com.gollersoft.jultragame.core.UGPoint;
import com.gollersoft.jultragame.core.display.UGImage;
import com.gollersoft.jultragame.core.event.UGMouseClickEvent;
import com.gollersoft.jultragame.core.event.UGMouseDelegate;
import com.gollersoft.jultragame.layer.UGImageScrollLayer;
import com.gollersoft.jultragame.layer.UGSpriteLayer;
import com.gollersoft.jultragame.scene.UGScene;
import com.gollersoft.jultragame.scene.UGSpritePool;
import com.gollersoft.jultragame.sprite.UGSprite;
import com.gollersoft.jultragame.sprite.UGSpriteAnimation;
import com.gollersoft.jultragame.sprite.UGSpriteAnimationStorage;

/**
 * Created with IntelliJ IDEA.
 * User: neosam-code
 * Date: 4/21/12
 * Time: 4:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameScene extends UGScene {
    private final UG ug;
    private final UGImage spriteImage;
    private final UGSpriteLayer spriteLayer;
    private final UGImageScrollLayer backgroundLayer;
    private final GameState state;
    private final int pores = 6;


    public GameScene(UG ug) {
        super(ug);
        this.ug = ug;

        state = new GameState();
        backgroundLayer = new UGImageScrollLayer(ug.getImage("backgroundskin.png"), getCamera());
        spriteImage = ug.getImage("sprites.png");
        spriteLayer = new UGSpriteLayer(ug, getCamera());
        addLayer(backgroundLayer);
        addLayer(new BuildAreaLayer(getSpritePool()));
        addLayer(spriteLayer);
        addLayer(new StateDisplayLayer(state));
        new SpriteAnimator().register(this);
        ug.setMouseDelegate(new UGMouseDelegate() {
            @Override
            public void mouseClicked(UGMouseClickEvent event) {
                System.out.println(getSpriteAt(event.x, event.y));
            }
        });
        registerPerFrameAction(new Runnable() {
            @Override
            public void run() {
                state.step();
            }
        });

        state.setCheese(10000);
        state.setWater(10000);
        addHQ(302, 250);

        for (int i = 0; i < pores; i++)
            addPore((int) (Math.random() * 700), (int) (Math.random() * 500));
    }

    public UGSprite createSprite(int x, int y, int animX, int animY, int animWidth, int animHeight, int animOffset,
                                 int animLenght, int animationSpeed) {
        UGSpriteAnimationStorage hqAnimationStorage = new UGSpriteAnimationStorage(ug);
        UGSpriteAnimation animation = new UGSpriteAnimation(new UGFinalRect(animX, animY, animWidth, animHeight),
                animOffset, animLenght);
        animation.setNext(animationSpeed);
        hqAnimationStorage.put("default", animation);
        UGSprite sprite = new UGSprite(spriteImage, hqAnimationStorage);
        sprite.setPos(new UGPoint(x, y));
        spriteLayer.add(sprite);
        registerSprite(sprite);
        return sprite;
    }

    public void addHQ(int x, int y) {
        UGSprite sprite = createSprite(x, y, 0, 64, 64, 64, 0, 1, 10);
        getSpritePool().getSpritePoolItem(sprite).getLabels().add("hq");
        state.hqAdded();
    }

    public void addPore(int x, int y) {
        UGSprite sprite = createSprite(x, y, 64, 64, 32, 32, 0, 1, 10);
        getSpritePool().getSpritePoolItem(sprite).getLabels().add("pore");
    }

    public void addTranspare(int x, int y) {
        UGSprite sprite = createSprite(x, y, 64, 96, 32, 32, 0, 2, 10);
        getSpritePool().getSpritePoolItem(sprite).getLabels().add("transpore");
        sprite.setAnimation("default");
    }

    public void addInfector(int x, int y) {
        UGSprite sprite = createSprite(x, y, 0, 128, 32, 32, 0, 1, 10);
        getSpritePool().getSpritePoolItem(sprite).getLabels().add("infector");
    }

    public UGSprite getSpriteAt(int x, int y) {
        final UGSpritePool spritePool = getSpritePool();
        final int length = spritePool.size();
        for (int i = 0; i < length; i++) {
            final UGSprite sprite = spritePool.at(i);
            if (Collision.pointInRect(new UGFinalPoint(x, y), sprite.getSpriteRect()))
                return sprite;
        }
        return null;
    }
}
