package dancecompetition.service;

/**
 *
 * @author daviddonley
 */
public class Placement {
    private int score;
    private int danceNum;
    
    public Placement(int setScore, int setDanceNum){
        score = setScore;
        danceNum = setDanceNum;  
    }
    
    // This is the added up score of all of the multi-dance results
    public int getScore(){
        return score;
    }
    
    // returns the number on the back of the dancer
    public int getDanceNum(){
        return danceNum;
    }
    public void setDanceNum(int DanceNum){
        danceNum = DanceNum;
    }
    
    public void setScore(int newScore){
        score = newScore;
    }
}
