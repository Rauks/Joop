/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package player.states;

import geometry.Hitbox;
import geometry.Path;
import geometry.Vector;
import player.states.itf.Left;
import player.states.itf.LongJump;
import player.states.itf.Run;

/**
 *
 * @author
 * Karl
 */
public class RunLongJumpLeftState extends State implements Run, LongJump, Left {
    public RunLongJumpLeftState(){
        super(RunLongJumpLeftState.class.getSimpleName());
        Path path = new Path();
        path.addVector(Vector.UP);
        path.addVector(Vector.LEFT);
        path.addVector(Vector.LEFT);
        this.setPath(path);
        this.setHitbox(Hitbox.STANDUP);
        this.setMovingState(true);
    }
}
