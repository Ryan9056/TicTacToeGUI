import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TicTacToeFrame extends JFrame {
    JPanel mainPnl;
    JPanel TTTPnl;  // Top
    JPanel controlPnl; // Bottom

    JButton quitBtn;

    TicTacToeButton[][] board = new TicTacToeButton[3][3];


    private final int ROW = 3;
    private final int COL = 3;
    boolean playing = true;
    String player = "X";
    int moveCnt = 0;
    final int MOVES_FOR_WIN = 5;
    final int MOVES_FOR_TIE = 7;

    public TicTacToeFrame()
    {
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());

        createTicTacTie();
        createControlPanel();
        mainPnl.add(controlPnl, BorderLayout.SOUTH);
        add(TTTPnl, BorderLayout.NORTH);
        add(mainPnl);
        setSize(810, 575);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


    private void createTicTacTie() {
        TTTPnl = new JPanel();
        TTTPnl.setPreferredSize(new Dimension(810, 500));
        TTTPnl.setLayout(new GridLayout(3,3));



        for( int row = 0; row < 3; row++)
            for(int col= 0; col < 3; col++)
            {
                board[row][col] = new TicTacToeButton(row, col);
                board[row][col].setText(" ");
                board[row][col].addActionListener(listener);
                TTTPnl.add(board[row][col]);
            }

    }

    ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof TicTacToeButton) {
                if (isValidMove(((TicTacToeButton) e.getSource()).getRow(),((TicTacToeButton) e.getSource()).getCol())) {
                    Game(((TicTacToeButton) e.getSource()).getRow(),((TicTacToeButton) e.getSource()).getCol());;
                }
            }
        }
    };


    private void playAgain()
    {
        int choice = JOptionPane.showConfirmDialog(null, "Would you like to play again?",null,JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            for( int row = 0; row < 3; row++)
                for(int col= 0; col < 3; col++)
                {
                    board[row][col].setText(" ");
                }
            player = "O";
            moveCnt = 0;
        } else if (choice == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }


    private void createControlPanel()
    {
        controlPnl = new JPanel();
        controlPnl.setLayout(new GridLayout(1, 1));

        quitBtn = new JButton("Quit!");
        quitBtn.setFont(new Font("Verdana", Font.PLAIN, 20));
        quitBtn.addActionListener((ActionEvent ae) -> System.exit(0));


        controlPnl.add(quitBtn);

    }

    private void Game(int row, int col){


            board[row][col].setText(player);
            moveCnt++;

            if (moveCnt >= MOVES_FOR_WIN) {
                if (isWin(player)) {

                    JOptionPane.showMessageDialog(null, "Player " + player + " wins!", null, JOptionPane.INFORMATION_MESSAGE);
                    playAgain();

                }
            }
            if (moveCnt >= MOVES_FOR_TIE) {
                if (isTie()) {

                    JOptionPane.showMessageDialog(null, "It's a Tie", null, JOptionPane.INFORMATION_MESSAGE);
                    playAgain();

                }
            }

            if (player.equals("X")) {
                player = "O";

            } else {
                player = "X";

            }

    }

    private boolean isValidMove(int row, int col)
    {
        boolean retVal = false;
        String text = board[row][col].getText();
        if(text.equals(" ")) {
            retVal = true;
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Move", null, JOptionPane.INFORMATION_MESSAGE);
        }


        return retVal;

    }
    private boolean isWin(String player)
    {
        if(isColWin(player) || isRowWin(player) || isDiagnalWin(player))
        {
            return true;
        }

        return false;
    }
    private boolean isColWin(String player)
    {
        // checks for a col win for specified player
        for(int col=0; col < COL; col++)
        {
            if(board[0][col].getText().equals(player) &&
                    board[1][col].getText().equals(player) &&
                    board[2][col].getText().equals(player))
            {
                return true;
            }
        }
        return false; // no col win
    }
    private boolean isRowWin(String player)
    {
        // checks for a row win for the specified player
        for(int row=0; row < ROW; row++)
        {
            if(board[row][0].getText().equals(player) &&
                    board[row][1].getText().equals(player) &&
                    board[row][2].getText().equals(player))
            {
                return true;
            }
        }
        return false; // no row win
    }
    private boolean isDiagnalWin(String player)
    {
        // checks for a diagonal win for the specified player

        if(board[0][0].getText().equals(player) &&
                board[1][1].getText().equals(player) &&
                board[2][2].getText().equals(player) )
        {
            return true;
        }
        if(board[0][2].getText().equals(player) &&
                board[1][1].getText().equals(player) &&
                board[2][0].getText().equals(player) )
        {
            return true;
        }
        return false;
    }

    private boolean isTie()
    {
        boolean xFlag = false;
        boolean oFlag = false;
        // Check all 8 win vectors for an X and O so
        // no win is possible
        // Check for row ties
        for(int row=0; row < ROW; row++)
        {
            if(board[row][0].getText().equals("X") ||
                    board[row][1].getText().equals("X") ||
                    board[row][2].getText().equals("X"))
            {
                xFlag = true; // there is an X in this row
            }
            if(board[row][0].getText().equals("O") ||
                    board[row][1].getText().equals("O") ||
                    board[row][2].getText().equals("O"))
            {
                oFlag = true; // there is an O in this row
            }

            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a row win
            }

            xFlag = oFlag = false;

        }
        // Now scan the columns
        for(int col=0; col < COL; col++)
        {
            if(board[0][col].getText().equals("X") ||
                    board[1][col].getText().equals("X") ||
                    board[2][col].getText().equals("X"))
            {
                xFlag = true; // there is an X in this col
            }
            if(board[0][col].getText().equals("O") ||
                    board[1][col].getText().equals("O") ||
                    board[2][col].getText().equals("O"))
            {
                oFlag = true; // there is an O in this col
            }

            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a col win
            }
        }
        // Now check for the diagonals
        xFlag = oFlag = false;

        if(board[0][0].getText().equals("X") ||
                board[1][1].getText().equals("X") ||
                board[2][2].getText().equals("X") )
        {
            xFlag = true;
        }
        if(board[0][0].getText().equals("O") ||
                board[1][1].getText().equals("O") ||
                board[2][2].getText().equals("O") )
        {
            oFlag = true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }
        xFlag = oFlag = false;

        if(board[0][2].getText().equals("X") ||
                board[1][1].getText().equals("X") ||
                board[2][0].getText().equals("X") )
        {
            xFlag =  true;
        }
        if(board[0][2].getText().equals("O") ||
                board[1][1].getText().equals("O") ||
                board[2][0].getText().equals("O") )
        {
            oFlag =  true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }

        // Checked every vector so I know I have a tie
        return true;
    }

}
