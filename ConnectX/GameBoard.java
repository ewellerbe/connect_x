public class GameBoard extends AbsGameBoard {


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
    private final char[][] twoD_arr;


    /**
     * Creates a new object to keep track of tokens
     *
     * @post [Instantiates an Array of Objects with size being NUM_ROW x NUM_COL] AND
     *       NUM_ROW = Rows AND NUM_COL = Columns AND WIN_CON = inARow
     */
    public GameBoard(int Rows, int Columns, int inARow) {
        NUM_ROW =  Rows;
        NUM_COL = Columns;
        WIN_CON = inARow;

        twoD_arr = new char[NUM_ROW][NUM_COL];

        for (int r = 0; r < NUM_ROW; r++) {
            for (int c = 0; c < NUM_COL; c++) {
                twoD_arr[r][c] = ' ';
            }
        }
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
        for (int r = 0; r < NUM_ROW; r++) {
            if (twoD_arr[r][c] == ' ') {
                twoD_arr[r][c] = p;
                tokensPlaced++;
                return;
            }
        }
    }
    public char whatsAtPos(BoardPosition pos) {
        return (twoD_arr[pos.getRow()][pos.getColumn()]);
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
