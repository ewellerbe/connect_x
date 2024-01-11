public abstract class AbsGameBoard implements IGameBoard {

    /**
     * This method overrides {@link Object#toString()} to provide a string
     * representation for GameBoard objects.
     *
     * @post [A string representation of GameBoard]
     */
    @Override
    public String toString() {
        String formatted = "";
        int TOP = getNumRows()-1;
        int NUM_COL = getNumColumns();

       for (int i = 0; i < NUM_COL; i++) {
            if (i < 10) formatted = formatted.concat("| " + i);
            else formatted = formatted.concat("|" + i);

       }

       formatted = formatted.concat("|\n");

        for (int r = TOP; r >= 0; r--) {
            for (int c = 0; c < NUM_COL; c++) {
                BoardPosition cell = new BoardPosition(r, c);

                formatted = formatted.concat("|" + whatsAtPos(cell) + " ");

            }
            formatted = formatted.concat("|\n");
        }

        return formatted;
    }
}
