package gurankio.scaffale;

import gurankio.Libro;
import gurankio.menu.io.util.CharPacks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Mensola {

    private List<Libro> volumi;
    private static final int NUM_MAX_VOLUMI = 15;

    Mensola() {
        volumi = new ArrayList<>(NUM_MAX_VOLUMI);
        for (int i=0; i<NUM_MAX_VOLUMI; i++) volumi.add(null);
    }

    int setVolume(Libro libro, int posizione) {
        if (posizione < 0 || posizione >= NUM_MAX_VOLUMI) return -1;
        volumi.set(posizione, libro);
        return 0;
    }

    Libro getVolume(int posizione) {
        if (posizione < 0 || posizione >= NUM_MAX_VOLUMI) return null;
        return volumi.get(posizione);
    }

    int rimuoviVolume(int posizione) {
        if (posizione < 0 || posizione >= NUM_MAX_VOLUMI) return -1;
        volumi.set(posizione, null);
        return 0;
    }

    static int getNumMaxVolumi() {
        return NUM_MAX_VOLUMI;
    }

    long getNumVolumi() {
        return volumi.stream().filter(Objects::nonNull).count();
    }

    @Override
    public String toString() {
        return "Mensola\n"
                + CharPacks.selected.buildArrow(CharPacks.selected.getLineCorner())
                + volumi.stream()
                .map(l -> l == null ? "*" : l.toString())
                .collect(Collectors.joining(" | "));
    }
}
