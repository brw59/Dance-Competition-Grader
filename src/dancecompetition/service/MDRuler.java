/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dancecompetition.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;



/**
 *
 * @author daviddonley
 */
public class MDRuler {
    
   /**
    * The current score that is being assigned. We retain this here so that all
    * methods have access to it
    */
   private int mCurrentScore;
   private int [] position;
   /**
    * The ruler objects
    */
   private Placement [] place;
   private Ruler [] mRuler;
   private Map<Integer, Integer> map;

   private int numCouples;
    private int numDances;
   private MultiDanceTable MDTable;

    public MDRuler(int pNumCouples, int pNumDances, List<Placement> placement)
    {
	numCouples = pNumCouples;
	numDances = pNumDances; 
        place = new Placement[placement.size()];
	MDTable = new MultiDanceTable(pNumCouples);
    }

    /**
    * The interface that must be implemented in order for the rules to be applied.
    * Takes an array of Ruler objects, mutates them, and returns them when 
    * finished. This way the function can be applied in testing functions as well
    * as for the main program
    * 
    * @param  pRuler The Ruler array object that will be mutated
    * @return mRuler The Ruler array object, having been modified by the rules.
    */
   public Placement [] implementRules(List<Placement> placement)
   {
      for(int i = 0; i < placement.size(); i++)
      {
          place[i] = placement.get(i);
      }
      
      MDTable.computeTotals(place);
      checkTies(rule9());
      return place;
   }

   /**
    * Implements rule 9. Rule 9 adds the totals from the dance
    *
    */
   private Map<Integer, Integer> rule9()
   {   //This sorts the map from greatest to least.
       ValueComparator bvc = new ValueComparator(map);
       TreeMap<Integer, Integer> sorted_Map = new TreeMap<Integer, Integer>(bvc);
       sorted_Map.putAll(map);
       System.out.println("Results" + sorted_Map);
       return sorted_Map;
   }
   
   class ValueComparator implements Comparator<Integer>{
       Map<Integer, Integer> base;
       public ValueComparator(Map<Integer, Integer> base){
           this.base = base;
       }
       public int compare(Integer a, Integer b){
           if(base.get(a) >= base.get(b)){
               return -1;
           }
           else{
               return 1;
          }
    }
}
    
   private void checkTies(Map<Integer, Integer> map)
   {
       
   }

   /**
    * Applies rule 10. 
    *
    * @param pCouples represents the Couple objects being placed.
    */
   private void rule10(List<Integer> ties)
   {
       //find out what position is running the tie
       int pos = 0;

       for(int i = 0; i < numCouples; i++)
       {
	   if(ties.contains(position[i]))
	   {
	       pos = i;
	       break;
	   }
       }

       //total # of position 'pos' or higher and the total
       int [] numPos = new int [ties.size()];
       int [] totPos = new int [ties.size()];
       int index = 0;
       for(Integer iCouple : ties)
       {
	   for(int i = 0; i < numDances; i++)
	   {
	       Couple tCouple = mRuler[i].getData().getCouple(iCouple);
	       for(int k = 0; k < tCouple.getHeat().getNumJudges(); k++)
	       {
		   if(tCouple.getScores(k) >= pos)
		   {
		       numPos[index]++;
		       totPos[index] += tCouple.getScores(k);
		   }
	       }
	   }
	   index++;
       }

       //check for winner with # of positions
       int check = 0;
       for(int i = 0; i < ties.size(); i ++)
       {
	   for(int k = i; k < ties.size(); k++)
	   {
	       if(numPos[i] > numPos[k])
	       {
		   check++;
	       }
	       else if(numPos[i] == numPos[k])
	       {
		   rule11();
	       }
	   }
	   position[ties.get(i)] = pos + ties.size() - check - 1;
	   check = 0;
       }


   }

   /**
    * Implements Rule 11.
    * 
    * @param pCouples represents the couples being placed.
    */
   private void rule11()
   {

   }  
   
   /**
    * MultiDanceTable()- This is a glorified data structure for storing and
    *                    computing the data for multi dances. 
    *
    * @author Davis McClellan
    */   
    public class MultiDanceTable
    {
	private int numCouples;
	private int numDances;
	private int [] totals; 

	public MultiDanceTable(int tNumCouples)
	{
	    numCouples = tNumCouples;
	    totals = new int[numCouples];
	}

	public void computeTotals(Placement [] placement)
	{
            
            map = new HashMap<>();
            for(Placement p : placement){
                int Couple = p.getDanceNum();
                int Score = p.getScore();
                if(map.containsKey(Couple)){
                    int oldScore = map.get(Couple);
                    map.put(Couple, oldScore + Score);
                }
                else{
                    map.put(Couple, Score);
                }
            }
	}

	public int [] getTotals()
	{
	    return totals;
	}

	public int getResult(int pIndex)
	{
	    return totals[pIndex];
	}
        
        
    }
    
}
