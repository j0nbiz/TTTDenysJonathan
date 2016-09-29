package five.team.tttdenysjonathan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int round = 0;
    private int gameMode = 1;
    private List<Tile> tileGrid = new ArrayList<Tile>(9);
    private int scoreX = 0;
    private int scoreO = 0;
    private int[] gameArray = new int[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetArrayOfGameBlocks();

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
    }

    public void checkVictoryCondition() {
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
            scoreX++;
            showEndGameDialog("X wins the game!");
        } else if (
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
            scoreO++;
            showEndGameDialog("O wins the game!");
        }

        if (round == 9) {
            showEndGameDialog("It's a draw!");
        }
    }

    public void showEndGameDialog(String gameRest) {
        AlertDialog.Builder dlgVictory = new AlertDialog.Builder(this);
        dlgVictory.setMessage(gameRest);
        dlgVictory.setTitle("Game Result");
        dlgVictory.setPositiveButton("Ok", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                resetGame(null);
            }
        });
        dlgVictory.create().show();

    }

    public void showAbout(View view) {
        AlertDialog.Builder dlgAbout = new AlertDialog.Builder(this);
        dlgAbout.setMessage(R.string.about);
        dlgAbout.setTitle(R.string.abouthead);
        dlgAbout.setPositiveButton("Ok", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dlgAbout.create().show();
    }

    public void startGame(View view) {
        if (findViewById(R.id.oneplayer) == view)
            gameMode = 1;
        else
            gameMode = 2;

        // Hide gamemode buttons
        Button singleButton = (Button) findViewById(R.id.oneplayer);
        singleButton.setVisibility(View.INVISIBLE);
        Button multiButton = (Button) findViewById(R.id.multiplayer);
        multiButton.setVisibility(View.INVISIBLE);

        // Reveal tiles
        for (Tile tile : tileGrid) {
            tile.getButton().setClickable(true);
        }
    }

    public void resetGame(View view) {
        // Reset grid images and clickable
        for (Tile tile : tileGrid) {
            tile.reset();
        }

        // Set round number to 0
        round = 0;

        // Show orignial buttons
        Button singleButton = (Button) findViewById(R.id.oneplayer);
        singleButton.setVisibility(View.VISIBLE);
        Button multiButton = (Button) findViewById(R.id.multiplayer);
        multiButton.setVisibility(View.VISIBLE);
    }

    public void onClick(View view) {
        if (round % 2 == 0) {
            // Match button id to tile button id with for loop
            for(Tile tile: tileGrid){
                if(tile.getButton().getId() == view.getId()){
                    tile.setX();
                }
            }
        } else {
            // Match button id to tile button id with for loop
            for(Tile tile: tileGrid){
                if(tile.getButton().getId() == view.getId()){
                    tile.setO();
                }
            }
        }

        // Check for win after human plays
        round++;
        checkVictoryCondition();

        // Make ai play right after if vs ai
        if (gameMode == 1 && round < 9) {
            aiMove();
        }
    }

    private void aiMove() {
        int num = (int) (Math.random() * 9);

        while (tileGrid.get(num).isOwned()) {
            num = (int) (Math.random() * 9);
        }

        tileGrid.get(num).setO();

        // Check for win after ai plays
        round++;
        checkVictoryCondition();
    }
}
