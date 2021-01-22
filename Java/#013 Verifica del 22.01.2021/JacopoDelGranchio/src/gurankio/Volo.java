package gurankio;

import gurankio.menu.io.util.StringPrettify;
import gurankio.menu.io.util.TreeBuilder;

import java.time.LocalDateTime;

public abstract class Volo {

    private LocalDateTime partenza;
    private String aereo;
    private String destinazione;

    public Volo(LocalDateTime partenza, String aereo, String destinazione) {
        this.partenza = partenza;
        this.aereo = aereo;
        this.destinazione = destinazione;
    }

    public LocalDateTime getPartenza() {
        return partenza;
    }

    public void setPartenza(LocalDateTime partenza) {
        this.partenza = partenza;
    }

    public String getAereo() {
        return aereo;
    }

    public void setAereo(String aereo) {
        this.aereo = aereo;
    }

    public String getDestinazione() {
        return destinazione;
    }

    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
    }

    public abstract int calcolaImporto();

    @Override
    public String toString() {
        return new TreeBuilder("Volo")
                .arrow("partenza = " + StringPrettify.toPrettyString(partenza))
                .arrow("aereo = " + StringPrettify.toPrettyString(aereo))
                .arrow("destinazione = " + StringPrettify.toPrettyString(destinazione))
                .endAll()
                .toString();
    }
}
