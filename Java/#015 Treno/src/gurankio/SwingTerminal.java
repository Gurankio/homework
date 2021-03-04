package gurankio;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;

public class SwingTerminal {

    private GridLayout grid;
    private JLabel[][] chars;
    private Color color;
    private int r;
    private int c;


    public SwingTerminal() {
        grid = new GridLayout(25, 78, 0, 0);

        JPanel container = new JPanel();
        container.setLayout(grid);
        container.setBackground(Color.black);
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        chars = new JLabel[grid.getRows()][grid.getColumns()];
        for (int i = 0; i < grid.getRows(); i++) {
            for (int j = 0; j < grid.getColumns(); j++) {
                JLabel c = new JLabel();
                c.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
                c.setBackground(new Color(0, 0, 0, 0));
                c.setForeground(Color.white);
                c.setText(" ");
                chars[i][j] = c;
                container.add(c);
            }
        }

        color = Color.white;
        r = 0;
        c = 0;

        JFrame frame = new JFrame("Questo schermata è offerta da Java, una lingua di alto livello!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(container);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    public void print(int b) {
        switch (b) {
            case '\r':
                c = 0;
                break;

            case '\n':
                c = 0;
                r++;
                break;

            default:
                chars[r][c].setForeground(color);
                chars[r][c++].setText(String.valueOf((char) b));
        }

        if (c == grid.getColumns()) {
            c = 0;
            r++;
        }

        if (r == grid.getRows()) {
            for (int c = 0; c < grid.getColumns(); c++) {
                for (int r = 0; r < grid.getRows() - 1; r++) {
                    chars[r][c].setForeground(chars[r + 1][c].getForeground());
                    chars[r][c].setText(chars[r + 1][c].getText());
                }
                chars[r - 1][c].setText(" ");
            }
            r--;
        }
    }

    public void print(String s) {
        s.chars().forEach(this::print);
    }

    public void println(String s) {
        (s + "\n").chars().forEach(this::print);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setCursor(int r, int c) {
        this.r = r;
        this.c = c;
    }

}