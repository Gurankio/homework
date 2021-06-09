package gurankio;

import java.time.LocalDate;
import java.util.Queue;
import java.util.Stack;

public class Adulto extends Cliente {

    private Sesso sesso;

    public Adulto(String nome, String cognome, LocalDate dataNascita, Sesso sesso) {
        super(nome, cognome, dataNascita);
        this.sesso = sesso;
    }

    public Adulto() {
    }

    public Sesso getSesso() {
        return sesso;
    }

    public void setSesso(Sesso sesso) {
        this.sesso = sesso;
    }

    @Override
    double getSconto() {
        return sesso.sconto;
    }

    // Funzioni per IO su file.

    /*
    @Override
    public void serialize(Queue<String> tokens) {
        tokens.add(sesso.toString());
        super.serialize(tokens);
    }

    @Override
    public boolean parse(Queue<String> tokens) {
        sesso = Sesso.valueOf(tokens.remove());
        return super.parse(tokens);
    }
    */

    @Override
    public String toString() {
        return "Adulto{" +
                "sesso=" + sesso +
                '}' + super.toString();
    }

    // Struttura Dati

    public enum Sesso {
        UOMO(0.25),
        DONNA(0.30);

        private final double sconto;

        Sesso(double sconto) {
            this.sconto = sconto;
        }
    }


}
