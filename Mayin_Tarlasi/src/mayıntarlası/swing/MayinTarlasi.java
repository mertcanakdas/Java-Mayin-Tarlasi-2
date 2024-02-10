package mayıntarlası.swing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MayinTarlasi implements MouseListener {

    JFrame frame;
    Buton[][] board = new Buton[10][10];
    int openedBtn;
    
   

    public MayinTarlasi() {
        openedBtn = 0;
        frame = new JFrame("Mayın Tarlası");
        frame.setSize(700, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(10, 10));

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                Buton btn = new Buton(row, col);
              //  btn.setIcon(new ImageIcon(getClass().getResource("/icons/grass.png")));
                btn.addMouseListener(this);
                frame.add(btn);
                board[row][col] = btn;
            }
        }

        generateMine();
        updateCount();

        //print();
        frame.setVisible(true);
    }

    public void generateMine() {
        int i = 0;
        while (i < 10) {
            int randRow = (int) (Math.random() * board.length);
            int randCol = (int) (Math.random() * board[0].length);

            while (board[randRow][randCol].isMine()) {
                randRow = (int) (Math.random() * board.length);
                randCol = (int) (Math.random() * board[0].length);

            }

            board[randRow][randCol].setMine(true);
            i++;

        }
    }

    public void print() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col].isMine()) {
                    board[row][col].setIcon(new ImageIcon(getClass().getResource("/icons/creeper3.png")));
                } else {
                    board[row][col].setText(board[row][col].getCount() + "");
                    board[row][col].setEnabled(false);
                }
            }
        }

    }

    public void updateCount() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col].isMine()) {
                    counting(row, col);
                }

            }
        }

    }

    public void counting(int row, int col) {
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                try {
                    int value = board[i][j].getCount();
                    board[i][j].setCount(++value);
                } catch (Exception e) {
                }
            }
        }
    }

    public void open(int r, int c) {
        
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length || board[r][c].getText().length() > 0 || board[r][c].isEnabled() == false) {
            return;
        } else if (board[r][c].getCount() != 0) {
            
            board[r][c].setText(board[r][c].getCount() + "");
            board[r][c].setEnabled(false);
            
            openedBtn++;
        } else {
            board[r][c].setEnabled(false);
            
            openedBtn++;
            open(r - 1, c);
            open(r + 1, c);
            open(r, c - 1);
            open(r, c + 1);

        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Buton btn = (Buton) e.getComponent();
        if (e.getButton() == 1) {
            if (btn.isMine()) {
               // btn.setBackground(null);
                JOptionPane.showMessageDialog(frame, "Mayına Bastınız Oyun Bitti !");
                print();
            } else {
                open(btn.getRow(), btn.getCol());
                if (openedBtn == (board.length * board[0].length) - 10) {
                    JOptionPane.showMessageDialog(frame, "Tebrikler, hiçbir Creeper'e yakalanmadınız");
                    print();
                }
            }

        } else if (e.getButton() == 3) {
            if (!btn.isFlag()) {
                btn.setIcon(new ImageIcon(getClass().getResource("/icons/flag.png")));
                btn.setFlag(true);
            } else {
                btn.setIcon(null);
                btn.setFlag(false);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
