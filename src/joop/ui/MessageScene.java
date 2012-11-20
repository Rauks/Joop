/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joop.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import joop.Joop;
import joop.ui.componants.Overlay;
import joop.ui.stylizers.TextStylizer;

/**
 *
 * @author
 * Karl
 */
public final class MessageScene extends JoopScene{
    public Timeline timeline;
    public Overlay overlay;
    public Text message;
    
    public MessageScene(Joop context){
        super(context);
        this.timeline = new Timeline();
        KeyFrame timelineKeyFrame = new KeyFrame(Duration.seconds(3));
        this.timeline.getKeyFrames().add(timelineKeyFrame);
        this.overlay = new Overlay(this.getContext().game);
        this.message = new Text();
        TextStylizer.styleMessageTitle(this.message);
        
        StackPane root = new StackPane();
        root.setMaxSize(Joop.WINDOW_WIDTH, Joop.WINDOW_HEIGHT);
        root.getChildren().add(this.overlay.getRootPane());
        StackPane.setAlignment(this.overlay.getRootPane(), Pos.CENTER);
        root.getChildren().add(this.message);
        StackPane.setAlignment(this.message, Pos.CENTER);
        this.setScene(new Scene(root, Joop.WINDOW_WIDTH, Joop.WINDOW_HEIGHT));
    }
}
