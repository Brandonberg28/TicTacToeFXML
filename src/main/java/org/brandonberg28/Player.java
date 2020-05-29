package org.brandonberg28;

public abstract class Player 
{

   private String name;
   private int totalWins = 0;
   
   public Player(String name)
   {
      this.name = name;
   }
   
   public String getName()
   {
      return name;
   }

   public void addAWin() {
      totalWins++;
   }
   
   public abstract String getSymbol();
   
   
 

}