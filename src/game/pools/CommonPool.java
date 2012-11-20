/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.pools;

import game.screens.CommonScreen;
import game.screens.Screen;

/**
 *
 * @author Karl
 */
public class CommonPool extends Pool{
    public CommonPool(){
        super();
    }
    
    @Override
    public Screen getScreen(int id){
        return new CommonScreen(super.getScreen(id));
    }
}
