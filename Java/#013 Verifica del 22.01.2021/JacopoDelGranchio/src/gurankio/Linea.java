package gurankio;

import gurankio.menu.io.util.StringPrettify;
import gurankio.menu.io.util.TreeBuilder;

import java.time.LocalDateTime;

public class Linea extends Volo {

    private static final int FISSO = 2500;
    private static final int PASSEGGIERO = 15;
    private static final int PASSEGGIERO_MINIMO = 100;

    private String compagnia;
    private Integer bigliettiVenduti;

    public Linea(LocalDateTime partenza, String aereo, String compagnia, String destinazione, Integer bigliettiVenduti) {
        super(partenza, aereo, destinazione);
        this.compagnia = compagnia;
        this.bigliettiVenduti = bigliettiVenduti;
    }

    public String getCompagnia() {
        return compagnia;
    }

    public void setCompagnia(String compagnia) {
        this.compagnia = compagnia;
    }

    public Integer getBigliettiVenduti() {
        return bigliettiVenduti;
    }

    public void setBigliettiVenduti(Integer bigliettiVenduti) {
        this.bigliettiVenduti = bigliettiVenduti;
    }

    @Override
    public int calcolaImporto() {
        return FISSO + Math.max(bigliettiVenduti - PASSEGGIERO_MINIMO, 0) * PASSEGGIERO;
    }

    @Override
    public String toString() {
        return new TreeBuilder("Linea")
                .arrow("compagnia = " + StringPrettify.toPrettyString(compagnia))
                .arrow("bigliettiVenduti = " + StringPrettify.toPrettyString(bigliettiVenduti))
                .arrowReverse(super.toString())
                .endAll()
                .toString();
    }
}
