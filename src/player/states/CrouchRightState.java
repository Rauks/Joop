/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package player.states;

import geometry.Hitbox;
import geometry.Path;
import player.states.itf.Crouch;
import player.states.itf.Right;

/**
 *
 * @author
 * Karl
 */
public class CrouchRightState extends State implements Crouch, Right{
    public CrouchRightState(){
        super(CrouchRightState.class.getSimpleName());
        Path path = new Path();
        this.setPath(path);
        this.setHitbox(Hitbox.CROUCH);
        this.setMovingState(false);
    }
}
