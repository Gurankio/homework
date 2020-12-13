package gurankio.scaffale;

import gurankio.Libro;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Mensola {

    private List<Libro> volumi;
    private static final int NUM_MAX_VOLUMI = 15;

    protected Mensola() {
        volumi = new ArrayList<>(NUM_MAX_VOLUMI);
        for (int i=0; i<NUM_MAX_VOLUMI; i++) volumi.add(null);
    }

    public int setVolume(Libro libro, int posizione) {
        if (posizione < 0 || posizione >= NUM_MAX_VOLUMI) return -1;
        volumi.set(posizione, libro);
        return 0;
    }

    public Libro getVolume(int posizione) {
        if (posizione < 0 || posizione >= NUM_MAX_VOLUMI) return null;
        return volumi.get(posizione);
    }

    public int rimuoviVolume(int posizione) {
        if (posizione < 0 || posizione >= NUM_MAX_VOLUMI) return -1;
        volumi.set(posizione, null);
        return 0;
    }

    public static int getNumMaxVolumi() {
        return NUM_MAX_VOLUMI;
    }

    public long getNumVolumi() {
        return volumi.stream().filter(Objects::nonNull).count();
    }

    @Override
    public String toString() {
        return "Mensola{" +
                "volumi=" + volumi +
                '}';
    }
}
