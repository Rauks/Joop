/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.screens;

import game.Background;
import game.BlocMatrix;

/**
 *
 * @author Karl
 */
public class OutScreen extends PlayableScreen{
    public OutScreen(BlocMatrix matrix, Background background) {
        super(matrix, background);
    }

    public OutScreen(Screen selected) {
        super(selected);
    }
}
