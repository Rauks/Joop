/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joop.ui.componants;

import game.Game;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import joop.ui.stylizers.TextStylizer;

/**
 *
 * @author
 * Karl
 */
public class Overlay {
    private BorderPane rootOverlay;
    private final Text scoreOverlay;
    private final LifeBar lifeBarOverlay;
    private final GridPane lifeOverlay;
    private Game game;
    
    public Overlay(Game game){
        this.game = game;
        this.scoreOverlay = new Text();
        TextStylizer.styleOverlayScore(this.scoreOverlay);
        this.lifeBarOverlay = new LifeBar();
        this.lifeOverlay = this.lifeBarOverlay.getBar();
        
        this.rootOverlay = new BorderPane();
        this.rootOverlay.setLeft(this.scoreOverlay);
        BorderPane.setAlignment(this.scoreOverlay, Pos.TOP_LEFT);
        this.rootOverlay.setRight(this.lifeOverlay);
        BorderPane.setAlignment(this.lifeOverlay, Pos.TOP_RIGHT);
        this.rootOverlay.setPadding(new Insets(0, 20, 20, 20));
    }
    public BorderPane getRootPane(){
        return this.rootOverlay;
    }
    public void refresh(){
        this.scoreOverlay.setText(String.valueOf(this.game.getPlayerScore()));
        this.lifeBarOverlay.setLife(this.game.getPlayerLife());
    }
}
