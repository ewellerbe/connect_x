import java.util.*;

public class GameBoardMem extends AbsGameBoard {

    /**
     * Correspondence self.Rows = NUM_ROW
     *                self.Columns = NUM_COL
     *                self.RequiredTokens = WIN_CON
     *                self.GameBoard = twoD_arr
     *
     * @invariant [No spaces can be between tokens placed vertically] AND tokensPlaced >= 0 AND
     *            AND WIN_CON > 0 AND NUM_COL > 0 AND NUM_ROW > 0
     */


    private int tokensPlaced = 0;
    private final int NUM_COL;
    private final int NUM_ROW;
    private final int WIN_CON;
    Map<Character, List<BoardPosition>> matrix;

    /**
     * Creates a Map to keep track of tokens placed
     *
     * @post [Instantiates a Map] AND NUM_ROW = Rows
     *       AND NUM_COL = Columns AND WIN_CON = inARow
     */
    public GameBoardMem(int Rows, int Columns, int inARow) {
        NUM_ROW = Rows;
        NUM_COL = Columns;
        WIN_CON = inARow;

        matrix = new HashMap<Character, List<BoardPosition>>();
    }


    public int getNumRows() {
        return NUM_ROW;
    }

    public int getNumColumns() {
        return NUM_COL;
    }

    public int getNumToWin() {
        return WIN_CON;
    }

    public void placeToken(char p, int c) {

        Main_Loop:
        for (int r = 0; r < NUM_ROW; r++) {
            BoardPosition pos = new BoardPosition(r, c);

            // No tokens placed, anywhere is free
            if (tokensPlaced == 0) {
                matrix.computeIfAbsent(p, k -> new ArrayList<BoardPosition>()).add(pos);
                tokensPlaced++;
                return;
            }

            // token placed in spot?
            for (char player : matrix.keySet()) {
                if (matrix.get(player).contains(pos) ) continue Main_Loop;
            }

            // No token found in lowest row of input column
            matrix.computeIfAbsent(p, k -> new ArrayList<BoardPosition>()).add(pos);
            tokensPlaced++;
            return;
        }

    }

    public char whatsAtPos(BoardPosition pos) {
        // Base case
        if (matrix.isEmpty()) return ' ';

        for (char player : matrix.keySet()) {
            if (matrix.get(player).contains(pos) ) return player;
        }

        return ' ';
    }

    /**
     * Returns true if the player is at position
     *
     * @param pos position of a cell in the GameBoard
     * @param player char representing player
     * @return true if the character is at pos, false otherwise
     * @pre 0 <= pos.getRow() < Rows AND 0 <= pos.getCol() < Columns
     * @post [true when a position in the list of specified key matches pos, false otherwise]
     */
    @Override
    public boolean isPlayerAtPos(BoardPosition pos, char player) {
        return ( matrix.get(player).contains(pos) );
    }

    /**
     * Returns true if the game board results in a tie game, otherwise it returns false
     *
     * @return True if the the top row is filled, false otherwise
     * @post checkTie = true iff (tokensPlaced  >= NUM_COL * NUM_ROW)
     */
    @Override
    public boolean checkTie() {
        return (tokensPlaced >= NUM_COL * NUM_ROW);
    }

}
