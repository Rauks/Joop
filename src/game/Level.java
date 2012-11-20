/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.pools.CommonPool;
import game.pools.InPool;
import game.pools.OutPool;

/**
 *
 * @author Karl
 */
public class Level { 
    private InPool inPool;
    private OutPool outPool;
    private CommonPool commonPool;
    private String name;
    
    public Level(String name){
        this.inPool = new InPool();
        this.outPool = new OutPool();
        this.commonPool = new CommonPool();
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
    public InPool getInPool(){
        return this.inPool;
    }
    public OutPool getOutPool(){
        return this.outPool;
    }
    public CommonPool getCommonPool(){
        return this.commonPool;
    }
    public boolean isWellFormed(){
        if((this.commonPool.size() > 0) && (this.outPool.size() > 0) && (this.inPool.size() > 0)){
        }
        else{
        }
        return (this.commonPool.size() > 0) && (this.outPool.size() > 0) && (this.inPool.size() > 0);
    }
}
