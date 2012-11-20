/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joop.ui;

import audio.Playlist;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
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
import joop.ui.disposers.MenuDisposer;
import joop.ui.stylizers.ImageStylizer;
import joop.ui.stylizers.TextStylizer;

/**
 *
 * @author Karl
 */
public class PseudoPromptScene extends JoopScene{
    private TextField pseudoField;
    
    public PseudoPromptScene(Joop context){
        super(context);
        
        ImageView background = new ImageView(new Image(MainMenuScene.class.getResource("/ressources/ui/main.jpg").toString()));
        ImageStylizer.styleMainBackground(background);
        
        Text titlePseudo = new Text("Quel est votre pseudo ?");
        TextStylizer.styleOptionLabel(titlePseudo);
        this.pseudoField = new TextField();
        this.pseudoField.setMaxSize(160, 22);
        if(!this.getContext().options.getPseudo().isEmpty()){
            this.pseudoField.setText(this.getContext().options.getPseudo());
        }
        
        VBox divers = new VBox();
        divers.setSpacing(20);
        divers.setAlignment(Pos.CENTER);
        divers.setPadding(new Insets(210, 0, 0, 0));
        divers.getChildren().add(titlePseudo);
        divers.getChildren().add(this.pseudoField);
        
        
        final PseudoPromptScene me = this;
        Text valid = new MenuButton("Valider", 
            new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    me.save();
                    Joop joop = me.getContext();
                    joop.music.stopAll();
                    joop.music.playLoopRandomFromPlaylist(Playlist.PLAYLIST_MUSIC_MENU);
                    joop.showMainMenu(joop.options.getAnimations());
                }
            }
        ).getText();
        Text pass = new MenuButton("Passer", 
            new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    Joop joop = me.getContext();
                    joop.music.stopAll();
                    joop.music.playLoopRandomFromPlaylist(Playlist.PLAYLIST_MUSIC_MENU);
                    joop.showMainMenu(joop.options.getAnimations());
                }
            }
        ).getText();
        HBox boxChoices = new HBox();
        boxChoices.getChildren().add(valid);
        boxChoices.getChildren().add(pass);
        
        StackPane root = MenuDisposer.dispose(boxChoices, divers, background, MenuDisposer.INSETS_SUBMENU);
        
        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                Joop joop = me.getContext();
                KeyCode key = ke.getCode();
                if(key.equals(KeyCode.ENTER)){
                    me.save();
                    joop.music.stopAll();
                    joop.music.playLoopRandomFromPlaylist(Playlist.PLAYLIST_MUSIC_MENU);
                    joop.showMainMenu(joop.options.getAnimations());
                }
            }
        });
        
        this.setScene(new Scene(root, Joop.WINDOW_WIDTH, Joop.WINDOW_HEIGHT));
    }
    
    private void save(){
        this.getContext().options.setPseudo(this.pseudoField.getText());
        this.getContext().options.saveXml();
    }
}
