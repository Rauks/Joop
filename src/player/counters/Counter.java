/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package player.counters;

/**
 *
 * @author
 * Simon
 */
public abstract class Counter {
    private int value;
	
    public Counter(){
        this.value = 0;
    }
    public Counter(int init){
        this.value = init;
    }
    public int getValue(){
        return this.value;
    }
    public void setValue(int value){
        this.value = value;
    }
    public void plus(int value){
        this.value += value;
    }
    public void minus(int value){
        this.value -= value;
    }
    public void incr(){
        this.value++;
    }
    public void decr(){
        this.value--;
    }
}
