/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author Karl
 */
public class GameException extends Exception{
    public static final String NOT_PLAYABLE_SCREEN = "Current Screen is not a playableScreen";

    public GameException(String message){
        super(message);
    }
}
