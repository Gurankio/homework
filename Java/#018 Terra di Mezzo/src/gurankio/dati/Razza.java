package gurankio.dati;

import java.util.function.Function;

public enum Razza {

    UOMO(Schieramento.BENE, exp -> 30 + 6 * exp),
    ELFO(Schieramento.BENE, exp -> exp < 5 ? 20 + 3 * exp : 80 + 2 * exp),
    NANO(Schieramento.BENE, exp -> 20 + 4 * exp),
    HOBBIT(Schieramento.BENE, exp -> 10 + 3 * exp),
    ORCO(Schieramento.MALE, exp -> exp < 5 ? 30 + 2 * exp : 70 + 3 * exp),
    URUKHAI(Schieramento.MALE, exp -> 50 + 5 * exp),
    SUDRONI(Schieramento.MALE, exp -> 40 + 5 * exp);

    private final Schieramento schieramento;
    private final Function<Integer, Integer> forza;

    Razza(Schieramento schieramento, Function<Integer, Integer> forza) {
        this.schieramento = schieramento;
        this.forza = forza;
    }

    public Schieramento getSchieramento() {
        return schieramento;
    }

    public Function<Integer, Integer> getForza() {
        return forza;
    }
}
