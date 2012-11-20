/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package audio;

import database.DataBase;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.AudioClip;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author
 * Karl
 */
public class Audio {
    private static final String FILE_XML_PLAYLIST_MUSIC = "playlist.xml";
    private static final String XML_PLAYLIST = "playlist";
    private static final String XML_PLAYLIST_NAME = "name";
    private static final String XML_PLAYLIST_MEDIA = "media";
    
    public static final String DIR_MUSIC = "/ressources/audio/music/";
    public static final String DIR_FX = "/ressources/audio/fx/";
    public static final String FORMAT_MUSIC = ".mp3";
    public static final String FORMAT_FX = ".wav";
    
    private ArrayList<AudioClip> musics;
    private HashMap<String, Playlist> playlists;
    private SAXBuilder sxb;
    private Random randomPlay;
    
    private void parseAudio(String dir, String format){
        URL fileUrl;
        AudioClip audio;
        int i = 0;
        while((fileUrl = Playlist.class.getResource(dir + i + format)) != null){
            audio = new AudioClip(fileUrl.toString());
            this.musics.add(audio);
            i++;
        }
    }
    private void parsePlaylists(String dir) throws JDOMException, IOException{
        Element currentPlaylist;
        Element currentMedia;
        String playlistName;
        Document xmlPlaylists = this.sxb.build(DataBase.class.getResource(dir + Audio.FILE_XML_PLAYLIST_MUSIC));
        Element rootPlaylists = xmlPlaylists.getRootElement();
        for(Iterator<Element> itPlaylist = rootPlaylists.getChildren(Audio.XML_PLAYLIST).iterator(); itPlaylist.hasNext();){
            currentPlaylist = itPlaylist.next();
            playlistName = currentPlaylist.getChild(Audio.XML_PLAYLIST_NAME).getText();
            Playlist playlist = new Playlist();
            for(Iterator<Element> itMedia = currentPlaylist.getChildren(Audio.XML_PLAYLIST_MEDIA).iterator(); itMedia.hasNext();){
                currentMedia = itMedia.next();
                playlist.add(Integer.parseInt(currentMedia.getText()));
            }
            this.playlists.put(playlistName, playlist);
        }
    }
    
    synchronized public void setVolume(double arg0){
        AudioClip current;
        for(Iterator<AudioClip> it = this.musics.iterator(); it.hasNext();){
            current = it.next();
            current.setVolume(arg0);
            if(current.isPlaying()){
                current.stop();
                current.play();
            }
        }
    }
    public Audio(String dir, String format) throws JDOMException, IOException, AudioException{
        this.musics = new ArrayList<>();
        this.playlists = new HashMap<>();
        this.parseAudio(dir, format);
        this.sxb = new SAXBuilder();
        this.parsePlaylists(dir);
        if(this.musics.size() < 1){
            throw new AudioException(AudioException.EMPTY_AUDIO_ENGINE);
        }
        this.randomPlay = new Random();
    }
    public int size(){
        return this.musics.size();
    }
    public void play(int i){
        AudioClip audio = this.musics.get(i);
        audio.setCycleCount(1);
        audio.play();
    }
    public void playLoop(int i){
        AudioClip audio = this.musics.get(i);
        audio.setCycleCount(AudioClip.INDEFINITE);
        audio.play();
    }
    public void playRandom(){
        this.play(this.randomPlay.nextInt(this.size()));
    }
    public void playLoopRandom(){
        this.playLoop(this.randomPlay.nextInt(this.size()));
    }
    public void playRandomFromPlaylist(String playlist){
        try {
            this.play(this.playlists.get(playlist).getRandom());
        } catch (AudioException ex) {
            Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void playLoopRandomFromPlaylist(String playlist){
        try {
            this.playLoop(this.playlists.get(playlist).getRandom());
        } catch (AudioException ex) {
            Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void stop(int i){
        this.musics.get(i).stop();
    }
    public void stopAll(){
        for(Iterator<AudioClip> it = this.musics.iterator(); it.hasNext();){
            it.next().stop();
        }
    }
    public void stopPlaylist(String playlist){
        for(Iterator<Integer> it = this.playlists.get(playlist).iterator(); it.hasNext();){
            this.musics.get(it.next().intValue()).stop();
        }
    }
}
