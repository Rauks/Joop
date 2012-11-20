/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

/**
 *
 * @author Thibaud
 */
public class Position {    
    public int x;
    public int y;

    public Position() {
        this.x = 0;
        this.y = 0;
    }
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Position(Position p){
        this.x = p.x;
        this.y = p.y;
    }
    public void move(Vector v){
        this.x += v.dx;
        this.y += v.dy;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Position other = (Position) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 10 * this.x + this.y;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(this.x);
        sb.append(", ");
        sb.append(this.y);
        sb.append(")");
        return sb.toString();
    }
}
