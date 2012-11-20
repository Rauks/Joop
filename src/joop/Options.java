/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Karl
 */
public class Options {
    private static final String XML_FILE = "options.opt";
    private static final String XML_ROOT = "root";
    private static final String XML_ROOT_AUDIO = "audio";
    private static final String XML_ROOT_AUDIO_MUSIC = "music";
    private static final String XML_ROOT_AUDIO_FX = "fx";
    private static final String XML_ROOT_GRAPH = "graph";
    private static final String XML_ROOT_GRAPH_ANIMATIONS = "animations";
    private static final String XML_ROOT_KEY = "key";
    private static final String XML_ROOT_KEY_UP = "up";
    private static final String XML_ROOT_KEY_DOWN = "down";
    private static final String XML_ROOT_KEY_LEFT = "left";
    private static final String XML_ROOT_KEY_RIGHT = "right";
    private static final String XML_ROOT_KEY_PAUSE = "pause";
    private static final String XML_ROOT_PLAYER = "player";
    private static final String XML_ROOT_PLAYER_PSEUDO = "pseudo";
    
    public static final double DEFAUT_VOLUME_MUSIC = 1.0;
    public static final double DEFAUT_VOLUME_FX = 0.4;
    public static final boolean DEFAUT_GRAPH_ANIMATIONS = true;
    public static final KeyCode DEFAUT_KEY_UP = KeyCode.UP;
    public static final KeyCode DEFAUT_KEY_DOWN = KeyCode.DOWN;
    public static final KeyCode DEFAUT_KEY_LEFT = KeyCode.LEFT;
    public static final KeyCode DEFAUT_KEY_RIGHT = KeyCode.RIGHT;
    public static final KeyCode DEFAUT_KEY_PAUSE = KeyCode.ESCAPE;
    public static final String DEFAUT_PSEUDO = "";
    
    private double volumeMusic;
    private double volumeFx;
    private boolean animations;
    private KeyCode keyUp;
    private KeyCode keyDown;
    private KeyCode keyLeft;
    private KeyCode keyRight;
    private KeyCode keyPause;
    private String pseudo;
    
    private SAXBuilder sxb;
    
    public Options() throws IOException, JDOMException{
        this.sxb = new SAXBuilder();
        this.loadOptions();
    }
    synchronized public void saveXml(){
        final Options me = this;
        Thread saver = new Thread(){
            @Override
            public void run(){
                try {
                    Element root = new Element(Options.XML_ROOT);
                    Element audio = new Element(Options.XML_ROOT_AUDIO);
                    Element graph = new Element(Options.XML_ROOT_GRAPH);
                    Element key = new Element(Options.XML_ROOT_KEY);
                    Element player = new Element(Options.XML_ROOT_PLAYER);

                    root.addContent(audio);
                    root.addContent(graph);
                    root.addContent(key);
                    root.addContent(player);

                    Element fx = new Element(Options.XML_ROOT_AUDIO_FX);
                    fx.setText(String.valueOf(me.volumeFx));
                    audio.addContent(fx);
                    Element music = new Element(Options.XML_ROOT_AUDIO_MUSIC);
                    music.setText(String.valueOf(me.volumeMusic));
                    audio.addContent(music);

                    Element anim = new Element(Options.XML_ROOT_GRAPH_ANIMATIONS);
                    anim.setText(String.valueOf(me.animations));
                    graph.addContent(anim);
                    
                    Element up = new Element(Options.XML_ROOT_KEY_UP);
                    up.setText(me.keyUp.getName());
                    key.addContent(up);
                    Element down = new Element(Options.XML_ROOT_KEY_DOWN);
                    down.setText(me.keyDown.getName());
                    key.addContent(down);
                    Element left = new Element(Options.XML_ROOT_KEY_LEFT);
                    left.setText(me.keyLeft.getName());
                    key.addContent(left);
                    Element right = new Element(Options.XML_ROOT_KEY_RIGHT);
                    right.setText(me.keyRight.getName());
                    key.addContent(right);
                    Element pause = new Element(Options.XML_ROOT_KEY_PAUSE);
                    pause.setText(me.keyPause.getName());
                    key.addContent(pause);
                    
                    Element pseudo = new Element(Options.XML_ROOT_PLAYER_PSEUDO);
                    pseudo.setText(me.pseudo);
                    player.addContent(pseudo);

                    Document doc = new Document(root);

                    XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
                    sortie.output(doc, new FileOutputStream(Options.XML_FILE));
                } 
                catch (IOException ex) {
                    Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        saver.start();
    }
    synchronized private void loadOptions() throws IOException, JDOMException{
        File xml = new File(Options.XML_FILE);
        if(xml.exists()){
            Document xmlDoc = this.sxb.build(xml);
            Element root = xmlDoc.getRootElement();
            
            Element audio = root.getChild(Options.XML_ROOT_AUDIO);
            this.volumeMusic = Double.parseDouble(audio.getChildText(Options.XML_ROOT_AUDIO_MUSIC));
            this.volumeFx = Double.parseDouble(audio.getChildText(Options.XML_ROOT_AUDIO_FX));

            Element graph = root.getChild(Options.XML_ROOT_GRAPH);
            this.animations = Boolean.parseBoolean(graph.getChildText(Options.XML_ROOT_GRAPH_ANIMATIONS));
            
            Element key = root.getChild(Options.XML_ROOT_KEY);
            this.keyUp = KeyCode.getKeyCode(key.getChildText(Options.XML_ROOT_KEY_UP));
            this.keyDown = KeyCode.getKeyCode(key.getChildText(Options.XML_ROOT_KEY_DOWN));
            this.keyLeft = KeyCode.getKeyCode(key.getChildText(Options.XML_ROOT_KEY_LEFT));
            this.keyRight = KeyCode.getKeyCode(key.getChildText(Options.XML_ROOT_KEY_RIGHT));
            this.keyPause = KeyCode.getKeyCode(key.getChildText(Options.XML_ROOT_KEY_PAUSE));
            
            Element player = root.getChild(Options.XML_ROOT_PLAYER);
            this.pseudo = player.getChildText(Options.XML_ROOT_PLAYER_PSEUDO);
        }
        else{
            this.loadDefaut();
            this.saveXml();
        }
    }
    public void loadDefaut(){
        this.volumeMusic = Options.DEFAUT_VOLUME_MUSIC;
        this.volumeFx  = Options.DEFAUT_VOLUME_FX;
        this.animations = Options.DEFAUT_GRAPH_ANIMATIONS;
        this.keyUp = Options.DEFAUT_KEY_UP;
        this.keyDown = Options.DEFAUT_KEY_DOWN;
        this.keyLeft = Options.DEFAUT_KEY_LEFT;
        this.keyRight = Options.DEFAUT_KEY_RIGHT;
        this.keyPause = Options.DEFAUT_KEY_PAUSE;
        this.pseudo = Options.DEFAUT_PSEUDO;
    }
    public KeyCode getKeyDown() {
        return this.keyDown;
    }
    public KeyCode getKeyLeft() {
        return this.keyLeft;
    }
    public KeyCode getKeyPause() {
        return this.keyPause;
    }
    public KeyCode getKeyRight() {
        return this.keyRight;
    }
    public KeyCode getKeyUp() {
        return this.keyUp;
    }
    public double getVolumeFx() {
        return this.volumeFx;
    }
    public double getVolumeMusic() {
        return this.volumeMusic;
    }
    public boolean getAnimations() {
        return this.animations;
    }
    public String getPseudo() {
        return this.pseudo;
    }
    public void setKeyDown(KeyCode keyDown) {
        this.keyDown = keyDown;
    }
    public void setKeyLeft(KeyCode keyLeft) {
        this.keyLeft = keyLeft;
    }
    public void setKeyPause(KeyCode keyPause) {
        this.keyPause = keyPause;
    }
    public void setKeyRight(KeyCode keyRight) {
        this.keyRight = keyRight;
    }
    public void setKeyUp(KeyCode keyUp) {
        this.keyUp = keyUp;
    }
    public void setVolumeFx(double volumeFx) {
        this.volumeFx = volumeFx;
    }
    public void setVolumeMusic(double volumeMusic) {
        this.volumeMusic = volumeMusic;
    }
    public void setAnimations(boolean animations) {
        this.animations = animations;
    }
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
