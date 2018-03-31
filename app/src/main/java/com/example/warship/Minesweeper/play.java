package com.example.warship.Minesweeper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Random;

public class play extends AppCompatActivity {

    private TableLayout mineField;
    private Block[][] blocks;


    private int blockDimension = 100; // width of each block
    private int blockPadding = 0; // padding between blocks
    private int numberOfRowsInMineField = 10;
    private int numberOfColumnsInMineField = 7;
    private int totalNumberOfMines = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        mineField = (TableLayout) findViewById(R.id.MineField);

        createMineField();
        showMineField();
        setMinesBomb();
    }


    private void showMineField()
    {
        for (int row = 1; row < numberOfRowsInMineField + 1; row++)
        {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams((blockDimension + blockPadding) * numberOfColumnsInMineField, blockDimension + blockPadding));

            for (int column = 1; column < numberOfColumnsInMineField + 1; column++)
            {
                blocks[row][column].setLayoutParams(new TableRow.LayoutParams(
                        blockDimension + blockPadding,
                        blockDimension + blockPadding));
                blocks[row][column].setPadding(blockPadding, blockPadding, blockPadding, blockPadding);
                tableRow.addView(blocks[row][column]);
            }
            mineField.addView(tableRow,new TableRow.LayoutParams(
                    (blockDimension + blockPadding) * numberOfColumnsInMineField, blockDimension + blockPadding));
        }
    }

    private void createMineField() {
        // we take one row extra row for each side
        // overall two extra rows and two extra columns
        // first and last row/column are used for calculations purposes only
        //	 x|xxxxxxxxxxxxxx|x
        //	 ------------------
        //	 x|              |x
        //	 x|              |x
        //	 ------------------
        //	 x|xxxxxxxxxxxxxx|x
        // the row and columns marked as x are just used to keep counts of near by mines

        blocks = new Block[numberOfRowsInMineField + 2][numberOfColumnsInMineField + 2];

        for (int row = 0; row < numberOfRowsInMineField + 2; row++)
        {
            for (int column = 0; column < numberOfColumnsInMineField + 2; column++)
            {
                blocks[row][column] = new Block(this);
                blocks[row][column].setDefaults();

                // pass current row and column number as final int's to event listeners
                // this way we can ensure that each event listener is associated to
                // particular instance of block only
                final int currentRow = row;
                final int currentColumn = column;

                // add Click Listener
                // this is treated as Left Mouse click
                blocks[row][column].setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view)
                    {
                        // set mines on first click

//                        setMines(currentRow, currentColumn);


                        // this is not first click
                        // check if current block is flagged
                        // if flagged the don't do anything
                        // as that operation is handled by LongClick
                        // if block is not flagged then uncover nearby blocks
                        // till we get numbered mines
                        if (!blocks[currentRow][currentColumn].isFlagged())
                        {
                            // open nearby blocks till we get numbered blocks
                            rippleUncover(currentRow, currentColumn);

                            // did we clicked a mine
                            if (blocks[currentRow][currentColumn].hasMine())
                            {
//                                Context context = getApplicationContext();
//                                CharSequence text = "Hello toast!";
//                                int duration = Toast.LENGTH_SHORT;
//
//                                Toast toast = Toast.makeText(context, text, duration);
//                                toast.show();
                                // Oops, game over
                                finishGame(currentRow,currentColumn);
                            }

                            // check if we win the game

                        }
                    }
                });
            }
        }
    }

    private void finishGame(int currentRow, int currentColumn)
    {

        // show all mines
        // disable all blocks
        for (int row = 1; row < numberOfRowsInMineField + 1; row++)
        {
            for (int column = 1; column < numberOfColumnsInMineField + 1; column++)
            {
                // disable block
                blocks[row][column].setBlockAsDisabled(false);
                blocks[row][column].setClickable(false);
                blocks[row][column].OpenBlock();
                // block has mine and is not flagged
                if (blocks[row][column].hasMine() && !blocks[row][column].isFlagged())
                {
                    // set mine icon
                    blocks[row][column].setMineIcon(false);
                }


                // block is flagged
                if (blocks[row][column].isFlagged())
                {
                    // disable the block
                    blocks[row][column].setClickable(false);
                }
            }
        }

        // trigger mine
        blocks[currentRow][currentColumn].triggerMine();
        AlertDialog.Builder digAlert = new AlertDialog.Builder(play.this);
        digAlert.setMessage("You Lose");
        digAlert.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                play.this.recreate();
            }
        });
        digAlert.setCancelable(false);
        digAlert.create().show();
    }

    private void setMinesBomb()
    {
        // set mines excluding the location where user clicked
        Random rand = new Random();
        int mineRow, mineColumn;

        int row = 0;
        while(row<totalNumberOfMines)
        {
            mineRow = rand.nextInt(numberOfColumnsInMineField);
            mineColumn = rand.nextInt(numberOfRowsInMineField);
            if(blocks[mineColumn + 1][mineRow + 1].hasMine() == true){
                mineRow = rand.nextInt(numberOfColumnsInMineField);
                mineColumn = rand.nextInt(numberOfRowsInMineField);
            }else{

                blocks[mineColumn + 1][mineRow + 1].plantMine();
                row++;
            }

//            if ((mineRow + 1 != currentColumn) || (mineColumn + 1 != currentRow))
//            {
//                if (blocks[mineColumn + 1][mineRow + 1].hasMine())
//                {
//                    row--; // mine is already there, don't repeat for same block
//                }
//                // plant mine at this location
//                blocks[mineColumn + 1][mineRow + 1].plantMine();
//            }
//            // exclude the user clicked location
//            else
//            {
//                row--;
//            }
        }

        int nearByMineCount;

        // count number of mines in surrounding blocks
        for (row = 0; row < numberOfRowsInMineField + 2; row++)
        {
            for (int column = 0; column < numberOfColumnsInMineField + 2; column++)
            {
                // for each block find nearby mine count
                nearByMineCount = 0;
                if ((row != 0) && (row != (numberOfRowsInMineField + 1)) && (column != 0) && (column != (numberOfColumnsInMineField + 1)))
                {
                    // check in all nearby blocks
                    for (int previousRow = -1; previousRow < 2; previousRow++)
                    {
                        for (int previousColumn = -1; previousColumn < 2; previousColumn++)
                        {
                            if (blocks[row + previousRow][column + previousColumn].hasMine())
                            {
                                // a mine was found so increment the counter
                                nearByMineCount++;
                            }
                        }
                    }

                    blocks[row][column].setNumberOfMinesInSurrounding(nearByMineCount);
                }
                // for side rows (0th and last row/column)
                // set count as 9 and mark it as opened
                else
                {
                    blocks[row][column].setNumberOfMinesInSurrounding(9);
                    blocks[row][column].OpenBlock();
                }
            }
        }
    }


    private void setMines(int currentRow, int currentColumn)
    {
        // set mines excluding the location where user clicked
        Random rand = new Random();
        int mineRow, mineColumn;

        for (int row = 0; row < totalNumberOfMines; row++)
        {
            mineRow = rand.nextInt(numberOfColumnsInMineField);
            mineColumn = rand.nextInt(numberOfRowsInMineField);

            if ((mineRow + 1 != currentColumn) || (mineColumn + 1 != currentRow))
            {
                if (blocks[mineColumn + 1][mineRow + 1].hasMine())
                {
                    row--; // mine is already there, don't repeat for same block
                }
                // plant mine at this location
                blocks[mineColumn + 1][mineRow + 1].plantMine();
            }
            // exclude the user clicked location
            else
            {
                row--;
            }
        }

        int nearByMineCount;

        // count number of mines in surrounding blocks
        for (int row = 0; row < numberOfRowsInMineField + 2; row++)
        {
            for (int column = 0; column < numberOfColumnsInMineField + 2; column++)
            {
                // for each block find nearby mine count
                nearByMineCount = 0;
                if ((row != 0) && (row != (numberOfRowsInMineField + 1)) && (column != 0) && (column != (numberOfColumnsInMineField + 1)))
                {
                    // check in all nearby blocks
                    for (int previousRow = -1; previousRow < 2; previousRow++)
                    {
                        for (int previousColumn = -1; previousColumn < 2; previousColumn++)
                        {
                            if (blocks[row + previousRow][column + previousColumn].hasMine())
                            {
                                // a mine was found so increment the counter
                                nearByMineCount++;
                            }
                        }
                    }

                    blocks[row][column].setNumberOfMinesInSurrounding(nearByMineCount);
                }
                // for side rows (0th and last row/column)
                // set count as 9 and mark it as opened
                else
                {
                    blocks[row][column].setNumberOfMinesInSurrounding(9);
                    blocks[row][column].OpenBlock();
                }
            }
        }
    }

    private void rippleUncover(int rowClicked, int columnClicked)
    {
        // don't open flagged or mined rows
        if (blocks[rowClicked][columnClicked].hasMine() || blocks[rowClicked][columnClicked].isFlagged())
        {
            return;
        }

        // open clicked block
        blocks[rowClicked][columnClicked].OpenBlock();

        // if clicked block have nearby mines then don't open further
        if (blocks[rowClicked][columnClicked].getNumberOfMinesInSurrounding() != 0 )
        {
            return;
        }

        // open next 3 rows and 3 columns recursively
        for (int row = 0; row < 3; row++){
            for (int column = 0; column < 3; column++){
                // check all the above checked conditions
                // if met then open subsequent blocks
                if (blocks[rowClicked + row - 1][columnClicked + column - 1].isCovered()
                        && (rowClicked + row - 1 > 0) && (columnClicked + column - 1 > 0)
                        && (rowClicked + row - 1 < numberOfRowsInMineField + 1) && (columnClicked + column - 1 < numberOfColumnsInMineField + 1))
                {
                    rippleUncover(rowClicked + row - 1, columnClicked + column - 1 );
                }
            }
        }
        return;
    }
}
