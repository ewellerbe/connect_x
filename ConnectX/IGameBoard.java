/**
 * This class will represent the GameBoard itself and handle operations related to win conditions and token placements
 *
 * Defines:     Rows: Z - The amount of rows the two dimensional array will contain
 *              Columns: Z - The amount of columns the two dimensional array will contain
 *              CellRow: Z - The row that a cell in the array occupies
 *              CellCol: Z - The column that a cell in the array occupies
 *              TokensInRow: Z - The numbers of tokens in a row (horizontally, vertically, or diagonally)
 *              RequiredTokens: Z - The amount of tokens in a row needed to win the game
 *              GameBoard - Two dimensional array representing the game board
 *
 * Constraints: 0 < Columns AND 0 < Rows AND
 *              0 <= CellRow < Rows AND
 *              0 <= CellCol < Columns AND 0 <= TokensInRoW AND
 *              0 < RequiredTokens
 */

public interface IGameBoard {

    /**
     * Places a token at the lowest available row
     *
     * @param p character representing a player
     * @param c column the token will be placed in
     * @pre 0 <= c < Columns AND [Column is not full]
     * @post [Most recent non-empty cell in column c is equal to p]
     */
    void placeToken(char p, int c);

    /**
     * Returns the character at position pos of the board
     *
     * @param pos position of a cell in the GameBoard
     * @return The character at pos or a blank space if char is empty.
     * @pre 0 <= pos.getRow() < Rows AND 0 <= pos.getCol() < Columns
     * @post [whatsAtPos is the character at position in board]
     */
    char whatsAtPos(BoardPosition pos);


    /**
     * @return number of rows in the GameBoard
     * @post getNumRows = Rows
     */
    int getNumRows();

    /**
     * @return number of columns in the GameBoard
     * @post getNumColumns = Columns
     */
    int getNumColumns();

    /**
     * @return number of tokens in a row needed to win the game
     * @post getNumToWin = RequiredTokens
     */
    int getNumToWin();

    /**
     * Returns true if the player is at position
     *
     * @param pos position of a cell in the GameBoard
     * @param player char representing player
     * @return true if the character is at pos, false otherwise
     * @pre 0 <= pos.getRow() < Rows AND 0 <= pos.getCol() < Columns
     * @post isPlayerAtPos iff (whatsAtPos(pos) == player)
     */
    default boolean isPlayerAtPos(BoardPosition pos, char player) {
        return (whatsAtPos(pos) == player);
    }

    /**
     * Returns true if the last token placed (which was placed in position pos and player p)
     * resulted in the player winning by getting 5 in a row horizontally
     *
     * @param pos position of a cell in the GameBoard
     * @param p char representing player
     * @return True if player has 5 in a row horizontally, false otherwise
     * @pre 0 <= pos.getRow() < NUM_ROW AND 0 <= pos.getCol() < NUM_COL
     * @post checkHorizWin = true iff (TokensInRow >= RequiredTokens)
     */
    default boolean checkHorizWin(BoardPosition pos, char p) {

        int NUM_COL = getNumColumns();
        int WIN_CON = getNumToWin();
        int col = pos.getColumn();
        int row = pos.getRow();
        int count = 1;
        BoardPosition cell;


        for (int i = 1 ; col + i < NUM_COL; i++) {
            cell = new BoardPosition(row, col+i);
            if (isPlayerAtPos(cell, p)) count++;
            else break;
        }

        for (int i = 1 ; col - i >= 0; i++) {
            cell = new BoardPosition(row, col-i);
            if (isPlayerAtPos(cell, p)) count++;
            else break;
        }

        return (count >= WIN_CON);
    }

    /**
     * Returns true if the last token placed (which was placed in position pos and player p)
     * resulted in the player winning by getting 5 in a row vertically
     *
     * @param pos position of a cell in the GameBoard
     * @param p char representing player
     * @return True if player has 5 in a row vertically, false otherwise
     * @pre 0 <= pos.getRow() < NUM_ROW AND 0 <= pos.getCol() < NUM_COL
     * @post checkVertWin = true iff (TokensInRow >= RequiredTokens)
     */
    default boolean checkVertWin(BoardPosition pos, char p) {

        int NUM_ROW = getNumRows();
        int WIN_CON = getNumToWin();
        int col = pos.getColumn();
        int row = pos.getRow();
        int count = 1;
        BoardPosition cell;

        for (int i = 1 ; row + i < NUM_ROW; i++) {
            cell = new BoardPosition(row+i, col);
            if (isPlayerAtPos(cell, p)) count++;
            else break;
        }

        for (int i = 1 ; row - i >= 0; i++) {
            cell = new BoardPosition(row-i, col);
            if (isPlayerAtPos(cell, p)) count++;
            else break;
        }
        return (count >= WIN_CON);
    }

    /**
     * Returns true if the last token placed (which was placed in position pos and player p)
     * resulted in the player winning by getting 5 in a row diagonally
     *
     * @param pos position of a cell in the GameBoard
     * @param p char representing player
     * @return True if player has 5 in a row diagonally, false otherwise
     * @pre 0 <= pos.getRow() < NUM_ROW AND 0 <= pos.getCol() < NUM_COL
     * @post checkDiagWin = true iff (TokensInRow >= RequiredTokens)
     */
    default boolean checkDiagWin(BoardPosition pos, char p) {

        int NUM_COL = getNumColumns();
        int NUM_ROW = getNumRows();
        int WIN_CON = getNumToWin();
        int col = pos.getColumn();
        int row = pos.getRow();
        int count = 1;
        BoardPosition cell;

        // check right -> left diagonals
        for (int i = 1; row + i < NUM_ROW && col + i < NUM_COL; i++) {
                cell = new BoardPosition(row+i, col+i);
                if (isPlayerAtPos(cell, p)) count++;
                else break;
        }

        for (int i = 1; row - i >= 0 && col - i >= 0; i++) {
                cell = new BoardPosition(row-i, col-i);
                if (isPlayerAtPos(cell, p)) count++;
                else break;
        }

        if (count >= WIN_CON) return true;

        count = 1;


        // check left -> right diagonals
        for (int i = 1; row + i < NUM_ROW && col - i >= 0; i++) {
                cell = new BoardPosition(row+i, col-i);
                if (isPlayerAtPos(cell, p)) count++;
                else break;
        }

        for (int i = 1; row - i >= 0 && col + i < NUM_COL; i++) {
                cell = new BoardPosition(row-i, col+i);
                if (isPlayerAtPos(cell, p)) count++;
                    else break;
        }

        return (count >= WIN_CON);
    }

    /**
     * Returns true if the game board results in a tie game, otherwise it returns false
     *
     * @return True if the the top row is filled, false otherwise
     * @post checkTie = true iff (whatsAtPos(cell) == ' ')
     *
     */
    default boolean checkTie() {

        int NUM_COL = getNumColumns();
        int TOP = getNumRows()-1;
        char HOLDER = ' ';
        BoardPosition cell;

        for (int i = 0; i < NUM_COL; i ++) {
            cell = new BoardPosition(TOP, i);
            if (whatsAtPos(cell) == HOLDER) return false;
        }

        return true;
    }

    /**
     * Checks to see if column can take another token
     *
     * @param c column the token will be placed in
     * @return true if column is able to accept another token, false otherwise.
     * @pre   0 <= c < Columns
     * @post  checkIfFree = true iff  (whatsAtPos(cell) != ' ')
     *
     */
    default boolean checkIfFree(int c) {
        int TOP = getNumRows()-1;
        char HOLDER = ' ';
        BoardPosition cell = new BoardPosition(TOP, c);
        return (whatsAtPos(cell) == HOLDER);
    }

    /**
     * Checks to see if the player has won the game
     *
     * @param c  column the token will be placed in
     * @return returns true if the last token placed resulted in the player winning the game
     * @pre  0 <= 0 < Columns
     * @post checkHorizWin = true iff  (checkHorizWin == true || checkVertWin == true || checkDiagWin == true)
     *
     */
    default boolean checkForWin(int c) {
        int TOP = getNumRows()-1;
        char HOLDER = ' ';
        BoardPosition cell;

        for (int i = TOP; i >= 0; i--) {
            cell = new BoardPosition(i, c);
            char plr = whatsAtPos(cell);
            if ( plr != HOLDER) {
              return ( checkVertWin(cell, plr) || checkHorizWin(cell, plr) || checkDiagWin(cell, plr) );
            }
        }
        return false;
    }

}


