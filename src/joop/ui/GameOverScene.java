/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joop.ui;

import audio.Playlist;
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
public final class GameOverScene extends JoopScene{
    public Text score;
    
    public GameOverScene(Joop context){
        super(context);
        
        ImageView background = new ImageView(new Image(GameOverScene.class.getResource("/ressources/ui/gameover.jpg").toString()));
        ImageStylizer.styleGameOverBackground(background);
        
        Text text = new Text("Game Over !");
        TextStylizer.styleGameOverTitle(text);
        this.score = new Text();
        TextStylizer.styleGameOverSubtitle(this.score);
        
        final Joop joop = this.getContext();
        Text menu = new MenuButton("Menu", 
            new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    joop.music.stopAll();
                    joop.music.playLoopRandomFromPlaylist(Playlist.PLAYLIST_MUSIC_MENU);
                    joop.showMainMenu(joop.options.getAnimations());
                }
            }
        ).getText();
        Text replay = new MenuButton("Rejouer", 
            new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    joop.game.gameStart();
                    joop.showGameStart(joop.options.getAnimations());
                }
            }
        ).getText();
        
        HBox boxChoices = new HBox();
        boxChoices.getChildren().add(menu);
        boxChoices.getChildren().add(replay);
        
        VBox box = new VBox();
        box.getChildren().add(text);
        box.getChildren().add(this.score);
        box.setAlignment(Pos.CENTER);
        
        StackPane root = MenuDisposer.dispose(boxChoices, box, background, MenuDisposer.INSETS_MAINMENU);
        this.setScene(new Scene(root, Joop.WINDOW_WIDTH, Joop.WINDOW_HEIGHT));
    }
}
