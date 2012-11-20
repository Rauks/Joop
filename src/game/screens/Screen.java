/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.screens;

import game.Background;

/**
 *
 * @author Karl
 */
public abstract class Screen{
    private Background background;
    
    public Background getBackground(){
        return this.background;
    }
    
    protected Screen(Background background){
        this.background = background;
    }
}
