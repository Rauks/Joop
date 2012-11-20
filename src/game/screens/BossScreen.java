/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.screens;

import game.Background;
import game.questions.Question;

/**
 *
 * @author Karl
 */
public class BossScreen extends Screen{
    private Question question;
    
    public BossScreen(Question question, Background background){
        super(background);
        this.question = question;
    }
    
    public Question getQuestion(){
        return this.question;
    }
}
