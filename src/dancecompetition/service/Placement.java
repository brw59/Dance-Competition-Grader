/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    
    public int getScore(){
        return score;
    }
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
