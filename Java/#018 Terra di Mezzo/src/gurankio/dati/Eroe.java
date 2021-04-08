package gurankio.dati;

import java.util.Random;

public class Eroe extends Combattente {

    private final Schieramento schieramento;
    private int vita;

    public Eroe(Random r, Schieramento schieramento) {
        super(r);
        this.schieramento = schieramento;
        this.vita = r.nextInt(10) + 1;
    }

    public int getVita() {
        return vita;
    }

    public boolean decrementVita() {
        vita -= 1;
        return vita == 0;
    }

    @Override
    public Schieramento getSchieramento() {
        return schieramento;
    }

    @Override
    public int getForza() {
        return 50 * getExp() + 50 * getVita();
    }
}
