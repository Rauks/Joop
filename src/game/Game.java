/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import database.DataBase;
import database.DataBaseException;
import game.screens.BossScreen;
import game.screens.OutScreen;
import game.screens.PlayableScreen;
import game.screens.Screen;
import geometry.Hitbox;
import geometry.Path;
import geometry.Position;
import geometry.Vector;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Logger;
import player.Player;
import player.states.State;
import player.states.itf.Crouch;
import player.states.itf.Left;
import player.states.itf.Right;

/**
 *
 * @author
 * Karl
 */
public class Game {
    private static final int GAME_STACK_MAX = 10;
    private static final int GAME_COMMON_BEFORE_TRY_OUT = 5; // GAME_COMMON_BEFORE_TRY_OUT commons avant de commencer a piocher des outScreen
    private static final int GAME_COMMON_OR_OUT_RATIO = 10; // 1 chance sur GAME_COMMON_OR_OUT_RATIO de piocher un outScreen
    private static final int SCORE_FOR_LIFE = 1000;
    private static final int SCORE_FOR_CORRECT_ANSWER = 100;
    private static final int SCORE_MAX_FOR_PASSING_PLAYABLE = 50;
    
    private Player player;
    private Stack levelStack;
    private Level currentLevel;
    private Screen currentScreen;
    private DataBase db;
    private Random randomTryOut;
    private Random randomScore;
    private boolean hasBonus;
    private boolean hasMalus;
    
    public Game(Player player, DataBase db){
        this.db = db;
        this.player = player;
        this.levelStack = new Stack(Game.GAME_STACK_MAX);
        this.randomTryOut = new Random();
        this.randomScore = new Random();
        this.hasBonus = false;
        this.hasMalus = false;
    }
    
    public void playerStop(){
        State currentState = this.player.getState();
        if(currentState.equals(Player.STATE_RUN_CROUCH_LEFT)){
            this.player.setState(Player.STATE_CROUCH_LEFT);
        }
        else if(currentState.equals(Player.STATE_RUN_CROUCH_RIGHT)){
            this.player.setState(Player.STATE_CROUCH_RIGHT);
        }
        else if(currentState.equals(Player.STATE_RUN_LEFT) || currentState.equals(Player.STATE_RUN_JUMP_LEFT) || currentState.equals(Player.STATE_RUN_LONGJUMP_LEFT)){
            this.player.setState(Player.STATE_STANDBY_LEFT);
        }
        else if(currentState.equals(Player.STATE_RUN_RIGHT) || currentState.equals(Player.STATE_RUN_JUMP_RIGHT) || currentState.equals(Player.STATE_RUN_LONGJUMP_RIGHT)){
            this.player.setState(Player.STATE_STANDBY_RIGHT);
        }
        if(!(this.currentScreen instanceof BossScreen)){
            this.playerPickupOnlyRamassablesBlocs();
        }
    }
    public void playerJump(){
        try {
            State currentState = this.player.getState();
            if(currentState.equals(Player.STATE_CROUCH_RIGHT)){
                Hitbox hit = Player.STATE_STANDBY_RIGHT.getHitbox();
                PlayableScreen currentPlayableScreen = (PlayableScreen) this.currentScreen;
                Position currentHitPosition;
                boolean canMove = true;
                for(Iterator<Position> itPositions = hit.getPositions(this.player.getPosition()).iterator(); itPositions.hasNext();){
                    currentHitPosition = itPositions.next();
                    if(BlocMatrix.outOfBound(currentHitPosition) || !currentPlayableScreen.getMatrix().getBloc(currentHitPosition).isTraversable()){
                        canMove = false;
                        break;
                    }
                }
                if(canMove){
                    this.player.setState(Player.STATE_STANDBY_RIGHT);
                }
            }
            else if(currentState.equals(Player.STATE_CROUCH_LEFT)){
                Hitbox hit = Player.STATE_STANDBY_LEFT.getHitbox();
                PlayableScreen currentPlayableScreen = (PlayableScreen) this.currentScreen;
                Position currentHitPosition;
                boolean canMove = true;
                for(Iterator<Position> itPositions = hit.getPositions(this.player.getPosition()).iterator(); itPositions.hasNext();){
                    currentHitPosition = itPositions.next();
                    if(BlocMatrix.outOfBound(currentHitPosition) || !currentPlayableScreen.getMatrix().getBloc(currentHitPosition).isTraversable()){
                        canMove = false;
                        break;
                    }
                }
                if(canMove){  
                    this.player.setState(Player.STATE_STANDBY_LEFT);
                }
            }
            else{
                PlayableScreen currentPlayableScreen = (PlayableScreen) this.currentScreen;
                Hitbox currentHitbox = currentState.getHitbox();
                if(currentState.equals(Player.STATE_STANDBY_RIGHT)){
                    Position nextPosRight = currentHitbox.getBottomRight(this.player.getPosition());
                    nextPosRight.move(Vector.RIGHT);
                    if(nextPosRight.x > BlocMatrix.BOUND_RIGHT){
                        this.player.setState(Player.STATE_RUN_JUMP_RIGHT);
                    }
                    else {
                        if(currentPlayableScreen.getMatrix().getBloc(nextPosRight).isTraversable()){
                            this.player.setState(Player.STATE_RUN_LONGJUMP_RIGHT);
                        }
                        else{
                            this.player.setState(Player.STATE_RUN_JUMP_RIGHT);
                        }
                    }
                }
                else if(currentState.equals(Player.STATE_STANDBY_LEFT)){
                    Position nextPosLeft = currentHitbox.getBottomLeft(this.player.getPosition());
                    nextPosLeft.move(Vector.LEFT);
                    if(nextPosLeft.x < BlocMatrix.BOUND_LEFT){
                        this.player.setState(Player.STATE_RUN_JUMP_LEFT);
                    }
                    else {
                        if(currentPlayableScreen.getMatrix().getBloc(nextPosLeft).isTraversable()){
                            this.player.setState(Player.STATE_RUN_LONGJUMP_LEFT);
                        }
                        else{
                            this.player.setState(Player.STATE_RUN_JUMP_LEFT);
                        }
                    }
                }
            }
            this.calculateMoves();
        } 
        catch (BlocMatrixException ex) {
            Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    public void playerRunLeft(){
        State currentState = this.player.getState();
        if(!this.player.getPosition().equals(BlocMatrix.TO_PREVIOUS)){
            if(currentState instanceof Crouch){
                this.player.setState(Player.STATE_RUN_CROUCH_LEFT);
            }
            else if(currentState.equals(Player.STATE_STANDBY_RIGHT)){
                this.player.setState(Player.STATE_STANDBY_LEFT);
            }
            else{
                this.player.setState(Player.STATE_RUN_LEFT);
            }
            this.calculateMoves();
        }
        else if(this.player.getState() instanceof Right){
            if(currentState instanceof Crouch){
                this.player.setState(Player.STATE_CROUCH_LEFT);
            }
            else{
                this.player.setState(Player.STATE_STANDBY_LEFT);
            }
            this.gotoPreviousPlayableScreen();
        }
    }
    public void playerRunRight(){
        State currentState = this.player.getState();
        if(!this.player.getPosition().equals(BlocMatrix.TO_NEXT)){
            if(currentState instanceof Crouch){
                this.player.setState(Player.STATE_RUN_CROUCH_RIGHT);
            }
            else if(currentState.equals(Player.STATE_STANDBY_LEFT)){
                this.player.setState(Player.STATE_STANDBY_RIGHT);
            }
            else{
                this.player.setState(Player.STATE_RUN_RIGHT);
            }
            this.calculateMoves();
        }
        else if(this.player.getState() instanceof Left){
            if(currentState instanceof Crouch){
                this.player.setState(Player.STATE_CROUCH_RIGHT);
            }
            else{
                this.player.setState(Player.STATE_STANDBY_RIGHT);
            }
            this.gotoNextPlayableScreen();
        }
    }
    public void playerCrouch(){
        State currentState = this.player.getState();
        if(currentState.equals(Player.STATE_STANDBY_RIGHT)){
            this.player.setState(Player.STATE_CROUCH_RIGHT);
        }
        else{
            this.player.setState(Player.STATE_CROUCH_LEFT);
        }
    }
    
    private void calculateMoves(){
        PlayableScreen screen = (PlayableScreen) this.currentScreen;
        boolean canMove;
        State playerState = this.player.getState();
        Hitbox hit = playerState.getHitbox();
        Path path = playerState.getPath();
        Position pos = new Position(this.player.getPosition());
        Position currentPlayerPos = new Position(this.player.getPosition());
        Position currentHitPosition;
        Vector currentVector;
        try {
            //On suit le trajet de path
            canMove = true;
            for(Iterator<Vector> itVectors = path.iterator(); itVectors.hasNext();){
                currentVector = itVectors.next();
                pos = currentVector.getTarget(pos);
                for(Iterator<Position> itPositions = hit.getPositions(pos).iterator(); itPositions.hasNext();){
                    currentHitPosition = itPositions.next();
                    if(BlocMatrix.outOfBound(currentHitPosition) || !screen.getMatrix().getBloc(currentHitPosition).isTraversable()){
                        canMove = false;
                        break;
                    }
                }
                if(canMove){
                    this.player.addMove(new Position(pos));
                    currentPlayerPos = new Position(pos);
                }
                else{
                    break;
                }
            }
            //Gravite, meme pas besoin des formules de Newton
            pos = new Position(currentPlayerPos);
            boolean fall = true;
            while(fall){
                pos.move(Vector.DOWN);
                for(Iterator<Position> itPositions = hit.getPositions(pos).iterator(); itPositions.hasNext();){
                    currentHitPosition = itPositions.next();
                    if(BlocMatrix.outBottom(currentHitPosition) || !screen.getMatrix().getBloc(currentHitPosition).isTraversable()){
                        fall = false;
                        break;
                    }
                }
                if(fall){
                    this.player.addMove(new Position(pos));
                }
            }
        } catch (BlocMatrixException ex) {
            Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    private void pickupBlocs(boolean onlyRamassables){
        try {
            PlayableScreen screen = (PlayableScreen) this.currentScreen;
            State playerState = this.player.getState();
            Hitbox hit = playerState.getHitbox();
            boolean replaceBloc;
            int blocLife;
            int blocScore;
            Bloc currentHitBloc;
            Position currentHitPosition;
            for(Iterator<Position> it = hit.getPositions(this.player.getPosition()).iterator(); it.hasNext();){
                replaceBloc = false;
                currentHitPosition = it.next();
                currentHitBloc = screen.getMatrix().getBloc(currentHitPosition);
                blocLife = currentHitBloc.getLife();
                blocScore = currentHitBloc.getScore();
                if(blocLife > 0){
                    if(this.player.fullLife()){
                        this.player.winScore(Game.SCORE_FOR_LIFE);
                        this.hasBonus = true;
                        replaceBloc = true;
                    }
                    else{
                        this.player.winLife(blocLife);
                        this.hasBonus = true;
                        replaceBloc = true;
                    }
                }
                if((blocLife < 0) && !onlyRamassables){
                    this.player.loseLife(Math.abs(blocLife));
                    this.hasMalus = true;
                }
                if(blocScore > 0){
                    this.player.winScore(blocScore);
                    this.hasBonus = true;
                    replaceBloc = true;
                }
                else if(blocScore < 0){
                    this.player.loseScore(-blocScore);
                    this.hasMalus = true;
                    replaceBloc = true;
                }
                if(replaceBloc){ 
                    //Bloc ramassé = bloc remplacé
                    screen.getMatrix().setBloc(currentHitPosition, this.db.getBloc(Bloc.NULL_ID));
                }
            }
        } 
        catch (BlocMatrixException | DataBaseException ex) {
            Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    private void playerPickupBlocs(){
        this.pickupBlocs(false);
    }
    private void playerPickupOnlyRamassablesBlocs(){
        this.pickupBlocs(true);
    }
    public boolean getBonusToken() {
        if(this.hasBonus){
            this.hasBonus = false;
            return true;
        }
        return false;
    }
    public boolean getMalusToken() {
        if(this.hasMalus){
            this.hasMalus = false;
            return true;
        }
        return false;
    }
    public void playerToNextMove(){
        Position pos;
        if(this.player.hasMovesTokens()){
            pos = this.player.toNextMove();
            this.playerPickupBlocs();
            Hitbox hit = this.player.getState().getHitbox();
            pos = hit.getBottomLeft(pos);
            if(!this.playerHasMovesTokens()){
                // On est sur le dernier jeton de mouvement
                if(pos.equals(BlocMatrix.TO_PREVIOUS) && this.player.getState() instanceof Left){
                    this.gotoPreviousPlayableScreen();
                }
                else if(pos.equals(BlocMatrix.TO_NEXT) && this.player.getState() instanceof Right){
                    if(this.currentScreen instanceof OutScreen){
                        this.player.flushMoves();
                        this.gotoBossScreen();
                    }
                    else{
                        this.gotoNextPlayableScreen();
                    }
                }
                else if(pos.y == BlocMatrix.BOUND_BOTTOM){
                    this.player.die();
                }
            }
        }
        else{
            this.playerStop();
        }
    }
    public boolean playerHasMovesTokens() {
        return this.player.hasMovesTokens();
    }
    public int playerMovesInQueue(){
        return this.player.movesInQueue();
    }
    public Position playerMovesTarget(){
        return this.player.movesTarget();
    }
   
    private void gotoNextPlayableScreen(){
        Screen nextInStack = this.levelStack.getNext();
        if(nextInStack == null){
            try {
                if(this.levelStack.onePlaceLeft()){
                    this.currentScreen = this.currentLevel.getOutPool().getRandomScreen();
                    this.levelStack.push(this.currentScreen);
                }
                else{
                    if(this.levelStack.size() < Game.GAME_COMMON_BEFORE_TRY_OUT){
                        this.currentScreen = this.currentLevel.getCommonPool().getRandomScreen();
                        this.levelStack.push(this.currentScreen);
                    }
                    else{
                        if(this.randomTryOut.nextInt(Game.GAME_COMMON_OR_OUT_RATIO) == 1){
                            this.currentScreen = this.currentLevel.getOutPool().getRandomScreen();
                            this.levelStack.push(this.currentScreen); 
                        }
                        else{
                            this.currentScreen = this.currentLevel.getCommonPool().getRandomScreen();
                            this.levelStack.push(this.currentScreen);
                        }
                    }
                }
                this.player.winScore(this.randomScore.nextInt(Game.SCORE_MAX_FOR_PASSING_PLAYABLE));
            }
            catch (StackException ex) {
                Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
        else{
            this.currentScreen = nextInStack;
        }
        this.player.addMove(BlocMatrix.START_NEXT);
    }
    private void gotoPreviousPlayableScreen(){
        Screen tryPrevious;
        if((tryPrevious = this.levelStack.getPrevious()) != null){
            this.currentScreen = tryPrevious;
            this.player.addMove(BlocMatrix.START_PREVIOUS);
        }
    }
    private void startPlayableLevel(){
        try {
            this.levelStack.flush();
            this.currentLevel = db.getRamdomLevel();
            this.currentScreen = this.currentLevel.getInPool().getRandomScreen();
            this.levelStack.push(this.currentScreen);
            this.player.setPosition(BlocMatrix.START_LEVEL);
        } catch (StackException ex) {
            Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    private void gotoBossScreen(){
        this.currentScreen = db.getRandomBoss();
    }
    
    public void gameStart(){
        this.player.reset();
        this.startPlayableLevel();
    }
    public void answerCorrect(){
        this.player.winScore(Game.SCORE_FOR_CORRECT_ANSWER);
        this.startPlayableLevel();
    }
    public void answerWrong(){
        this.player.loseLife();
        this.gotoBossScreen();
    }
    public int getPlayerLife(){
        return this.player.getLifes();
    }
    public int getPlayerScore(){
        return this.player.getScore();
    }
    public String getCurrentLevelName(){
        return this.currentLevel.getName();
    }
    public Position getPlayerPosition(){
        return this.player.getPosition();
    }
    public State getPlayerState(){
        return this.player.getState();
    }
    public Screen getCurrentScreen(){
        return this.currentScreen;
    }
    public boolean isOver(){
        return !this.player.isAlive();
    }
}
