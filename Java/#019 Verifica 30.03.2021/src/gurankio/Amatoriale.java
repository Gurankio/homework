package gurankio;

import java.io.File;
import java.time.LocalDateTime;

public class Amatoriale extends Cliente {

    private Esperienza esperienza;

    public Amatoriale(String nome, String cognome, LocalDateTime nascita, Disciplina disciplina, int numeroTessera, Abbonamento abbonamento, Esperienza esperienza) {
        super(nome, cognome, nascita, disciplina, numeroTessera, abbonamento);
        this.esperienza = esperienza;
    }

    public Amatoriale(File file) {
        super(file);
    }

    public Esperienza getEsperienza() {
        return esperienza;
    }

    public void setEsperienza(Esperienza esperienza) {
        this.esperienza = esperienza;
    }

    @Override
    protected double calcolaSconto() {
        return esperienza.getPercentuale();
    }

    public enum Esperienza {

        PRINCIPIANTE(.0),
        MEDIO(.0),
        ESPERTO(.15);

        private final double percentuale;

        Esperienza() {
            percentuale = 0;
        }

        Esperienza(double percentuale) {
            this.percentuale = percentuale;
        }

        public double getPercentuale() {
            return percentuale;
        }
    }

    @Override
    public String toString() {
        return String.format("%s\nLivello di esperienza: %s", super.toString(), esperienza);
    }
}
