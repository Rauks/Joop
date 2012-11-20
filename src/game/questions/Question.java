/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 *
 * @author Laetitia
 */
public class Question {
    private String str;
    private ArrayList<Answer> listQuestion;

    public Question(String str) {
        this.listQuestion = new ArrayList<>();
        this.str = str;
    }
    public String getStr() {
        return this.str;
    }
    public int countAnswers() {
        return this.listQuestion.size();
    }
    public void addAnswer(Answer answer) {
        this.listQuestion.add(answer);
    }
    public Answer getAnswer(int id) {
        return this.listQuestion.get(id);
    }
    public Iterator<Answer> iterator() {
        return this.listQuestion.iterator();
    }
    public void shuffleAnswers(){
        Collections.shuffle(this.listQuestion);  
    }
}
