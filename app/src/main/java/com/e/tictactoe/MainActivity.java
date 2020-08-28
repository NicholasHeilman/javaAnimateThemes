package com.e.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";
    //    private static final String TAG = "MainActivity";
    // 1 = yellow 2 = red
    int activePlayer = 1;
    boolean isGameActive = true;
    int[] gameState = {0,0,0,0,0,0,0,0,0};
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    int gameMoveCount = 0;
    int[] gameScore = {0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dropIn(View view) {
        ImageView playerChip = (ImageView) view;
        int tappedTag = Integer.parseInt(playerChip.getTag().toString());

        //check if space is occupied
        if( gameState[tappedTag] == 0 && isGameActive ) {
            gameState[tappedTag] = activePlayer;
            playerChip.setTranslationY(-1500);
            if (activePlayer == 1) {
                playerChip.setImageResource(R.drawable.yellow);
                activePlayer = 2;
            } else {
                playerChip.setImageResource(R.drawable.red);
                activePlayer = 1;
            }
            gameMoveCount++;
            playerChip.animate().translationY(0).rotationBy(500).setDuration(1000).start();

            //  draw game condition
            if(gameMoveCount >=9 && isGameActive){
                isGameActive = false;
                TextView winnerTextView = findViewById(R.id.winnerTextView);
                Button playAgainButton =  findViewById(R.id.playAgainButton);
                winnerTextView.setText(R.string.draw_game);
                winnerTextView.setVisibility(View.VISIBLE);
                playAgainButton.setVisibility(View.VISIBLE);
            }

            //check winning conditions
            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 0) {
                    String winner;
                    isGameActive = false;
                    if (activePlayer == 2) {
                        winner = "Yellow";
                        gameScore[0]++;
                        Log.i(TAG, String.format("%s", Arrays.toString(gameScore)));
                    } else {
                        winner = "Red";
                        gameScore[1]++;
                        Log.i(TAG, String.format("%s", Arrays.toString(gameScore)));
                    }
                    TextView winnerTextView = findViewById(R.id.winnerTextView);
                    Button playAgainButton =  findViewById(R.id.playAgainButton);
                    winnerTextView.setText(String.format("%s Wins!!", winner));
                    winnerTextView.setVisibility(View.VISIBLE);
                    playAgainButton.setVisibility(View.VISIBLE);
                    Log.i(TAG, String.format("%s", Arrays.toString(gameScore)));
                }
            }
        } else {
            Toast.makeText(this, "Pick another space", Toast.LENGTH_SHORT).show();
        }// end winning condition

    }// end dropIn

    public void resetGame(View view) {
        TextView winnerTextView = findViewById(R.id.winnerTextView);
        Button playAgainButton =  findViewById(R.id.playAgainButton);
        winnerTextView.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for(int i = 0; i < gridLayout.getChildCount(); i++){
            ImageView child = (ImageView) gridLayout.getChildAt(i);
            child.setImageDrawable(null);
        }
        activePlayer = 1;
        isGameActive = true;
        Arrays.fill(gameState, 0);
        gameMoveCount = 0;
    }// end resetGame
}