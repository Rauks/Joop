/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Karl
 */
public class DataBaseException extends Exception{
    public static final String XML_PARSE = "XML parsing error";
    public static final String XML_LOAD = "XML loading error";
    public static final String CONTENT_BLOC = "Invalid bloc ID";
    public static final String CONTENT_BACKGROUND = "Invalid background ID";
    public static final String CONTENT_LEVEL = "Invalid level ID";

    public DataBaseException(String message){
        super(message);
    }
}
