/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author Karl
 */
public class StackException extends Exception{
    public static final String FULL = "Stack is full";

    public StackException(String message){
        super(message);
    }
}
