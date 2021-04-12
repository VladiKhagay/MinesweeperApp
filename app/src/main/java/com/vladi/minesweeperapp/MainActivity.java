package com.vladi.minesweeperapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Static string variables for the scores preferences.
    public static final String TOP_SCORES_PREFS = "TopScoresPrefs";
    public static final String BEGINNERS_TOP_SCORE = "BeginnerTopScore";
    public static final String INTERMEDIATE_TOP_SCORE = "IntermediatesTopScore";
    public static final String EXPERTS_TOP_SCORE = "ExpertsTopScore";

    // Static string variables for the last game  attributes preferences.
    public static final String GAME_OVER_PREFS = "GameOverPrefs";
    public static final String GAME_OVER_STATUS = "GameOverStatus";
    public static final String GAME_SCORE = "GameScore";
    public static final String GAME_DIFFICULTY = "GameDifficulty";

    public static String SELECTED_DIFFICULTY;

    private Spinner difficulty_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpDifficultySpinner();
        setUpStartButton();
        setTopScores();
    }

    @Override
    protected void onResume() {
        super.onResume();
        difficulty_spinner = findViewById(R.id.diff_spinner);
        difficulty_spinner.setSelection(restoreDifficultyPrefs());
    }

    private void setUpStartButton() {
        Button startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra(SELECTED_DIFFICULTY, difficulty_spinner.getSelectedItem().toString());
            startActivity(intent);
        });
    }

    /**
     * Method to setup the difficulty selection spinner
     */
    private void setUpDifficultySpinner() {
        difficulty_spinner = findViewById(R.id.diff_spinner);

        difficulty_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SELECTED_DIFFICULTY = difficulty_spinner.getSelectedItem().toString();
                storeDifficultyPrefs();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // set the current selection as the most recent difficulty selected
        difficulty_spinner.setSelection(restoreDifficultyPrefs());

    }


    /**
     * Method to set the top scores of the game.
     * sets the scores that stored in the scores preferences.
     */
    @SuppressLint("SetTextI18n")
    private void setTopScores() {
        TextView beginners_top = findViewById(R.id.beginners_top);
        TextView interm_top = findViewById(R.id.interm_top);
        TextView experts_top = findViewById(R.id.experts_top);
        try {

            SharedPreferences preferences = getSharedPreferences(TOP_SCORES_PREFS, MODE_PRIVATE);

            beginners_top.setText(preferences.getString(BEGINNERS_TOP_SCORE, "0") + " sec");
            interm_top.setText(preferences.getString(INTERMEDIATE_TOP_SCORE, "0") + " sec");
            experts_top.setText(preferences.getString(EXPERTS_TOP_SCORE, "0") + " sec");
        } catch (Exception e) {
            // do nothing
        }
    }


    /**
     * Method to store the recent difficulty selected in the preferences.
     */
    private void storeDifficultyPrefs() {

        try {
            difficulty_spinner = findViewById(R.id.diff_spinner);

            String diff = difficulty_spinner.getSelectedItem().toString();

            SharedPreferences preferences = getSharedPreferences(GAME_OVER_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString(GAME_DIFFICULTY, diff);
            editor.commit();

        } catch (Exception e) {
            // do nothing
        }


    }


    /**
     * Method to restore the recent difficulty from the preferences.
     */
    private int restoreDifficultyPrefs() {
        try {
            SharedPreferences preferences = getSharedPreferences(GAME_OVER_PREFS, MODE_PRIVATE);
            String diff = preferences.getString(GAME_DIFFICULTY, "Beginner");

            switch (diff) {
                case "Intermediate":
                    return 1;
                case "Expert":
                    return 2;
                default:
                    return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }
}