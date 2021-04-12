package com.vladi.minesweeperapp.logic;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Timer;

public class Game {

    public static int BEGINNER_MINES = 6;
    public static int INTERMEDIATE_MINES = 9;
    public static int EXPERT_MINES = 20;

    public static int BEGINNER_SIZE = 6;
    public static int INTERMEDIATE_SIZE = 9;
    public static int EXPERT_SIZE = 11;

    public enum GameState {
        // Enum that describes the state of the game.
        READY, PLAYING, WON, LOST;

        @NonNull
        @Override
        public String toString() {
            switch (this) {
                case READY:
                    return "Ready";
                case PLAYING:
                    return "Playing";
                case WON:
                    return "Won";
                case LOST:
                    return "Lost";
                default:
                    return "";
            }
        }
    }
    public enum Difficulty {

        //Enum that describes the game difficulty.
        BEGINNER, INTERMEDIATE, EXPERT;

        @NonNull
        @Override
        public String toString() {
            switch (this) {
                case BEGINNER:
                    return "Beginner";
                case INTERMEDIATE:
                    return "Intermediate";
                case EXPERT:
                    return "Expert";
                default:
                    return "";
            }
        }
    }

    // Instance of the game, only one instance creation is possible.
    private static Game instance = null;

    private int flagsCount;

    private Minefield mineField;
    private Difficulty difficulty;
    private GameState gameState;

    // private constructor.
    private Game() {
        this.flagsCount = 0;
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public Minefield getMineField() {
        return this.mineField;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }


    public GameState getGameState() {
        return this.gameState;
    }

    public void setGameState(GameState state) {
        this.gameState = state;
    }


    /**
     * Method that initialize the game due to the difficulty that passed.
     * @param difficulty
     */
    public void initGame(Difficulty difficulty) {
        this.setDifficulty(difficulty);
        switch (difficulty) {
            case BEGINNER:
                this.mineField = new Minefield(BEGINNER_SIZE, BEGINNER_MINES);
                this.mineField.initializeMinefield();
                this.setGameState(GameState.READY);

                break;

            case INTERMEDIATE:

                this.mineField = new Minefield(INTERMEDIATE_SIZE, INTERMEDIATE_MINES);
                this.mineField.initializeMinefield();
                this.setGameState(GameState.READY);

                break;

            case EXPERT:

                this.mineField = new Minefield(EXPERT_SIZE, EXPERT_MINES);
                this.mineField.initializeMinefield();
                this.setGameState(GameState.READY);

                break;
        }
    }

    /**
     * Method that updates the board after a click on a tile.
     * @param tile
     */
    public void handleTileClick(Tile tile) {
        if (this.gameState == GameState.READY) {
            this.gameState = GameState.PLAYING;
        }
        if (tile.isFlagged()) {
            // do nothing
            return;
        }

        if (tile.isMine()) {
            this.setGameState(GameState.LOST);
            revealAllMines();
            return;
        }

        if (tile.getAdjacentMinesCount() > 0) {
            tile.setRevealed(true);
            return;
        } else {
            revealTiles(tile);
        }
    }

    /**
     * Method that reveals recursively all the tiles relevant tiles.
     * reveals the adjacent blank tiles, until it gets to anon blank tile.
     * @param tile
     */
    public void revealTiles(Tile tile) {
        if (tile == null) {
            return;
        }
        if (tile.isRevealed() || tile.isMine() || tile.isFlagged()) {
            return;
        } else if (tile.getAdjacentMinesCount() > 0) {
            tile.setRevealed(true);
            return;
        } else {
            tile.setRevealed(true);
            int index = this.mineField.getTilesGrid().indexOf(tile);

            int[] coordinates = this.mineField.parseToXY(index);

            revealTiles(this.mineField.getTileAt(coordinates[0] - 1, coordinates[1] - 1));
            revealTiles(this.mineField.getTileAt(coordinates[0] - 1, coordinates[1]));
            revealTiles(this.mineField.getTileAt(coordinates[0] - 1, coordinates[1] + 1));
            revealTiles(this.mineField.getTileAt(coordinates[0], coordinates[1] - 1));
            revealTiles(this.mineField.getTileAt(coordinates[0], coordinates[1] + 1));
            revealTiles(this.mineField.getTileAt(coordinates[0] + 1, coordinates[1] - 1));
            revealTiles(this.mineField.getTileAt(coordinates[0] + 1, coordinates[1]));
            revealTiles(this.mineField.getTileAt(coordinates[0] +1, coordinates[1] + 1));

        }
    }

    /**
     * Reveals all the mined tiles.
     */
    public void revealAllMines(){
        for (int i = 0; i < mineField.getMinedTilesIndexes().length; i ++) {
            if (!mineField.getTileAt(mineField.getMinedTilesIndexes()[i]).isFlagged())
            mineField.getTileAt(mineField.getMinedTilesIndexes()[i]).setRevealed(true);
        }
    }

    /**
     * checks whether the game is won
     * @return
     */
    public boolean isWon() {
        boolean isWon = true;

        for (Tile tile : this.mineField.getTilesGrid()) {
            if (!tile.isMine()) {
                isWon = isWon & tile.isRevealed();
            }
        }

        if (isWon) {
            this.gameState = GameState.WON;
        }

        return isWon;
    }

    /**
     * check whether the game is lost
     * @return
     */
    public boolean isLost(){
        return this.gameState == GameState.LOST;
    }

}
