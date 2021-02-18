package gurankio.util;

import java.util.*;
import java.util.stream.Collectors;

// Does not allow empty strings.
public class TreeBuilder {

    private final CharPacks pack;
    private final StringBuilder builder;
    private int indentation;
    private int arrowCount;

    public TreeBuilder(CharPacks pack) {
        this.pack = pack;
        this.builder = new StringBuilder();
        this.indentation = 0;
        this.arrowCount = 1;
    }

    public TreeBuilder() {
        this(CharPacks.selected);
    }

    public TreeBuilder(String start) {
        this();
        start(start);
    }

    public TreeBuilder start(String string) {
        if (string == null || string.equalsIgnoreCase("")) return this;
        arrow(string);
        indentation++;
        return this;
    }

    public TreeBuilder arrow(String string) {
        if (string == null || string.equalsIgnoreCase("")) return this;
        List<String> lines = Arrays.asList(string.stripTrailing().split("\n"));
        for (int i=0; i<lines.size(); i++) {
            if (indentation > 1) {
                for (int j=0; j<indentation-1; j++) builder.append(pack.getLineVertical()).append(pack.getSpacer(CharPacks.WIDTH - 1));
            }
            if (i == 0) {
                if (indentation > 0) builder.append(pack.buildArrow(pack.getLineT()));
            } else {
                if (indentation > 0) builder.append(pack.getLineVertical()).append(pack.getSpacer(CharPacks.WIDTH - 1));
            }
            builder.append(lines.get(i));
            builder.append("\n");
        }
        return this;
    }

    public TreeBuilder arrowCounted(String string) {
        if (string == null || string.equalsIgnoreCase("")) return this;
        List<String> lines = Arrays.asList(string.stripTrailing().split("\n"));
        for (int i=0; i<lines.size(); i++) {
            if (indentation > 1) {
                for (int j=0; j<indentation-1; j++) builder.append(pack.getLineVertical()).append(pack.getSpacer(CharPacks.WIDTH - 1));
            }
            if (i == 0) {
                if (indentation > 0) builder.append(pack.buildArrow(pack.getLineT()));
                builder.append(String.format("%-20s <%02d>", lines.get(i), arrowCount++));
            } else {
                if (indentation > 0) builder.append(pack.getLineVertical()).append(pack.getSpacer(CharPacks.WIDTH - 1));
                builder.append(lines.get(i));
            }
            builder.append("\n");
        }
        return this;
    }

    public TreeBuilder arrowReverse(String string) {
        if (string == null || string.equalsIgnoreCase("")) return this;
        List<String> lines = Arrays.asList(string.stripTrailing().split("\n"));
        for (int i=0; i<lines.size(); i++) {
            if (indentation > 1) {
                for (int j=0; j<indentation-1; j++) builder.append(pack.getLineVertical()).append(pack.getSpacer(CharPacks.WIDTH - 1));
            }
            if (i == 0) {
                if (indentation > 0) builder.append(pack.buildArrowReverse(pack.getLineT()));
            } else {
                if (indentation > 0) builder.append(pack.getLineVertical()).append(pack.getSpacer(CharPacks.WIDTH - 1));
            }
            builder.append(lines.get(i));
            builder.append("\n");
        }
        return this;
    }

    public TreeBuilder end() {
        if (indentation == 0) return this;
        indentation--;

        List<Integer> lengths = builder.toString().lines().map(String::length).collect(Collectors.toList());
        List<Integer> starts = new ArrayList<>();
        starts.add(0);
        for (int i=0; i<lengths.size(); i++) starts.add(starts.get(i)+lengths.get(i)+1); // String::length does not count newlines.

        for (int i=lengths.size()-1; i>=0; i--) {
            if (lengths.get(i) > indentation * CharPacks.WIDTH) {
                int p = starts.get(i) + indentation * CharPacks.WIDTH;
                if (builder.charAt(p) == pack.getLineT()) {
                    builder.setCharAt(p, pack.getLineCorner());
                    for (int j=lengths.size()-1; j>i; j--) {
                        builder.setCharAt(starts.get(j) + indentation * CharPacks.WIDTH, pack.getSpacer());
                    }
                    break;
                }
            }
        }

        return this;
    }

    public TreeBuilder endAll() {
        while (indentation > 0) end();
        return this;
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
