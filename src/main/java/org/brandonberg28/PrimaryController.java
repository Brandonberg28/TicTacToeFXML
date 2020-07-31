package org.brandonberg28;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class PrimaryController implements Initializable {

    @FXML
    private TextField scoreBoard;
    @FXML
    private TextField textFieldPlayerX;
    @FXML
    private TextField textFieldPlayerO;
    @FXML 
    private Label labelWinsPlayerX;
    @FXML 
    private Label labelWinsPlayerO;
    @FXML
    private Button buttonStartGame;
    @FXML
    private Button button00;
    @FXML
    private Button button01;
    @FXML
    private Button button02;
    @FXML
    private Button button10;
    @FXML
    private Button button11;
    @FXML
    private Button button12;
    @FXML
    private Button button20;
    @FXML
    private Button button21;
    @FXML
    private Button button22;
    @FXML
    private Label labelPlayAgain;
    @FXML
    private HBox hBoxPlayAgain;
    @FXML
    private Button buttonYes;
    @FXML
    private Button buttonNo;

    private Button[][] buttonArray;
    private PlayerX player1;
    private PlayerO player2;
    private TicTacToeLogic TTTLogic;
    private Player currentPlayer;
    ButtonPosition BP = new ButtonPosition();

    @FXML
    protected void startGameClicked() throws IOException {
        player1 = new PlayerX(textFieldPlayerX.getText());
        player2 = new PlayerO(textFieldPlayerO.getText());
        TTTLogic = new TicTacToeLogic();
        buttonStartGame.setVisible(false);
        resetGame();
        
    }

    private void resetGame() {
        showAndUpdatePlayerScores();
        TTTLogic.emptyTable();
        updateBoard();
        currentPlayer = player1;
        updateWhosTurnItIs(currentPlayer);
        enableAllButtons();
        TTTLogic.clearTurnCounter();
    }

    private void showAndUpdatePlayerScores() {
        labelWinsPlayerX.setText("Wins: "+player1.getTotalWins());
        labelWinsPlayerO.setText("Wins: "+player2.getTotalWins());
        labelWinsPlayerX.setVisible(true);
        labelWinsPlayerO.setVisible(true);
    }

    private void updateBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttonArray[i][j].setText(TTTLogic.getTable()[i][j]);
            }
        }
    }

    private void updateWhosTurnItIs(Player player) {
        scoreBoard.setText(currentPlayer.getName() + " its your turn.");
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        initializeButtons();
    }

    private void initializeButtons() {
        buttonArray = new Button[3][3];
        buttonArray[0][0] = button00;
        buttonArray[0][1] = button01;
        buttonArray[0][2] = button02;
        buttonArray[1][0] = button10;
        buttonArray[1][1] = button11;
        buttonArray[1][2] = button12;
        buttonArray[2][0] = button20;
        buttonArray[2][1] = button21;
        buttonArray[2][2] = button22;
    }

    @FXML
    protected void ButtonHandler(ActionEvent e) {   // set state first and then change the userInterface based on the state of the model

        Button clickedButton = (Button) e.getSource();

        figureOutRowAndColumn(BP, clickedButton);
        boolean placeSuccessfullyMarked = TTTLogic.markPosition(currentPlayer, BP);

        if (placeSuccessfullyMarked) 
        {
            markButton(clickedButton, currentPlayer);
            TTTLogic.addToTurnCounter();
            if (TTTLogic.checkIfWinner(currentPlayer)) 
            {
                currentPlayer.addAWin();
                declareWinnerOnScoreBoard(currentPlayer);
                disableAllButtons();
                showPlayAgainOptions();
            } 
            else if (TTTLogic.getTurnCounter() == 9) 
            {
                declareTieOnScoreBoard();
                disableAllButtons();
                showPlayAgainOptions();
            } 
            else 
            {
                if (currentPlayer == player1) 
                {
                    currentPlayer = player2;
                } 
                else 
                {
                    currentPlayer = player1;
                }
                updateWhosTurnItIs(currentPlayer);
            }
        } 
        else // if place NOT SuccessfullyMarked
        {
            scoreBoard.setText("That spot is already marked");
        }

    }

    private void figureOutRowAndColumn(ButtonPosition BP, Button clickedButton) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttonArray[i][j] == clickedButton) {
                    BP.setRow(i);
                    BP.setCol(j);
                    break;
                }
            }
        }
    }

    private void markButton(Button button, Player player) {
        button.setText(player.getSymbol());
    }

    private void declareWinnerOnScoreBoard(Player player) {
        scoreBoard.setText(player.getName() + " you won!");
    }

    private void declareTieOnScoreBoard() {
        scoreBoard.setText("Game ended in a tie!");
    }

    private void disableAllButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttonArray[i][j].setDisable(true);
            }
        }
    }

    private void enableAllButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttonArray[i][j].setDisable(false);
            }
        }
    }

    private void showPlayAgainOptions() {
        labelPlayAgain.setVisible(true);
        hBoxPlayAgain.setVisible(true);
    }

    private void hidePlayAgainOptions() {
        labelPlayAgain.setVisible(false);
        hBoxPlayAgain.setVisible(false);
    }

    @FXML
    private void yesButtonHandler() throws IOException {  
        hidePlayAgainOptions();
        resetGame();
    }

    @FXML
    private void noButtonHandler() throws IOException { 
        hidePlayAgainOptions();
        disableAllButtons();
        showAndUpdatePlayerScores();    
        if(player1.getTotalWins() == player2.getTotalWins())
        {
            scoreBoard.setText("You both tied!");
        }
        else
        {
            Player winner = TTTLogic.checkWhoWonMostGames(player1, player2);
            int totalWins = winner.getTotalWins();
            if(totalWins == 1) 
            {
                scoreBoard.setText(winner.getName()+" won with "+totalWins+" win!");
            }
            else 
            {
                scoreBoard.setText(winner.getName()+" won with "+totalWins+" wins!");
            }
            
        }
    }

}

