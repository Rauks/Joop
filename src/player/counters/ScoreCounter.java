/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package player.counters;

/**
 *
 * @author
 * Karl
 */
public class ScoreCounter extends Counter {
    public static final int INIT_SCORE = 0;
    public static final int MIN_SCORE = 0;
    
    private int min;
    
    @Override
    public void setValue(int value){
        if(value <= this.min){
            super.setValue(this.min);
        }
        else{
            super.setValue(value);
        }
    }
    public ScoreCounter(int min){
        super();
        this.min = min;
    }
    public ScoreCounter(int min, int init){
        super(init);
        this.min = min;
    }
    @Override
    public void decr(){
        if(this.getValue() > this.min){
            super.decr();
        }
    }
    @Override
    public void plus(int value){
        int result = this.getValue() + value;
        if(result <= this.min){
            super.setValue(this.min);
        }
        else{
            super.setValue(result);
        }
    }
    @Override
    public void minus(int value){
        int result = this.getValue() - value;
        if(result <= this.min){
            super.setValue(this.min);
        }
        else{
            super.setValue(result);
        }
    }
}
