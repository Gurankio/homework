package gurankio.dati;

import java.util.Random;

public abstract class Combattente implements Comparable<Combattente> {

    private int exp;

    public Combattente(Random r) {
        exp = r.nextInt(10) + 1;
    }

    public int getExp() {
        return exp;
    }

    public void incrementExp() {
        exp = Math.min(exp + 1, 10);
    }

    public abstract Schieramento getSchieramento();

    public abstract int getForza();

    @Override
    public int compareTo(Combattente o) {
        return this.getForza() - o.getForza();
    }

    @Override
    public String toString() {
        return getSchieramento().toString();
    }
}
