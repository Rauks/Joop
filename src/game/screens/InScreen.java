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
public class InScreen extends PlayableScreen{
    public InScreen(BlocMatrix matrix, Background background) {
        super(matrix, background);
    }

    public InScreen(Screen selected) {
        super(selected);
    }
}
