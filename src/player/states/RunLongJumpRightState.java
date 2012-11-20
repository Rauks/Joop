/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package player.states;

import geometry.Hitbox;
import geometry.Path;
import geometry.Vector;
import player.states.itf.LongJump;
import player.states.itf.Right;
import player.states.itf.Run;

/**
 *
 * @author
 * Karl
 */
public class RunLongJumpRightState extends State implements Run, LongJump, Right {
    public RunLongJumpRightState(){
        super(RunLongJumpRightState.class.getSimpleName());
        Path path = new Path();
        path.addVector(Vector.UP);
        path.addVector(Vector.RIGHT);
        path.addVector(Vector.RIGHT);
        this.setPath(path);
        this.setHitbox(Hitbox.STANDUP);
        this.setMovingState(true);
    }
}
