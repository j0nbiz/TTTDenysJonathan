package five.team.tttdenysjonathan;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TicTacToe extends AppCompatActivity {
    // Shared preferences
    public static final String PREF_SCORES = "Scores";

    // State instances
    public static final String STATE_SCOREX = "scoreX";
    public static final String STATE_SCOREY = "scoreY";
    public static final String STATE_SCORET = "scoreT";


    // Game variables
    private int round = 0;
    private int gameMode = 0;
    private List<Tile> tileGrid = new ArrayList<Tile>(9);
    private int scoreX = 0;
    private int scoreO = 0;
    private int scoreT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictactoe);

        // Restore file first
        SharedPreferences settings = getSharedPreferences(PREF_SCORES, 0);
        scoreX = settings.getInt("scoreX", 0);
        scoreO = settings.getInt("scoreO", 0);
        scoreT = settings.getInt("scoreT", 0);

        // Restore instance second
        if (savedInstanceState != null) {
            scoreX = savedInstanceState.getInt(STATE_SCOREX);
            scoreO = savedInstanceState.getInt(STATE_SCOREY);
            scoreT = savedInstanceState.getInt(STATE_SCORET);
        }

        SetArrayOfGameBlocks();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save scores
        savedInstanceState.putInt(STATE_SCOREX, scoreX);
        savedInstanceState.putInt(STATE_SCOREY, scoreO);
        savedInstanceState.putInt(STATE_SCORET, scoreT);

        // Calling super class
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();

        // Save preferences
        savePrefs();
    }

    public void savePrefs() {
        // Saving preferences
        SharedPreferences settings = getSharedPreferences(PREF_SCORES, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("scoreX", scoreX);
        editor.putInt("scoreO", scoreO);
        editor.putInt("scoreT", scoreT);

        // Saved
        editor.commit();
    }

    public void SetArrayOfGameBlocks() {
        // Setup a 3x3 grid of buttons
        tileGrid.add(0, new Tile((ImageButton) findViewById(R.id.btn_1_1)));
        tileGrid.add(1, new Tile((ImageButton) findViewById(R.id.btn_1_2)));
        tileGrid.add(2, new Tile((ImageButton) findViewById(R.id.btn_1_3)));

        tileGrid.add(3, new Tile((ImageButton) findViewById(R.id.btn_2_1)));
        tileGrid.add(4, new Tile((ImageButton) findViewById(R.id.btn_2_2)));
        tileGrid.add(5, new Tile((ImageButton) findViewById(R.id.btn_2_3)));

        tileGrid.add(6, new Tile((ImageButton) findViewById(R.id.btn_3_1)));
        tileGrid.add(7, new Tile((ImageButton) findViewById(R.id.btn_3_2)));
        tileGrid.add(8, new Tile((ImageButton) findViewById(R.id.btn_3_3)));

        lockBoard();
    }

    public void checkVictoryCondition() {
        // Increment round
        round++;

        // Check for X
        if (
            // Horizontal
                (tileGrid.get(0).isX() && tileGrid.get(1).isX() && tileGrid.get(2).isX()) ||
                        (tileGrid.get(3).isX() && tileGrid.get(4).isX() && tileGrid.get(5).isX()) ||
                        (tileGrid.get(6).isX() && tileGrid.get(7).isX() && tileGrid.get(8).isX()) ||
                        // Vertical
                        (tileGrid.get(0).isX() && tileGrid.get(3).isX() && tileGrid.get(6).isX()) ||
                        (tileGrid.get(1).isX() && tileGrid.get(4).isX() && tileGrid.get(7).isX()) ||
                        (tileGrid.get(2).isX() && tileGrid.get(5).isX() && tileGrid.get(8).isX()) ||
                        //Diagonal
                        (tileGrid.get(0).isX() && tileGrid.get(4).isX() && tileGrid.get(8).isX()) ||
                        (tileGrid.get(2).isX() && tileGrid.get(4).isX() && tileGrid.get(6).isX())
                ) {
            lockBoard();
            gameMode = 0; // Stop the game and prevent ai from player right after human
            scoreX++;
            showEndGameDialog("X wins the game! Press reset to play again."); // New dialog for win according to specs
        }

        // Check for O
        if (
            // Horizontal
                (tileGrid.get(0).isO() && tileGrid.get(1).isO() && tileGrid.get(2).isO()) ||
                        (tileGrid.get(3).isO() && tileGrid.get(4).isO() && tileGrid.get(5).isO()) ||
                        (tileGrid.get(6).isO() && tileGrid.get(7).isO() && tileGrid.get(8).isO()) ||
                        // Vertical
                        (tileGrid.get(0).isO() && tileGrid.get(3).isO() && tileGrid.get(6).isO()) ||
                        (tileGrid.get(1).isO() && tileGrid.get(4).isO() && tileGrid.get(7).isO()) ||
                        (tileGrid.get(2).isO() && tileGrid.get(5).isO() && tileGrid.get(8).isO()) ||
                        //Diagonal
                        (tileGrid.get(0).isO() && tileGrid.get(4).isO() && tileGrid.get(8).isO()) ||
                        (tileGrid.get(2).isO() && tileGrid.get(4).isO() && tileGrid.get(6).isO())
                ) {
            lockBoard();
            gameMode = 0; // Stop the game and prevent ai from player right after human
            scoreO++;
            showEndGameDialog("O wins the game! Press reset to play again."); // New dialog for win according to specs
        }

        // Check for Tie
        if (round == 9 && gameMode != 0) {
            lockBoard();
            scoreT++;
            Toast.makeText(TicTacToe.this, "The game is tied! Press reset to play again.", Toast.LENGTH_SHORT).show(); // Toast for tie according to specs
        }
    }

    public void lockBoard() {
        // Disable clicking
        for (Tile tile : tileGrid) {
            tile.getButton().setClickable(false);
        }
    }

    public void unlockBoard() {
        // Enable clicking
        for (Tile tile : tileGrid) {
            tile.getButton().setClickable(true);
        }
    }

    public void showEndGameDialog(String msg) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(msg);
        dialog.setTitle("Game Result X:" + scoreX);
        dialog.setPositiveButton("Ok", null);
        dialog.create().show();

    }

    public void showAbout(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(R.string.about);
        dialog.setTitle(R.string.abouthead);
        dialog.setPositiveButton("Ok", null);
        dialog.create().show();
    }

    public void showScore(View view) {
        // Launching activity
        Intent score = new Intent(TicTacToe.this, Score.class);
        // Passing score
        score.putExtra("scoreX", scoreX);
        score.putExtra("scoreO", scoreO);
        score.putExtra("scoreT", scoreT);

        startActivity(score);
    }

    public void startAI(View view) {
        gameMode = 1;
        startGame();
    }

    public void startHuman(View view) {
        gameMode = 2;
        startGame();
    }

    public void startGame() {
        // Hide gamemode buttons
        Button singleButton = (Button) findViewById(R.id.btn_startai);
        singleButton.setVisibility(View.INVISIBLE);
        Button multiButton = (Button) findViewById(R.id.btn_starthuman);
        multiButton.setVisibility(View.INVISIBLE);

        // Unlock board
        unlockBoard();
    }

    public void resetGame(View view) {
        // Reset tiles
        for (Tile tile : tileGrid) {
            tile.reset();
        }

        // Set round number to 0
        round = 0;

        // Show gamemode buttons
        Button singleButton = (Button) findViewById(R.id.btn_startai);
        singleButton.setVisibility(View.VISIBLE);
        Button multiButton = (Button) findViewById(R.id.btn_starthuman);
        multiButton.setVisibility(View.VISIBLE);
    }

    public void onClick(View view) {
        for (Tile tile : tileGrid) {
            if (tile.getButton().getId() == view.getId()) {
                chooseTile(tile);
            }
        }

        // Make ai play right after if vs ai
        if (gameMode == 1 && round < 9) {
            aiMove();
        }
    }

    public void aiMove() {
        int num = (int) (Math.random() * 9);

        while (tileGrid.get(num).isOwned()) {
            num = (int) (Math.random() * 9);
        }

        chooseTile(tileGrid.get(num));
    }

    public void chooseTile(Tile tile) {
        if (round % 2 == 0) {
            tile.setX();
        } else {
            tile.setO();
        }

        checkVictoryCondition();
    }

    public void zeroAll(View view) {
        // Reset scores
        scoreX = 0;
        scoreO = 0;
        scoreT = 0;

        // Save preferences
        savePrefs();
    }
}
