package gurankio.carrello;

import gurankio.menu.io.util.StringPrettify;
import gurankio.menu.io.util.TreeBuilder;

public abstract class Alimentari extends Merce {

    private String descrizione;
    private double peso;
    private double calorieUnitarie;

    public Alimentari(String codice, double prezzoUnitario, String descrizione, double peso, double calorieUnitarie) {
        super(codice, prezzoUnitario);
        this.descrizione = descrizione;
        this.peso = peso;
        this.calorieUnitarie = calorieUnitarie;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getCalorieUnitarie() {
        return calorieUnitarie;
    }

    public void setCalorieUnitarie(double calorieUnitarie) {
        this.calorieUnitarie = calorieUnitarie;
    }

    public double calcolaCalorie() {
        return getPeso() * getCalorieUnitarie();
    }

    @Override
    public String toString() {
        return new TreeBuilder("Alimentari")
                .arrow("descrizione = " + StringPrettify.toPrettyString(descrizione))
                .arrow("peso = " + StringPrettify.toPrettyString(peso))
                .arrow("calorieUnitarie = " + StringPrettify.toPrettyString(calorieUnitarie))
                .arrowReverse(super.toString())
                .endAll()
                .toString();
    }
}
