package org.brandonberg28;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

public class PrimaryController implements Initializable {


    @FXML
    private TextField scoreBoard;
    @FXML
    private TextField textFieldPlayerX;
    @FXML
    private TextField textFieldPlayerO;
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
    private TextArea textAreaPlayAgain;
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


    @FXML
    protected void startGameClicked() throws IOException {
        player1 = new PlayerX(textFieldPlayerX.getText());
        player2 = new PlayerO(textFieldPlayerO.getText());
        TTTLogic = new TicTacToeLogic(); 
        resetGame();
    }

    private void resetGame() {
        currentPlayer = player1;
        updateWhosTurnItIs(currentPlayer);        
        updateBoard();
    }

    private void updateWhosTurnItIs(Player player) {
        scoreBoard.setText(currentPlayer.getName()+" its your turn.");
    }

    private void updateBoard() {
        for(int i=0; i<3; i++)
        {
            for(int j=0; j<3; j++)
            {
                buttonArray[i][j].setText(TTTLogic.getTable()[i][j]);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        setupToolTips();
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

    //change  this method to initializeButtonArray
    private void setupToolTips() {
        //buttonStartGame.setTooltip(new Tooltip("This is an example tooltip"));
    }

    private void figureOutRowAndColumn(ButtonPosition BP, Button clickedButton) {
        for(int i=0; i<3; i++)
        {
            for(int j=0; j<3; j++)
            {
                if(buttonArray[i][j] == clickedButton)
                {
                    BP.row = i;
                    BP.col = j;
                    break;
                }
            }
        }
    }

    //var to hold currentPlayer, ButtonHandler() executes for current player, if button successfully marked then change currentPlayer, if currentPlayer is null dont allow button to be pressed
    //set state first and then change the userInterface based on the state of the model

    @FXML
    protected void ButtonHandler(ActionEvent e) {  

        Button clickedButton = (Button)e.getSource();
        ButtonPosition BP = new ButtonPosition();
        figureOutRowAndColumn(BP, clickedButton);
        boolean placeSuccessfullyMarked = TTTLogic.markPosition(currentPlayer, BP);

        if(placeSuccessfullyMarked)  //if the place was successfully marked
        {
            markButton(clickedButton,currentPlayer);
            TTTLogic.addToTurnCounter();
            if(TTTLogic.checkIfWinner(currentPlayer))
            {
                currentPlayer.addAWin();  //adds one win to the player object
                declareWinnerOnScoreBoard(currentPlayer);  //the currentPlayer should not be the winner
                disableAllButtons();
                //currentPlayer = null;
                //ask if they want to play again
                showPlayAgainOptions();
                //if yes then clear the board
                //then set the scoreboard to player 1 with one win
            }
            else if (TTTLogic.getTurnCounter() == 9) 
            {
                showPlayAgainOptions();
                declareTieOnScoreBoard();
            }
            else
            {
                if(currentPlayer == player1)
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
        else  //if PlaceIsAlreadyMarked
        {
            declareSpotAlreadyMarked();
        }
        
    }

    private void markButton(Button button, Player player){
        button.setText(player.getSymbol());
    }

    private void declareWinnerOnScoreBoard(Player player) {
        scoreBoard.setText(player.getName()+" you won!");
    }

    private void declareTieOnScoreBoard() {
        scoreBoard.setText("Game ended in a tie!");
    }

    private void disableAllButtons() {
        //for loop these
        button00.setDisable(true);
        button01.setDisable(true);
        button02.setDisable(true);
        button10.setDisable(true);
        button11.setDisable(true);
        button12.setDisable(true);
        button20.setDisable(true);
        button21.setDisable(true);
        button22.setDisable(true);
    }

    private void enableAllButtons() {
        //for loop these
        button00.setDisable(false);
        button01.setDisable(false);
        button02.setDisable(false);
        button10.setDisable(false);
        button11.setDisable(false);
        button12.setDisable(false);
        button20.setDisable(false);
        button21.setDisable(false);
        button22.setDisable(false);
    }


    private void declareSpotAlreadyMarked() {
        scoreBoard.setText("That spot is already marked");
    }

    private void showPlayAgainOptions() {   //this doesnt 
        textAreaPlayAgain.setVisible(true);
        textAreaPlayAgain.setText("Would you like to play again?");
        hBoxPlayAgain.setVisible(true);
    }

    @FXML
    private void yesButtonHandler() throws IOException {  //execute if yesButton is pressed
        TTTLogic.emptyTable();
        enableAllButtons();
        resetGame();
    }

    @FXML
    private void noButtonHandler() throws IOException {  //execute if noButton is pressed
        Player winner = TTTLogic.checkWhoWonMostGames(player1, player2);
        scoreBoard.setText(winner.getName()+" won with "+winner.getTotalWins()+" wins!");
        //not currentPlayer. check to see who has more wins and then show them when NO is pressed
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

}

