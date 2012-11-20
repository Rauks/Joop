/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joop;

import audio.Audio;
import audio.Playlist;
import database.DataBase;
import game.Game;
import game.Scores;
import game.screens.BossScreen;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import joop.ui.*;
import joop.ui.stylizers.TextStylizer;

/**
 *
 * @author Karl
 */
public class Joop extends Application {
    public static final int VERSION = 6;
    public static final int WINDOW_WIDTH = 1024;
    public static final int WINDOW_HEIGHT = 576;
    private static final Duration FADEIN_DURATION = Duration.millis(300);
    private static final Duration FADEOUT_DURATION = Duration.millis(300);
    
    //Contexte
    public Stage stage;
    public Game game;
    public DataBase db;
    public Options options;
    public Scores scores;
    public Audio music;
    public Audio fx;
    
    //Scenes
    private PlayableScene scenePlayable;
    private BossScene sceneBoss;
    private MessageScene sceneMessage;
    private GameOverScene sceneGameOver;
    private MainMenuScene sceneMenuMain;
    private PseudoPromptScene scenePseudoPrompt;
    private CreditsScene sceneCredits;
    private OptionsScene sceneOptions;
    private ScoresScene sceneScores;
    private PauseScene scenePause;
    
    //Divers
    private boolean isPlaying;
    private FadeTransition fadeIn;
    private FadeTransition fadeOut;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void loadScenes(){
        this.scenePlayable = new PlayableScene(this);
        this.sceneBoss = new BossScene(this);
        this.sceneMessage = new MessageScene(this);
        this.sceneGameOver = new GameOverScene(this);
        this.sceneMenuMain = new MainMenuScene(this);
        this.scenePseudoPrompt = new PseudoPromptScene(this);
        this.sceneCredits = new CreditsScene(this);
        this.sceneOptions = new OptionsScene(this);
        this.sceneScores = new ScoresScene(this);
        this.scenePause = new PauseScene(this);
    }
    
    @Override
    public void start(Stage stage) {
        System.out.println();
        System.out.println("Joop - Play & Learn !");
        System.out.println("Version " + Joop.VERSION + ", Projet ENSISA 1A IR 2012");
        System.out.println();
        System.out.println("Realisation :");
        System.out.println("    Laetitia Gaignier");
        System.out.println("    Florent Lacroix");
        System.out.println("    Thibaud Renaux");
        System.out.println("    Simon Chevalier");
        System.out.println("    Karl Woditsch");
        System.out.println();
        System.out.println("Professeur referent :");
        System.out.println("    Jean-Marc Perronne");
        System.out.println();
        System.out.println("Musiques :");
        System.out.println("    Celestial Aeon Project");
        System.out.println();
        System.out.println("Remerciments :");
        System.out.println("    Nicolas Devenet");
        System.out.println("    Nacer Djenane");
        System.out.println("    Xavier Fayolle");
        System.out.println("    Benoit Gradit");
        System.out.println("    Charles Hazard");
        System.out.println("    Suzanne Noll");
        System.out.println("    Georges Olivares");

        this.stage = stage;
        this.stage.setTitle("Joop - Play & Learn");
        this.stage.setResizable(false);
        this.stage.centerOnScreen();
        
        this.isPlaying = false;
        this.fadeOut = new FadeTransition(Joop.FADEOUT_DURATION);
        this.fadeIn = new FadeTransition(Joop.FADEIN_DURATION);
        
        final ProgressBar loading = new ProgressBar();
        loading.setPrefWidth(400);
        final Text text = new Text("Chargement");
        TextStylizer.styleLoadingTitle(text);
        
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(text);
        box.getChildren().add(loading);
        
        StackPane root = new StackPane();
        root.setMaxHeight(Joop.WINDOW_HEIGHT);
        root.setMaxWidth(Joop.WINDOW_WIDTH);
        root.getChildren().add(box);
        
        this.stage.setScene(new Scene(root, Joop.WINDOW_WIDTH, Joop.WINDOW_HEIGHT));
        this.stage.show();
        
        final LoadingTask task = new LoadingTask(this);
        loading.progressProperty().bind(task.progressProperty());
        text.textProperty().bind(task.getText());
        
        final Joop joop = this;
        new Thread(task).start();
        task.stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends State> observable, State oldValue, Worker.State newState) {
                if(newState == Worker.State.SUCCEEDED){
                    task.cancel();
                    joop.loadScenes();
                    joop.showPseudoPrompt(joop.options.getAnimations());
                }
                else if(newState == Worker.State.FAILED){
                    task.cancel();
                    text.textProperty().unbind();
                    text.setText("Erreur de chargement");
                    TextStylizer.styleLoadingErrorTitle(text);
                    loading.progressProperty().unbind();
                    loading.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
                }
            }
        });
    }
    
    public void displayScene(Scene s){
        if(this.stage.getScene() != s){
            this.stage.setScene(s);
        }
    }
    public void sceneFadeIn(EventHandler e){
        this.fadeIn.setNode(this.stage.getScene().getRoot());
        this.fadeIn.setFromValue(0.0);
        this.fadeIn.setToValue(1.0);
        this.fadeIn.setOnFinished(e);
        this.fadeIn.play();
    }
    public void sceneFadeIn(){
        this.fadeIn.setNode(this.stage.getScene().getRoot());
        this.fadeIn.setFromValue(0.0);
        this.fadeIn.setToValue(1.0);
        this.fadeIn.setOnFinished(null);
        this.fadeIn.play();
    }
    public void sceneFadeOut(EventHandler e){
        this.fadeOut.setNode(this.stage.getScene().getRoot());
        this.fadeOut.setFromValue(1.0);
        this.fadeOut.setToValue(0.0);
        this.fadeOut.setOnFinished(e);
        this.fadeOut.play();
    }
    public void sceneeFadeOut(){
        this.fadeOut.setNode(this.stage.getScene().getRoot());
        this.fadeOut.setFromValue(1.0);
        this.fadeOut.setToValue(0.0);
        this.fadeOut.setOnFinished(null);
        this.fadeOut.play();
        
    }
    public void ajustAudioVolume(){
        final Joop me = this;
        Thread ajust = new Thread(){
            @Override
            public void run() {
                me.music.setVolume(me.options.getVolumeMusic());
                me.fx.setVolume(me.options.getVolumeFx());
            }
        };
        ajust.start();
    }
    
    public void showGameStart(boolean animation){
        this.isPlaying = true;
        this.music.stopAll();
        this.scenePlayable.refreshPlayerPosition();
        this.sceneMessage.overlay.refresh();
        final Joop joop = this;
        this.sceneMessage.timeline.setOnFinished(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                joop.showLevelStart(false);
            }
        });
        this.sceneMessage.timeline.play();
        
        if(animation){
            this.sceneMessage.getScene().getRoot().setOpacity(0.0);
            this.sceneFadeOut(new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    joop.sceneMessage.message.setText("Bienvenue dans le monde de Joop...");
                    joop.displayScene(joop.sceneMessage.getScene());
                    joop.sceneFadeIn();
                }
            });
        }
        else{
            this.sceneMessage.getScene().getRoot().setOpacity(1.0);
            this.sceneMessage.message.setText("Bienvenue dans le monde de Joop...");
            this.displayScene(this.sceneMessage.getScene());
        }
        
    }
    public void showPlayableScreen(boolean animation){
        this.scenePlayable.refreshPlayableGrid();
        this.scenePlayable.overlay.refresh();
        this.scenePlayable.refreshPlayerPosition();
        this.scenePlayable.refreshPlayerImage();
        
        if(animation){
            this.scenePlayable.getScene().getRoot().setOpacity(0.0);
            final Joop joop = this;
            this.sceneFadeOut(new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    joop.displayScene(joop.scenePlayable.getScene());
                    joop.sceneFadeIn();
                }
            });
        }
        else{
            this.scenePlayable.getScene().getRoot().setOpacity(1.0);
            this.displayScene(this.scenePlayable.getScene());
        }
    }
    public void showBossScreen(boolean animation){
        this.sceneBoss.setBoss((BossScreen) this.game.getCurrentScreen());
        
        if(animation){
            this.sceneBoss.getScene().getRoot().setOpacity(0.0);
            final Joop joop = this;
            this.sceneFadeOut(new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    joop.displayScene(joop.sceneBoss.getScene());
                    joop.sceneFadeIn();
                }
            });
        }
        else{
            this.sceneBoss.getScene().getRoot().setOpacity(1.0);
            this.displayScene(this.sceneBoss.getScene());
        }
    }
    public void showBossAlert(boolean animation){
        this.music.stopAll();
        this.music.playLoopRandomFromPlaylist(Playlist.PLAYLIST_MUSIC_BOSS);
        this.sceneMessage.overlay.refresh();
        final Joop joop = this;
        this.sceneMessage.timeline.setOnFinished(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                joop.showBossScreen(false);
            }
        });
        this.sceneMessage.timeline.play();
        
        if(animation){
            this.sceneMessage.getScene().getRoot().setOpacity(0.0);
            this.sceneFadeOut(new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    joop.sceneMessage.message.setText("Un boss approche !");
                    joop.displayScene(joop.sceneMessage.getScene());
                    joop.sceneFadeIn();
                }
            }); 
        }
        else{
            this.sceneMessage.getScene().getRoot().setOpacity(1.0);
            this.sceneMessage.message.setText("Un boss approche !");
            this.displayScene(this.sceneMessage.getScene());
        }
    }
    public void showLevelStart(boolean animation){
        this.music.stopAll();
        this.music.playLoopRandomFromPlaylist(Playlist.PLAYLIST_MUSIC_PLAYABLE);
        this.scenePlayable.refreshPlayerPosition();
        this.sceneMessage.overlay.refresh();
        final Joop joop = this;
        this.sceneMessage.timeline.setOnFinished(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                joop.showPlayableScreen(false);
            }
        });
        this.sceneMessage.timeline.play();
        
        if(animation){
            this.sceneMessage.getScene().getRoot().setOpacity(0.0);
            this.sceneFadeOut(new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    joop.sceneMessage.message.setText(joop.game.getCurrentLevelName());
                    joop.displayScene(joop.sceneMessage.getScene());
                    joop.sceneFadeIn();
                }
            });    
        }
        else{
            this.sceneMessage.getScene().getRoot().setOpacity(1.0);
            this.sceneMessage.message.setText(this.game.getCurrentLevelName());
            this.displayScene(this.sceneMessage.getScene());
        }
    }
    public void showCorrectAnswer(boolean animation){
        this.fx.playRandomFromPlaylist(Playlist.PLAYLIST_FX_BONUS);
        this.sceneMessage.overlay.refresh();
        final Joop joop = this;
        this.sceneMessage.timeline.setOnFinished(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                joop.showLevelStart(false);
            }
        });
        this.sceneMessage.timeline.play();
        
        if(animation){
            this.sceneMessage.getScene().getRoot().setOpacity(0.0);
            this.sceneFadeOut(new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    joop.sceneMessage.message.setText("Réponse correcte !");
                    joop.displayScene(joop.sceneMessage.getScene());
                    joop.sceneFadeIn();
                }
            });            
        }
        else{
            this.sceneMessage.getScene().getRoot().setOpacity(1.0);
            this.sceneMessage.message.setText("Réponse correcte !");
            this.displayScene(this.sceneMessage.getScene());
        }
    }
    public void showWrongAnswer(boolean animation){
        this.fx.playRandomFromPlaylist(Playlist.PLAYLIST_FX_MALUS);
        this.sceneMessage.overlay.refresh();
        final Joop joop = this;
        this.sceneMessage.timeline.setOnFinished(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                if(joop.game.isOver()){
                    joop.showGameOverMenu(joop.options.getAnimations());
                }
                else{
                    joop.showBossScreen(false);
                }
            }
        });
        this.sceneMessage.timeline.play();
        
        if(animation){
            this.sceneMessage.getScene().getRoot().setOpacity(0.0);
            this.sceneFadeOut(new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    joop.sceneMessage.message.setText("Réponse incorrecte !");
                    joop.displayScene(joop.sceneMessage.getScene());
                    joop.sceneFadeIn();
                }
            });
        }
        else{
            this.sceneMessage.getScene().getRoot().setOpacity(1.0);
            this.sceneMessage.message.setText("Réponse incorrecte !");
            this.displayScene(this.sceneMessage.getScene());
        } 
    } 
    public void showToLateAnswer(boolean animation){
        this.fx.playRandomFromPlaylist(Playlist.PLAYLIST_FX_MALUS);
        this.sceneMessage.overlay.refresh();
        final Joop joop = this;
        this.sceneMessage.timeline.setOnFinished(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                if(joop.game.isOver()){
                    joop.showGameOverMenu(joop.options.getAnimations());
                }
                else{
                    joop.showBossScreen(false);
                }
            }
        });
        this.sceneMessage.timeline.play();
        
        if(animation){
            this.sceneMessage.getScene().getRoot().setOpacity(0.0);
            this.sceneFadeOut(new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    joop.sceneMessage.message.setText("Trop tard !");
                    joop.displayScene(joop.sceneMessage.getScene());
                    joop.sceneFadeIn();
                }
            });
        }
        else{
            this.sceneMessage.getScene().getRoot().setOpacity(1.0);
            this.sceneMessage.message.setText("Trop tard !");
            this.displayScene(this.sceneMessage.getScene());
        } 
    } 
    public void showGameOverMenu(boolean animation){
        this.isPlaying = false;
        if(!this.options.getPseudo().isEmpty()){
            /*
            ScoreSender sd = new ScoreSender(this.options.getPseudo(), this.game.getPlayerScore());
            sd.start();
            */
            this.scores.addScore(this.options.getPseudo(), this.game.getPlayerScore());
        }
        this.music.stopAll();
        this.music.playLoopRandomFromPlaylist(Playlist.PLAYLIST_MUSIC_GAMEOVER);
        StringBuilder sb = new StringBuilder();
        sb.append("Score : ");
        sb.append(String.valueOf(this.game.getPlayerScore()));
        this.sceneGameOver.score.setText(sb.toString());
        
        if(animation){
            this.sceneGameOver.getScene().getRoot().setOpacity(0.0);
            final Joop joop = this;
            this.sceneFadeOut(new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    joop.displayScene(joop.sceneGameOver.getScene());
                    joop.sceneFadeIn();
                }
            });
        }
        else{
            this.sceneGameOver.getScene().getRoot().setOpacity(1.0);
            this.displayScene(this.sceneGameOver.getScene());
        }
    }
    public void showMainMenu(boolean animation){
        if(animation){
            this.sceneMenuMain.getScene().getRoot().setOpacity(0.0);
            final Joop joop = this;
            this.sceneFadeOut(new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    joop.displayScene(joop.sceneMenuMain.getScene());
                    joop.sceneFadeIn();
                }
            });
        }
        else{
            this.sceneMenuMain.getScene().getRoot().setOpacity(1.0);
            this.displayScene(this.sceneMenuMain.getScene());
        }
    }
    public void showPseudoPrompt(boolean animation){
        if(animation){
            this.scenePseudoPrompt.getScene().getRoot().setOpacity(0.0);
            final Joop joop = this;
            this.sceneFadeOut(new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    joop.displayScene(joop.scenePseudoPrompt.getScene());
                    joop.sceneFadeIn();
                }
            });
        }
        else{
            this.scenePseudoPrompt.getScene().getRoot().setOpacity(1.0);
            this.displayScene(this.scenePseudoPrompt.getScene());
        }
    }
    public void showCredits(boolean animation){
        this.sceneCredits.displayTeam();
        
        if(animation){
            this.sceneCredits.getScene().getRoot().setOpacity(0.0);
            final Joop joop = this;
            this.sceneFadeOut(new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    joop.displayScene(joop.sceneCredits.getScene());
                    joop.sceneFadeIn();
                }
            });
        }
        else{
            this.sceneCredits.getScene().getRoot().setOpacity(1.0);
            this.displayScene(this.sceneCredits.getScene());
        }
    }
    public void showQuit(){
        this.stage.close();
    }
    public void showOptions(boolean animation) {
        this.sceneOptions.refresh();
        if(animation){
            this.sceneOptions.getScene().getRoot().setOpacity(0.0);
            final Joop joop = this;
            this.sceneFadeOut(new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    joop.displayScene(joop.sceneOptions.getScene());
                    joop.sceneFadeIn();
                }
            });
        }
        else{
            this.sceneOptions.getScene().getRoot().setOpacity(1.0);
            this.displayScene(this.sceneOptions.getScene());
        }
    }
    public void showScores(boolean animation){
        this.sceneScores.refresh();
        if(animation){
            this.sceneScores.getScene().getRoot().setOpacity(0.0);
            final Joop joop = this;
            this.sceneFadeOut(new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    joop.displayScene(joop.sceneScores.getScene());
                    joop.sceneFadeIn();
                }
            });
        }
        else{
            this.sceneScores.getScene().getRoot().setOpacity(1.0);
            this.displayScene(this.sceneScores.getScene());
        }
    }
    public void showPause(boolean animation) {
        
        if(animation){
            this.scenePause.getScene().getRoot().setOpacity(0.0);
            final Joop joop = this;
            this.sceneFadeOut(new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    joop.displayScene(joop.scenePause.getScene());
                    joop.sceneFadeIn();
                }
            });
        }
        else{
            this.scenePause.getScene().getRoot().setOpacity(1.0);
            this.displayScene(this.scenePause.getScene());
        }
    }
    public boolean isPLaying(){
        return this.isPlaying;
    }
}
