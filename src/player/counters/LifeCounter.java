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
public class LifeCounter extends Counter {
    public static final int INIT_LIFE = 5;
    public static final int MIN_LIFE = 0;
    public static final int MAX_LIFE = 5;
    
    private int min;
    private int max;

    @Override
    public void setValue(int value){
        if(value >= this.max){
            super.setValue(this.max);
        }	
        else if(value <= this.min){
            super.setValue(this.min);
        }
        else{
            super.setValue(value);
        }
    }

    public LifeCounter(int min, int max){
        super();
        this.min = min;
        this.max = max;
    }
    public LifeCounter(int min,int max,int init){
        super(init);
        this.min = min;
        this.max = max;
    }

    @Override
    public void incr(){
        if(this.getValue()<this.max){
            super.incr();
        }
    }
    @Override
    public void decr(){
        if(this.getValue()>this.min){
            super.decr();
        }
    }
    @Override
    public void plus(int value){
        int result = this.getValue() + value;
        if(result >= this.max){
            super.setValue(this.max);
        }	
        else if(result <= this.min){
            super.setValue(this.min);
        }
        else{
            super.setValue(result);
        }
    }
    @Override
    public void minus(int value){
        int result = this.getValue() - value;
        if(result >= this.max){
            super.setValue(this.max);
        }	
        else if(result <= this.min){
            super.setValue(this.min);
        }
        else{
            super.setValue(result);
        }
    }
    public boolean isAlive(){
        return (this.getValue() > this.min);
    }
    public boolean isFull(){
        return (this.getValue() == this.max);
    }
}
