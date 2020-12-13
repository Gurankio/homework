package gurankio.menu.io;

import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

// TODO: String has an indent utility. Too bad.
public class ConsoleOutput {

    private static final int WIDTH = 3;
    private static AtomicInteger indentation = new AtomicInteger(0);
    private static final Stack<Integer> indentationStack = new Stack<>();

    public static void incrementIndentation() {
        indentation.incrementAndGet();
    }

    public static void decrementIndentation() {
        if (indentation.decrementAndGet() < 0) indentation.set(0);
    }

    public static void pushIndentation() {
        indentationStack.push(indentation.get());
    }

    public static void popIndentation() {
        indentation.set(indentationStack.pop());
    }

    // Basic print

    public static void print(String prefix, String s) {
        s.lines().forEach(l -> {
            if (indentation.get() != 0) System.out.print(" ".repeat(WIDTH).repeat(indentation.get()-1));
            System.out.print(prefix);
            System.out.print(l);
        });
    }

    public static void print(String prefix, Object o) {
        print(prefix, o.toString());
    }

    public static void print(String s) {
        s.lines().forEach(l -> {
            System.out.print(" ".repeat(WIDTH).repeat(indentation.get()));
            System.out.print(l);
        });
    }

    public static void print(Object o) {
        print(o.toString());
    }

    // Print + new line

    public static void println(String prefix, String s) {
        s.lines().forEach(l -> {
            if (indentation.get() != 0) System.out.print(" ".repeat(WIDTH).repeat(indentation.get()-1));
            System.out.print(prefix);
            System.out.println(l);
        });
    }

    public static void println(String prefix, Object o) {
        println(prefix, o.toString());
    }

    public static void println(String s) {
        s.lines().forEach(l -> {
            System.out.print(" ".repeat(WIDTH).repeat(indentation.get()));
            System.out.println(l);
        });
    }

    public static void println(Object o) {
        println(o.toString());
    }

    public static void println() {
        System.out.println();
    }

}
