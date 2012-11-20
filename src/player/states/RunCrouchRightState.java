/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package player.states;

import geometry.Hitbox;
import geometry.Path;
import geometry.Vector;
import player.states.itf.Crouch;
import player.states.itf.Right;
import player.states.itf.Run;

/**
 *
 * @author
 * Karl
 */
public class RunCrouchRightState extends State implements Run, Crouch, Right{
    public RunCrouchRightState(){
        super(RunCrouchRightState.class.getSimpleName());
        Path path = new Path();
        path.addVector(Vector.RIGHT);
        this.setPath(path);
        this.setHitbox(Hitbox.CROUCH);
        this.setMovingState(true);
    }
}
