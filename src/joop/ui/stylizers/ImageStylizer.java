/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joop.ui.stylizers;

import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 *
 * @author Karl
 */
public abstract class ImageStylizer {
    public static void styleBossBackground(ImageView img){
        GaussianBlur gb = new GaussianBlur();
        gb.setRadius(15);
        img.setEffect(gb);
        img.setOpacity(0.2);
    }
    public static void styleMainBackground(ImageView img){
        GaussianBlur gb = new GaussianBlur();
        gb.setRadius(15);
        img.setEffect(gb);
        img.setOpacity(0.3);
    }
    public static void stylePauseBackground(ImageView img){
        GaussianBlur gb = new GaussianBlur();
        gb.setRadius(15);
        img.setEffect(gb);
        img.setOpacity(0.3);
    }
    public static void styleGameOverBackground(ImageView img){
        GaussianBlur gb = new GaussianBlur();
        gb.setRadius(15);
        img.setEffect(gb);
        img.setOpacity(0.3);
    }
    public static void styleOptionsBackground(ImageView img){
        GaussianBlur gb = new GaussianBlur();
        gb.setRadius(15);
        img.setEffect(gb);
        img.setOpacity(0.3);
    }
    public static void styleScoresBackground(ImageView img){
        GaussianBlur gb = new GaussianBlur();
        gb.setRadius(15);
        img.setEffect(gb);
        img.setOpacity(0.3);
    }
    public static void styleCreditsBackground(ImageView img){
        GaussianBlur gb = new GaussianBlur();
        gb.setRadius(1.5);
        img.setEffect(gb);
    }
    public static void stylePlayableBackground(ImageView img) {
        img.setOpacity(0.7);
    }
    public static void styleLifeBarLife(ImageView img) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setSpread(0.8);
        dropShadow.setRadius(20);
        dropShadow.setColor(Color.WHITE);
        img.setEffect(dropShadow);
    }
}
