package gurankio.swing;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SwingTerminal {

    private ConcurrentLinkedDeque<Command> commandStack;

    private GridLayout grid;
    private JLabel[][] chars;

    public SwingTerminal() {
        commandStack = new ConcurrentLinkedDeque<>();
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

        JFrame frame = new JFrame("Questo schermata è offerta da Java, una lingua di alto livello!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(container);
        frame.setResizable(false);
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
                    Thread.sleep(100);
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