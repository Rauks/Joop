/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author Karl
 */
public class BlocMatrixException extends Exception{
    public static final String OUT_OF_BOUND = "Out of matrix bound";

    public BlocMatrixException(String message){
        super(message);
    }
}
