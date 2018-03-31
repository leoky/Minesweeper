package com.example.warship.Minesweeper;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

/**
 * Created by Inspiron on 2/18/2018.
 */

public class Block extends android.support.v7.widget.AppCompatButton{

    private boolean isCovered; // is block covered yet
    private boolean isMined; // does the block has a mine underneath
    private boolean isFlagged; // is block flagged as a potential mine
    private boolean isQuestionMarked; // is block question marked
    private boolean isClickable; // can block accept click events
    private int numberOfMinesInSurrounding; // number of mines in nearby blocks


    // set default properties for the block
    public void setDefaults()
    {
        isCovered = true;
        isMined = false;
        isFlagged = false;
        isQuestionMarked = false;
        isClickable = true;
        numberOfMinesInSurrounding = 0;

        this.setBackgroundResource(R.drawable.square_blue);
    }

    public Block(Context context)
    {
        super(context);
    }

    public Block(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public Block(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }


    // set text as nearby mine count
    public void updateNumber(int text)
    {
        if (text != 0)
        {
            this.setText(Integer.toString(text));
        }
    }

    // mark the block as disabled/opened
    // update the number of nearby mines
    public void setNumberOfSurroundingMines(int number)
    {
        this.setBackgroundResource(R.drawable.square_grey);

        updateNumber(number);
    }

    // set block as disabled/opened if false is passed
    // else enable/close it
    public void setBlockAsDisabled(boolean enabled)
    {
        if (!enabled)
        {
            this.setBackgroundResource(R.drawable.square_grey);
        }
        else
        {
            this.setBackgroundResource(R.drawable.square_blue);
        }
    }

    // uncover this block
    public void OpenBlock()
    {
        // cannot uncover a mine which is not covered
        if (!isCovered)
            return;

        setBlockAsDisabled(false);
        isCovered = false;

        // check if it has mine
        if (hasMine())
        {
            setMineIcon(false);
        }
        // update with the nearby mine count
        else
        {
            setNumberOfSurroundingMines(numberOfMinesInSurrounding);
        }
    }

    // does the block have any mine underneath
    public boolean hasMine()
    {
        return isMined;
    }


    public void setMineIcon(boolean enabled)
    {
        this.setText("M");

        if (!enabled)
        {
            this.setBackgroundResource(R.drawable.square_grey);
            this.setTextColor(Color.RED);
        }
        else
        {
            this.setTextColor(Color.BLACK);
        }
    }

    // set block as a mine underneath
    public void plantMine()
    {
        isMined = true;
    }

    // mine was opened
    // change the block icon and color
    public void triggerMine()
    {
        setMineIcon(true);
        this.setTextColor(Color.RED);
    }

    // is block still covered
    public boolean isCovered()
    {
        return isCovered;
    }



    // set number of nearby mines
    public void setNumberOfMinesInSurrounding(int number)
    {
        numberOfMinesInSurrounding = number;
    }

    // get number of nearby mines
    public int getNumberOfMinesInSurrounding()
    {
        return numberOfMinesInSurrounding;
    }

    // is block marked as flagged
    public boolean isFlagged()
    {
        return isFlagged;
    }

    // mark block as flagged
    public void setFlagged(boolean flagged)
    {
        isFlagged = flagged;
    }

    // is block marked as a question mark
    public boolean isQuestionMarked()
    {
        return isQuestionMarked;
    }

    // set question mark for the block
    public void setQuestionMarked(boolean questionMarked)
    {
        isQuestionMarked = questionMarked;
    }

    // can block receive click event
    public boolean isClickable()
    {
        return isClickable;
    }

    // disable block for receive click events
    public void setClickable(boolean clickable)
    {
        isClickable = clickable;
    }

}
