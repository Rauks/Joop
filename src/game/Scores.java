/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import joop.Options;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Karl
 */
public class Scores {
    private static final String XML_FILE = "scores.dat";
    private static final String XML_ROOT = "scores";
    private static final String XML_ROOT_SCORE = "score";
    private static final String XML_ROOT_SCORE_PSEUDO = "pseudo";
    private static final String XML_ROOT_SCORE_VALUE = "value";
    
    private static final String DEFAUT_PSEUDO = "Joop";
    private static final int DEFAUT_SCORE = 0;
    
    public static final int SCORES = 6;
    
    private ArrayList<String> pseudos; 
    private ArrayList<Integer> scores; 
    private SAXBuilder sxb;
    
    public String getPseudo(int index){
        return this.pseudos.get(index);
    }
    public int getScore(int index){
        return this.scores.get(index).intValue();
    }
    public void addScore(String pseudo, int value){
        for(int i = 0; i < Scores.SCORES; i++){
            int currentScore = this.scores.get(i);
            if(currentScore <= value){
                for(int j = Scores.SCORES - 2; j >= i; j--){
                    this.pseudos.set(j + 1, this.pseudos.get(j));
                    this.scores.set(j + 1, this.scores.get(j));
                }
                this.pseudos.set(i, pseudo);
                this.scores.set(i, value);
                break;
            }
        }
        this.saveXml();
    }
    public int getSize(){
        return Scores.SCORES;
    }
    
    synchronized private void loadScores(){
        File xml = new File(Scores.XML_FILE);
        if(xml.exists()){
            try{
                this.pseudos = new ArrayList<>();
                this.scores = new ArrayList<>();
                
                Element current;
                String pseudo;
                int score;
                Document xmlScores = this.sxb.build(xml);
                Element rootScores = xmlScores.getRootElement();
                for(Iterator<Element> i = rootScores.getChildren(Scores.XML_ROOT_SCORE).iterator(); i.hasNext();){
                    current = i.next();
                    pseudo = current.getChild(Scores.XML_ROOT_SCORE_PSEUDO).getText();
                    score = Integer.parseInt(current.getChild(Scores.XML_ROOT_SCORE_VALUE).getText());
                    this.pseudos.add(pseudo);
                    this.scores.add(score);
                }
            }
            catch(JDOMException | IOException ex){
                Logger.getLogger(Scores.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally{
                if(this.pseudos.size() != Scores.SCORES || this.scores.size() != Scores.SCORES){
                    this.loadDefaut();
                    this.saveXml();
                }
            }
        }
        else{
            this.loadDefaut();
            this.saveXml();
        }
    }
    private void loadDefaut(){
        this.pseudos = new ArrayList<>();
        this.scores = new ArrayList<>();
        for(int i = 0; i < Scores.SCORES; i++){
            this.pseudos.add(Scores.DEFAUT_PSEUDO);
            this.scores.add(Scores.DEFAUT_SCORE);
        }
    }
    
    synchronized private void saveXml(){
        final Scores me = this;
        Thread saver = new Thread(){
            @Override
            public void run(){
                try {
                    Element root = new Element(Scores.XML_ROOT);
                    for(int i = 0; i < Scores.SCORES; i++){
                        Element score = new Element(Scores.XML_ROOT_SCORE);
                        Element pseudo = new Element(Scores.XML_ROOT_SCORE_PSEUDO);
                        Element value = new Element(Scores.XML_ROOT_SCORE_VALUE);
                        pseudo.setText(me.pseudos.get(i));
                        value.setText(String.valueOf(me.scores.get(i)));
                        score.addContent(pseudo);
                        score.addContent(value);
                        root.addContent(score); 
                    }

                    Document doc = new Document(root);

                    XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
                    sortie.output(doc, new FileOutputStream(Scores.XML_FILE));
                } 
                catch (IOException ex) {
                    Logger.getLogger(Options.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        };
        saver.start();
    }
    
    public Scores(){
        this.sxb = new SAXBuilder();
        this.loadScores();
    }
}
