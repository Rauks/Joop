/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joop.ui.componants;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.text.Text;
import joop.ui.stylizers.TextStylizer;

/**
 *
 * @author
 * Karl
 */
public class MenuButton{
    private Text text;
    
    public Text getText(){
        return this.text;
    }
    private void addEffect(){
        this.text.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                Text me = (Text) arg0.getSource();
                TextStylizer.styleMenuButtonHover(me);
                
            }
        });
        this.text.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                Text me = (Text) arg0.getSource();
                TextStylizer.styleMenuButton(me);
            }
        });
    }
    public MenuButton(String str, EventHandler event){
        this.text = new Text(str);
        TextStylizer.styleMenuButton(this.text);
        this.text.setOnMouseClicked(event);
        this.addEffect();
    }
}
