/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package player;

import game.BlocMatrix;
import geometry.Position;
import java.util.LinkedList;
import player.counters.LifeCounter;
import player.counters.ScoreCounter;
import player.states.*;

/**
 *
 * @author
 * Karl
 */
public class Player {
    public static final State STATE_CROUCH_RIGHT = new CrouchRightState();
    public static final State STATE_CROUCH_LEFT = new CrouchLeftState();
    public static final State STATE_RUN_RIGHT = new RunRightState();
    public static final State STATE_RUN_JUMP_RIGHT = new RunJumpRightState();
    public static final State STATE_RUN_LONGJUMP_RIGHT = new RunLongJumpRightState();
    public static final State STATE_RUN_CROUCH_RIGHT = new RunCrouchRightState();
    public static final State STATE_RUN_LEFT = new RunLeftState();
    public static final State STATE_RUN_JUMP_LEFT = new RunJumpLeftState();
    public static final State STATE_RUN_LONGJUMP_LEFT = new RunLongJumpLeftState();
    public static final State STATE_RUN_CROUCH_LEFT = new RunCrouchLeftState();
    public static final State STATE_STANDBY_LEFT = new StandbyLeftState();
    public static final State STATE_STANDBY_RIGHT = new StandbyRightState();
    
    private State currentState;
    private LifeCounter life;
    private ScoreCounter score;
    private Position pos;
    private LinkedList<Position> moves;
    
    public Player(){
        this.life = new LifeCounter(LifeCounter.MIN_LIFE, LifeCounter.MAX_LIFE, LifeCounter.INIT_LIFE);
        this.score = new ScoreCounter(ScoreCounter.MIN_SCORE, ScoreCounter.INIT_SCORE);
        this.pos = BlocMatrix.START_LEVEL;
        this.currentState = Player.STATE_STANDBY_RIGHT;
        this.moves = new LinkedList<>();
    }
    public void winLife(){
        this.life.incr();
    }
    public void winLife(int value){
        this.life.plus(value);
    }
    public void loseLife(){
        this.life.decr();
    }
    public void loseLife(int value){
        this.life.minus(value);
    }
    public void die(){
        this.life.setValue(0);
    }
    public boolean fullLife(){
        return this.life.isFull();
    }
    public boolean isAlive(){
        return this.life.isAlive();
    }
    public int getLifes(){
        return this.life.getValue();
    }
    public void winScore(int value){
        this.score.plus(value);
    }
    public void loseScore(int value){
        this.score.minus(value);
    }
    public int getScore(){
        return this.score.getValue();
    }
    public void setPosition(Position pos){
        this.pos = pos;
    }
    public Position getPosition(){
        return this.pos;
    }
    public State getState(){
        return this.currentState;
    }
    public void setState(State s){
        this.currentState = s;
    }
    public void reset() {
        this.life.setValue(LifeCounter.INIT_LIFE);
        this.score.setValue(ScoreCounter.INIT_SCORE);
        this.pos = BlocMatrix.START_LEVEL;
        this.currentState = Player.STATE_STANDBY_RIGHT;
    }
    public void flushMoves(){
        this.moves.clear();
    }
    public int movesInQueue(){
        return this.moves.size();
    }
    public Position movesTarget(){
        if(this.hasMovesTokens())
            return this.moves.getLast();
        return this.getPosition();
    }
    public boolean hasMovesTokens(){
        return (this.moves.size() != 0);
    }
    public Position toNextMove(){
        if(this.hasMovesTokens()){
            this.pos = this.moves.peek();
        }
        return this.moves.poll();
    }
    public void addMove(Position pos){
        this.moves.add(pos);
    }
}
