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
public class CommonScreen extends PlayableScreen{
    public CommonScreen(BlocMatrix matrix, Background background) {
        super(matrix, background);
    }

    public CommonScreen(Screen selected) {
        super(selected);
    }
}
