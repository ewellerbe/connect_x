public class BoardPosition {

    /**
     * @invariant [Rows and Columns are within the bounds of MAX_COL and MAX_ROW]
     */

    private final int row, column;

    /**
     * @param r integer representing row coordinate
     * @param c integer representing column coordinate
     *
     * @pre 0 <= r < MAX_ROW AND 0 <= c < MAX_COL
     * @post row = r AND column = c
     */
    public BoardPosition(int r, int c) {
        row = r;
        column = c;
    }

    /**
     * @return integer representing the row of a cell
     * @post  [return the value of row] AND row = #row
     */
    public int getRow(){
        return row;
    }

    /**
     * @return integer representing the column of a cell
     * @post [return the value of column] AND column = #column
     */
    public int getColumn(){
        return column;
    }

    /**
     * This method overrides equals to make sure two
     * board positions are equals
     *
     * @return true if they have the same row and column, false otherwise
     * @post [returns true if the two objects have the same row, and same col] AND col = #col AND row = #row
     */
    @Override
    public boolean equals(Object o) {

        if (getClass() != o.getClass())
            return false;

        BoardPosition bp = (BoardPosition) o;
        return (bp.row == this.row && bp.column == this.column);
    }


    /**
     * This method overrides {@link Object#toString()} to provide a string
     * representation for BoardPosition objects.
     *
     * @return A string
     * @post [returns a formatted string <row>,<column>] AND col = #col AND row = #row
     */
    @Override
    public String toString() {
        return (row + ", " + column);
    }


}
