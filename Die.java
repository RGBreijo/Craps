/*=======================================================================
|   Source code:  Die.java
|
|                 Class: Die
|
|
|    Assignment:  Assignment 3 craps
|
|        Course: COP 3337 (Intermediate Programming)
|        Section: U06
|
|
|
|
|        Language:  Java
|        Compile:
|
|                javac Die.java
|
|
|        Purpose:   To simulate the roll of one or more die.
|
|
|    Inherits From:  None
|
|
|     Interfaces:  None
|
|
|  +-----------------------------------------------------------------------
|
|      Constants:  There are no public constants.
|
|
| +-----------------------------------------------------------------------
|
|   Constructors:  Die() - Empty constructor which sets default dice side values.
|                  Die(int sides) - Number of sides for the dice.
|
|  Class Methods:  No private class methods
|
|
|  Instance Methods:  rollDie() Returns an int
|                     rollDice(int amountOfDie) Returns an int
|                     checkDiceSides(int sides) Returns an int
|                     setDieSide(int sides) Void
|
|  *===========================================================================*/




import java.util.Random; // Used to simulate a fair dice roll


public class Die
{

    private final int MIN_DICE_SIDES = 2;
    private final int MAX_DICE_SIDES = 100;
    private final int DEFAULT_DICE_SIDES = 6;

    private int diceSides = 0;

    private Random generator = new Random();


    public Die(int sides)
    {
        diceSides = checkDiceSides(sides);
    }


    public Die()
    {

        diceSides = DEFAULT_DICE_SIDES;
    }



    /**
        Simulates the rolling of a die.

        @return A number in the range 1 - diceSides
     */

    public int rollDie()
    {
        final int MIN_DICE_VALUE = 1;

        return ( MIN_DICE_VALUE + generator.nextInt(diceSides));
    }


    /**
        Simulates the rolling of a specified number of Die.

         @param amountOfDice how many dice will be rolled.
         @return The sum of the dices rolled.
     */

    public int rollDice(int amountOfDice)
    {

        int sum = 0;

        for(int count = 0; count < amountOfDice; count++)
        {
            sum += rollDie();
        }

        return sum;
    }


    /**
        Checks to see if the specified number of sides is within the range 2 - 100

        @param sides Desired number of sides for the dice.
        @return An acceptable value for dice sides.
     */

    public int checkDiceSides(int sides)
    {

        if (sides < MIN_DICE_SIDES)
        {
           return MIN_DICE_SIDES;

        }else if (sides > MAX_DICE_SIDES)
        {
            return MAX_DICE_SIDES;

        }else
        {
            return sides;
        }
    }

    /**
       Setter for diceSides
       @param sides number of sides in the dice.
     */

    public void setDieSide(int sides)
    {
        diceSides = checkDiceSides(sides);
    }

}