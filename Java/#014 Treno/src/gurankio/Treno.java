package gurankio;

import gurankio.io.text.TextSerializer;
import gurankio.util.TreeBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Treno implements Serializable {

    private List<Vagone> vagoni;

    public Treno() {
        vagoni = new ArrayList<>();
    }

    public void aggiungiVagone(Vagone vagone) {
        if (vagone == null) return;
        vagoni.add(vagone);
    }

    public void rimuoviVagone(String codice) {
        Vagone vagone = vagoni.stream().filter(v -> v.getCodice().equalsIgnoreCase(codice)).findAny().orElse(null);
        if (vagone != null) vagoni.remove(vagone);
    }

    public Object[] cercaCasaCostruttrice(String casaCostruttrice) {
        return vagoni.stream().filter(v -> v.getCasaCostruttrice().equalsIgnoreCase(casaCostruttrice)).toArray();
    }

    public double calcolaPesoTotale() {
        return vagoni.stream().map(Vagone::calcolaPeso).reduce(Double::sum).orElse(0.0);
    }

    @Override
    public String toString() {
        return "Treno[\n" + TextSerializer.serialize(vagoni).lines().map(s -> "   " + s).collect(Collectors.joining("\n")) + "\n]";
    }
}
