package com.vladi.minesweeperapp.logic;

public class Tile {

    private int adjacentMinesCount;
    private boolean isFlagged, isMine, isRevealed;

    public Tile() {
        this.adjacentMinesCount = 0;
        this.isFlagged = false;
        this.isMine = false;
        this.isRevealed = false;
    }

    public int getAdjacentMinesCount() {
        return adjacentMinesCount;
    }

    public void setAdjacentMinesCount(int adjacentMinesCount) {
        this.adjacentMinesCount = adjacentMinesCount;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

}
