/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package player.states;

import geometry.Hitbox;
import geometry.Path;
import geometry.Vector;
import player.states.itf.Jump;
import player.states.itf.Right;
import player.states.itf.Run;

/**
 *
 * @author
 * Karl
 */
public class RunJumpRightState extends State implements Run, Jump, Right {
    public RunJumpRightState(){
        super(RunJumpRightState.class.getSimpleName());
        Path path = new Path();
        path.addVector(Vector.UP);
        path.addVector(Vector.RIGHT);
        this.setPath(path);
        this.setHitbox(Hitbox.STANDUP);
        this.setMovingState(true);
    }
}
