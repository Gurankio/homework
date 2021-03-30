package gurankio;

import gurankio.util.GenericScanner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Abbonamento {

    private Tipo tipo;
    private LocalDateTime creazione;

    public Abbonamento() {
        tipo = null;
        creazione = null;
    }

    public Abbonamento(Tipo tipo, LocalDateTime creazione) {
        this.tipo = tipo;
        this.creazione = creazione;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getCreazione() {
        return creazione;
    }

    public void setCreazione(LocalDateTime creazione) {
        this.creazione = creazione;
    }

    public boolean isScaduto() {
        return ChronoUnit.MONTHS.between(getCreazione(), LocalDateTime.now()) >= getTipo().getNumeroMesi();
    }

    public enum Tipo {

        MENSILE(1, 50),
        TRIMESTRALE(3,  120),
        ANNUALE(12, 300);

        private final int numeroMesi;
        private final double costo;

        Tipo() {
            numeroMesi = 0;
            costo = 0;
        }

        Tipo(int numeroMesi, double costo){
            this.numeroMesi = numeroMesi;
            this.costo = costo;
        }

        public int getNumeroMesi () {
            return numeroMesi;
        }

        public double getCosto () {
            return costo;
        }
    }
}
