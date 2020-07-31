package org.brandonberg28;


public class TicTacToeLogic 
{

   private String [][] table = {{"-","-","-"},{"-","-","-"},{"-","-","-"}};
   private int turnCounter = 0;   
   
   public TicTacToeLogic()
   {
    
   }
   
   public void emptyTable()
   {
      for (int i=0; i<3; i++)
      {
         for (int j=0; j<3; j++)
         {
            table[i][j] = "-";
         }
      }
   }
   
   public String[][] getTable()
   {
      return table;
   }
   
   public boolean markPosition(Player player, ButtonPosition BP)
   {
      if(table[BP.getRow()][BP.getCol()] == "-")
      {
         table[BP.getRow()][BP.getCol()] = player.getSymbol();
         return true;
      }
      else
      {
         return false;
      }
   }

   public void addToTurnCounter() {
      turnCounter++;
   }

   public int getTurnCounter() {
      return turnCounter;
   }

   public void clearTurnCounter() {
      turnCounter = 0;
   }

   public Player checkWhoWonMostGames(Player player1, Player player2) {
      return player1.getTotalWins() > player2.getTotalWins() ? player1 : player2;
   }
   
   public boolean checkIfWinner(Player player) 
   {
      int InARow = 0, InACol = 0;
      
      for(int i=0; i<3; i++)  
      {
         for(int j=0; j<3; j++)  
         {
            if(table[i][j] == player.getSymbol())  //checks rows
            {
               InARow++;
            }
            if(table[j][i] == player.getSymbol())  //checks collumns
            {
               InACol++;
            }
            if(InARow == 3 || InACol == 3)
            {
               return true;
            }
         }
         InARow = 0;  //reset when you check next row
         InACol = 0;  //reset when you check next collumn
      }
      //check for diagonals
      if(table[0][0] == player.getSymbol() && table[1][1] == player.getSymbol() && table[2][2] == player.getSymbol())
      {
         return true;
      }
      if(table[0][2] == player.getSymbol() && table[1][1] == player.getSymbol() && table[2][0] == player.getSymbol())
      {
         return true;
      }
      return false;
   }



}