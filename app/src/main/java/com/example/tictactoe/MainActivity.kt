package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {

    //2d array of buttons
    lateinit var board:Array<Array<Button>>

    //Board status
    var boardStatus = Array(3){IntArray(3)}

    var player1 = true
    var turnCount = 0;

    //TextView to show messages
    lateinit var msgTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize buttons
        initializeButtons()
        //initialize board status
        initilizeBoard()

        val resetBtn :Button = findViewById(R.id.resetBtn)
        resetBtn.apply {
            setOnClickListener{
                player1 = true
                turnCount =0
                initilizeBoard()
            }
        }

    }

    private fun initilizeBoard() {
        msgTextView.text="Player X"
        for(row in 0..2){
            for(column in 0..2){
                boardStatus[row][column] = -1
                board[row][column].isEnabled = true;
                board[row][column].text = ""
            }
        }
    }

    private fun initializeButtons() {
        //view binding
        board = arrayOf(
            arrayOf(findViewById(R.id.btn1),findViewById(R.id.btn2),findViewById(R.id.btn3)),
            arrayOf(findViewById(R.id.btn4),findViewById(R.id.btn5),findViewById(R.id.btn6)),
            arrayOf(findViewById(R.id.btn7),findViewById(R.id.btn8),findViewById(R.id.btn9))
        )
        msgTextView = findViewById(R.id.playerNameTextView)
        //setting up listener for buttons
        for(i in board){
            for(button in i){
                button.setOnClickListener(this)
            }
        }
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.btn1 -> {updateValue(row= 0, column=0) }
            R.id.btn2 -> {updateValue(row= 0, column=1) }
            R.id.btn3 -> {updateValue(row= 0, column=2) }
            R.id.btn4 -> {updateValue(row= 1, column=0) }
            R.id.btn5 -> {updateValue(row= 1, column=1) }
            R.id.btn6 -> {updateValue(row= 1, column=2) }
            R.id.btn7 -> {updateValue(row= 2, column=0) }
            R.id.btn8 -> {updateValue(row= 2, column=1) }
            R.id.btn9 -> {updateValue(row= 2, column=2) }
        }
        player1= !player1
        turnCount++;
        val msg = if(turnCount == 9) "Draw!"
        else if(player1) "Player X"
        else "Player O"
        updateMessage(msg)
        checkWinner()
    }

    private fun checkWinner() {
        var found:Boolean = false;
        //Horizontal Rows
        if(!found)
        for(i in 0 .. 2){
            if(boardStatus[i][0]!=-1 &&boardStatus[i][0]==boardStatus[i][1] && boardStatus[i][1]==boardStatus[i][2]){
                val msg = if(boardStatus[i][0] == 1) "Player X Wins!" else "Player O Wins!"
                found = true
                updateMessage(msg)
                break
            }
        }

        //Vertical Rows
        if(!found)
        for(i in 0 .. 2){
            if(boardStatus[0][i]!=-1 &&boardStatus[0][i]==boardStatus[1][i] && boardStatus[1][i]==boardStatus[2][i]){
                val msg = if(boardStatus[i][0] == 1) "Player X Wins!" else "Player O Wins!"
                found = true
                updateMessage(msg)
                break
            }
        }

        //Diagonals
        if(!found)
            if(boardStatus[0][0]!=-1 &&boardStatus[0][0]==boardStatus[1][1] && boardStatus[1][1]==boardStatus[2][2]){
                val msg = if(boardStatus[0][0] == 1) "Player X Wins!" else "Player O Wins!"
                updateMessage(msg)
                found = true
            }

        if(!found)
            if(boardStatus[0][2]!=-1 &&boardStatus[0][2]==boardStatus[1][1] && boardStatus[1][1]==boardStatus[2][0]){
                val msg = if(boardStatus[0][2] == 1) "Player X Wins!" else "Player O Wins!"
                updateMessage(msg)
                found = true
            }

    }

    private fun disableButtons(){
        for(row in 0..2){
            for(column in 0..2){
                board[row][column].isEnabled = false;
            }
        }
    }

    private fun updateMessage(msg:String) {
        msgTextView.text = msg
        if(msg.contains("Wins!"))
            disableButtons()

    }

    private fun updateValue(row: Int, column: Int) {
        val sign:String  = if(player1) "X" else "O"
        val value = if(player1) 1 else 0
        board[row][column].apply {
            text=sign
            isEnabled = false
        }
        boardStatus[row][column] = value
    }

}
