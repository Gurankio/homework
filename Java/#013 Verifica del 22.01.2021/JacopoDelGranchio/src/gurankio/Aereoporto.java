package gurankio;

import gurankio.menu.io.ConsoleOutput;
import gurankio.menu.io.util.StringPrettify;
import gurankio.menu.io.util.TreeBuilder;

import java.util.ArrayList;

public class Aereoporto {

    private ArrayList<Volo> voli;

    public Aereoporto() {
        voli = new ArrayList<>();
    }

    public void inserisciVolo(Volo volo) {
        voli.add(volo);
    }

    public void eliminaVolo(String aereo) {
        voli.remove(voli.stream().filter(volo -> volo.getAereo().equalsIgnoreCase(aereo)).findFirst().orElse(null));
    }

    public void cercaDestinazione(String destinazione) {
        voli.stream()
            .filter(volo -> volo.getDestinazione().equalsIgnoreCase(destinazione))
            .forEach(ConsoleOutput::println);
    }

    public int fatturatoAnno(int anno) {
        return voli.stream()
                .filter(volo -> volo.getPartenza().getYear() == anno)
                .map(Volo::calcolaImporto)
                .reduce(Integer::sum)
                .orElse(0);
    }

    @Override
    public String toString() {
        return new TreeBuilder("Aereoporto")
                .arrow("voli = " + StringPrettify.toPrettyString(voli))
                .endAll()
                .toString();
    }
}

