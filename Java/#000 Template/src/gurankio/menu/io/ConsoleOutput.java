package gurankio.menu.io;

import gurankio.Main;
import gurankio.menu.io.util.CharPacks;
import gurankio.menu.io.util.StringPrettify;

import java.util.List;
import java.util.stream.Collectors;

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
    public static void print(String s) {
        s.lines().forEach(l -> {
            System.out.print(CharPacks.selected.getSpacer(indentation * CharPacks.WIDTH));
            System.out.print(l);
        });
    }

    public static void print(Object o) {
        print(StringPrettify.toPrettyString(o));
    }

    public static void arrow(String s) {
        List<String> lines = s.lines().collect(Collectors.toList());
        for (int i=0; i<lines.size(); i++) {
            if (i == 0) {
                indentation--;
                print(CharPacks.selected.buildArrow(CharPacks.selected.getSpacer()) + lines.get(i));
                indentation++;
            } else print(lines.get(i));
        }
    }

    public static void arrow(Object o) {
        arrow(StringPrettify.toPrettyString(o));
    }

    // Print + new line
    public static void println(String s) {
        s.lines().forEach(l -> {
            System.out.print(CharPacks.selected.getSpacer(indentation * CharPacks.WIDTH));
            System.out.println(l);
        });
    }

    public static void println(Object o) {
        println(StringPrettify.toPrettyString(o));
    }

    public static void println() { println(" "); }

    public static void arrowln(String s) {
        List<String> lines = s.lines().collect(Collectors.toList());
        for (int i=0; i<lines.size(); i++) {
            if (i == 0) {
                indentation--;
                println(CharPacks.selected.buildArrow(CharPacks.selected.getSpacer()) + lines.get(i));
                indentation++;
            } else println(lines.get(i));
        }
    }

    public static void arrowln(Object o) {
        arrowln(StringPrettify.toPrettyString(o));
    }

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
