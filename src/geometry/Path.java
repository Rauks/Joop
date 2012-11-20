/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

/**
 *
 * @author Thibaut
 */
import java.util.ArrayList;
import java.util.Iterator;

public class Path {
    ArrayList<Vector> vectors;

    public Path(){
        this.vectors = new ArrayList<>();
    }

    public void addVector(Vector v){
        this.vectors.add(v);
    }

    public Position getTarget(Position pos){
        Position target = new Position(pos);
        Vector current;
        for(Iterator<Vector> it = this.vectors.iterator(); it.hasNext();){
            current = it.next();
            target.move(current);
        }
        return target;
    }
    public int size(){
        return this.vectors.size();
    }
    public Iterator<Vector> iterator(){
        return this.vectors.iterator();
    }
}
