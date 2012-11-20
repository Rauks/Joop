/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;


import game.Background;
import game.Bloc;
import game.BlocMatrix;
import game.Level;
import game.pools.BossPool;
import game.questions.Answer;
import game.questions.Question;
import game.screens.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Karl
 */
public class DataBase {
    //Dossiers de ressources
    private static final String DIR_XML_BOSS = "/ressources/boss/";
    private static final String DIR_XML_LEVELS = "/ressources/levels/";
    private static final String DIR_XML_BLOCS = "/ressources/blocs/";
    private static final String DIR_XML_BACKGROUNDS = "/ressources/backgrounds/";
    
    //Adresses des fichiers xml dans les dossiers de ressource
    private static final String FILE_XML_BLOCS = "blocs.xml";
    private static final String FILE_XML_BACKGROUNDS = "backgrounds.xml";
    private static final String FILE_XML_BOSS = "boss.xml";
    
    //Balises et contenu des fichiers xml
    private static final String XML_LEVEL_NAME = "name";
    private static final String XML_LEVEL_SCREENS = "screen";
    private static final String XML_LEVEL_SCREENS_MATRIX = "matrix";
    private static final String XML_LEVEL_SCREENS_MATRIX_SEPARATOR = ",";
    private static final String XML_LEVEL_SCREENS_BACKGROUND = "background";
    private static final String XML_LEVEL_SCREENS_TYPE = "type";
    private static final String XML_LEVEL_SCREENS_TYPE_IN = "IN";
    private static final String XML_LEVEL_SCREENS_TYPE_OUT = "OUT";
    private static final String XML_LEVEL_SCREENS_TYPE_COMMON = "COMMON";
    
    private static final String XML_BLOC = "bloc";
    private static final String XML_BLOC_ID = "id";
    private static final String XML_BLOC_DESCRIPTION = "description";
    private static final String XML_BLOC_TRAVERSABLE = "throwable";
    private static final String XML_BLOC_SCORE = "score";
    private static final String XML_BLOC_LIFE = "vie";
    
    private static final String XML_BACKGROUND = "background";
    private static final String XML_BACKGROUND_ID = "id";
    private static final String XML_BACKGROUND_DESCRIPTION = "description";
    
    private static final String XML_BOSS = "boss";
    private static final String XML_BOSS_QUESTION = "question";
    private static final String XML_BOSS_BACKGROUND = "background";
    private static final String XML_BOSS_ANSWERS = "answer";
    private static final String XML_BOSS_ANSWERS_STR = "str";
    private static final String XML_BOSS_ANSWERS_VALID = "valid";
    
    private HashMap<Integer, Bloc> blocs;
    private HashMap<Integer, Background> backgrounds;
    private ArrayList<Level> levels;
    private BossPool boss;
    private SAXBuilder sxb;
    private Random randomLevel;
    
    public DataBase() throws DataBaseException{
        this.backgrounds = new HashMap<>();
        this.blocs = new HashMap<>();
        this.levels = new ArrayList<>();
        this.boss = new BossPool();
        this.sxb = new SAXBuilder();
        this.randomLevel = new Random();
    }
    
    public Bloc getBloc(int id) throws DataBaseException{
        if(!this.blocs.containsKey(id)) throw new DataBaseException(DataBaseException.CONTENT_BLOC);
        return this.blocs.get(id);
    }
    public Background getBackground(int id) throws DataBaseException{
        if(!this.backgrounds.containsKey(id)) throw new DataBaseException(DataBaseException.CONTENT_BACKGROUND);
        return this.backgrounds.get(id);
    }
    public Level getLevel(int id) throws DataBaseException{
        if(this.levels.get(id) == null) throw new DataBaseException(DataBaseException.CONTENT_LEVEL);
        return this.levels.get(id);
    }
    public Level getRamdomLevel(){
        return this.levels.get(this.randomLevel.nextInt(this.countLevels()));
    }
    public int countLevels(){
        return this.levels.size();
    }
    public int countBlocs(){
        return this.blocs.size();
    }
    public int countBackgrounds(){
        return this.backgrounds.size();
    }
    public int countBoss(){
        return this.boss.size();
    }
    public Screen getBoss(int id){
        return this.boss.getScreen(id);
    }
    public Screen getRandomBoss(){
        return this.boss.getRandomScreen();
    }
    
    public void parseBackgrounds() throws JDOMException, IOException{
        Element current;
        int id;
        Document xmlBackgrounds = this.sxb.build(DataBase.class.getResource(DataBase.DIR_XML_BACKGROUNDS + DataBase.FILE_XML_BACKGROUNDS));
        Element rootBackgrounds = xmlBackgrounds.getRootElement();
        for(Iterator<Element> it = rootBackgrounds.getChildren(DataBase.XML_BACKGROUND).iterator(); it.hasNext();){
            current = it.next();
            id = Integer.parseInt(current.getChild(DataBase.XML_BACKGROUND_ID).getText());
            this.backgrounds.put(id, new Background(id));
        }
    }
    public void parseBlocs() throws JDOMException, IOException{
        Element current;
        int id;
        boolean traversable;
        int score;
        int life;
        Document xmlBlocs = this.sxb.build(DataBase.class.getResource(DataBase.DIR_XML_BLOCS + DataBase.FILE_XML_BLOCS));
        Element rootBlocs = xmlBlocs.getRootElement();
        for(Iterator<Element> i = rootBlocs.getChildren(DataBase.XML_BLOC).iterator(); i.hasNext();){
            current = i.next();
            id = Integer.parseInt(current.getChild(DataBase.XML_BLOC_ID).getText());
            traversable = Boolean.parseBoolean(current.getChild(DataBase.XML_BLOC_TRAVERSABLE).getText());
            score = Integer.parseInt(current.getChild(DataBase.XML_BLOC_SCORE).getText());
            life = Integer.parseInt(current.getChild(DataBase.XML_BLOC_LIFE).getText());
            this.blocs.put(id, new Bloc(id, traversable, score, life));
        }
    }
    public void parseBoss() throws JDOMException, IOException{
        Element current;
        Element currentAnswer;
        int background;
        int id = 0;
        boolean valid;
        Document xmlBoss = this.sxb.build(DataBase.class.getResource(DataBase.DIR_XML_BOSS + DataBase.FILE_XML_BOSS));
        Element rootBoss = xmlBoss.getRootElement();
        for(Iterator<Element> it = rootBoss.getChildren(DataBase.XML_BOSS).iterator(); it.hasNext();){
            current = it.next();
            background = Integer.parseInt(current.getChild(DataBase.XML_BOSS_BACKGROUND).getText());
            id++;
            Question question = new Question(current.getChild(DataBase.XML_BOSS_QUESTION).getText());
            for(Iterator subIt = current.getChildren(DataBase.XML_BOSS_ANSWERS).iterator(); subIt.hasNext();){
                currentAnswer = (Element)subIt.next();
                valid = Boolean.parseBoolean(currentAnswer.getChild(DataBase.XML_BOSS_ANSWERS_VALID).getText());
                question.addAnswer(new Answer(currentAnswer.getChild(DataBase.XML_BOSS_ANSWERS_STR).getText(), valid));
            }
            BossScreen bossScreen = new BossScreen(question, this.backgrounds.get(background));
            this.boss.add(bossScreen);
        }
    }
    public void parseLevels() throws JDOMException, IOException, DataBaseException{
        Document xmlLevelCurrent;
        URL fileUrl;
        Element rootLevelCurrent;
        Element currentScreen;
        int i = 0;
        while((fileUrl = DataBase.class.getResource(DataBase.DIR_XML_LEVELS + i + ".xml")) != null){
            xmlLevelCurrent = this.sxb.build(fileUrl);
            rootLevelCurrent = xmlLevelCurrent.getRootElement();
            Level lvl = new Level(rootLevelCurrent.getChild(DataBase.XML_LEVEL_NAME).getText());
            for(Iterator<Element> it = rootLevelCurrent.getChildren(DataBase.XML_LEVEL_SCREENS).iterator(); it.hasNext();){
                currentScreen = it.next();
                BlocMatrix matrix = new BlocMatrix(new StringTokenizer(currentScreen.getChild(DataBase.XML_LEVEL_SCREENS_MATRIX).getText(), DataBase.XML_LEVEL_SCREENS_MATRIX_SEPARATOR), this);
                Background background = this.getBackground(Integer.parseInt(currentScreen.getChild(DataBase.XML_LEVEL_SCREENS_BACKGROUND).getText()));
                switch (currentScreen.getChild(DataBase.XML_LEVEL_SCREENS_TYPE).getText()) {
                    case DataBase.XML_LEVEL_SCREENS_TYPE_COMMON:
                        lvl.getCommonPool().add(new CommonScreen(matrix, background));
                        break;
                    case DataBase.XML_LEVEL_SCREENS_TYPE_IN:
                        lvl.getInPool().add(new InScreen(matrix, background));
                        break;
                    case DataBase.XML_LEVEL_SCREENS_TYPE_OUT:
                        lvl.getOutPool().add(new OutScreen(matrix, background));
                        break;
                }
            }
            if(lvl.isWellFormed()){
                this.levels.add(lvl);
            }
            i++;
        }
    }
}
