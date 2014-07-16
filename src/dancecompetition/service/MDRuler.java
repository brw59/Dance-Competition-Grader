/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dancecompetition.service;

import java.util.ArrayList;
import java.util.List;



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
   private int [] rule9()
   {
       int [] totals = MDTable.getTotals();
       position = new int [place.length];
       System.out.println(place.length);
       
       for(int p = 0; p < place.length; p++)
       {
	   position[p] = p;
       }
       for(int t = 0; t < place.length; t++)
       {	   
	   for(int p = 0; p < place.length; p++)
	   {
	       if(totals[t] < totals[position[p]])
	       {
		   int temp = position[p];
		   position[p] = t;
		   position[t] = temp;
	       }
	   }
       }
       return totals;
   }
    
   private void checkTies(int [] totals)
   {
       List<Integer> [] tTotals = new ArrayList[place.length];
       for(int i = 0; i < place.length; i++){
	   tTotals[totals[i]].add(position[i]);
       }

       for(int i = 0; i <= numCouples * numDances; i++)
       {
	   if(tTotals[i].size() > 1)
	       rule10(tTotals[i]);
       }
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
            //System.out.println(placement[0].getScore());
	    for (int i = 0; i < numCouples; i++)
	    {
                for(int j = 0; j < place.length; i++)
                {
                    if(placement[i].getDanceNum() == placement[j].getDanceNum())
                    {
                        System.out.println(totals[i]);
                        totals[i] += placement[j].getScore();
                    }
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
