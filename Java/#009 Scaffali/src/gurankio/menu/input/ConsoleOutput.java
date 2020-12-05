package gurankio.menu.input;

import java.util.List;

// TODO: String has an indent utility. Too bad.
public class ConsoleOutput {

    private static final int WIDTH = 3;
    private static int indentation = 0;

    public static void incrementIndentation() {
        indentation++;
    }

    public static void decrementIndentation() {
        indentation--;
        if (indentation < 0) indentation = 0;
    }

    // Basic print

    public static void print(String prefix, String s) {
        s.lines().forEach(l -> {
            if (indentation != 0) System.out.print(" ".repeat(WIDTH).repeat(indentation-1));
            System.out.print(prefix);
            System.out.print(l);
        });
    }

    public static void print(String prefix, Object o) {
        print(prefix, o.toString());
    }

    public static void print(String s) {
        s.lines().forEach(l -> {
            System.out.print(" ".repeat(WIDTH).repeat(indentation));
            System.out.print(l);
        });
    }

    public static void print(Object o) {
        print(o.toString());
    }

    // Print + new line

    public static void println(String prefix, String s) {
        s.lines().forEach(l -> {
            if (indentation != 0) System.out.print(" ".repeat(WIDTH).repeat(indentation-1));
            System.out.print(prefix);
            System.out.println(l);
        });
    }

    public static void println(String prefix, Object o) {
        println(prefix, o.toString());
    }

    public static void println(String s) {
        s.lines().forEach(l -> {
            System.out.print(" ".repeat(WIDTH).repeat(indentation));
            System.out.println(l);
        });
    }

    public static void println(Object o) {
        println(o.toString());
    }

    public static void println() {
        System.out.println();
    }

    // Path

    public static void path(String prefix, List<?> objects) {
        for (int i = 0; i < objects.size(); i++) {
            println(i == 0 ? "" : prefix, objects.get(i));
            indentation++;
        }
        indentation -= objects.size();
    }

    public static void path(List<?> objects) {
        path("└> ", objects);
    }

}
