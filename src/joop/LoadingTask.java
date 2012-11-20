/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joop;

import audio.Audio;
import audio.AudioException;
import database.DataBase;
import database.DataBaseException;
import game.Game;
import game.Scores;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.concurrent.Task;
import org.jdom2.JDOMException;
import player.Player;

/**
 *
 * @author
 * Karl
 */
public class LoadingTask extends Task{
    private ReadOnlyStringWrapper text = new ReadOnlyStringWrapper("Chargement...");
    private Joop context;

    public LoadingTask(Joop context){
        this.context = context;
    }

    public ReadOnlyStringProperty getText(){
        return this.text.getReadOnlyProperty();
    }

    @Override 
    public Void call() throws Exception {
        try{
            this.updateProgress(0, 100);
            this.text.setValue("Options");
            this.context.options = new Options();
            this.updateProgress(3, 100);
            
            this.updateProgress(0, 100);
            this.text.setValue("Scores");
            this.context.scores = new Scores();
            this.updateProgress(5, 100);
            
            this.context.db = new DataBase();

            this.text.setValue("Backgrounds");
            this.context.db.parseBackgrounds();//Backgrounds
            this.updateProgress(20, 100);

            this.text.setValue("Blocs");
            this.context.db.parseBlocs();//Blocs
            this.updateProgress(35, 100);

            this.text.setValue("Levels");
            this.context.db.parseLevels();//Levels - necessite les backgrounds et les blocs
            this.updateProgress(50, 100);

            this.text.setValue("Boss");
            this.context.db.parseBoss();//Boss - necessite les backgrounds
            this.updateProgress(60, 100);

            this.text.setValue("Player");
            Player player = new Player();
            this.updateProgress(65, 100);

            this.text.setValue("Musiques");
            this.context.music = new Audio(Audio.DIR_MUSIC, Audio.FORMAT_MUSIC);
            this.context.music.setVolume(this.context.options.getVolumeMusic());
            this.updateProgress(80, 100);

            this.text.setValue("Bruitages");
            this.context.fx = new Audio(Audio.DIR_FX, Audio.FORMAT_FX);
            this.context.fx.setVolume(this.context.options.getVolumeFx());
            this.updateProgress(95, 100);

            this.text.setValue("Moteur Joop");
            this.context.game = new Game(player, this.context.db);
            System.gc();
            this.updateProgress(100, 100);
        }
        catch(IOException | JDOMException | DataBaseException | AudioException ex){
            Logger.getLogger(Joop.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
    }
}
