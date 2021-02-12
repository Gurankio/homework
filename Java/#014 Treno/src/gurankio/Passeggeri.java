package gurankio;

import gurankio.io.text.TextSerializer;
import gurankio.util.TreeBuilder;

public class Passeggeri extends Vagone {

    private static final double PESO_MEDIO = 65;

    private String classe;
    private int postiDisponibili;
    private int postiOccupati;

    public Passeggeri(String codice, double peso, String azienda, int annoCostruzione, String classe, int postiDisponibili, int postiOccupati) {
        super(codice, peso, azienda, annoCostruzione);
        this.classe = classe;
        this.postiDisponibili = postiDisponibili;
        this.postiOccupati = postiOccupati;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    public void setPostiDisponibili(int postiDisponibili) {
        this.postiDisponibili = postiDisponibili;
    }

    public int getPostiOccupati() {
        return postiOccupati;
    }

    public void setPostiOccupati(int postiOccupati) {
        this.postiOccupati = postiOccupati;
    }

    @Override
    public double calcolaPeso() {
        return getPeso() + getPostiOccupati() * PESO_MEDIO;
    }

    @Override
    public String toString() {
        return "Passeggeri{" +
                "classe='" + classe + '\'' +
                ", postiDisponibili=" + postiDisponibili +
                ", postiOccupati=" + postiOccupati +
                "} " + super.toString();
    }
}
