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
public class Background {
    private static final String DIR_IMAGES = "/ressources/backgrounds/";
    private static final String FORMAT_IMAGES = ".png";
    
    private final int id;
    private final Image img;
    
    public Background(int id){
        this.id = id;
        StringBuilder sb = new StringBuilder();
        sb.append(Background.DIR_IMAGES);
        sb.append(id);
        sb.append(Background.FORMAT_IMAGES);
        this.img = new Image(Background.class.getResource(sb.toString()).toString());
    }
    
    public Image getImage(){
        return this.img;
    }
    public int getId(){
        return this.id;
    }
}
