package dancecompetition.service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author daviddonley
 */
public class Heat {
    /**
    * mAge stores the age group of the heat
    */
   private String mAge;

   /**
    * mLevel stores the level of the dancers in the heat
    */
   private String mLevel;

   /**
    * mStyle stores the style of the dance heat
    */
   private String mStyle;

   /**
    * mNumJudges stores the number of judges
    */
   private int mNumJudges;

   /**
    * mNumCouples stores the number of couples
    */
   private int mNumCouples;

   /**
    * mNumCouples stores the number of couples
    */
   private String mAB;
   
   //David Added this
   
   private int mNumDances;

   /**
    * public Heat constructor, simply creates a new Heat
    *
    * @param pAge
    * @param pLevel
    * @param pStyle
    * @param pNumJudges
    * @param pNumCouples
    * @param pAB
    */
   public Heat(String pAge, String pLevel, String pStyle, int pNumJudges,
      int pNumCouples, String pAB)
   {
      mAge = pAge;
      mLevel = pLevel;
      mStyle = pStyle;
      mNumJudges = pNumJudges;
      mNumCouples = pNumCouples;
      mAB = pAB;
   }

   //David Added THis
   public Heat(String pAge, String pLevel, String pStyle, int pNumJudges,
           int pNumCouples, int numDances){
       mAge = pAge;
       mLevel = pLevel;
       mStyle = pStyle;
       mNumJudges = pNumJudges;
       mNumCouples = pNumCouples;
       mNumDances = numDances;
   }
   //
   // =====================================
   // Accessors and Mutators
   // =====================================
   //

   /**
    * Returns the age value of the Heat.
    *
    * @return String age.
    */
   public String getAge()
   {
      return mAge;
   }

   /**
    * Returns the level value of the Heat.
    *
    * @return String level.
    */
   public String getLevel()
   {
      return mLevel;
   }

   /**
    * Returns the Style value for
    *
    * @return String style
    */
   public String getStyle()
   {
      return mStyle;
   }

   /**
    * Returns the number of judges for this Heat.
    *
    * @return int number of judges.
    */
   public int getNumJudges()
   {
      return mNumJudges;
   }

   /**
    * Returns the number of Couples for this Heat.
    *
    * @return int number of couples.
    */
   public int getNumCouples()
   {
      return mNumCouples;
   }

   /**
    * Returns the value saying weather this Heat is A or B.
    *
    * @return String value A or B.
    */
   public String getAB()
   {
      return mAB;
   }
   
   
   // David Added this
   public int getNumDances(){
       return mNumDances;
   }
   
   
   /**
    * Sets the age range for the Heat.
    *
    * @param pAge age to be assigned.
    */
   public void setAge(String pAge)
   {
      mAge = pAge;
   }

   /**
    * Sets the level for the Heat.
    *
    * @param pLevel level to be assigned.
    */
   public void setLevel(String pLevel)
   {
      mLevel = pLevel;
   }

   //David Added This
   public void setnumDances(int pNumDances)
   {
       mNumDances = pNumDances;
   }
   /**
    * Sets the style for the Heat.
    *
    * @param pStyle to be assigned
    */
   public void setStyle(String pStyle)
   {
      mStyle = pStyle;
   }

   /**
    * Validates that the number of judges is in range and sets the value.
    *
    * @param pNumJudges to be allocated.
    */
   public void setNumJudges(int pNumJudges)
   {
      if ((pNumJudges > 15) || (pNumJudges < 1))
      {
         System.out.println("ERROR: Number of judges is out of range!");
      }
      else
      {
         mNumJudges = pNumJudges;
      }
   }

   /**
    * Validates that the number of couples is in range and sets the value.
    *
    * @param pNumCouples to be allocated.
    */
   public void setNumCouples(int pNumCouples)
   {
      if ((pNumCouples > 15) || (pNumCouples < 1))
      {
         System.out.println("ERROR: Number of couples is out of range!");
      }
      else
      {
         mNumCouples = pNumCouples;
      }
   }

   /**
    * This will build a string of the current heat to be displayed.
    * This method is depricated
    * @deprecated 
    * @return String displaying the current Heat.
    */
   public String buildString()
   {
      return "" + mAge + "\n" + mLevel + "\n" + mStyle + "\n\n" +
      "Number of Judges: " + mNumJudges + "\n" + "Number of Couples: " +
      mNumCouples + "\n" + mAB;
   }

   /**
    * display is purely for output
    * This method is depricated
    * @deprecated 
    */
   public void display()
   {
      System.out.println(buildString());
   }
}
