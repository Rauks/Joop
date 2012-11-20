/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package player.states;

import geometry.Hitbox;
import geometry.Path;
import player.states.itf.Crouch;
import player.states.itf.Left;

/**
 *
 * @author
 * Karl
 */
public class CrouchLeftState extends State implements Crouch, Left{
    public CrouchLeftState(){
        super(CrouchLeftState.class.getSimpleName());
        Path path = new Path();
        this.setPath(path);
        this.setHitbox(Hitbox.CROUCH);
        this.setMovingState(false);
    }
}
