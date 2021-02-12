package gurankio;

import gurankio.io.text.TextSerializer;
import gurankio.util.TreeBuilder;

import java.io.Serializable;

public abstract class Vagone implements Serializable {

    private String codice;
    private double peso; // a vuoto
    private String casaCostruttrice;
    private int annoCostruzione;

    public Vagone(String codice, double peso, String azienda, int annoCostruzione) {
        this.codice = codice;
        this.peso = peso;
        this.casaCostruttrice = azienda;
        this.annoCostruzione = annoCostruzione;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getCasaCostruttrice() {
        return casaCostruttrice;
    }

    public void setCasaCostruttrice(String casaCostruttrice) {
        this.casaCostruttrice = casaCostruttrice;
    }

    public int getAnnoCostruzione() {
        return annoCostruzione;
    }

    public void setAnnoCostruzione(int annoCostruzione) {
        this.annoCostruzione = annoCostruzione;
    }

    public abstract double calcolaPeso();

    @Override
    public String toString() {
        return "Vagone{" +
                "codice='" + codice + '\'' +
                ", peso=" + peso +
                ", casaCostruttrice='" + casaCostruttrice + '\'' +
                ", annoCostruzione=" + annoCostruzione +
                '}';
    }
}
