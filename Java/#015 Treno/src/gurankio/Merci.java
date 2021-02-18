package gurankio;

import gurankio.io.text.TextSerializer;
import gurankio.util.TreeBuilder;

import java.io.Serializable;

public class Merci extends Vagone {

    private double volume;
    private double pesoMassimo;
    private double pesoEffettivo;

    public Merci() {
        this("-", 0, "default", 0, 0, 0, 0);
    }

    public Merci(String codice, double peso, String azienda, int annoCostruzione, double volume, double pesoMassimo, double pesoEffettivo) {
        super(codice, peso, azienda, annoCostruzione);
        this.volume = volume;
        this.pesoMassimo = pesoMassimo;
        this.pesoEffettivo = pesoEffettivo;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getPesoMassimo() {
        return pesoMassimo;
    }

    public void setPesoMassimo(double pesoMassimo) {
        this.pesoMassimo = pesoMassimo;
    }

    public double getPesoEffettivo() {
        return pesoEffettivo;
    }

    public void setPesoEffettivo(double pesoEffettivo) {
        this.pesoEffettivo = pesoEffettivo;
    }

    @Override
    public double calcolaPeso() {
        return getPeso() + getPesoEffettivo();
    }

    @Override
    public String toString() {
        return "Merci{" +
                "volume=" + volume +
                ", pesoMassimo=" + pesoMassimo +
                ", pesoEffettivo=" + pesoEffettivo +
                "} " + super.toString();
    }
}
