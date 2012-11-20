/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import javafx.scene.image.Image;

/**
 *
 * @author Karl
 */
public class Bloc {
    private static final String DIR_IMAGES = "/ressources/blocs/";
    private static final String FORMAT_IMAGES = ".png";
    
    public static final int NULL_ID = 0;
    public static final int PIXEL_SIZE = 64;
    
    private final boolean traversable;
    private final int id;
    private final int score;
    private final int life;
    private final Image img;
    
    public Bloc(int id, boolean traversable, int score, int life){
        this.id = id;
        this.traversable = traversable;
        this.score = score;
        this.life = life;
        StringBuilder sb = new StringBuilder();
        sb.append(Bloc.DIR_IMAGES);
        sb.append(id);
        sb.append(Bloc.FORMAT_IMAGES);
        this.img = new Image(Bloc.class.getResource(sb.toString()).toString());
    }
    
    public Boolean isTraversable(){
        return this.traversable;
    }
    public int getId(){
        return this.id;
    }
    public int getScore(){
        return this.score;
    }
    public int getLife(){
        return this.life;
    }
    public Image getImage(){
        return this.img;
    }
}
