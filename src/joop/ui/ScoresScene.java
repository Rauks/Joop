/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joop.ui;

import game.Scores;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import joop.Joop;
import joop.ui.componants.MenuButton;
import joop.ui.disposers.MenuDisposer;
import joop.ui.stylizers.ImageStylizer;
import joop.ui.stylizers.TextStylizer;

/**
 *
 * @author Karl
 */
public class ScoresScene extends JoopScene {
    private GridPane ladder;
    
    public ScoresScene(Joop context){
        super(context);
        
        this.ladder = new GridPane();
        this.ladder.setAlignment(Pos.CENTER);
        this.ladder.setHgap(20);
        
        ImageView background = new ImageView(new Image(OptionsScene.class.getResource("/ressources/ui/main.jpg").toString()));
        ImageStylizer.styleScoresBackground(background);
        
        final ScoresScene me = this;
        Text back = new MenuButton("Retour", 
            new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    Joop joop = me.getContext();
                    if(joop.isPLaying()){
                        joop.showPause(joop.options.getAnimations());
                    }
                    else{
                        joop.showMainMenu(joop.options.getAnimations());
                    }
                }
            }
        ).getText();
        
        HBox boxChoices = new HBox();
        boxChoices.getChildren().add(back);
        
        StackPane root = MenuDisposer.dispose(boxChoices, this.ladder, background, MenuDisposer.INSETS_SUBMENU);
        this.setScene(new Scene(root, Joop.WINDOW_WIDTH, Joop.WINDOW_HEIGHT));
    }
    
    public void refresh(){
        this.ladder.getChildren().removeAll(this.ladder.getChildren());
        for(int i = 0; i < Scores.SCORES; i++){
            Text pseudo = new Text(this.getContext().scores.getPseudo(i));
            TextStylizer.styleScoresPseudo(pseudo);
            this.ladder.add(pseudo, 0, i);
            GridPane.setHalignment(pseudo, HPos.RIGHT);
            Text score = new Text(String.valueOf(this.getContext().scores.getScore(i)));
            TextStylizer.styleScoresScore(score);
            this.ladder.add(score, 2, i);
            GridPane.setHalignment(score, HPos.LEFT);
        }
        this.ladder.add(new ImageView(new Image(OptionsScene.class.getResource("/ressources/ui/score1.png").toString())), 1, 0);
        this.ladder.add(new ImageView(new Image(OptionsScene.class.getResource("/ressources/ui/score2.png").toString())), 1, 1);
        this.ladder.add(new ImageView(new Image(OptionsScene.class.getResource("/ressources/ui/score3.png").toString())), 1, 2);
    }
}
