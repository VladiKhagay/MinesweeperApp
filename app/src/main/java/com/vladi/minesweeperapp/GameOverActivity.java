package com.vladi.minesweeperapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {

    private static String GAME_OVER_STATUS;
    private static String GAME_DIFFICULTY;
    private static String GAME_SCORE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        loadPrefs();

        setGameStatusTextView();
        setGameScoreTextView();
        setMainMenuButton();
        setPlayAgainButton();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    private void setGameStatusTextView() {
        TextView resultTextView = findViewById(R.id.result_text_view);
        switch (GAME_OVER_STATUS) {
            case "Won":
                resultTextView.setText(R.string.won);
                break;
            case "Lost":
                resultTextView.setText(R.string.lost);
                break;
        }
    }

    private void setGameScoreTextView() {
        TextView scoreTextView = findViewById(R.id.score_text_view);
        scoreTextView.setText(GAME_SCORE + " sec");

    }

    private void setPlayAgainButton() {
        Button playAgainButton = findViewById(R.id.play_again_button);

        playAgainButton.setOnClickListener(v -> {
            Intent intent = new Intent(GameOverActivity.this, GameActivity.class);
            intent.putExtra(GAME_DIFFICULTY, GAME_DIFFICULTY);
            updateTopScore();
            startActivity(intent);
        });
    }

    private void setMainMenuButton() {

        Button mainMenuButton = findViewById(R.id.main_menu_button);

        mainMenuButton.setOnClickListener(v -> {
            Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
            updateTopScore();
            startActivity(intent);
        });

    }

    private void loadPrefs() {
        SharedPreferences prefs = getSharedPreferences(MainActivity.GAME_OVER_PREFS, MODE_PRIVATE);

        GAME_OVER_STATUS = prefs.getString(MainActivity.GAME_OVER_STATUS, "");
        GAME_SCORE = prefs.getString(MainActivity.GAME_SCORE, "-1");
        GAME_DIFFICULTY = prefs.getString(MainActivity.GAME_DIFFICULTY, "Beginner");

    }


    private void updateTopScore() {

        SharedPreferences preferences = getSharedPreferences(MainActivity.TOP_SCORES_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int currentScore = 0;
        int newScore = Integer.parseInt(GAME_SCORE);

        if (GAME_OVER_STATUS == "Won") {
            switch (GAME_DIFFICULTY) {
                case "Beginner":
                    currentScore = Integer.parseInt(preferences.getString(MainActivity.BEGINNERS_TOP_SCORE, "0"));
                    break;
                case "Intermediate":
                    currentScore = Integer.parseInt(preferences.getString(MainActivity.INTERMEDIATE_TOP_SCORE, "0"));
                    break;
                case "Expert":
                    currentScore = Integer.parseInt(preferences.getString(MainActivity.EXPERTS_TOP_SCORE, "0"));
                    break;
            }

            if (currentScore == 0) {
                setTopScorePrefs(GAME_DIFFICULTY, newScore, editor);
            } else if (newScore < currentScore) {
                setTopScorePrefs(GAME_DIFFICULTY, newScore, editor);
            }
        }
    }

    private void setTopScorePrefs(String difficulty, int newScore, SharedPreferences.Editor editor) {

        switch (difficulty) {
            case "Beginner":
                editor.putString(MainActivity.BEGINNERS_TOP_SCORE, String.valueOf(newScore));
                break;
            case "Intermediate":
                editor.putString(MainActivity.INTERMEDIATE_TOP_SCORE, String.valueOf(newScore));
                break;
            case "Expert":
                editor.putString(MainActivity.EXPERTS_TOP_SCORE, String.valueOf(newScore));
                break;
        }

        editor.commit();

    }
}