/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import database.DataBase;
import database.DataBaseException;
import geometry.Position;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 *
 * @author Karl
 */
public class BlocMatrix{
    public static final int BOUND_LEFT = 0;
    public static final int BOUND_RIGHT = 15;
    public static final int BOUND_TOP = 0;
    public static final int BOUND_BOTTOM = 8;
    public static final int MATRIX_SIZE = (BlocMatrix.BOUND_RIGHT - BlocMatrix.BOUND_LEFT) * (BlocMatrix.BOUND_BOTTOM - BlocMatrix.BOUND_TOP);
    
    private static final int GROUND = 5;
    
    public static final Position START_NEXT = new Position(BlocMatrix.BOUND_LEFT, BlocMatrix.GROUND);
    public static final Position TO_NEXT = new Position(BlocMatrix.BOUND_RIGHT, BlocMatrix.GROUND);
    public static final Position START_PREVIOUS = new Position(BlocMatrix.BOUND_RIGHT, BlocMatrix.GROUND);
    public static final Position TO_PREVIOUS = new Position(BlocMatrix.BOUND_LEFT, BlocMatrix.GROUND);
    public static final Position START_LEVEL = new Position(BlocMatrix.BOUND_LEFT + 1, BlocMatrix.GROUND);
    
    private HashMap<Position,Bloc> matrix;
    
    private HashMap<Position,Bloc> getHashMap(){
        return this.matrix;
    }
    
    public BlocMatrix(StringTokenizer st, DataBase db) throws DataBaseException{
        this.matrix = new HashMap<>(BlocMatrix.MATRIX_SIZE);
        for(int y = BlocMatrix.BOUND_TOP; y <= BlocMatrix.BOUND_BOTTOM; y++){
            for(int x = BlocMatrix.BOUND_LEFT; x <= BlocMatrix.BOUND_RIGHT; x++){
                this.matrix.put(new Position(x, y), db.getBloc(Integer.parseInt(st.nextToken())));
            }
        }
    }

    public BlocMatrix(BlocMatrix matrix) {
        this.matrix = (HashMap<Position, Bloc>) matrix.getHashMap().clone();
    }
    public Bloc getBloc(Position pos) throws BlocMatrixException{
        if(BlocMatrix.outOfBound(pos)){
            throw new BlocMatrixException(BlocMatrixException.OUT_OF_BOUND);
        }
        return this.matrix.get(pos);
    }
    public void setBloc(Position pos, Bloc bloc){
        this.matrix.put(pos, bloc);
    }
    
    public static boolean outLeft(Position pos){
        return (pos.x < BlocMatrix.BOUND_LEFT);
    }
    public static boolean outRight(Position pos){
        return (pos.x > BlocMatrix.BOUND_RIGHT);
    }
    public static boolean outTop(Position pos){
        return (pos.y < BlocMatrix.BOUND_TOP);
    }
    public static boolean outBottom(Position pos){
        return (pos.y > BlocMatrix.BOUND_BOTTOM);
    }
    public static boolean outOfBound(Position pos){
        return (BlocMatrix.outLeft(pos) || BlocMatrix.outRight(pos) || BlocMatrix.outTop(pos) || BlocMatrix.outBottom(pos));
    }
}
