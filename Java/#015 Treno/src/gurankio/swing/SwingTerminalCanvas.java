package gurankio.swing;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.regex.Pattern;

public class SwingTerminalCanvas extends JPanel {

    private static final Font font = new Font("JetBrains Mono", Font.PLAIN, 14);
    private static final Border down = BorderFactory.createEmptyBorder(4, 8, -12, 8);
    private static final Border up = BorderFactory.createEmptyBorder(-16, 8, 4, 8);

    private ConcurrentLinkedDeque<Command> commandStack;

    private GridLayout grid;
    private JPanel container;
    private boolean downHidden;
    private JLabel[][] chars;

    private Color color = Color.white;
    private int r = 0;
    private int c = 0;

    public SwingTerminalCanvas() {
        commandStack = new ConcurrentLinkedDeque<>();

        grid = new GridLayout(25, 78, 0, 0);

        container = new JPanel();
        container.setLayout(grid);
        container.setBackground(Color.black);
        container.setBorder(down);
        downHidden = true;

        chars = new JLabel[grid.getRows()][grid.getColumns()];
        for (int i = 0; i < grid.getRows(); i++) {
            for (int j = 0; j < grid.getColumns(); j++) {
                JLabel c = new JLabel();
                c.setFont(font);
                c.setForeground(color);
                c.setText(" ");
                chars[i][j] = c;
                container.add(c);
            }
        }

        JFrame frame = new JFrame("Questo schermata è offerta da Java, una lingua di alto livello!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(container);
        // frame.setSize(500, 500);
        // frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        SwingWorker<Object, Object> sw = new SwingWorker<Object, Object>() {

            Color color = Color.white;
            int r = 0;
            int c = 0;

            @Override
            protected Object doInBackground() throws Exception {
                while (true) {
                    while (commandStack.size() > 0) {
                        Command command = commandStack.pop();
                        switch (command.type) {
                            case "print":
                                ((String) command.data).chars().forEach(this::print);
                                break;

                            case "color":
                                color = (Color) command.data;
                                break;

                            case "cursor":
                                r = ((Integer[]) command.data)[0];
                                c = ((Integer[]) command.data)[1];
                                break;
                        }
                    }
                    Thread.sleep(50);
                }
            }

            void print(int b) {
                switch (b) {
                    case '\r':
                        c = 0;
                        break;

                    case '\n':
                        c = 0;
                        r++;
                        repaint();
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
                    if (downHidden) {
                        container.setBorder(up);
                        downHidden = false;

                        for (int c = 0; c < grid.getColumns(); c++) {
                            chars[0][c].setForeground(chars[2][c].getForeground());
                            chars[0][c].setText(chars[2][c].getText());
                        }

                    } else {
                        container.setBorder(down);
                        downHidden = true;

                    }
                    r--;
                }
            }
        };

        sw.execute();
    }

    public void print(String s) {
        commandStack.push(new Command("print", s));
    }

    public void println(String s) {
        commandStack.push(new Command("print", s + "\n"));
    }

    public void setColor(Color color) {
        commandStack.push(new Command("color", color));
    }

    public void setCursor(int r, int c) {
        commandStack.push(new Command("cursor", new Integer[]{r, c}));
    }

    private static class Command {

        String type;
        Object data;

        Command(String type, Object data) {
            this.type = type;
            this.data = data;
        }

    }

}