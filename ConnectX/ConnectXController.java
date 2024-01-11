public class ConnectXController {
    private IGameBoard curGame;
    private ConnectXView screen;
    public static final int MAX_PLAYERS = 10;
    private char[] playerChar = {'X', 'O', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    private int numPlayers;
    private int currentPlayer;

    ConnectXController(IGameBoard model, ConnectXView view, int np) {
        this.curGame = model;
        this.screen = view;
        numPlayers = np;
    }

    public void processButtonClick(int col) {
        if (curGame.checkForWin(col)) {
            newGame();
            return;
        }
        if (curGame.checkTie()) {
            newGame();
            return;
        }
        screen.setMessage("Player " + playerChar[(currentPlayer + 1) % numPlayers] + "'s turn");
        if (!curGame.checkIfFree(col)) {
            screen.setMessage("Choose another column");
        } else if (curGame.checkIfFree(col) && !curGame.checkForWin(col)) {
            int r = -1;
            for (int i = 0; i < curGame.getNumRows() && r == -1; i++) {
                BoardPosition pos = new BoardPosition(i, col);
                if (curGame.whatsAtPos(pos) == ' ')
                    r = i;
            }
            curGame.placeToken(playerChar[currentPlayer % numPlayers], col);
            screen.setMarker(r, col, playerChar[currentPlayer % numPlayers]);
            currentPlayer++;
        }
        if (curGame.checkTie()) {
            screen.setMessage("Its a tie! Click to play again");
        }
        if (curGame.checkForWin(col)) {
            currentPlayer--;
            screen.setMessage("Player " + playerChar[currentPlayer % numPlayers] +
                    " wins! Click to play again");
        }
    }

    private void newGame() {
        screen.dispose();
        SetupView setupScreen = new SetupView();
        SetupController setupController = new SetupController(setupScreen);
        setupScreen.registerObserver(setupController);
    }
}
