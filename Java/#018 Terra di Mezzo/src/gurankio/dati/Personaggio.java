package gurankio.dati;

import java.util.Random;

public class Personaggio extends Combattente {

    private final Razza razza;

    public Personaggio(Random r, Razza razza) {
        super(r);
        this.razza = razza;
    }

    @Override
    public Schieramento getSchieramento() {
        return razza.getSchieramento();
    }

    @Override
    public int getForza() {
        return razza.getForza().apply(getExp());
    }

}
