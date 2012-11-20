/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package player.states;

import geometry.Hitbox;
import geometry.Path;
import geometry.Vector;
import player.states.itf.Left;
import player.states.itf.Run;

/**
 *
 * @author
 * Karl
 */
public class RunLeftState extends State implements Run, Left {
    public RunLeftState(){
        super(RunLeftState.class.getSimpleName());
        Path path = new Path();
        path.addVector(Vector.LEFT);
        this.setPath(path);
        this.setHitbox(Hitbox.STANDUP);
        this.setMovingState(true);
    }
}
