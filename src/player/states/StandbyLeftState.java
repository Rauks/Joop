/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package player.states;

import geometry.Hitbox;
import geometry.Path;
import player.states.itf.Left;
import player.states.itf.Standby;

/**
 *
 * @author
 * Karl
 */
public class StandbyLeftState extends State implements Standby, Left {
    public StandbyLeftState(){
        super(StandbyLeftState.class.getSimpleName());
        Path path = new Path();
        this.setPath(path);
        this.setHitbox(Hitbox.STANDUP);
        this.setMovingState(false);
    }
}
