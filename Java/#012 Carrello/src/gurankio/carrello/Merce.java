package gurankio.carrello;

import gurankio.menu.io.util.StringPrettify;
import gurankio.menu.io.util.TreeBuilder;

public abstract class Merce {

    private String codice;
    private double prezzoUnitario;

    public Merce(String codice, double prezzoUnitario) {
        this.codice = codice;
        this.prezzoUnitario = prezzoUnitario;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public double getPrezzoUnitario() {
        return prezzoUnitario;
    }

    public void setPrezzoUnitario(double prezzoUnitario) {
        this.prezzoUnitario = prezzoUnitario;
    }

    public abstract double calcolaImporto();

    @Override
    public String toString() {
        return new TreeBuilder("Merce")
                .arrow("codice = " + StringPrettify.toPrettyString(codice))
                .arrow("prezzoUnitario = " + StringPrettify.toPrettyString(prezzoUnitario))
                .endAll()
                .toString();
    }
}
