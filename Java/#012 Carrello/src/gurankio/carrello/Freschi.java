package gurankio.carrello;

import gurankio.menu.io.util.StringPrettify;
import gurankio.menu.io.util.TreeBuilder;

public class Freschi extends Alimentari {

    private double costoSacchetto;
    private boolean confezionato;

    public Freschi(String codice, double prezzoUnitario, String descrizione, double peso, double calorieUnitarie, double costoSacchetto, boolean confezionato) {
        super(codice, prezzoUnitario, descrizione, peso, calorieUnitarie);
        this.costoSacchetto = costoSacchetto;
        this.confezionato = confezionato;
    }

    public double getCostoSacchetto() {
        return costoSacchetto;
    }

    public void setCostoSacchetto(double costoSacchetto) {
        this.costoSacchetto = costoSacchetto;
    }

    public boolean isConfezionato() {
        return confezionato;
    }

    public void setConfezionato(boolean confezionato) {
        this.confezionato = confezionato;
    }

    @Override
    public double calcolaImporto() {
        return getPrezzoUnitario() + getCostoSacchetto();
    }

    @Override
    public String toString() {
        return new TreeBuilder("Freschi")
                .arrow("costoSacchetto = " + StringPrettify.toPrettyString(costoSacchetto))
                .arrow("confezionato = " + StringPrettify.toPrettyString(confezionato))
                .arrowReverse(super.toString())
                .endAll()
                .toString();
    }

}
