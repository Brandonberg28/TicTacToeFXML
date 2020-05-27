package org.brandonberg28;


public class TicTacToeLogic 
{

   private String [][] table = {{"-","-","-"},{"-","-","-"},{"-","-","-"}};
   //private Player player1, player2;   
   
   public TicTacToeLogic(/*Player player1, Player player2*/)
   {
      //this.player1 = player1;
      //this.player1 = player2;
   }
   
   public void emptyTable()
   {
      for (int i=0; i<3; i++)//loop for rows
      {
         for (int j=0; j<3; j++)//loop for collumns
         {
            table[i][j] = "-";
         }
      }
   }
   
   public String[][] getTable()
   {
      return table;
   }
   
   public boolean markPosition(Player player, int row, int col)
   {
      //add range checking ****
      if(table[row][col] == "-")
      {
         table[row][col] = player.getSymbol();
         return true;
      }
      else
      {
         return false;
      }
   }
   
   public boolean checkIfWinner(Player player) 
   {
      int InARow = 0, InACol = 0;
      //check for Xs
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