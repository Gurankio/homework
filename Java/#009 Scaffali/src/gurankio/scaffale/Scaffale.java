package gurankio.scaffale;

import gurankio.Libro;

import java.util.ArrayList;
import java.util.List;

public class Scaffale {

    private List<Mensola> ripiani;
    private static final int NUM_RIPIANI = 5;

    public Scaffale() {
        ripiani = new ArrayList<>(NUM_RIPIANI);
        for (int i=0; i<NUM_RIPIANI; i++) ripiani.add(new Mensola());
    }

    public int setLibro(Libro libro, int ripiano, int posizione) {
        ripiani.get(ripiano).setVolume(libro, posizione);
        return 1; // TODO: Cosa dovrebbe ritornare?
    }

    public Libro getLibro(int ripiano, int posizione) {
        return ripiani.get(ripiano).getVolume(posizione);
    }

    public int rimuoviLibro(int ripiano, int posizione) {
        ripiani.get(ripiano).rimuoviVolume(posizione);
        return 1; // TODO: Cosa dovrebbe ritornare?
    }

    public static int getNumRipiani() {
        return NUM_RIPIANI;
    }

    public int getNumMaxLibri() {
        return NUM_RIPIANI * Mensola.getNumMaxVolumi();
    }

    public long getNumLibri() {
        return ripiani.stream()
                .map(Mensola::getNumVolumi)
                .reduce(Long::sum)
                .orElse(0L);
    }

    public long getNumLibri(int ripiano) {
        return ripiani.get(ripiano).getNumVolumi();
    }

    @Override
    public String toString() {
        return "Scaffale{" +
                "ripiani=" + ripiani +
                '}';
    }
}
