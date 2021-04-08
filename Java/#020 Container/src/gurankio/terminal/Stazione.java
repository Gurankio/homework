package gurankio.terminal;

import java.io.File;
import java.util.Arrays;

public class Stazione extends Deposito {

    public Stazione() {
    }

    public Stazione(File file) {
        super(file);
    }

    public void caricaTreno(Treno treno, int[] codici) {
        Arrays.stream(codici).forEach(codice -> treno.caricaContainer(this.removeContainer(this.findContainer(codice))));
    }

    public void scaricaTreno(Treno treno, int[] codici) {
        Arrays.stream(codici).forEach(codice -> this.addContainer(treno.scaricaContainer(codice)));
    }

    @Override
    public String toString() {
        return String.format("Stazione con %d container.", getContainersMap().size());
    }
}
