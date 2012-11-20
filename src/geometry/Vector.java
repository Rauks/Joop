/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

/**
 *
 * @author Thibaut
 */
public class Vector {
    public static final Vector NULL = new Vector(0, 0); 
    public static final Vector UP = new Vector(0, -1); 
    public static final Vector LEFT = new Vector(-1, 0); 
    public static final Vector RIGHT = new Vector(1, 0); 
    public static final Vector DOWN = new Vector(0, 1); 
    
    public int dx;
    public int dy;

    public Vector (){
        this.dx = 0;
        this.dy = 0;
    }
    public Vector(int dx, int dy){
        this.dx = dx;
        this.dy = dy;
    }
    public Vector(Vector v){
        this.dx = v.dx;
        this.dy = v.dy;
    }

    public Position getTarget(Position pos){
        Position target = new Position(pos);
        target.move(this);
        return target;
    }
}
