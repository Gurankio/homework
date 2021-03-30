package gurankio.util;

import java.io.*;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

// TODO: thread-safe, when multithreading could print Thread name, also object caller with stacktrace?
public enum Logger {

    DEBUG    (System.out, " @ "),
    FILE     (Logger::file, null),
    DIRECT   (System.out, null),
    INFO     (System.out, "   "),
    WARN     (System.out, " ! "),
    ERROR    (System.err, " ! "),
    FATAL    (System.err, " # ");

    private static final BitSet ENABLED = new BitSet(values().length);
    static {
        FILE.disable();
        DEBUG.disable();
        DIRECT.enable();
        INFO.enable();
        WARN.enable();
        ERROR.enable();
        FATAL.enable();
    }

    private final PrintStream stream;
    private final String indent;

    Logger(PrintStream stream, String indent) {
        this.stream = stream;
        this.indent = indent;
    }

    Logger(Supplier<PrintStream> supplier, String indent) {
        this.stream = supplier.get();
        this.indent = indent;
    }
    
    public void enable() {
        ENABLED.set(ordinal(), true);
    }

    public void disable() {
        ENABLED.set(ordinal(), false);
    }

    // Exception

    public void exception(Exception e) {
        StringBuilder string = new StringBuilder();
        e.printStackTrace(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                string.append((char) b);
            }
        }));
        print(string);
    }

    // Print Wrapper

    public void print(String string) {
        string = wrap(string, indent);
        if (ENABLED.get(ordinal())) stream.print(string);
        if (this != FILE) FILE.print(string);
    }

    public void print(Object o) {
        print(o.toString());
    }

    // Println Wrapper

    public void println(String string) {
        print(string + "\n");
    }

    public void println(Object o) {
        println(o.toString());
    }

    public void println() {
        println("");
    }

    // Utility

    public static String wrap(String string, String indent) {
        if (indent == null) return string;
        return string.lines()
                .map(line -> line.isBlank() ? line : indent + line)
                .collect(Collectors.joining("\n"))
                + (string.endsWith("\n") ? "\n" : "");
    }

    // TODO: would be nice to not create a file if FILE is disabled.
    private static PrintStream file() {
        try {
            File file = new File("./logs/" + System.currentTimeMillis() + ".log");
            file.getParentFile().mkdirs();
            return new PrintStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            return new PrintStream(new OutputStream() {
                @Override
                public void write(int b) {
                    // NO-OP
                }
            });
        }
    }

}
