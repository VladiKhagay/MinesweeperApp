package com.vladi.minesweeperapp.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Minefield {
    private List<Tile> tilesGrid;
    private int size;
    private int numOfMines;
    private int[] minedTilesIndexes;


    public Minefield(int size, int numOfMines) {
        this.size = size;
        this.numOfMines = numOfMines;
        this.minedTilesIndexes = new int[numOfMines];
        this.tilesGrid = new ArrayList<>();
        for (int i = 0; i < size * size; i++) {
            tilesGrid.add(new Tile());
        }
    }

    public List<Tile> getTilesGrid() {

        return tilesGrid;
    }

    public int getSize() {
        return size;
    }

    public int getNumOfMines() {
        return numOfMines;
    }


    /**
     * Method to initialize the board.
     * place the mines, and set the adjacent mines count.
     */
    public void initializeMinefield() {
        int minesPlaced = 0;
        Random random = new Random();
        while (minesPlaced < this.numOfMines) {

            int x = random.nextInt(this.size);
            int y = random.nextInt(this.size);

            if (getTileAt(x, y) != null && !getTileAt(x, y).isMine()) {
                getTileAt(x, y).setMine(true);
                this.minedTilesIndexes[minesPlaced] = this.parsToIndex(x,y);
                minesPlaced++;
            }

        }
        this.setAdjacentMinesCount();
    }


    /**
     *
     * Method to  set the dajacent mines count on every tile in the board.
     *           |         |
     * i-1, j-1  |  i-1, j |  i-1,j+1
     * __________|_________|__________
     *           |         |
     * i, j-1    |   i, j  |  i, j+ 1
     * __________|_________|__________
     *           |         |
     * i+1, j-1  | i+1, j  |  i+1, j+1
     *           |         |
     */


    private void setAdjacentMinesCount() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                int minesCount = 0;

                if (this.getTileAt(i - 1, j - 1) != null && this.getTileAt(i - 1, j - 1).isMine()) {
                    minesCount++;
                }
                if (this.getTileAt(i - 1, j) != null && this.getTileAt(i - 1, j).isMine()) {
                    minesCount++;
                }
                if (this.getTileAt(i - 1, j + 1) != null && this.getTileAt(i - 1, j + 1).isMine()) {
                    minesCount++;
                }
                if (this.getTileAt(i, j - 1)!= null && this.getTileAt(i, j - 1).isMine()) {
                    minesCount++;
                }
                if (this.getTileAt(i, j + 1) != null && this.getTileAt(i, j + 1).isMine()) {
                    minesCount++;
                }
                if (this.getTileAt(i + 1, j - 1) != null &&this.getTileAt(i + 1, j - 1).isMine()) {
                    minesCount++;
                }
                if (this.getTileAt(i + 1, j)!= null && this.getTileAt(i + 1, j).isMine()) {
                    minesCount++;
                }
                if (this.getTileAt(i + 1, j + 1) != null && this.getTileAt(i + 1, j + 1).isMine()) {
                    minesCount++;
                }
                getTileAt(i, j).setAdjacentMinesCount(minesCount);

            }
        }
    }


    /**
     * Method that gets x and y coordinates and returns the relevant tile
     * @param x
     * @param y
     * @return
     */
    public Tile getTileAt(int x, int y) {
        if (x < 0 || x > this.size - 1 || y < 0 || y > this.size - 1) {
            return null;
        }

        return tilesGrid.get(parsToIndex(x, y));
    }

    /**
     * Mwthod that gets and index and returns the relevant tile.
     * @param index
     * @return
     */
    public Tile getTileAt(int index) {
        if (index >= size*size) {
            return null;
        }

        return tilesGrid.get(index);
    }

    public int[] getMinedTilesIndexes() {
        return this.minedTilesIndexes;
    }

    /**
     * Method that gets x and y coordinates and parses them to an index in the tiles list.
     * @param x
     * @param y
     * @return
     */
    private int parsToIndex(int x, int y) {
        return x + (y * this.size);
    }


    /**
     * method that gets and index and parses it to a x and y coordinates.
     * @param index
     * @return
     */
    public int[] parseToXY(int index) {
        int x, y;
        y = (int) (index / this.size);
        x = (int) (index - (y * this.size));

        return new int[]{x, y};
    }

}
