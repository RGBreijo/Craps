/*=============================================================================
|    Source code: Analyzer.java
|
|    Assignment:  Assignment 3 craps
|
|        Course:  COP 3337 (Intermediate Programming)
|        Section:  U06
|
|
|       Language:  Java
|       Compile/Run:
|
|       javac Analyzer.java
|       java Analyzer
|
|  +-----------------------------------------------------------------------------
|
|  Description:  This program analyzes a user set number of craps games.
|
|                The following are useful formulas provided in the spec sheet of assignment 3:
|
|                Average Length = (total rolls / total games)
|                Outcome of winning = total wins / total games)
|                Outcome of winning on the coming-out roll = (coming-out wins / coming-out games)
|                Games ending on the coming-out roll = coming - out games / total games
|                Games continuing-on after the coming-out roll  = ((total games - coming-out games) / total games)
|
|       Input:    User specifies how many games they wish to be analyzed.
|
|       Output:   The program displays statistical information gathered from all the games played.
|
|       Process:  The program's steps are as follows:
|
|                      1.  The program prompts user to enter how many games they want analyzed 1 - 1 million inclusive
|                      2.  The program collects information about the specified number of games.
|                      3.  The program computes statistical values using the information gathered.
|                      4.  The program displays the results.
|
|
|                No particular algorithms are used.
|
|
|   Required Features Not Included: All required features are included.
|
|   Known Bugs:  None; the program operates correctly.
|
|  *===========================================================================*/


import java.util.Scanner; // To take user input


public class Analyzer
{

    // To access the part of a 2D array (array: playCrapsInfo) that holds info about Game statics and game length
    private static final int GAME_STATS_INDEX = 0;
    private static final int GAME_LENGTH_INDEX = 1;
    
    // Index for different elements on the game statics side of the 2D array (array: playCrapsInfo)
    private static final int TOTAL_ROLLS_INDEX = 0;
    private static final int LONGEST_GAME_INDEX = 1;
    private static final int COMING_OUT_WIN_INDEX = 2;
    private static final int COMING_OUT_LOSE_INDEX = 3;
    private static final int TOTAL_WIN_INDEX = 4;

    // The number of elements each section (Game stats / Game length) holds
    private static final int ELEMENTS_IN_STATS = 5;
    private static final int ELEMENTS_IN_LENGTH = 21;

    // The Two arrays Game statics & Game length that will be put in the 2D array
    private static final int AMOUNT_OF_INFO = 2;

    private static final int DIE_SIDES = 6; // Number of sides per die

    // Indexes for the array that holds Expected values
    private static final int COMING_OUT_PROP_INDEX = 0;
    private static final int WIN_PROP_INDEX = 1;
    private static final int CONTINUE_GAME_PROP_INDEX = 2;

    // The size of the expected array
    private static final int ELEMENTS_IN_EXPECTED = 3;





    public static void main(String[] args)
    {

        Scanner input = new Scanner(System.in);
        Craps playerOne = new Craps();


        int numOfGames = validateInput(input);

        int[][] playCrapsInfo = playCraps(numOfGames, playerOne);

        displayStatistics(numOfGames, playCrapsInfo);
    }


    /**
       Gets the required information needed to display the game statistics and game length.

       @param gamesToPlay Number of games the player wants to play
       @param player An object of the class Craps

       @return A 2D array with game statistics and game length.
     */

    public static int[][] playCraps(int gamesToPlay, Craps player)
    {

        int[][] allGameInfo = new int[AMOUNT_OF_INFO][];
        int[] gameStats = new int[ELEMENTS_IN_STATS];
        int[] gameLength = new int[ELEMENTS_IN_LENGTH];


        for(int count = 0; count < gamesToPlay; count++)
        {
            player.playGame();

            gameStats[TOTAL_ROLLS_INDEX] += player.getGameRolls();


            if(player.getWon())
            {
                gameStats[TOTAL_WIN_INDEX] += 1;
            }


            if(gameStats[LONGEST_GAME_INDEX] < player.getGameRolls())
            {
                gameStats[LONGEST_GAME_INDEX] = player.getGameRolls();
            }


            if(player.getGameRolls() == 1 && player.getWon())
            {
                gameStats[COMING_OUT_WIN_INDEX] += 1;

            }else if(player.getGameRolls() == 1 && !player.getWon()){

                gameStats[COMING_OUT_LOSE_INDEX] += 1;
            }

            gameLength[lengthIndex(player.getGameRolls() - 1)] += 1; // ( - 1 )index start at 0

        }

        allGameInfo[GAME_STATS_INDEX] = gameStats;
        allGameInfo[GAME_LENGTH_INDEX] = gameLength;

        return allGameInfo;
    }





    /**
       Outputs information about game statistics.

       Formula:
               Average Rolls = Total Rolls / Total Game

       Note:
              Formula was obtained from Assignment 3 Specifications

       @param totalGames The number of games the user wants to examine.
       @param gameStats An array containing game statistics.
     */

    public static void gameStatistics(int totalGames, int[] gameStats)
    {

        int totalRolls = gameStats[TOTAL_ROLLS_INDEX];
        int longestGame = gameStats[LONGEST_GAME_INDEX];

        double avgRolls = 0.0;


        if (totalGames != 0)
        {
            avgRolls = (double) totalRolls / totalGames;
        }

        System.out.println("Summary of Game Statistics");
        System.out.println("+-----------------------------------+");
        System.out.printf("| (1) Total Games %10d %7s|", totalGames, "");
        System.out.printf("\n| (2) Total Rolls %10d %7s|", totalRolls, "");
        System.out.printf("\n| (3) Average Rolls %7.04f %8s|", avgRolls, "");
        System.out.printf("\n| (4) Longest Game %4d %12s|", longestGame, "");
        System.out.println("\n+-----------------------------------+");

    }



    /**
        Outputs information about win statistics.

        Formulas:
                totalWinResults =  totalWins / totalGames
                comingOutWinsResults = comingOutWins /  comingOutGames
                comingOutGamesResults = comingOutGames / totalGames;

        Note:

        Formulas where obtained from Assignment 3 Specifications

        EXPECTED_WIN = 0.4929 (6) was obtained from the link: https://www.mscs.dal.ca/~hoshino/book/ch20craps.pdf

        * Other statistics formulas are documented in the crapsExpectedValues method.

        @param totalGames The number of games the user wants to examine.
        @param winStats An array containing win statistics about the game.
     */

    public static void winStatistics( int totalGames, int[] winStats)
    {
        final double EXPECTED_WIN =  0.4929;

        int comingOutWins = winStats[COMING_OUT_WIN_INDEX];
        int comingOutGames = winStats[COMING_OUT_WIN_INDEX] + winStats[COMING_OUT_LOSE_INDEX];
        int totalWins = winStats[TOTAL_WIN_INDEX];

        double totalWinResults = 0.0;
        double comingOutWinsResults = 0.0;
        double comingOutGamesResults = 0.0;

        double[] expected = crapsExpectedValues();


        if(comingOutGames != 0)
        {
            comingOutWinsResults = (double) comingOutWins / comingOutGames;

        }

        if(totalGames != 0)
        {
            totalWinResults =  (double) totalWins / totalGames;
            comingOutGamesResults = (double) comingOutGames / totalGames;
        }


        System.out.println("Summary of Win ending Statistics");
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.printf("| Stat%6s         Games               Expected             Results             |", "");
        System.out.println("\n+---------------------------------------------------------------------------------+");
        System.out.printf("| Total Wins  %13d (5) %15.04f (6)  %15.04f (7)%8s  |", totalWins, EXPECTED_WIN, totalWinResults, "");
        System.out.printf("\n| Coming-Out Wins  %8d (8) %15.04f (9)  %15.04f (10)%7s  |", comingOutWins, expected[WIN_PROP_INDEX], comingOutWinsResults, "");
        System.out.printf("\n| Coming-Out Games  %7d (11) %14.04f (12)  %14.04f (13)%7s  |", comingOutGames, expected[COMING_OUT_PROP_INDEX], comingOutGamesResults, "");
        System.out.println("\n+---------------------------------------------------------------------------------+");

}


    /**
        Outputs information about ending statistics.

       Formula:
                comingOutResults = (totalGames - comingOutGames ) / totalGames
       Note:
       Formula was obtained from Assignment 3 Specifications

       * Other statistics formulas are documented in the crapsExpectedValues method.

       @param totalGames The number of games the user wants to examine.
       @param endStats  An array containing ending statistics about the game.
     */

    public static void endingStatistics(int totalGames, int[] endStats)

    {

        int comingOutGames = endStats[COMING_OUT_WIN_INDEX] + endStats[COMING_OUT_LOSE_INDEX];

        double comingOutResults = 0;
        double[] expected = crapsExpectedValues();
        int continuingGames = totalGames - comingOutGames;


        if (totalGames != 0)
        {
            comingOutResults = ((double) totalGames - comingOutGames ) / totalGames;
        }

        System.out.println("Summary of Ending ending Statistics");
        System.out.println("+----------------------------------------------------------------------------------+");
        System.out.printf("| Stat%6s                Games               Expected             Results       | ", "");
        System.out.println("\n+----------------------------------------------------------------------------------+");
        System.out.printf("| Continuing-On Games  %11d (14) %14.04f (15)  %14.04f (16)   |", continuingGames, expected[CONTINUE_GAME_PROP_INDEX] , comingOutResults);
        System.out.println("\n+----------------------------------------------------------------------------------+");

    }



// 272 113 861
// wt2e66

    /**
       Outputs information about game length

       @param totalGames Amount of games the user wants to test
       @param gameLength  An array containing information about game Length.
     */


    public static void gameLength(int totalGames, int[] gameLength)
    {

        final int TWENTY_ONE_AND_OVER = 20; // Index 20 is actually value 21

        System.out.println("Summary of Game Lengths in Rolls (17)");
        System.out.println("+-----------------------------------------+");
        System.out.println("| Rolls               # of Games          |");
        System.out.println("+-----------------------------------------+");


        for(int count = 0; count < ELEMENTS_IN_LENGTH; count ++)
        {
            if (count == TWENTY_ONE_AND_OVER)
            {
                System.out.printf("|   21+ %20d %11s  |", gameLength[count], "");
            }else
            {
                System.out.printf("|  %3d %21d %11s  |\n", (count + 1), gameLength[count], "");
            }
        }

        System.out.println("\n------------------------------------------+");
        System.out.printf("| Total %20d %11s  |", totalGames, "");
        System.out.println("\n------------------------------------------+");
    }



    /**
       Determines the index of where a game should go on the gameLength Array based on how many rolls were in the game.

       @param rolls number of rolls of one game.
       @return An index indicating where the game should go.

     */


    public static int lengthIndex(int rolls)
    {
        final int OVER_TWENTY_ONE = 20;  // index for rolls 21+

        if (rolls < OVER_TWENTY_ONE)
        {
            return rolls;

        }else
        {
            return OVER_TWENTY_ONE;
        }

    }



    /**
        Computes the expected value of a craps game

        Formulas used:
         comingOutWinProp = ( ( (double) comingOutWinSum / amountOfResults) / comingOutGameProp )
         comingOutGameProp = ( comingOutGameSum / amountOfResults)
         continueGameProp = (amountOfResults - comingOutGameSum) / amountOfResults

        These formulas where derived using statistics and the hints provided by assignment 3 spec sheet.
        For further analysis the link: https://people.richland.edu/james/misc/simulation/craps.html provides
        References to verify the probability.

        @return An array containing expected values
     */

    public static double[] crapsExpectedValues()
    {

        final int[] COMING_OUT_WIN_NUMS = {7, 11}; // Coming-out winning numbers in craps
        final int[] COMING_OUT_GAME_NUMS = {7, 11, 2, 3, 12}; // Continuing-on loosing numbers in craps.

        int[][] propTable = createPropTable();

        double[] expectedProp = new double[ELEMENTS_IN_EXPECTED];

        double amountOfResults = DIE_SIDES * DIE_SIDES; // amount of elements in the array

        int comingOutWinSum = sumOfOccurrence(propTable, COMING_OUT_WIN_NUMS);
        int comingOutGameSum = sumOfOccurrence(propTable, COMING_OUT_GAME_NUMS);


        double comingOutWinProp = 0;
        double comingOutGameProp = 0;
        double continueGameProp = 0;


        if (amountOfResults != 0)
        {
            comingOutGameProp = ( comingOutGameSum / amountOfResults); // Expected for (12)
            continueGameProp = (amountOfResults - comingOutGameSum) / amountOfResults; // Expected for (15)


            if (comingOutGameProp != 0)
            {
                comingOutWinProp = ( ( (double) comingOutWinSum / amountOfResults) / comingOutGameProp ); // Expected for (9)
            }
        }


        expectedProp[COMING_OUT_PROP_INDEX] = comingOutGameProp;
        expectedProp[WIN_PROP_INDEX] = comingOutWinProp;
        expectedProp[CONTINUE_GAME_PROP_INDEX] = continueGameProp;

        return expectedProp;

    }




    /**
        Sums together all the occurrences of specified numbers in a 2D probability table

       @param propTable An array filled with values
       @param nums An array with the numbers to search for and sum
       @return A sum of occurrences for the specified number
     */


    public static int sumOfOccurrence(int[][] propTable, int[] nums)
    {

        // Start at 1 because elements in  Row 0 and Col 0 are NOT part of the calculated results
        final int START_ROW = 1;
        final int START_COL = 1;

        int rowLen = propTable.length;
        int colLen =  propTable[0].length;

        int sum = 0;


        for (int element : nums)
        {

            for (int row = START_ROW; row < rowLen; row++)
            {

                for (int col = START_COL; col < colLen; col++)
                {
                    if (propTable[row][col] == element)
                    {
                        sum += 1;
                    }
                }
            }

        }

        return sum;
    }



    /**
        Creates a 2D probability table using the the number of sides in the die.

        @return A probability table
     */

    public static int[][]  createPropTable()
    {


        int[][] propTable = new int[DIE_SIDES + 1 ][DIE_SIDES + 1 ]; // +1 for values used to calculate probability


        // Sets the values that will be used to calculate probability
        for(int count = 0; count < propTable.length; count++)
        {
            propTable[0][count] = count;
            propTable[count][0] = count;
        }


        // Fills out the prop table

        for (int row = 1; row < propTable.length; row++)
        {
            for (int col = 1; col < propTable.length; col++)
            {
                propTable[row][col] = propTable[row][0] + propTable[0][col];
            }
        }

        return propTable;
    }


    /**
       Takes in user input and verifies that it is within range 1 - 1 million

       @param input Scanner object of user input
       @return An acceptable number of games
     */

    public static int validateInput(Scanner input)
    {
        boolean invalidInput = true;
        int userValue = 0;

        final int MIN = 1;
        final int MAX = 1000000;


        System.out.println("How many games do you want to analyze? Range (1 - 1,000,000) inclusive:  ");

        do
        {
            if(input.hasNextInt())
            {

                userValue = input.nextInt();

                if(userValue >= MIN && userValue <= MAX)
                {
                    invalidInput = false;
                }else
                {
                    System.out.println("Please enter a valid Range (1 - 1,000,000) inclusive");
                    input.nextLine();
                }

            }else
            {
                System.out.println("Please enter a valid number");
                input.nextLine();
            }

        }while(invalidInput);

        return userValue;
    }


    /**
       Calls the required methods to output all the game information.

      @param numOfGames number of user specified games
      @param playCrapsInfo A 2D array which holds information about the game

     */

    public static void displayStatistics(int numOfGames, int[][] playCrapsInfo)
    {

        gameStatistics(numOfGames, playCrapsInfo[GAME_STATS_INDEX]);
        System.out.println();

        winStatistics(numOfGames, playCrapsInfo[GAME_STATS_INDEX]);
        System.out.println();

        endingStatistics(numOfGames, playCrapsInfo[GAME_STATS_INDEX]);
        System.out.println();

        gameLength(numOfGames, playCrapsInfo[GAME_LENGTH_INDEX]);

    }
}