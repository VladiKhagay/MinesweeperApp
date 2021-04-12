package com.vladi.minesweeperapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.vladi.minesweeperapp.logic.Game;
import com.vladi.minesweeperapp.logic.Minefield;
import com.vladi.minesweeperapp.logic.Tile;

public class TileAdapter extends BaseAdapter {

    private Minefield minefield;
    private Context context;

    private int cachedWidth = -1;
    private int cachedHeight = -1;

    public TileAdapter(Minefield minefield, Context context) {
        this.minefield = minefield;
        this.context = context;
    }

    @Override
    public int getCount() {

        return minefield.getTilesGrid().size();
    }

    @Override
    public Object getItem(int position) {

        return minefield.getTileAt(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TileView tileView;

        if (convertView != null) {
            tileView = (TileView) convertView;
        } else {
            tileView = new TileView(context);
        }

        if (cachedHeight < 0 || cachedWidth < 0) {
            calculateSizes((GridView) parent);
        }

        ViewGroup.LayoutParams layoutParams = new GridView.LayoutParams(cachedWidth, cachedHeight);

        tileView.setLayoutParams(layoutParams);

        if (minefield.getTileAt(position).isRevealed()) {
            if (minefield.getTileAt(position).isMine()) {
                tileView.setImage(9);
            } else {
                tileView.setImage(minefield.getTileAt(position).getAdjacentMinesCount());
            }
        } else if (minefield.getTileAt(position).isFlagged()){
            tileView.setImage(11);

        } else {
            tileView.setImage(10);
        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return tileView;
    }

    private void calculateSizes(GridView gridView) {

        int hSpacing = gridView.getHorizontalSpacing();
        int vSpacing = gridView.getVerticalSpacing();

        int columns = gridView.getNumColumns();
        int rows = columns;

        int hPaddingSize = hSpacing * (columns - 1);
        int vPaddingSize = vSpacing * (rows - 1);

        cachedWidth = (gridView.getWidth() - hPaddingSize) / columns;
        cachedHeight = (gridView.getHeight() - vPaddingSize) / rows;

    }
}
