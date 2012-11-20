/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package audio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author
 * Karl
 */
public class Playlist{
    public static final String PLAYLIST_MUSIC_BOSS = "boss";
    public static final String PLAYLIST_MUSIC_MENU = "menu";
    public static final String PLAYLIST_MUSIC_PLAYABLE = "playable";
    public static final String PLAYLIST_MUSIC_GAMEOVER = "gameover";
    public static final String PLAYLIST_FX_RUN = "run";
    public static final String PLAYLIST_FX_CROUCH = "crouch";
    public static final String PLAYLIST_FX_JUMP = "jump";
    public static final String PLAYLIST_FX_BONUS = "bonus";
    public static final String PLAYLIST_FX_MALUS = "malus";
    
    private ArrayList<Integer> musics;
    private Random randomGet;
    
    public Playlist(){
        this.musics = new ArrayList<>();
        this.randomGet = new Random();
    }
    public void add(int id){
        musics.add(id);
    }
    public int getRandom() throws AudioException{
        if(this.hasMusic()){
            return this.musics.get(this.randomGet.nextInt(this.size()));
        }
        throw new AudioException(AudioException.EMPTY_PLAYLIST);
    }
    public int size(){
        return this.musics.size();
    }
    public boolean hasMusic(){
        return this.musics.size() >= 1;
    }
    public Iterator<Integer> iterator(){
        return this.musics.iterator();
    }
}
