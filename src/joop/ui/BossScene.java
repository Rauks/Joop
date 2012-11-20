/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joop.ui;

import game.questions.Answer;
import game.screens.BossScreen;
import java.util.Iterator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import joop.Joop;
import joop.ui.componants.Overlay;
import joop.ui.stylizers.ImageStylizer;
import joop.ui.stylizers.TextStylizer;

/**
 *
 * @author
 * Karl
 */
public final class BossScene extends JoopScene{
    public static final int TIMER = 20;
    
    public VBox answers;
    public ImageView background;
    public Text question;
    public Overlay overlay;
    public ProgressBar timerBar;
    
    public BossScene(Joop context){
        super(context);
        this.background = new ImageView();
        ImageStylizer.styleBossBackground(this.background);
        
        this.overlay = new Overlay(this.getContext().game);
        this.question = new Text();
        TextStylizer.styleBossQuestion(this.question);
        this.answers = new VBox();
        this.answers.setAlignment(Pos.CENTER);
        
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(this.question);
        box.getChildren().add(this.answers);
        
        this.timerBar = new ProgressBar();
        this.timerBar.setPrefWidth(400);
        this.timerBar.setTranslateY(-20);
        
        StackPane root = new StackPane();
        root.setMaxSize(Joop.WINDOW_WIDTH, Joop.WINDOW_HEIGHT);
        root.getChildren().add(this.background);
        StackPane.setAlignment(this.background, Pos.CENTER);
        root.getChildren().add(this.overlay.getRootPane());
        StackPane.setAlignment(this.overlay.getRootPane(), Pos.CENTER);
        root.getChildren().add(timerBar);
        StackPane.setAlignment(timerBar, Pos.BOTTOM_CENTER);
        root.getChildren().add(box);
        StackPane.setAlignment(box, Pos.CENTER);
        this.setScene(new Scene(root, Joop.WINDOW_WIDTH, Joop.WINDOW_HEIGHT));
        
        /*
        this.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                KeyCode key = ke.getCode();
                if(key.equals(joop.options.getKeyPause())){
                    joop.showPause(joop.options.getAnimations());
                }
            }
        });
        */
    }
    public void setBoss(BossScreen screen){
        this.question.setText(screen.getQuestion().getStr());
        this.background.setImage(screen.getBackground().getImage());
        this.overlay.refresh();
        this.answers.getChildren().removeAll(this.answers.getChildren());
        
        final Task timer = new Task(){
            @Override 
            public synchronized Void call() throws InterruptedException{
                int totalTime = BossScene.TIMER * 100;
                for(int time = 0; time < totalTime; time++){
                    this.updateProgress(time, totalTime);
                    this.wait(10);
                }
                return null;
            }
        };
        timerBar.progressProperty().bind(timer.progressProperty());
        
        final Joop joop = this.getContext();
        timer.stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newState) {
                if(newState == Worker.State.SUCCEEDED){
                    joop.game.answerWrong();
                    joop.showToLateAnswer(false);
                }
            }
        });
        
        screen.getQuestion().shuffleAnswers();
        for(Iterator<Answer> it = screen.getQuestion().iterator(); it.hasNext();){
            Answer answer = it.next();
            Text answerText = new Text(answer.getStr());
            TextStylizer.styleBossAnswer(answerText);
            
            answerText.setOnMouseEntered(new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    Text me = (Text) arg0.getSource();
                    TextStylizer.styleBossAnswerHover(me);
                }
            });
            answerText.setOnMouseExited(new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    Text me = (Text) arg0.getSource();
                    TextStylizer.styleBossAnswer(me);
                }
            });
            
            if(answer.isValid()){
                answerText.setOnMouseClicked(new EventHandler() {
                    @Override
                    public void handle(Event arg0) {
                        timer.cancel();
                        joop.game.answerCorrect();
                        joop.showCorrectAnswer(false);
                    }
                });
            }
            else{
                answerText.setOnMouseClicked(new EventHandler() {
                    @Override
                    public void handle(Event arg0) {
                        timer.cancel();
                        joop.game.answerWrong();
                        joop.showWrongAnswer(false);
                    }
                });
            }
            this.answers.getChildren().add(answerText);
        }
        
        new Thread(timer).start();
    }
}
