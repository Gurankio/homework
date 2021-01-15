package gurankio.carrello;

import gurankio.menu.io.util.StringPrettify;
import gurankio.menu.io.util.TreeBuilder;

public class Conservati extends Alimentari {

    private String marca;

    public Conservati(String codice, double prezzoUnitario, String descrizione, double peso, double calorieUnitarie, String marca) {
        super(codice, prezzoUnitario, descrizione, peso, calorieUnitarie);
        this.marca = marca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    @Override
    public double calcolaImporto() {
        return getPrezzoUnitario();
    }

    @Override
    public String toString() {
        return new TreeBuilder("Conservati")
                .arrow("marca = " + StringPrettify.toPrettyString(marca))
                .arrowReverse(super.toString())
                .endAll()
                .toString();
    }
}
