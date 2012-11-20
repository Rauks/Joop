/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joop.ui;

import javafx.scene.Scene;
import joop.Joop;

/**
 *
 * @author
 * Karl
 */
public abstract class JoopScene {
    private Scene scene;
    private Joop context;
    
    public JoopScene(Joop context){
        this.context = context;
    }
    protected Joop getContext(){
        return this.context;
    }
    protected void setScene(Scene scene){
        this.scene = scene;
    }
    public Scene getScene(){
        return this.scene;
    }
}
