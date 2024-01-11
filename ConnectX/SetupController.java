/*
This class is the controller for our setup screen. The processButtonClick method is called by
SetupView when someone clicks on the submit button. It is passed in the rows, cols, players and
the number to win by the view, but it still needs to validate that input. If there are any
errors it can use the displayError method in the SetupView class to inform the player of the
error, then wait for them to fix it and resubmit.

If there are no errors it will create a new IGameBoard object (the implementation will depend on
the size of the game board) to serve as the model, and the ConnectXController and ConnectXView.
Control is then passed over the the event dispatch thread that will wait for an event to occur
 */

public class SetupController {
    private SetupView view;
    private int max_size = 20;
	private int min_size = 3;
	private int min_to_win = 3;
    private final int BOARD_CUTOFF = 100;

    public SetupController(SetupView v)
    {
        view = v;
    }

    public void processButtonClick(int rows, int cols, int players, int numWin )
    {
        String errorMsg = "";
        if(rows < min_size || rows > max_size)
        {
            errorMsg += "Row constraints: [" + min_size + "-" + max_size + "] ";
        }

        if(cols < min_size || cols > max_size)
        {
            errorMsg += "Column constraints: ["+ min_size + "-" + max_size + "] ";
        }

        if (numWin > rows)
        {
            errorMsg += "Win threshold cannot exceed rows ";
        }

        if (numWin > cols)
        {
            errorMsg += "Win threshold cannot exceed columns ";
        }

        if(numWin < min_to_win)
        {
            errorMsg += "Win threshold must atleast be " + min_to_win;
        }

        if(!errorMsg.equals(""))
        {
            view.displayError(errorMsg);

        }
        
        else
        {
            view.closeScreen();
            IGameBoard model;
            //if the board is too big we'll want the memory efficient version
            if(rows*cols > BOARD_CUTOFF)
            {
                model = new GameBoardMem(rows, cols, numWin);
            }
            else {
                model = new GameBoard(rows, cols, numWin);
            }
            ConnectXView tview = new ConnectXView(rows, cols);
            ConnectXController tcontroller = new ConnectXController(model, tview, players);

            tview.registerObserver(tcontroller);
        }
    }
}
