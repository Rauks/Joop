/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package game;

import game.screens.Screen;
import java.util.ArrayList;

/**
*
* @author
* Florent
*/
public class Stack {    
    private int maxSize;
    private int pointer;
    private ArrayList<Screen> l;

    public Stack(int maxSize)
    {
        this.pointer = -1;
        this.l = new ArrayList<>();
        this.maxSize = maxSize;
    }
    public void push(Screen screen) throws StackException{
        if((this.pointer + 1) >= this.maxSize){
            throw new StackException(StackException.FULL);
        }
        this.pointer++;
        this.l.add(screen);
    }
    public void flush(){
        this.l.clear();
        this.pointer = -1;
    }
    public Screen getPrevious(){
        if(this.pointer > 0){
            this.pointer--;
            return this.l.get(this.pointer);
        }
        return null;
    }
    public Screen getNext(){
        if((this.pointer + 1) < this.size() && (this.pointer + 1) < this.maxSize){
            this.pointer++;
            return this.l.get(this.pointer);
        }
        return null;
    }
    public boolean onePlaceLeft(){
        return (l.size() == (this.maxSize - 1));
    }
    public int size(){
        return this.l.size();
    }
}
