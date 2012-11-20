/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.pools;

import game.screens.InScreen;
import game.screens.Screen;

/**
 *
 * @author Karl
 */
public class InPool extends Pool{
    public InPool(){
        super();
    }
    
    @Override
    public Screen getScreen(int id){
        return new InScreen(super.getScreen(id));
    }
}
