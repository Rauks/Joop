/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package audio;

/**
 *
 * @author Karl
 */
public class AudioException extends Exception{
    public static final String EMPTY_PLAYLIST = "Empty playlist";
    public static final String EMPTY_AUDIO_ENGINE = "Empty audio engine";

    public AudioException(String message){
        super(message);
    }
}
