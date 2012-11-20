/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

import java.util.ArrayList;

/**
 *
 * @author
 * Karl
 * 
 * HitBox permet de définir la surface du Player n'etant pas forcement de 1 bloc.
 * Il est a plusieurs positions en meme temps, tous les blocs a ces positions doivent êtres traversables si le Player veux y etre.
 */
public class Hitbox {
    public static final Hitbox STANDUP = new Hitbox(0, -1);
    public static final Hitbox CROUCH = new Hitbox(0, 0);
    
    private int dx;
    private int dy;
    
    public Hitbox(int dx, int dy){
        this.dx = dx;
        this.dy = dy;
    }
    public Position getTopLeft(Position current){
        Position pos = new Position(current);
        pos.x = (dx < 0) ? current.x + dx : current.x;
        pos.y = (dy < 0) ? current.y + dy : current.y;
        return pos;
    }
    public Position getTopRight(Position current){
        Position pos = new Position(current);
        pos.x = (dx < 0) ? current.x : current.x + dx;
        pos.y = (dy < 0) ? current.y + dy : current.y;
        return pos;
    }
    public Position getBottomLeft(Position current){
        Position pos = new Position(current);
        pos.x = (dx < 0) ? current.x + dx : current.x;
        pos.y = (dy < 0) ? current.y : current.y + dy;
        return pos;
    }
    public Position getBottomRight(Position current){
        Position pos = new Position(current);
        pos.x = (dx < 0) ? current.x : current.x + dx;
        pos.y = (dy < 0) ? current.y : current.y + dy;
        return pos;
    }
    
    public ArrayList<Position> getPositions(Position current){
        ArrayList<Position> innerPositions = new ArrayList<>();
        Position topLeft = this.getTopLeft(current);
        Position bottomRight = this.getBottomRight(current);
        for(int x = topLeft.x; x <= bottomRight.x; x++){
            for(int y = topLeft.y; y <= bottomRight.y; y++){
                Position pos = new Position(x, y);
                innerPositions.add(pos);
            }
        }
        return innerPositions;
    }
}
