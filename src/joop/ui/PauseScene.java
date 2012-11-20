/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joop.ui;

import game.screens.BossScreen;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import joop.Joop;
import joop.ui.componants.MenuButton;
import joop.ui.componants.Overlay;
import joop.ui.disposers.MenuDisposer;
import joop.ui.stylizers.ImageStylizer;
import joop.ui.stylizers.TextStylizer;

/**
 *
 * @author Karl
 */
public class PauseScene extends JoopScene {
    public Overlay overlay;
    
    public PauseScene(Joop context){
        super(context);
        
        ImageView background = new ImageView(new Image(PauseScene.class.getResource("/ressources/ui/pause.jpg").toString()));
        ImageStylizer.stylePauseBackground(background);
        
        this.overlay = new Overlay(this.getContext().game);
        Text pause = new Text("Pause");
        TextStylizer.stylePauseTitle(pause);
        
        final Joop joop = this.getContext();
        Text back = new MenuButton("Continuer", 
            new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    if(joop.game.getCurrentScreen() instanceof BossScreen){
                        joop.showBossScreen(joop.options.getAnimations());
                    }
                    else{
                        joop.showPlayableScreen(joop.options.getAnimations());
                    }
                }
            }
        ).getText();
        Text menu = new MenuButton("Abandonner", 
            new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    joop.showGameOverMenu(joop.options.getAnimations());
                }
            }
        ).getText();
        Text options = new MenuButton("Options", 
            new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    joop.showOptions(joop.options.getAnimations());
                }
            }
        ).getText();
        Text scores = new MenuButton("Scores", 
            new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    joop.showScores(joop.options.getAnimations());
                }
            }
        ).getText();
        
        HBox boxChoices = new HBox();
        boxChoices.getChildren().add(back);
        boxChoices.getChildren().add(menu);
        boxChoices.getChildren().add(options);
        boxChoices.getChildren().add(scores);
        
        VBox box = new VBox();
        box.getChildren().add(pause);
        box.getChildren().add(boxChoices);
        box.setAlignment(Pos.CENTER);
        
        StackPane root = MenuDisposer.dispose(boxChoices, box, background, MenuDisposer.INSETS_MAINMENU);
        this.setScene(new Scene(root, Joop.WINDOW_WIDTH, Joop.WINDOW_HEIGHT));
        
        this.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                KeyCode key = ke.getCode();
                if(key.equals(joop.options.getKeyPause())){
                    if(joop.game.getCurrentScreen() instanceof BossScreen){
                        joop.showBossScreen(joop.options.getAnimations());
                    }
                    else{
                        joop.showPlayableScreen(joop.options.getAnimations());
                    }
                }
            }
        });
    }
}
