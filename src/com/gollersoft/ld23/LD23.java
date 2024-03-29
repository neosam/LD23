package com.gollersoft.ld23;

import com.gollersoft.jultragame.binding.swing.UGSwing;
import com.gollersoft.jultragame.core.UG;
import com.gollersoft.jultragame.core.display.UGImage;
import com.gollersoft.jultragame.layer.UGImageScrollLayer;
import com.gollersoft.jultragame.layer.UGSpriteLayer;
import com.gollersoft.jultragame.scene.UGScene;

import javax.swing.*;
import java.awt.*;
import java.rmi.activation.UnknownGroupException;

/**
 * Created with IntelliJ IDEA.
 * User: neosam-code
 * Date: 4/21/12
 * Time: 7:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class LD23 {
    public static void main(String[] args) {
        final UG ug = new UGSwing(800, 600);
        final UGScene scene = new GameScene(ug);


        final JFrame frame = new JFrame("Ludum Dare 23: Athlete's foot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add((Component) ug.display.getElement());
        frame.pack();
        frame.setVisible(true);
    }
}
