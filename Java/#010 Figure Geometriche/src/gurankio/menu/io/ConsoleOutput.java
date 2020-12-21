package gurankio.menu.io;

import gurankio.Main;
import gurankio.menu.io.util.CharPacks;
import gurankio.menu.io.util.StringPrettify;

public class ConsoleOutput {

    private static int indentation = 1;

    public static void incrementIndentation() {
        indentation++;
    }

    public static void decrementIndentation() {
        indentation--;
        if (indentation < 1) indentation = 1;
    }

    // Basic print
    public static void arrow(String s) {
        System.out.print(CharPacks.selected.getSpacer((indentation - 1) * CharPacks.WIDTH) + CharPacks.selected.buildArrow(CharPacks.selected.getSpacer()) + s);
    }

    public static void arrow(Object o) {
        arrow(StringPrettify.toPrettyString(o));
    }

    public static void print(String s) {
        s.lines().forEach(l -> {
            System.out.print(CharPacks.selected.getSpacer(indentation * CharPacks.WIDTH));
            System.out.print(l);
        });
    }

    public static void print(Object o) {
        print(StringPrettify.toPrettyString(o));
    }

    // Print + new line
    public static void arrowln(String s) {
        System.out.println(CharPacks.selected.getSpacer((indentation - 1) * CharPacks.WIDTH) + CharPacks.selected.buildArrow(CharPacks.selected.getSpacer()) + s);
    }

    public static void arrowln(Object o) {
        arrowln(StringPrettify.toPrettyString(o));
    }

    public static void println(String s) {
        s.lines().forEach(l -> {
            System.out.print(CharPacks.selected.getSpacer(indentation * CharPacks.WIDTH));
            System.out.println(l);
        });
    }

    public static void println(Object o) {
        print(StringPrettify.toPrettyString(o));
    }

    public static void println() { println(" "); }

    // Debug
    public static void debug(String s) {
        if (Main.DEBUG) print(s);
    }

    public static void debug(Object o) {
        debug(StringPrettify.toPrettyString(o));
    }

    public static void debugln(String s) {
        if (Main.DEBUG) println(s);
    }

    public static void debugln(Object o) {
        debugln(StringPrettify.toPrettyString(o));
    }

    public static void debugln() { debugln(" "); }

}
