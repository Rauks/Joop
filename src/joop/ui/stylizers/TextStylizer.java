/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joop.ui.stylizers;

import javafx.scene.Cursor;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Karl
 */
public abstract class TextStylizer {
    //Fontes
    public static final Font FONT_DUSTIN = Font.loadFont(TextStylizer.class.getResourceAsStream("/ressources/fonts/dustin.ttf"), 40);
    public static final Font FONT_DUSTIN_BIG = Font.loadFont(TextStylizer.class.getResourceAsStream("/ressources/fonts/dustin.ttf"), 130);
    public static final Font FONT_DUSTIN_SMALL = Font.loadFont(TextStylizer.class.getResourceAsStream("/ressources/fonts/dustin.ttf"), 25);
    public static final Font FONT_CANDARA = Font.loadFont(TextStylizer.class.getResourceAsStream("/ressources/fonts/candara.ttf"), 18);
    public static final Font FONT_CANDARA_BOLD = Font.loadFont(TextStylizer.class.getResourceAsStream("/ressources/fonts/candaraBold.ttf"), 18);
    
    public static void styleOptionTitle(Text txt){
        txt.setTextAlignment(TextAlignment.LEFT);
        txt.setFont(TextStylizer.FONT_DUSTIN);
        txt.setFill(Color.GRAY);
        txt.setStrokeWidth(1);
        txt.setStroke(Color.GRAY);
    }
    public static void styleOptionLabel(Text txt){
        txt.setTextAlignment(TextAlignment.LEFT);
        txt.setFont(TextStylizer.FONT_DUSTIN_SMALL);
        txt.setFill(Color.BLACK);
        txt.setStrokeWidth(1);
        txt.setStroke(Color.BLACK);
    }public static void styleScoresScore(Text txt){
        txt.setTextAlignment(TextAlignment.LEFT);
        txt.setFont(TextStylizer.FONT_DUSTIN);
        txt.setFill(Color.BLACK);
        txt.setStrokeWidth(1);
        txt.setStroke(Color.BLACK);
    }
    public static void styleScoresPseudo(Text txt){
        txt.setTextAlignment(TextAlignment.LEFT);
        txt.setFont(TextStylizer.FONT_DUSTIN);
        txt.setFill(Color.GRAY);
        txt.setStrokeWidth(1);
        txt.setStroke(Color.GRAY);
    }
    public static void styleLoadingTitle(Text txt){
        txt.setTextAlignment(TextAlignment.LEFT);
        txt.setFont(TextStylizer.FONT_CANDARA_BOLD);
    }
    public static void styleLoadingErrorTitle(Text txt){
        txt.setFill(Color.web("#CC0000"));
    }
    public static void styleOverlayScore(Text txt){
        txt.setFont(TextStylizer.FONT_DUSTIN);
        txt.setStrokeWidth(1);
        txt.setStroke(Color.BLACK);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setSpread(0.8);
        dropShadow.setRadius(20);
        dropShadow.setColor(Color.WHITE);
        txt.setEffect(dropShadow);
    }
    public static void styleBossQuestion(Text txt){
        txt.setFill(Color.web("#CC0000"));
        txt.setFont(TextStylizer.FONT_DUSTIN);
        txt.setStroke(Color.web("#CC0000"));
        txt.setTextAlignment(TextAlignment.CENTER);
        txt.setWrappingWidth(900);
    }
    public static void styleBossAnswer(Text txt){
        txt.setFont(TextStylizer.FONT_DUSTIN);
        txt.setFill(Color.GRAY);
        txt.setStrokeWidth(1);
        txt.setStroke(Color.GRAY);
        txt.setCursor(Cursor.HAND);
        txt.setTextAlignment(TextAlignment.CENTER);
        txt.setWrappingWidth(900);
    }
    public static void styleBossAnswerHover(Text txt) {
        txt.setFill(Color.BLACK);
        txt.setStroke(Color.BLACK);
    }
    public static void styleCreditsTitle(Text txt){
        txt.setFont(TextStylizer.FONT_DUSTIN_SMALL);
        txt.setFill(Color.GRAY);
        txt.setStrokeWidth(1);
        txt.setStroke(Color.GRAY);
        txt.setTextAlignment(TextAlignment.CENTER);
    }
    public static void styleCreditsName(Text txt){
        txt.setFont(TextStylizer.FONT_DUSTIN);
        txt.setFill(Color.BLACK);
        txt.setStrokeWidth(1);
        txt.setStroke(Color.BLACK);
        txt.setTextAlignment(TextAlignment.CENTER);
        txt.setTranslateY(-10);
    }
    public static void styleMainTitle(Text txt){
        txt.setFont(TextStylizer.FONT_DUSTIN_BIG);
        txt.setFill(Color.BLACK);
        txt.setStrokeWidth(1);
        txt.setStroke(Color.BLACK);
        txt.setTextAlignment(TextAlignment.CENTER);
    }
    public static void styleMainSubtitle(Text txt){
        txt.setFont(TextStylizer.FONT_DUSTIN_SMALL);
        txt.setTranslateY(-60);
        txt.setFill(Color.GRAY);
        txt.setStrokeWidth(1);
        txt.setStroke(Color.GRAY);
        txt.setTextAlignment(TextAlignment.CENTER);
    }
    public static void styleMenuButton(Text txt){
        txt.setFont(TextStylizer.FONT_DUSTIN);
        txt.setFill(Color.BLACK);
        txt.setStrokeWidth(1);
        txt.setStroke(Color.BLACK);
        txt.setCursor(Cursor.HAND);
    }
    public static void styleMenuButtonHover(Text txt){
        txt.setFill(Color.web("#CC0000"));
        txt.setStroke(Color.web("#CC0000"));
    }
    public static void styleGameOverTitle(Text txt){
        txt.setFont(TextStylizer.FONT_DUSTIN_BIG);
        txt.setFill(Color.BLACK);
        txt.setStrokeWidth(1);
        txt.setStroke(Color.BLACK);
        txt.setTextAlignment(TextAlignment.CENTER);
    }
    public static void styleGameOverSubtitle(Text txt){
        txt.setFont(TextStylizer.FONT_DUSTIN);
        txt.setFill(Color.GRAY);
        txt.setStrokeWidth(1);
        txt.setStroke(Color.GRAY);
        txt.setTranslateY(-30);
        txt.setTextAlignment(TextAlignment.CENTER);
    }
    public static void styleMessageTitle(Text txt){
        txt.setFont(TextStylizer.FONT_DUSTIN);
        txt.setFill(Color.GRAY);
        txt.setStrokeWidth(1);
        txt.setStroke(Color.GRAY);
        txt.setTextAlignment(TextAlignment.CENTER);
    }
    public static void stylePauseTitle(Text txt) {
        txt.setFont(TextStylizer.FONT_DUSTIN_BIG);
        txt.setFill(Color.GRAY);
        txt.setStrokeWidth(1);
        txt.setStroke(Color.GRAY);
        txt.setTextAlignment(TextAlignment.CENTER);
    }
}
