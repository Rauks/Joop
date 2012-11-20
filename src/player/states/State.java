/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package player.states;

import geometry.Hitbox;
import geometry.Path;
import javafx.scene.image.Image;

/**
 *
 * @author
 * Karl
 */
public abstract class State {
    private static final String DIR_IMAGES = "/ressources/player/states/";
    private static final String FORMAT_IMAGES = ".png";
    
    private final Image img;
    private final String id;
    private Path path;
    private Hitbox hitbox;
    private boolean isMovingState;
    
    protected State(String id){
        this.id = id;
        this.img = new Image(State.class.getResource(State.DIR_IMAGES + id + State.FORMAT_IMAGES).toString());
    }
    public Image getImage(){
        return this.img;
    }
    protected void setPath(Path path){
        this.path = path;
    }
    public Path getPath(){
        return this.path;
    }
    protected void setHitbox(Hitbox hitbox){
        this.hitbox = hitbox;
    }
    public Hitbox getHitbox(){
        return this.hitbox;
    }
    protected void setMovingState(boolean bool){
        this.isMovingState = bool;
    }
    public boolean isMovingState(){
        return this.isMovingState;
    }
    
    @Override
    public String toString(){
        return "GameEngine: State to " + this.id;
    }
}
