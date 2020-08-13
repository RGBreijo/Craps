/*=======================================================================
|   Source code:  Craps.java
|
|                 Class: Craps
|
|
|    Assignment:  Assignment 3 craps
|
|        Course: COP 3337 (Intermediate Programming)
|        Section:  U06
|
|
|        Language:  Java
|        Compile:
|
|                javac Craps.java
|
|
|  Purpose: To play one game of non-betting Craps.
|
|
|  Inherits From:  None
|
|
|   Interfaces:  None
|
|
|  +-----------------------------------------------------------------------
|
|      Constants:  There are no public constants.
|
|
| +-----------------------------------------------------------------------
|
|   Constructors:  Craps() - Empty constructor which sets default dice side values.
|
|
|  Class Methods:  No private class methods
|
|
|  Instance Methods:  resetGame()void
|                     playGame() void
|                     getGameRolls() Returns an int
|                     setDieSide(int sides) Void
|                     getWon() returns a Boolean
|
|  *===========================================================================*/


public class Craps {

    // Standard name used for values in the game of craps
    private final int NATURAL = 7;
    private final int YO_LEVEN = 11;
    private final int SNAKE_EYES = 2;
    private final int ACE_DEUCE = 3;
    private final int BOX_CARS = 12;
    private final int LOOSING_ROLL = 7;

    private final int DICE_SIDES = 6;
    private final int NUMBER_OF_DICE = 2;

    private int gameRolls = 0;
    private int diceToRoll = 0; // Number of dice that will be rolled

    private boolean won = false;

    Die crapsDie = new Die();


    public Craps()
    {
        crapsDie.setDieSide(DICE_SIDES);
        diceToRoll = NUMBER_OF_DICE;
    }


    /**
        Resets the game board to the default values.
     */

    public void resetGame()
    {
        gameRolls = 0;
        won = false;
    }



    /**
        Plays one game of non-betting craps.
     */

    public void playGame()

    {
        resetGame();
        final int POINT = crapsDie.rollDice(diceToRoll);
        gameRolls++;


        if (POINT == NATURAL || POINT == YO_LEVEN)
        {
            won = true;

        } else if (POINT != SNAKE_EYES && POINT != ACE_DEUCE && POINT != BOX_CARS)
        {

            int value = 0;

            do
            {
                value = crapsDie.rollDice(diceToRoll);
                gameRolls++;

            } while (value != POINT && value != LOOSING_ROLL);


            if (value == POINT)
            {
                won = true;
            } else
            {
                won = false;
            }

        } else
        {
            won = false;
        }
    }


    /**
       Getter method for gameRolls.

       @return number of total rolls that it took to win / lose the game.
     */

    public int getGameRolls()
    {
        return gameRolls;
    }


    /**
       Getter method for Won.

       @return boolean value denoting if the game was won (true)  or lost (false).
     */

    public boolean getWon()
    {
        return won;
    }

}

