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
   private Ruler [] mRuler;

   private int numCouples;
    private int numDances;
   private MultiDanceTable MDTable;

    public MDRuler(int pNumCouples, int pNumDances)
    {
	numCouples = pNumCouples;
	numDances = pNumDances; 
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
   public Ruler [] implementRules(Ruler [] pRuler)
   {
      mRuler = pRuler;
      MDTable.computeTotals(mRuler); 
      
      checkTies(rule9());
      return mRuler;
   }

   /**
    * Implements rule 9. Rule 9 adds the totals from the dance
    *
    */
   private int [] rule9()
   {
       int [] totals = MDTable.getTotals();
       position = new int [numCouples];
       for(int p = 0; p < numCouples; p++)
       {
	   position[p] = p;
       }
       for(int t = 0; t < numCouples; t++)
       {	   
	   for(int p = 0; p < numCouples; p++)
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
       List<Integer> [] tTotals = new ArrayList[numCouples * numDances];
       for(int i = 0; i < numCouples; i++){
           System.out.println(totals[i]);
           System.out.println(position[i]);
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
	    totals = new int [numCouples];
	}

	public void computeTotals(Ruler [] pRuler)
	{
	    for (int i = 0; i < pRuler.length; i++)
	    {
		Couple [] tCouples = pRuler[i].getData().getCouples();
		for(int j = 0; j < numCouples; j++){
		    totals[j] = tCouples[j].getResult();
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
