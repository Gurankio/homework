package gurankio;

import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Agonista extends Cliente {

    public Agonista(String nome, String cognome, LocalDateTime nascita, Disciplina disciplina, int numeroTessera, Abbonamento abbonamento) {
        super(nome, cognome, nascita, disciplina, numeroTessera, abbonamento);
    }

    public Agonista(File file) {
        super(file);
    }

    @Override
    protected double calcolaSconto() {
        return Categoria.calcola(this).getSconto();
    }

    @Override
    public String toString() {
        return String.format("%s\nCategoria: %s", super.toString(), Categoria.calcola(this));
    }

    private enum Categoria {

        JUNIOR(0.5),
        SENIOR(0.3),
        GENTLEMAN(0.2);

        private final double sconto;

        Categoria(double sconto) {
            this.sconto = sconto;
        }

        public double getSconto() {
            return sconto;
        }

        public static Categoria calcola(Agonista agonista) {
            long anni = ChronoUnit.YEARS.between(agonista.getNascita(), LocalDateTime.now());
            if (anni < 19) return JUNIOR;
            else if (anni < 40) return SENIOR;
            else return GENTLEMAN;
        }
    }

}
