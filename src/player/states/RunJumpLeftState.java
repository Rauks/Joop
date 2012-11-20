/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package player.states;

import geometry.Hitbox;
import geometry.Path;
import geometry.Vector;
import player.states.itf.Jump;
import player.states.itf.Left;
import player.states.itf.Run;

/**
 *
 * @author
 * Karl
 */
public class RunJumpLeftState extends State implements Run, Jump, Left {
    public RunJumpLeftState(){
        super(RunJumpLeftState.class.getSimpleName());
        Path path = new Path();
        path.addVector(Vector.UP);
        path.addVector(Vector.LEFT);
        this.setPath(path);
        this.setHitbox(Hitbox.STANDUP);
        this.setMovingState(true);
    }
}
