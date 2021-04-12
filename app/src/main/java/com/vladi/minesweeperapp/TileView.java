package com.vladi.minesweeperapp;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vladi.minesweeperapp.logic.Game;
import com.vladi.minesweeperapp.logic.Minefield;

public class TileView extends LinearLayout {

    public TileView(@NonNull Context context) {
        super(context);

    }

    public void setImage(int image) {
        switch (image){
            case 0:
                this.setBackground(getResources().getDrawable(R.drawable.tilenum0,null));
                break;
            case 1:
                this.setBackground(getResources().getDrawable(R.drawable.tilenum1, null));

                break;
            case 2:
                this.setBackground(getResources().getDrawable(R.drawable.tilenum2,null));
                break;
            case 3:
                this.setBackground(getResources().getDrawable(R.drawable.tilenum3,null));

                break;
            case 4:
                this.setBackground(getResources().getDrawable(R.drawable.tilenum4,null));

                break;
            case 5:
                this.setBackground(getResources().getDrawable(R.drawable.tilenum5,null));

                break;
            case 6:
                this.setBackground(getResources().getDrawable(R.drawable.tilenum6,null));

                break;
            case 7:
                this.setBackground(getResources().getDrawable(R.drawable.tilenum7,null));

                break;
            case 8:
                this.setBackground(getResources().getDrawable(R.drawable.tilenum8,null));

                break;
            case 9:
                this.setBackground(getResources().getDrawable(R.drawable.bomb,null));
                break;

            case 10:
                this.setBackground(getResources().getDrawable(R.drawable.uncoverd,null));
                break;

            case 11:
                this.setBackground(getResources().getDrawable(R.drawable.flagged, null));
                break;

        }
    }
}
