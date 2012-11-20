/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joop.ui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import joop.Joop;
import joop.ui.componants.MenuButton;
import joop.ui.disposers.MenuDisposer;
import joop.ui.stylizers.ImageStylizer;
import joop.ui.stylizers.TextStylizer;

/**
 *
 * @author
 * Karl
 */
public final class MainMenuScene extends JoopScene{
    public MainMenuScene(Joop context){
        super(context);
        
        ImageView background = new ImageView(new Image(MainMenuScene.class.getResource("/ressources/ui/main.jpg").toString()));
        ImageStylizer.styleMainBackground(background);
        
        Text joopSplash = new Text("JOOP");
        TextStylizer.styleMainTitle(joopSplash);
        Text joopSub = new Text("Play & Learn !");
        TextStylizer.styleMainSubtitle(joopSub);
        
        final Joop joop = this.getContext();
        Text start = new MenuButton("Jouer", 
            new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    joop.game.gameStart();
                    joop.showGameStart(joop.options.getAnimations());
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
        Text credits = new MenuButton("Crédits", 
            new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    joop.showCredits(joop.options.getAnimations());
                }
            }
        ).getText();
        Text quit = new MenuButton("Quitter", 
            new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    joop.showQuit();
                }
            }
        ).getText();
        
        HBox boxChoices = new HBox();
        boxChoices.getChildren().add(start);
        boxChoices.getChildren().add(options);
        boxChoices.getChildren().add(scores);
        boxChoices.getChildren().add(credits);
        boxChoices.getChildren().add(quit);
        
        VBox box = new VBox();
        box.getChildren().add(joopSplash);
        box.getChildren().add(joopSub);
        box.setAlignment(Pos.CENTER);
        
        StackPane root = MenuDisposer.dispose(boxChoices, box, background, MenuDisposer.INSETS_MAINMENU);
        this.setScene(new Scene(root, Joop.WINDOW_WIDTH, Joop.WINDOW_HEIGHT));
    }
}
