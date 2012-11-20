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
public abstract class PlayableScreen extends Screen{
    private BlocMatrix matrix;
    
    public BlocMatrix getMatrix(){
        return this.matrix;
    }
    protected PlayableScreen(BlocMatrix matrix, Background background){
        super(background);
        this.matrix = matrix;
    }
    protected PlayableScreen(Screen selected) {
        super(selected.getBackground());
        this.matrix = new BlocMatrix(((PlayableScreen) selected).getMatrix());
    }
}
