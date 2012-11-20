/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.pools;

import game.screens.Screen;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Karl
 */
public abstract class Pool {
    private ArrayList<Screen> screens;
    private Random random;
    
    protected Pool(){
        this.screens = new ArrayList<>();
        this.random = new Random();
    }
    
    public int size(){
        return this.screens.size();
    }
    public void add(Screen screen){
        this.screens.add(screen);
    }
    public void remove(int id){
        this.screens.remove(id);
    }
    public Screen getScreen(int id){
        return this.screens.get(id);
    }
    public Screen getRandomScreen(){
        return this.getScreen(this.random.nextInt(this.size()));
    }
}
