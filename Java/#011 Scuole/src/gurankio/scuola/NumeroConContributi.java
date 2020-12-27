package gurankio.scuola;

import java.util.function.Supplier;

public class NumeroConContributi {

    private Integer dato;
    private Supplier<Integer> contributi;

    public NumeroConContributi(Integer dato, Supplier<Integer> contributi) {
        this.dato = dato;
        this.contributi = contributi;
    }

    public Integer getDato() {
        return dato;
    }

    public void setDato(Integer dato) {
        this.dato = dato;
    }

    public Integer getContributi() {
        return dato * contributi.get();
    }

    @Override
    public String toString() {
        return dato.toString();
    }
}
