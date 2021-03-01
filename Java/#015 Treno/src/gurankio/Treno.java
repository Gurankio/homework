package gurankio;

import gurankio.io.data.Persistent;
import gurankio.io.data.PersistentUnique;
import gurankio.io.text.TextSerializer;
import gurankio.menu.MenuOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Treno implements PersistentUnique {

    private List<Vagone> vagoni;

    public Treno() {
        vagoni = new ArrayList<>();
    }

    @MenuOptions.Hide
    public List<Vagone> getVagoni() {
        return vagoni;
    }

    @MenuOptions.Hide
    public void setVagoni(List<Vagone> vagoni) {
        this.vagoni = vagoni;
    }

    public void aggiungiVagone(Vagone vagone) {
        if (vagone == null) return;
        vagoni.add(vagone);
    }

    public void rimuoviVagone(String codice) {
        vagoni.stream()
                .filter(v -> v.getCodice().equalsIgnoreCase(codice))
                .findAny()
                .ifPresent(vagone -> vagoni.remove(vagone));
    }

    public Object[] cercaCasaCostruttrice(String casaCostruttrice) {
        return vagoni.stream().filter(v -> v.getCasaCostruttrice().equalsIgnoreCase(casaCostruttrice)).toArray();
    }

    public double calcolaPesoTotale() {
        return vagoni.stream().map(Vagone::calcolaPeso).reduce(Double::sum).orElse(0.0);
    }

    @Override
    public String toString() {
        return "Treno[\n" + vagoni.stream().map(vagone -> "    " + vagone.toString()).collect(Collectors.joining("\n")) + "\n]";
    }

}
