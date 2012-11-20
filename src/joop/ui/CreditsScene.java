/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joop.ui;

import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import joop.Joop;
import joop.ui.componants.MenuButton;
import joop.ui.disposers.MenuDisposer;
import joop.ui.stylizers.ImageStylizer;
import joop.ui.stylizers.TextStylizer;

/**
 *
 * @author Karl
 */
public class CreditsScene extends JoopScene{
    public static final Duration TIME_NAMES = Duration.seconds(8);
    public static final Duration TIME_OTHERS = Duration.seconds(5);
    public static final Duration TIME_TESTERS = Duration.seconds(8);
    private static final Duration FADEIN_DURATION = Duration.millis(150);
    private static final Duration FADEOUT_DURATION = Duration.millis(150);
    
    private Timeline timelineTeam;
    private Timeline timelineOthers;
    private Timeline timelineTesters;
    private ArrayList<Text> teamList;
    private ArrayList<Text> othersList;
    private ArrayList<Text> testersList;
    private FadeTransition fadeIn;
    private FadeTransition fadeOut;
    private VBox textBox;
    
    private void loadTeam(){
        this.teamList = new ArrayList<>();
        Text laetitia = new Text("Laëtitia Gaignier");
        TextStylizer.styleCreditsName(laetitia);
        Text thibaud = new Text("Thibaud Renaux");
        TextStylizer.styleCreditsName(thibaud);
        Text florent = new Text("Florent Lacroix");
        TextStylizer.styleCreditsName(florent);
        Text simon = new Text("Simon Chevalier");
        TextStylizer.styleCreditsName(simon);
        Text karl1 = new Text("Karl Woditsch");
        TextStylizer.styleCreditsName(karl1);
        Text karl2 = new Text("Karl Woditsch");
        TextStylizer.styleCreditsName(karl2);
        Text rg = new Text("Graphismes");
        TextStylizer.styleCreditsTitle(rg);
        Text dw = new Text("Developpement Web");
        TextStylizer.styleCreditsTitle(dw);
        Text dj = new Text("Developpement Java");
        TextStylizer.styleCreditsTitle(dj);
        
        this.teamList.add(rg);
        this.teamList.add(laetitia);
        this.teamList.add(florent);
        this.teamList.add(thibaud);
        this.teamList.add(dw);
        this.teamList.add(simon);
        this.teamList.add(karl1);
        this.teamList.add(dj);
        this.teamList.add(karl2);
    }
    private void loadOthers(){
        this.othersList = new ArrayList<>();
        Text prof = new Text("Professeur Référent");
        TextStylizer.styleCreditsTitle(prof);
        Text perronne = new Text("Jean-Marc Perronne");
        TextStylizer.styleCreditsName(perronne);
        Text music = new Text("Musiques");
        TextStylizer.styleCreditsTitle(music);
        Text axial = new Text("Celestial Aeon Project");
        TextStylizer.styleCreditsName(axial);
        
        this.othersList.add(prof);
        this.othersList.add(perronne);
        this.othersList.add(music);
        this.othersList.add(axial);
    }
    private void loadTesters(){
        this.testersList = new ArrayList<>();
        Text test = new Text("Testeurs");
        TextStylizer.styleCreditsTitle(test);
        Text testeur1 = new Text("Nicolas Devenet");
        TextStylizer.styleCreditsName(testeur1);
        Text testeur2 = new Text("Nacer Djenane");
        TextStylizer.styleCreditsName(testeur2);
        Text testeur3 = new Text("Xavier Fayolle");
        TextStylizer.styleCreditsName(testeur3);
        Text testeur7 = new Text("Benoit Gradit");
        TextStylizer.styleCreditsName(testeur7);
        Text testeur4 = new Text("Charles Hazard");
        TextStylizer.styleCreditsName(testeur4);
        Text testeur5 = new Text("Suzanne Noll");
        TextStylizer.styleCreditsName(testeur5);
        Text testeur6 = new Text("Georges Olivares");
        TextStylizer.styleCreditsName(testeur6);
        
        this.testersList.add(test);
        this.testersList.add(testeur1);
        this.testersList.add(testeur2);
        this.testersList.add(testeur3);
        this.testersList.add(testeur7);
        this.testersList.add(testeur4);
        this.testersList.add(testeur5);
        this.testersList.add(testeur6);
    }
    private void displayList(ArrayList<Text> list, Timeline tempo){
        tempo.play();
        if(this.getContext().options.getAnimations()){
            final ArrayList<Text> finalList = list;
            final CreditsScene me = this;
            this.fadeOut.setOnFinished(new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    me.textBox.getChildren().removeAll(textBox.getChildren());
                    me.textBox.getChildren().addAll(finalList);
                    me.fadeIn.play();
                }
            }); 
            this.fadeOut.play();
        }
        else{
            this.textBox.getChildren().removeAll(textBox.getChildren());
            this.textBox.getChildren().addAll(list);
        }
    }
    public void displayTeam(){
        this.displayList(this.teamList, this.timelineTeam);
    }
    public void displayOthers(){
        this.displayList(this.othersList, this.timelineOthers);
    }
    public void displayTesters(){
        this.displayList(this.testersList, this.timelineTesters);
    }
    
    public CreditsScene(Joop context){
        super(context);
        
        ImageView background = new ImageView(new Image(CreditsScene.class.getResource("/ressources/ui/credits.jpg").toString()));
        ImageStylizer.styleCreditsBackground(background);
        
        this.loadTeam();
        this.loadOthers();
        this.loadTesters();
        
        this.textBox = new VBox();
        this.textBox.setAlignment(Pos.TOP_LEFT);
        this.textBox.setSpacing(-10);
        //this.displayTeam();
        
        this.timelineTeam = new Timeline();
        this.timelineOthers = new Timeline();
        this.timelineTesters = new Timeline();
        
        this.fadeOut = new FadeTransition(CreditsScene.FADEOUT_DURATION, this.textBox);
        this.fadeOut.setFromValue(1.0);
        this.fadeOut.setToValue(0.0);
        this.fadeIn = new FadeTransition(CreditsScene.FADEIN_DURATION, this.textBox);
        this.fadeIn.setFromValue(0.0);
        this.fadeIn.setToValue(1.0);
        
        final CreditsScene me = this;
        KeyFrame timelineTeamKeyFrame = new KeyFrame(CreditsScene.TIME_NAMES);
        this.timelineTeam.getKeyFrames().add(timelineTeamKeyFrame);
        this.timelineTeam.setOnFinished(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                me.displayOthers();
            }
        });
        KeyFrame timelineOthersKeyFrame = new KeyFrame(CreditsScene.TIME_OTHERS);
        this.timelineOthers.getKeyFrames().add(timelineOthersKeyFrame);
        this.timelineOthers.setOnFinished(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                me.displayTesters();
            }
        });
        KeyFrame timelineTestersKeyFrame = new KeyFrame(CreditsScene.TIME_TESTERS);
        this.timelineTesters.getKeyFrames().add(timelineTestersKeyFrame);
        this.timelineTesters.setOnFinished(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                me.displayTeam();
            }
        });
        Text back = new MenuButton("Retour", 
            new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    Joop joop = me.getContext();
                    joop.showMainMenu(joop.options.getAnimations());
                    me.timelineOthers.stop();
                    me.timelineTeam.stop();
                }
            }
        ).getText();
        
        HBox boxChoices = new HBox();
        boxChoices.getChildren().add(back);
        
        StackPane root = MenuDisposer.dispose(boxChoices, this.textBox, background, MenuDisposer.INSETS_SUBMENU);
        this.setScene(new Scene(root, Joop.WINDOW_WIDTH, Joop.WINDOW_HEIGHT));
    }
}
