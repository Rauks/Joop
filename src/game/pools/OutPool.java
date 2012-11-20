/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.pools;

import game.screens.OutScreen;
import game.screens.Screen;

/**
 *
 * @author Karl
 */
public class OutPool extends Pool{
    public OutPool(){
        super();
    }
    
    @Override
    public Screen getScreen(int id){
        return new OutScreen(super.getScreen(id));
    }
}
