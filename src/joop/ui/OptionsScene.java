/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joop.ui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import joop.Joop;
import joop.Options;
import joop.ui.componants.MenuButton;
import joop.ui.disposers.MenuDisposer;
import joop.ui.stylizers.ImageStylizer;
import joop.ui.stylizers.TextStylizer;

/**
 *
 * @author Karl
 */

public class OptionsScene extends JoopScene{
    private Slider sliderMusic;
    private Slider sliderFx;
    private TextField controlsLeft;
    private TextField controlsRight;
    private TextField controlsJump;
    private TextField controlsCrouch;
    private TextField controlsPause;
    private CheckBox checkAnimations;
    private TextField pseudoField;
    
    private void styleField(TextField field){
        field.setMaxSize(70, 22);
        field.setEditable(false);
        final TextField me = field;
        field.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent ke) {
                KeyCode key = ke.getCode();
                me.setText(key.getName());
            }
        });
    }
    private void styleSlider(Slider slider){
        slider.setMajorTickUnit(0.5);
        slider.setMinorTickCount(4);
        slider.setSnapToTicks(true);
        slider.setShowTickMarks(true);
        slider.setPrefWidth(200);
        slider.setTranslateY(-10);
        slider.setBlockIncrement(0.1);
    }
    
    public OptionsScene(Joop context){
        super(context);
        
        ImageView background = new ImageView(new Image(OptionsScene.class.getResource("/ressources/ui/options.jpg").toString()));
        ImageStylizer.styleOptionsBackground(background);
        
        
        Text titleGraph = new Text("Graphismes");
        TextStylizer.styleOptionTitle(titleGraph);
        this.checkAnimations = new CheckBox();
        this.checkAnimations.setTranslateY(15);
        Text labelGraph = new Text("Animations : ");
        TextStylizer.styleOptionLabel(labelGraph);
        HBox graph = new HBox();
        graph.setSpacing(15);
        graph.getChildren().add(labelGraph);
        graph.getChildren().add(this.checkAnimations);
        Text titlePlayer = new Text("Joueur");
        TextStylizer.styleOptionTitle(titlePlayer);
        Text titlePseudo = new Text("Pseudo (scores) :");
        TextStylizer.styleOptionLabel(titlePseudo);
        this.pseudoField = new TextField();
        this.pseudoField.setMaxSize(160, 22);
        
        VBox divers = new VBox();
        divers.setSpacing(20);
        divers.getChildren().add(titleGraph);
        divers.getChildren().add(graph);
        divers.getChildren().add(titlePlayer);
        divers.getChildren().add(titlePseudo);
        divers.getChildren().add(this.pseudoField);
        
        
        Text titleAudio = new Text("Audio");
        TextStylizer.styleOptionTitle(titleAudio);
        Text labelMusic = new Text("Musiques :");
        TextStylizer.styleOptionLabel(labelMusic);
        this.sliderMusic = new Slider(0, 1, 0);
        this.styleSlider(this.sliderMusic);
        Text labelFx = new Text("Bruitages :");
        TextStylizer.styleOptionLabel(labelFx);
        this.sliderFx = new Slider(0, 1, 0);
        this.styleSlider(this.sliderFx);
        
        VBox audio = new VBox();
        audio.setSpacing(15);
        audio.getChildren().add(titleAudio);
        audio.getChildren().add(labelMusic);
        audio.getChildren().add(this.sliderMusic);
        audio.getChildren().add(labelFx);
        audio.getChildren().add(this.sliderFx);
        
        Text controlsTitle = new Text("Contrôles");
        TextStylizer.styleOptionTitle(controlsTitle);
        
        Text labelRight = new Text("Droite :");
        TextStylizer.styleOptionLabel(labelRight);
        this.controlsRight = new TextField();
        this.styleField(this.controlsRight);
        
        Text labelLeft = new Text("Gauche :");
        TextStylizer.styleOptionLabel(labelLeft);
        this.controlsLeft = new TextField();
        this.styleField(this.controlsLeft);
        
        Text labelJump = new Text("Sauter :");
        TextStylizer.styleOptionLabel(labelJump);
        this.controlsJump = new TextField();
        this.styleField(this.controlsJump);
        
        Text labelCrouch = new Text("S'accroupir :");
        TextStylizer.styleOptionLabel(labelCrouch);
        this.controlsCrouch = new TextField();
        this.styleField(this.controlsCrouch);
        
        Text labelPause = new Text("Pause :");
        TextStylizer.styleOptionLabel(labelPause);
        this.controlsPause = new TextField();
        this.styleField(this.controlsPause);
        
        GridPane controlsGrid = new GridPane();
        controlsGrid.setVgap(20);
        controlsGrid.setHgap(20);
        controlsGrid.add(labelLeft, 0, 0);
        controlsGrid.add(this.controlsLeft, 1, 0);
        controlsGrid.add(labelRight, 0, 1);
        controlsGrid.add(this.controlsRight, 1, 1);
        controlsGrid.add(labelJump, 0, 2);
        controlsGrid.add(this.controlsJump, 1, 2);
        controlsGrid.add(labelCrouch, 0, 3);
        controlsGrid.add(this.controlsCrouch, 1, 3);
        controlsGrid.add(labelPause, 0, 4);
        controlsGrid.add(this.controlsPause, 1, 4);
        
        VBox controls = new VBox();
        controls.setSpacing(20);
        controls.getChildren().add(controlsTitle);
        controls.getChildren().add(controlsGrid);
        
        HBox content = new HBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(70);
        content.getChildren().add(audio);
        content.getChildren().add(divers);
        content.getChildren().add(controls);
        
        final OptionsScene me = this;
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
        Text save = new MenuButton("Enregistrer", 
            new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    me.saveOptions();
                }
            }
        ).getText();
        Text defaut = new MenuButton("Défaut", 
            new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    me.defautOptions();
                }
            }
        ).getText();
        
        HBox boxChoices = new HBox();
        boxChoices.getChildren().add(back);
        boxChoices.getChildren().add(save);
        boxChoices.getChildren().add(defaut);
        
        StackPane root = MenuDisposer.dispose(boxChoices, content, background, MenuDisposer.INSETS_SUBMENU);
        this.setScene(new Scene(root, Joop.WINDOW_WIDTH, Joop.WINDOW_HEIGHT));
    }
    
    public void refresh(){
        Options options = this.getContext().options;
        this.sliderMusic.setValue(options.getVolumeMusic());
        this.sliderFx.setValue(options.getVolumeFx());
        this.controlsJump.setText(options.getKeyUp().getName());
        this.controlsCrouch.setText(options.getKeyDown().getName());
        this.controlsLeft.setText(options.getKeyLeft().getName());
        this.controlsRight.setText(options.getKeyRight().getName());
        this.controlsPause.setText(options.getKeyPause().getName());
        this.checkAnimations.setSelected(options.getAnimations());
        this.pseudoField.setText(options.getPseudo());
    }
    
    public void saveOptions(){
        Options options = this.getContext().options;
        options.setVolumeMusic(this.sliderMusic.getValue());
        options.setVolumeFx(this.sliderFx.getValue());
        options.setKeyUp(KeyCode.getKeyCode(this.controlsJump.getText()));
        options.setKeyDown(KeyCode.getKeyCode(this.controlsCrouch.getText()));
        options.setKeyLeft(KeyCode.getKeyCode(this.controlsLeft.getText()));
        options.setKeyRight(KeyCode.getKeyCode(this.controlsRight.getText()));
        options.setKeyPause(KeyCode.getKeyCode(this.controlsPause.getText()));
        options.setAnimations(this.checkAnimations.isSelected());
        options.setPseudo(this.pseudoField.getText());
        options.saveXml();
        this.getContext().ajustAudioVolume();
    }
    public void defautOptions(){
        Options options = this.getContext().options;
        options.loadDefaut();
        options.saveXml();
        this.refresh();
        this.getContext().ajustAudioVolume();
    }
}
