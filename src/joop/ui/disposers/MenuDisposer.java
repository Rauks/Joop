/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joop.ui.disposers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import joop.Joop;

/**
 *
 * @author
 * Karl
 */
public abstract class MenuDisposer {
    public static Insets INSETS_MAINMENU = new Insets(100, 50, 50, 50);
    public static Insets INSETS_SUBMENU = new Insets(25, 40, 20, 40);

    public static StackPane dispose(HBox returnChoices, Pane content, ImageView background, Insets insets){
        
        BorderPane borderPane = new BorderPane();
        borderPane.setMaxSize(Joop.WINDOW_WIDTH, Joop.WINDOW_HEIGHT);
        borderPane.setPrefSize(Joop.WINDOW_WIDTH, Joop.WINDOW_HEIGHT);
        borderPane.setPadding(insets);
        borderPane.setTop(content);
        returnChoices.setSpacing(80);
        returnChoices.setAlignment(Pos.CENTER);
        borderPane.setBottom(returnChoices);
        
        StackPane root = new StackPane();
        root.setMaxHeight(Joop.WINDOW_HEIGHT);
        root.setMaxWidth(Joop.WINDOW_WIDTH);
        root.getChildren().add(background);
        StackPane.setAlignment(background, Pos.CENTER);
        root.getChildren().add(borderPane);
        StackPane.setAlignment(borderPane, Pos.CENTER);
        return root;
    }
}
