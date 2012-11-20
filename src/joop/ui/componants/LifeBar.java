/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joop.ui.componants;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import joop.Joop;
import joop.ui.stylizers.ImageStylizer;

/**
 *
 * @author
 * Karl
 */
public class LifeBar {
    public static final String DIR_IMAGES = "/ressources/overlay/life/";
    public static final String FORMAT_IMAGES = ".png";
    
    private ArrayList<Image> emptyHeart;
    private ArrayList<Image> fullHeart;
    private ArrayList<ImageView> viewHeart;
    private GridPane bar;
    
    public LifeBar(){
        this.bar = new GridPane();
        this.bar.setPadding(new Insets(20, 0, 0, 0));
        this.emptyHeart = new ArrayList<>(5);
        this.fullHeart = new ArrayList<>(5);
        this.viewHeart = new ArrayList<>(5);
        String adr;
        for(int i = 0; i < 5; i++){
            adr = Joop.class.getResource(LifeBar.DIR_IMAGES + (i+1) + "f" + LifeBar.FORMAT_IMAGES).toString();
            this.emptyHeart.add(new Image(adr));
        }
        for(int i = 0; i < 5; i++){
            adr = Joop.class.getResource(LifeBar.DIR_IMAGES + (i+1) + "t" + LifeBar.FORMAT_IMAGES).toString();
            this.fullHeart.add(new Image(adr));
        }
        ImageView view;
        for(int i = 0; i < 5; i++){
            view = new ImageView();
            ImageStylizer.styleLifeBarLife(view);
            this.viewHeart.add(view);
            this.bar.add(view, i, 0);
        }
    }
    public void setLife(int life){
        for(int i = 0; i < (5 - life); i++){
            this.viewHeart.get(i).setImage(this.emptyHeart.get(i));
        }
        for(int i = (5 - life); i < 5; i++){
            this.viewHeart.get(i).setImage(this.fullHeart.get(i));
        }
    }
    
    public GridPane getBar(){
        return this.bar;
    }
}
