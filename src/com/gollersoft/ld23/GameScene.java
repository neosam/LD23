package com.gollersoft.ld23;

import com.gollersoft.jultragame.collision.Collision;
import com.gollersoft.jultragame.core.*;
import com.gollersoft.jultragame.core.display.UGImage;
import com.gollersoft.jultragame.core.event.UGMouseClickEvent;
import com.gollersoft.jultragame.core.event.UGMouseDelegate;
import com.gollersoft.jultragame.layer.UGImageScrollLayer;
import com.gollersoft.jultragame.layer.UGSpriteLayer;
import com.gollersoft.jultragame.scene.UGScene;
import com.gollersoft.jultragame.scene.UGSpritePool;
import com.gollersoft.jultragame.scene.UGSpritePoolItem;
import com.gollersoft.jultragame.sprite.UGSprite;
import com.gollersoft.jultragame.sprite.UGSpriteAnimation;
import com.gollersoft.jultragame.sprite.UGSpriteAnimationStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private final AntController antController;
    private final GameState state;
    private final int pores = 6;


    public GameScene(final UG ug) {
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
            public void mouseClicked(final UGMouseClickEvent event) {
                UGSprite sprite = getSpriteAt(event.x, event.y);
                if (sprite == null) {
                    if (!isNearOf(event.x, event.y, "hq", 128) || state.getCheese() <= 0 || state.getWater() <= 0)
                        return;
                    JPopupMenu buildPopup = new JPopupMenu();
                    JMenuItem buildHQ = new JMenuItem("Build HQ");
                    buildPopup.add(buildHQ);
                    if (isNearOf(event.x, event.y, "pore", 64)) {
                        JMenuItem buildTranspore = new JMenuItem("Build Transpore");
                        buildPopup.add(buildTranspore);
                        JMenuItem buildInfector = new JMenuItem("Build Infector");
                        buildPopup.add(buildInfector);
                        buildTranspore.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                addTranspare(event.x, event.y);
                            }
                        });
                        buildInfector.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                addInfector(event.x, event.y);
                            }
                        });
                    }
                    buildPopup.show((Component) ug.display.getElement(), event.x, event.y);

                    buildHQ.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            addHQ(event.x, event.y);
                        }
                    });

                }
                else {
                    if (event.button == 2)
                        removeSprite(sprite, false);
                }
            }
        });
        registerPerFrameAction(new Runnable() {
            @Override
            public void run() {
                state.step();
                if (state.getCheese() > 0 && state.getWater() > 0)
                    state.addScore(getSpritePool().getSpritePoolItemsWithLabel("hq").size());
            }
        });

        antController = new AntController(ug, this);
        registerPerFrameAction(antController);

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
        sprite.setPos(new UGPoint(x - animWidth / 2, y - animHeight / 2));
        spriteLayer.add(sprite);
        registerSprite(sprite);
        return sprite;
    }

    public void addHQ(int x, int y) {
        UGSprite sprite = createSprite(x, y, 0, 64, 64, 64, 0, 1, 10);
        getSpritePool().getSpritePoolItem(sprite).getLabels().add("hq");
        getSpritePool().getSpritePoolItem(sprite).getLabels().add("player");
        state.hqAdded();
    }

    public void addPore(int x, int y) {
        UGSprite sprite = createSprite(x, y, 64, 64, 32, 32, 0, 1, 10);
        getSpritePool().getSpritePoolItem(sprite).getLabels().add("pore");
        UGSpriteAnimation animation = new UGSpriteAnimation(new UGFinalRect(96, 64, 32, 32),
                0, 1);
        sprite.getAnimationStorage().put("infected", animation);

    }

    public void addTranspare(int x, int y) {
        UGSprite sprite = createSprite(x, y, 64, 96, 32, 32, 0, 2, 10);
        UGSpriteAnimation animation = new UGSpriteAnimation(new UGFinalRect(64, 128, 32, 32),
                0, 2);
        animation.setNext(10);
        sprite.getAnimationStorage().put("infected", animation);
        getSpritePool().getSpritePoolItem(sprite).getLabels().add("transpore");
        getSpritePool().getSpritePoolItem(sprite).getLabels().add("player");
        getSpritePool().getSpritePoolItem(sprite).getLabels().add("misplaced");
        sprite.setAnimation("default");

        if (isNearOf(x, y, "infectedpore", 64)) {
            sprite.setAnimation("infected");
            getSpritePool().getSpritePoolItem(sprite).getLabels().add("infected");
            state.infectedTransporeAdded();
        } else {
            state.transporeAdded();
        }

    }

    public void addInfector(int x, int y) {
        UGSprite sprite = createSprite(x, y, 0, 128, 32, 32, 0, 1, 10);
        getSpritePool().getSpritePoolItem(sprite).getLabels().add("infector");
        getSpritePool().getSpritePoolItem(sprite).getLabels().add("player");
        getSpritePool().getSpritePoolItem(sprite).getLabels().add("misplaced");
        UGList<UGSprite> pores = spritesNearOf(x, y, "pore", 64);
        final int size = pores.size();
        int transporeCounter = 0;
        for (int i = 0; i < size; i++) {
            pores.at(i).setAnimation("infected");
            getSpritePool().getSpritePoolItem(pores.at(i)).getLabels().add("infectedpore");
            final UGFinalRect rect = pores.at(i).getSpriteRect();
            final int poreX = rect.x + rect.width / 2;
            final int poreY = rect.y + rect.height / 2;
            final UGList<UGSprite> transpores = spritesNearOf(poreX, poreY, "transpore", 64);
            final int size2 = transpores.size();
            transporeCounter += size2;
            for (int j = 0; j < size2; j++) {
                transpores.at(j).setAnimation("infected");
                getSpritePool().getSpritePoolItem(transpores.at(j)).getLabels().add("infected");
            }
        }
        System.out.println("Infected " + transporeCounter + " transpores");
        state.infectorAdded(transporeCounter);
    }

    public void addAnt(int x, int y, UGSprite destination) {
        UGSprite sprite = createSprite(x, y, 0, 160, 32, 16, 0, 2, 3);
        getSpritePool().getSpritePoolItem(sprite).getLabels().add("ant");
        antController.addAnt(new Antity(sprite, destination));
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

    public boolean isNearOf(int x, int y, String label, int radius) {
        final UGList<UGSpritePoolItem> hqs = getSpritePool().getSpritePoolItemsWithLabel(label);
        final int size = hqs.size();
        for (int i = 0; i < size; i++) {
            final UGSprite sprite = hqs.at(i).getSprite();
            final int spriteX = sprite.getSpriteRect().x + sprite.getSpriteRect().width / 2;
            final int spriteY = sprite.getSpriteRect().y + sprite.getSpriteRect().height / 2;
            final int diffX = x - spriteX;
            final int diffY = y - spriteY;
            if (diffX * diffX + diffY * diffY < radius * radius)
                return true;
        }
        return false;
    }

    public UGList<UGSprite> spritesNearOf(int x, int y, String label, int radius) {
        final UGList<UGSprite> res = ug.createList();
        final UGList<UGSpritePoolItem> sprites = getSpritePool().getSpritePoolItemsWithLabel(label);
        final int size = sprites.size();
        for (int i = 0; i < size; i++) {
            final UGSprite sprite = sprites.at(i).getSprite();
            final int spriteX = sprite.getSpriteRect().x + sprite.getSpriteRect().width / 2;
            final int spriteY = sprite.getSpriteRect().y + sprite.getSpriteRect().height / 2;
            final int diffX = x - spriteX;
            final int diffY = y - spriteY;
            if (diffX * diffX + diffY * diffY < radius * radius)
                res.add(sprite);
        }
        return res;
    }

    public void antAttack(Antity antity) {
        spriteLayer.remove(antity.antSprite);
        getSpritePool().remove(antity.antSprite);
        removeSprite(antity.destination, true);
    }

    private void removeSprite(UGSprite destination, boolean explosion) {
        UGSpritePoolItem item = getSpritePool().getSpritePoolItem(destination);
        if (item == null)
            return;
        UGList<String> labels = item.getLabels();
        spriteLayer.remove(destination);
        getSpritePool().remove(destination);
        int size = labels.size();
        for (int i = 0; i < size; i++) {
            final String label = labels.at(i);
            if (label.equals("hq")) {
                state.hqRemoved();
                destroyMisplaced();
            }
            if (label.equals("transpore")) {
                boolean isInfected = false;
                for (int j = 0; j < size; j++) {
                    final String label2 = labels.at(j);
                    if (label2.equals("infected"))
                        isInfected = true;
                }
                if (isInfected)
                    state.infectedTransporeRemoved();
                else
                    state.transporeRemoved();
            }
            if (label.equals("infector")) {
                System.out.println("infector removed");
                UGList<UGSpritePoolItem> pores = getSpritePool().getSpritePoolItemsWithLabel("pore");
                int size2 = pores.size();
                for (int j = 0; j < size2; j++) {
                    final UGFinalRect rect = pores.at(j).getSprite().getSpriteRect();
                    if (!isNearOf(rect.x + rect.width / 2, rect.y + rect.height / 2, "infector", 64)) {
                        pores.at(j).getLabels().remove("infectedpore");
                        pores.at(j).getSprite().setAnimation("default");
                    } else {
                        pores.at(j).getLabels().add("infectedpore");
                        pores.at(j).getSprite().setAnimation("infected");
                    }
                }
                UGList<UGSpritePoolItem> transpores = getSpritePool().getSpritePoolItemsWithLabel("transpore");
                int size3 = transpores.size();
                for (int j = 0; j < size3; j++) {
                    final UGFinalRect rect = transpores.at(j).getSprite().getSpriteRect();
                    if (!isNearOf(rect.x + rect.width / 2, rect.y + rect.height / 2, "infectedpore", 64)) {
                        UGList<String> transporeLabels = transpores.at(j).getLabels();
                        for (int m = 0; m < transporeLabels.size(); m++) {     // OMG
                            if (transporeLabels.at(m).equals("infected")) {
                                transpores.at(j).getLabels().remove("infected");
                                transpores.at(j).getSprite().setAnimation("default");
                                state.infectedTransporeRemoved();
                                state.transporeAdded();
                            }
                        }

                    } else {
                        UGList<String> transporeLabels = transpores.at(j).getLabels();
                        boolean isInfected = false;
                        for (int m = 0; m < transporeLabels.size(); m++) {     // OMG
                            if (transporeLabels.at(m).equals("infected")) {
                                isInfected = true;
                                break;
                            }
                        }
                        if (isInfected) {
                            state.transporeRemoved();
                            state.infectedTransporeAdded();
                            transpores.at(j).getLabels().add("infected");
                            transpores.at(j).getSprite().setAnimation("infected");
                        }
                    }
                }
            }
        }
        recreateProduction();
    }

    private void destroyMisplaced() {
        System.out.println("Destroy misplaced");
        UGList<UGSpritePoolItem> sprites = getSpritePool().getSpritePoolItemsWithLabel("misplaced");
        int size1 = sprites.size();
        System.out.println("Checking " + size1 + " sprites");
        for (int i = 0; i < size1; i++) {
            UGSprite sprite = sprites.at(i).getSprite();
            UGFinalRect rect = sprite.getSpriteRect();
            if (!isNearOf(rect.x + rect.width / 2, rect.y + rect.height / 2, "hq", 128)) {
                removeSprite(sprite, false);
            }
        }
    }

    public void recreateProduction() {
        int hqs = getSpritePool().getSpritePoolItemsWithLabel("hq").size();
        int infectedTranspore = getSpritePool().getSpritePoolItemsWithLabel("infected").size();
        int transpore = getSpritePool().getSpritePoolItemsWithLabel("transpore").size() - infectedTranspore;
        state.setCheeseProduction(infectedTranspore * 2 - hqs * 10);
        state.setWaterProduction(transpore * 2 - hqs * 10);

    }
}
