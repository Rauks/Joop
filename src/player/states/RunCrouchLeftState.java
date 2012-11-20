/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package player.states;

import geometry.Hitbox;
import geometry.Path;
import geometry.Vector;
import player.states.itf.Crouch;
import player.states.itf.Left;
import player.states.itf.Run;

/**
 *
 * @author
 * Karl
 */
public class RunCrouchLeftState extends State implements Run, Crouch, Left{
    public RunCrouchLeftState(){
        super(RunCrouchLeftState.class.getSimpleName());
        Path path = new Path();
        path.addVector(Vector.LEFT);
        this.setPath(path);
        this.setHitbox(Hitbox.CROUCH);
        this.setMovingState(true);
    }
}
