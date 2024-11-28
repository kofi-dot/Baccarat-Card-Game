import java.util.Scanner;
import java.util.Random;

public class Baccarat {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in); Random random = new Random();
        random.setSeed(getSeed(console)); System.out.println();

        int numPWins = 0, numBWins = 0, numTies = 0, totalGames = 0;

        do {
            int[] pCards = getPlayerCards(random); String[] pCardName = getCardName(pCards);

            if (calcSum(pCards) == 8 || calcSum(pCards) == 9) {
                System.out.println("Player draws first card: " + pCardName[0]);
                System.out.println("Player draws second card: " + pCardName[1]);
                if (pCards[2] > 0) System.out.println("Player draws third card: " + pCardName[2]);
                System.out.println("Player total: " + calcSum(pCards));
                System.out.println("\nResult of game: player wins\n");
                ++numPWins; ++totalGames;
            } else {
                int[] bCards = getBankCards(random); String[] bCardName = getCardName(bCards);
                int winner = whoWins(pCards, bCards);

                if (winner == 1) ++numPWins; else if (winner == 2) ++numBWins;
                else if (winner == 3) ++numTies;
                ++totalGames; printStatistics(pCards, bCards, pCardName, bCardName, winner);
            }

        } while (playAgain(console));

        printPercents(numPWins, numBWins, numTies, totalGames);
    }

    /**
     *
     * @param console Seed input from user
     * @return Seed input from user
     */
    public static int getSeed(Scanner console) {
        System.out.print("Enter a seed: ");
        return console.nextInt();
    }

    /**
     *
     * @param random Used to get random number
     * @return An integer array with the numbers drawn for the player
     */
    public static int[] getPlayerCards(Random random) {
        int[] playerCards = new int[3];

        for (int i = 0; i < 2; ++i) {
            playerCards[i] = random.nextInt(13) + 1;
        }

        int sumP = (convertToZero(playerCards[0]) + convertToZero(playerCards[1])) % 10;

        if (sumP >= 0 && sumP <= 5) {
            playerCards[2] = random.nextInt(13) + 1;
        } else if (sumP == 8 || sumP == 9) {
            return playerCards;
        }

        return playerCards;
    }

    /**
     *
     * @param random Used to get random number
     * @return An integer array with the numbers drawn for the bank
     */
    public static int[] getBankCards(Random random) {
        int[] bankCards = new int[3];

        for (int i = 0; i < 2; ++i) {
            bankCards[i] = random.nextInt(13) + 1;
        }

        int sumB = (convertToZero(bankCards[0]) + convertToZero(bankCards[1])) % 10;

        if (sumB >= 0 && sumB <= 5) {
            bankCards[2] = random.nextInt(13) + 1;
        } else if (sumB == 8 || sumB == 9) {
            return bankCards;
        }

        return bankCards;
    }

    /**
     *
     * @param card The number at a specific element
     * @return 0 if the card is at least 10 or the value of the card if it is not
     */
    public static int convertToZero(int card) {
        if (card >= 10) {
            return 0;
        }
        return card;
    }

    /**
     *
     * @param cards Integer array representing the player or bank's cards
     * @return The sum of all cards drawn
     */
    public static int calcSum(int[] cards) {
        int[] copyCards = new int[cards.length];

        for (int i = 0; i < cards.length; ++i) {
            copyCards[i] = cards[i];
        }

        for (int i = 0; i < cards.length; ++i) {
            if (copyCards[i] >= 10) {
                copyCards[i] = 0;
            }
        }

        return (copyCards[0] + copyCards[1] + copyCards[2]) % 10;
    }

    /**
     *
     * @param cards Integer array representing the player or bank's cards
     * @return A string array with the card values converted to their string versions
     */
    public static String[] getCardName(int[] cards) {
        String[] cardName = new String[cards.length];

        for (int i = 0; i < cards.length; ++i) {
            if (cards[i] == 11) {
                cardName[i] = "Jack";
            } else if (cards[i] == 12) {
                cardName[i] = "Queen";
            } else if (cards[i] == 13) {
                cardName[i] = "King";
            } else if (cards[i] == 1) {
                cardName[i] = "Ace";
            } else {
                cardName[i] = cards[i] + "";
            }
        }
        return cardName;
    }

    /**
     *
     * @param pCards Integer array representing the player's cards
     * @param bCards Integer array representing the bank's cards
     * @return 1 if player wins, 2 if banks wins, or 3 if the game is a tie
     */
    public static int whoWins(int[] pCards, int[] bCards) {
        int sumPlayer = calcSum(pCards);
        int sumBank = calcSum(bCards);

        if (sumPlayer > sumBank) {
            return 1;
        } else if (sumPlayer < sumBank) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     *
     * @param console String input from user, y or n
     * @return True if user enters y, False if user enters n
     */
    public static boolean playAgain(Scanner console) {
        System.out.print("Would you like to play again (Y/N)? ");
        String answer = console.next().trim().toLowerCase();
        return answer.equals("y");
    }

    /**
     *
     * @param pCards Integer array representing the player's cards
     * @param bCards Integer array representing the bank's cards
     * @param pCardName String array with player card values converted to their string versions
     * @param bCardName String array with bank card values converted to their string versions
     * @param winner The number (1, 2, or 3) corresponding with the winner
     */
    public static void printStatistics(int[] pCards, int[] bCards, String[] pCardName,
                                       String[] bCardName, int winner) {

        System.out.println("Player draws first card: " + pCardName[0]);
        System.out.println("Player draws second card: " + pCardName[1]);
        if (pCards[2] > 0) System.out.println("Player draws third card: " + pCardName[2]);
        System.out.println("Player total: " + calcSum(pCards));
        System.out.println();
        if (calcSum(pCards) == 8 || calcSum(pCards) == 9) {
            System.out.println("Result of game: player wins\n");
            return;
        }

        System.out.println("Bank draws first card: " + bCardName[0]);
        System.out.println("Bank draws second card: " + bCardName[1]);
        if (bCards[2] > 0) System.out.println("Bank draws third card: " + bCardName[2]);
        System.out.println("Bank total: " + calcSum(bCards));
        System.out.println();
        if (calcSum(bCards) == 8 || calcSum(bCards) == 9) {
            System.out.println("Result of game: bank wins\n");
            return;
        }

        if (winner == 1) System.out.println("Result of game: player wins\n");
        else if (winner == 2) System.out.println("Result of game: bank wins\n");
        else if (winner == 3) System.out.println("Result of game: tie\n");
    }

    /**
     *
     * @param numPWins Number of times of player wins
     * @param numBWins Number of times bank Wins
     * @param numTies Number of ties
     * @param totalGames Total number of games played
     */
    public static void printPercents(int numPWins, int numBWins, int numTies, int totalGames) {
        double pPerc = 100.0 * numPWins / totalGames;
        double bPerc = 100.0 * numBWins / totalGames;
        double tPerc = 100.0 * numTies / totalGames;

        if (totalGames == 1) {
            System.out.printf("Player wins: %d of %d game (%.1f%%)\n", numPWins, totalGames,
                    pPerc);
            System.out.printf("Bank wins: %d of %d game (%.1f%%)\n", numBWins, totalGames, bPerc);
            System.out.printf("Ties: %d of %d game (%.1f%%)\n", numTies, totalGames, tPerc);
        } else if (totalGames >= 1) {
            System.out.printf("Player wins: %d of %d games (%.1f%%)\n", numPWins, totalGames,
                    pPerc);
            System.out.printf("Bank wins: %d of %d games (%.1f%%)\n", numBWins, totalGames,
                    bPerc);
            System.out.printf("Ties: %d of %d games (%.1f%%)\n", numTies, totalGames, tPerc);
        }
    }
}