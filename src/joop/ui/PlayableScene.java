/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joop.ui;

import audio.Playlist;
import game.Bloc;
import game.BlocMatrix;
import game.BlocMatrixException;
import game.Game;
import game.screens.BossScreen;
import game.screens.PlayableScreen;
import geometry.Position;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import joop.Joop;
import joop.ui.componants.Overlay;
import joop.ui.stylizers.ImageStylizer;
import player.states.State;
import player.states.itf.Left;
import player.states.itf.LongJump;
import player.states.itf.Right;

/**
 *
 * @author
 * Karl
 */
public final class PlayableScene extends JoopScene{
    public static final Duration TRANSITION_DURATION = Duration.millis(140);
    
    public ImageView background;
    public HashMap<Position, ImageView> blocGrid;
    public Overlay overlay;
    public ImageView player;
    public Timeline timeline;
    
    private Position previousPosition;
    private TranslateTransition translateTransition;
    
    public PlayableScene(Joop context){
        super(context);
        GridPane grid = new GridPane();
        grid.setMaxSize(Joop.WINDOW_WIDTH, Joop.WINDOW_HEIGHT);
        this.blocGrid = new HashMap<>();
        Position pointer = new Position();
        for(int y = BlocMatrix.BOUND_TOP; y <= BlocMatrix.BOUND_BOTTOM; y++){
            for(int x = BlocMatrix.BOUND_LEFT; x <= BlocMatrix.BOUND_RIGHT; x++){
                pointer.x = x;
                pointer.y = y;
                this.blocGrid.put(new Position(pointer), new ImageView());
            }
        }
        for(int y = BlocMatrix.BOUND_TOP; y <= BlocMatrix.BOUND_BOTTOM; y++){
            for(int x = BlocMatrix.BOUND_LEFT; x <= BlocMatrix.BOUND_RIGHT; x++){
                pointer.x = x;
                pointer.y = y;
                grid.add(this.blocGrid.get(pointer), x, y);
            }
        }
        
        this.background = new ImageView();
        ImageStylizer.stylePlayableBackground(this.background);
        this.overlay = new Overlay(this.getContext().game);
        
        this.timeline = new Timeline();
        KeyFrame timelineKeyFrame = new KeyFrame(PlayableScene.TRANSITION_DURATION);
        this.timeline.getKeyFrames().add(timelineKeyFrame);
        StackPane rootPlayer = new StackPane();
        rootPlayer.setMaxSize(Joop.WINDOW_WIDTH, Joop.WINDOW_HEIGHT);
        this.player = new ImageView();
        rootPlayer.getChildren().add(this.player);
        StackPane.setAlignment(this.player, Pos.TOP_LEFT);
        
        this.previousPosition = new Position(this.getContext().game.getPlayerPosition());
        this.translateTransition = new TranslateTransition(PlayableScene.TRANSITION_DURATION, this.player);
        
        this.refreshPlayerPosition();
        this.refreshPlayerImage();
        
        StackPane root = new StackPane();
        root.getChildren().add(this.background);
        StackPane.setAlignment(this.background, Pos.CENTER);
        root.getChildren().add(rootPlayer);
        StackPane.setAlignment(rootPlayer, Pos.CENTER);
        root.getChildren().add(grid);
        StackPane.setAlignment(grid, Pos.CENTER);
        root.getChildren().add(this.overlay.getRootPane());
        StackPane.setAlignment(this.overlay.getRootPane(), Pos.CENTER);
        this.setScene(new Scene(root, Joop.WINDOW_WIDTH, Joop.WINDOW_HEIGHT));
        
        final PlayableScene me = this;
        this.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                Joop joop = me.getContext();
                if(!joop.game.getPlayerState().isMovingState()){
                    KeyCode key = ke.getCode();
                    if(key.equals(joop.options.getKeyUp())){
                        joop.game.playerJump();
                        if(joop.game.playerHasMovesTokens()){
                            joop.fx.playRandomFromPlaylist(Playlist.PLAYLIST_FX_JUMP);
                        }
                        me.movePlayer();
                    }
                    else if(key.equals(joop.options.getKeyDown())){
                        joop.game.playerCrouch();
                        if(joop.game.playerHasMovesTokens()){
                            joop.fx.playRandomFromPlaylist(Playlist.PLAYLIST_FX_CROUCH);
                        }
                        me.movePlayer();
                    }
                    else if(key.equals(joop.options.getKeyLeft())){
                        State initState = joop.game.getPlayerState();
                        joop.game.playerRunLeft();
                        if(joop.game.playerHasMovesTokens() || initState instanceof Right){
                            joop.fx.playRandomFromPlaylist(Playlist.PLAYLIST_FX_RUN);
                        }
                        me.movePlayer();
                    }
                    else if(key.equals(joop.options.getKeyRight())){
                        State initState = joop.game.getPlayerState();
                        joop.game.playerRunRight();
                        if(joop.game.playerHasMovesTokens() || initState instanceof Left){
                            joop.fx.playRandomFromPlaylist(Playlist.PLAYLIST_FX_RUN);
                        }
                        me.movePlayer();
                    }
                    else if(key.equals(joop.options.getKeyPause())){
                        joop.showPause(joop.options.getAnimations());
                    }
                }
            }
        });
    }
    private void refreshPlayerPositionWithoutAnimation(Position reference){
        this.player.setTranslateX(Double.valueOf(Bloc.PIXEL_SIZE * reference.x));
        this.player.setTranslateY(Double.valueOf(Bloc.PIXEL_SIZE * reference.y));
    }
    private void refreshPlayerPositionWithTranslation(Position reference){
        Position translateRef = this.getContext().game.getPlayerState().getHitbox().getTopLeft(this.previousPosition);
        this.translateTransition.setFromX(Bloc.PIXEL_SIZE * translateRef.x);
        this.translateTransition.setFromY(Bloc.PIXEL_SIZE * translateRef.y);
        this.translateTransition.setToX(Bloc.PIXEL_SIZE * reference.x);
        this.translateTransition.setToY(Bloc.PIXEL_SIZE * reference.y);
        this.translateTransition.play();
    }
    private void playBonusMalusFx(){
        Game game = this.getContext().game;
        if(game.getBonusToken()){
            this.getContext().fx.playRandomFromPlaylist(Playlist.PLAYLIST_FX_BONUS);
        }
        if(game.getMalusToken()){
            this.getContext().fx.playRandomFromPlaylist(Playlist.PLAYLIST_FX_MALUS);
        }
        
    }
    public void refreshPlayerPosition(){
        Game game = this.getContext().game;
        Position current = game.getPlayerPosition();
        Position ref = game.getPlayerState().getHitbox().getTopLeft(current);
        Position target = game.playerMovesTarget();
        State currentState = game.getPlayerState();
        if(!this.previousPosition.equals(current) && Math.abs(this.previousPosition.x - current.x) < 4){
            if(currentState instanceof LongJump && game.playerHasMovesTokens() && target.x != this.previousPosition.x && Math.abs(this.previousPosition.x - target.x) < 4){
                game.playerToNextMove();
                Position newCurrent = game.getPlayerPosition();
                Position newRef = game.getPlayerState().getHitbox().getTopLeft(newCurrent);
                
                this.refreshPlayerPositionWithTranslation(newRef);
                current = newCurrent;
            }
            else{
                this.refreshPlayerPositionWithTranslation(ref);
            }
        }
        else{
            this.refreshPlayerPositionWithoutAnimation(ref);
        }
        this.previousPosition = new Position(current);
    }
    public void movePlayer(){
        Game game = this.getContext().game;
        if(game.playerHasMovesTokens()){
            this.refreshPlayerImage();
            game.playerToNextMove();
            this.refreshPlayerPosition();
            this.overlay.refresh();
            //this.refreshPlayableGrid();
            final PlayableScene me = this;
            this.timeline.setOnFinished(new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    Joop joop = me.getContext();
                    if(joop.game.isOver()){
                        joop.showGameOverMenu(joop.options.getAnimations());
                    }
                    else{
                        me.movePlayer();
                    }
                }
            });
            this.timeline.play();
        }
        else{
            this.getContext().game.playerStop();
            this.refreshPlayerImage();
            this.overlay.refresh();
            if(this.getContext().game.getCurrentScreen() instanceof BossScreen){
                this.getContext().showBossAlert(false);
            }
            else{
                this.refreshPlayerPosition();
                this.refreshPlayableGrid();
            }
        }
        this.playBonusMalusFx();
    }
    public void refreshPlayerImage(){
        this.player.setImage(this.getContext().game.getPlayerState().getImage());
    }
    
    public void refreshPlayableGrid(){
        try {
            if(this.getContext().game.getCurrentScreen() instanceof PlayableScreen){
                PlayableScreen ps = (PlayableScreen) this.getContext().game.getCurrentScreen();
                Position pointer = new Position();
                for(int y = BlocMatrix.BOUND_TOP; y <= BlocMatrix.BOUND_BOTTOM; y++){
                    for(int x = BlocMatrix.BOUND_LEFT; x <= BlocMatrix.BOUND_RIGHT; x++){
                        pointer.x = x;
                        pointer.y = y;
                        this.blocGrid.get(pointer).setImage(ps.getMatrix().getBloc(pointer).getImage());
                    }
                }
                this.background.setImage(ps.getBackground().getImage());
            }
        }
        catch (BlocMatrixException ex) {
            Logger.getLogger(Joop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
