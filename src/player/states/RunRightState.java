/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package player.states;

import geometry.Hitbox;
import geometry.Path;
import geometry.Vector;
import player.states.itf.Right;
import player.states.itf.Run;

/**
 *
 * @author
 * Karl
 */
public class RunRightState extends State implements Run, Right {
    public RunRightState(){
        super(RunRightState.class.getSimpleName());
        Path path = new Path();
        path.addVector(Vector.RIGHT);
        this.setPath(path);
        this.setHitbox(Hitbox.STANDUP);
        this.setMovingState(true);
    }
}
