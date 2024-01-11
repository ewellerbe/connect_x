import java.util.*;

public class GameScreen {

    /**
     * @invariant player != '\0'
     */
    private final static GameScreen game = new GameScreen();
    private static IGameBoard board;
    private char player;
    private int columnInput, numPlayers, Columns, Rows, inARow;
    private boolean fast = false;
    private char[] playerChars;

    public static void main(String[] args) {

        System.out.println("Welcome to ConnectX");
        // Creates an object that will read in data from the command line.
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        boolean gameover = true;

        while (!quit) {

            if (gameover) {
                gameover = false;
                do {
                    System.out.println("How many players?");

                    // Validates input
                    while(!scanner.hasNextInt()) {
                        scanner.next();
                        System.out.println("Input must be an Integer");
                        System.out.println("How many players?");
                    }

                    game.numPlayers = scanner.nextInt();
                    scanner.nextLine();

                    if (game.numPlayers > 10) System.out.println("Must be 10 players or fewer");
                    else if (game.numPlayers < 2) System.out.println("Must be at least 2 players");

                } while (game.numPlayers > 10 || game.numPlayers < 2);

                game.playerChars = new char[game.numPlayers];
                char player;

                for (int i = 1; i <= game.numPlayers; i++) {
                    System.out.println("Enter the character to represent player " + i);
                    player = Character.toUpperCase(scanner.nextLine().charAt(0));

                    if (player == ' ') {
                        System.out.println("No cheating!!");
                        i--;
                    }
                    else if (new String(game.playerChars).indexOf(player) == -1) game.playerChars[i - 1] = player;
                    else {
                        System.out.println(player + " is already taken as a player token!");
                        i--;
                    }
                }
                game.player = game.playerChars[game.numPlayers - 1];


                do {
                    System.out.println("How many rows should be on the board?");

                    // Validates input
                    while(!scanner.hasNextInt()) {
                        scanner.next();
                        System.out.println("Input must be an Integer");
                        System.out.println("How many rows should be on the board?");
                    }

                    game.Rows = scanner.nextInt();
                    scanner.nextLine();

                    if (game.Rows > 100) System.out.println("Must be 100 rows or fewer");
                    else if (game.Rows < 3) System.out.println("Must be at least 3 rows");

                } while (game.Rows > 100 || game.Rows < 3);

                do {
                    System.out.println("How many columns should be on the board?");

                    // Validates input
                    while(!scanner.hasNextInt()) {
                        scanner.next();
                        System.out.println("Input must be an Integer");
                        System.out.println("How many columns should be on the board?");
                    }

                    game.Columns = scanner.nextInt();
                    scanner.nextLine();

                    if (game.Columns > 100) System.out.println("Must be 100 columns or fewer");
                    else if (game.Columns < 3) System.out.println("Must be at least 3 columns");

                } while (game.Columns > 100 || game.Columns < 3);


                do {
                    System.out.println("How many in a row to win?");

                    // Validates input
                    while(!scanner.hasNextInt()) {
                        scanner.next();
                        System.out.println("Input must be an Integer");
                        System.out.println("How many in a row to win?");
                    }

                    game.inARow = scanner.nextInt();
                    scanner.nextLine();

                    if (game.inARow > 25) System.out.println("Must be 25 in a row or fewer");
                    else if (game.inARow > game.Rows || game.inARow > game.Columns) System.out.println("Must be fewer than number of columns and rows");
                    else if (game.inARow < 3) System.out.println("Must be at least 3 in a row");

                } while (game.inARow > 25 || game.inARow < 3 || game.inARow > game.Rows || game.inARow > game.Columns);

                String imp;
                do {
                    System.out.println("Would you like a Fast Game (F/f) or a Memory Efficient Game (M/m)?");
                    imp = scanner.nextLine();
                    if (imp.equalsIgnoreCase("F"))  game.fast = true;
                    else if (imp.equalsIgnoreCase("M")) game.fast = false;
                    else System.out.println("Invalid Input");
                } while (!imp.equalsIgnoreCase("F") && !imp.equalsIgnoreCase("M"));

                emptyBoard();
            }

            // Alternates the game between players.
            changePlayers();

            // Checks if input is valid and that the location is available.
            availableLoc(scanner);

            // Places a token in selected available location.
            board.placeToken(game.player, game.columnInput);

            // Win conditions
            printBoard();

            if (board.checkForWin(game.columnInput)) {
                System.out.println("Player " + game.player + " Wins!");
                gameover = true;
                String input;

                do {
                    System.out.println("Would you like to play again? Y/N");
                    input = scanner.nextLine();
                    if (input.equalsIgnoreCase("N"))  quit = true;
                    else if (input.equalsIgnoreCase("Y")) {
                        emptyBoard();
                    }
                } while (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N"));

            }


            else if (board.checkTie()) {
                System.out.println("Its a tie!");
                gameover = true;

                System.out.println("Would you like to play again? Y/N");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("N"))  quit = true;
                else {
                    emptyBoard();
                }
            }

        }

    }

    private static void emptyBoard() {
        // empties board based off of user chosen implementation
        if (game.fast) board = new GameBoard(game.Rows, game.Columns, game.inARow);
        else board = new GameBoardMem(game.Rows, game.Columns, game.inARow);

        printBoard();
    }

    /**
     * @post game.player == 'X' || game.player == 'O'
     */
    private static void changePlayers() {

        // Move to the last element and then loop to the first again
        int index = new String(game.playerChars).indexOf(game.player) + 1;
        if ( index == game.numPlayers) {
            game.player = game.playerChars[0];
        }
        else game.player = game.playerChars[index];
    }

    /**
     * @post [input is an integer]
     */
    private static void availableLoc(Scanner scanner) {
        boolean isValid = false;

        // prompts user for input until a valid option is entered
        while (!isValid) {

            System.out.println("Player " + game.player + ", what column do you want to place your marker in?");

            while(!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("Input must be an Integer");
                System.out.println("Player " + game.player + ", what column do you want to place your marker in?");
            }

            game.columnInput = scanner.nextInt();
            scanner.nextLine();

            if (game.columnInput < 0 || game.columnInput >= game.Columns) System.out.println("Input must be in bounds of 0-" + (game.Columns-1));
            else if (!board.checkIfFree(game.columnInput)) System.out.println("Column is full");
            else isValid = true;
        }
    }

    /**
     * @post [GameBoard is outputted]
     */
    private static void printBoard() {
        System.out.println(board.toString());
    }


}
