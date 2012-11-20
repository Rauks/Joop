/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package player.states;

import geometry.Hitbox;
import geometry.Path;
import player.states.itf.Right;
import player.states.itf.Standby;

/**
 *
 * @author
 * Karl
 */
public class StandbyRightState extends State implements Standby, Right {
    public StandbyRightState(){
        super(StandbyRightState.class.getSimpleName());
        Path path = new Path();
        this.setPath(path);
        this.setHitbox(Hitbox.STANDUP);
        this.setMovingState(false);
    }
}
