package com.vladi.minesweeperapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.vladi.minesweeperapp.logic.Game;
import com.vladi.minesweeperapp.logic.Minefield;

public class GameActivity extends AppCompatActivity {

    private GridView gridView;
    private TextView timeTextView;
    private TextView flagsCountTextView;

    private Game game = Game.getInstance();
    private Minefield minefield;

    private Handler handler;
    private Runnable timeRunnable;
    private int time;
    private int flagsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setUpGridView();
        timeTextView = findViewById(R.id.time_counter);
        flagsCountTextView = findViewById(R.id.flags_counter);
        time = 0;
        timeTextView.setText(String.valueOf(time));
        flagsCount = minefield.getNumOfMines();
        flagsCountTextView.setText(String.valueOf(flagsCount));

    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
    }

    /**
     * Method to set up the grid view.
     */
    private void setUpGridView() {

        gridView = findViewById(R.id.grid_view);
        game.initGame(setGameDifficulty());
        this.minefield = game.getMineField();
        gridView.setNumColumns(minefield.getSize());

        gridView.setAdapter(new TileAdapter(game.getMineField(), getApplicationContext()));

        gridView.setOnItemLongClickListener(new TileLongClickListener());

        gridView.setOnItemClickListener(new TileClickedListener());

    }

    /**
     * Method to set the game difficulty.
     * @return
     */
    private Game.Difficulty setGameDifficulty() {
        SharedPreferences preferences = getSharedPreferences(MainActivity.GAME_OVER_PREFS, MODE_PRIVATE);

        String diffString = preferences.getString(MainActivity.GAME_DIFFICULTY, "Beginner");

        return Game.Difficulty.valueOf(diffString.toUpperCase());
    }

    /**
     * Method to start the time count-up;
     */
    private void startTimer() {
        if (game.getGameState() != Game.GameState.PLAYING) {
            return;
        }
        handler = new Handler();
        timeRunnable = () -> {

            time++;
            handler.postDelayed(timeRunnable, 1000);
            timeTextView.setText(String.valueOf(time));
        };

        handler.postDelayed(timeRunnable, 1000);
    }

    /**
     * Method to stop the time count-up.
     */
    private void stopTimer() {
        if (handler != null) {
            handler.removeCallbacks(timeRunnable);
        }

    }

    /**
     * Method to refresh the view
     */
    private void refreshGridView() {
        ((TileAdapter) gridView.getAdapter()).notifyDataSetChanged();

        if (game.isWon() || game.isLost()) {
            handleGameOver();
        }
    }


    /**
     * Method to handle game over.
     * stops the timer, removes the grid view listeners.
     * sets new listener to continue to the next activity.
     * shows a toast with a message
     */
    private void handleGameOver() {
        stopTimer();
        gridView.setOnItemLongClickListener(null);
        gridView.setOnItemClickListener((parent, view, position, id) -> {

                Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
                setPrefs();

                startActivity(intent);
        });

        ConstraintLayout mConstraintLayout = (ConstraintLayout) findViewById(R.id.root);
        mConstraintLayout.setOnClickListener(v -> {
            Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
            setPrefs();

            startActivity(intent);
        });
        Toast.makeText(getApplicationContext(), R.string.game_over_toast, Toast.LENGTH_LONG).show();

    }


    /**
     * Method to refresh the flag count.
     */
    private void refreshFlagsCountView() {
        runOnUiThread(() -> flagsCountTextView.setText(String.valueOf(flagsCount)));
    }

    /**
     * Method to set the app preferences
     */
    private void setPrefs() {
        SharedPreferences pref = getSharedPreferences(MainActivity.GAME_OVER_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(MainActivity.GAME_OVER_STATUS, game.getGameState().toString());
        editor.putString(MainActivity.GAME_SCORE, String.valueOf(time));
        editor.commit();
    }

    /**
     * Private Listener class to handle tile click.
     */
    private class TileClickedListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if (game.getGameState() != Game.GameState.LOST) {
                game.handleTileClick(minefield.getTileAt(position));

                refreshGridView();
            }

            if (handler == null) {
                runOnUiThread(GameActivity.this::startTimer);
            }
        }
    }


    /**
     * Private listener class to handle long click on a tile.
     */
    private class TileLongClickListener implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            if (game.getGameState() != Game.GameState.LOST) {

                if (!minefield.getTileAt(position).isFlagged()) {
                    minefield.getTileAt(position).setFlagged(true);
                    flagsCount--;

                } else {
                    game.getMineField().getTileAt(position).setFlagged(false);
                    flagsCount++;
                }
                refreshFlagsCountView();
                refreshGridView();
            }

            if (handler == null) {
                runOnUiThread(GameActivity.this::startTimer);
            }
            return true;
        }
    }
}