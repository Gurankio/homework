package gurankio;

import gurankio.menu.io.util.StringPrettify;
import gurankio.menu.io.util.TreeBuilder;

import java.time.LocalDateTime;

public class Charter extends Volo {

    private static int FISSO = 3000;
    private static int PASSEGGIERO = 20;

    private String cliente;
    private String pilota;
    private int numeroPasseggieri;

    public Charter(LocalDateTime partenza, String aereo, String destinazione, String cliente, String pilota, int numeroPasseggieri) {
        super(partenza, aereo, destinazione);
        this.cliente = cliente;
        this.pilota = pilota;
        this.numeroPasseggieri = numeroPasseggieri;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getPilota() {
        return pilota;
    }

    public void setPilota(String pilota) {
        this.pilota = pilota;
    }

    public int getNumeroPasseggieri() {
        return numeroPasseggieri;
    }

    public void setNumeroPasseggieri(int numeroPasseggieri) {
        this.numeroPasseggieri = numeroPasseggieri;
    }

    @Override
    public int calcolaImporto() {
        return FISSO + numeroPasseggieri * PASSEGGIERO;
    }

    @Override
    public String toString() {
        return new TreeBuilder("Charter")
                .arrow("cliente = " + StringPrettify.toPrettyString(cliente))
                .arrow("pilota = " + StringPrettify.toPrettyString(pilota))
                .arrow("numeroPasseggieri = " + StringPrettify.toPrettyString(numeroPasseggieri))
                .arrowReverse(super.toString())
                .endAll()
                .toString();
    }
}
