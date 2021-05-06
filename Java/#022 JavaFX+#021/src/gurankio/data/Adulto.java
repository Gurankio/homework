package gurankio.data;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.Stack;

public class Adulto extends Cliente {

    private final ObjectProperty<Sesso> sesso;

    public Adulto() {
        this.sesso = new SimpleObjectProperty<>();
        this.scontoProperty().bind(getSesso().scontoProperty());
    }

    public Adulto(String nome, String cognome, LocalDate dataNascita, Sesso sesso) {
        super(nome, cognome, dataNascita);
        this.sesso = new SimpleObjectProperty<>(sesso);
        this.scontoProperty().bind(getSesso().scontoProperty());
    }

    public Sesso getSesso() {
        return sesso.get();
    }

    public ObjectProperty<Sesso> sessoProperty() {
        return sesso;
    }

    public void setSesso(Sesso sesso) {
        this.sesso.set(sesso);
    }

    // Funzioni per IO su file.

    @Override
    public void serialize(Stack<String> tokens) {
        tokens.add(getSesso().toString());
        super.serialize(tokens);
    }

    @Override
    public boolean parse(Stack<String> tokens) {
        setSesso(Sesso.valueOf(tokens.pop()));
        return super.parse(tokens);
    }

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

        private final DoubleProperty sconto;

        Sesso(double sconto) {
            this.sconto = new SimpleDoubleProperty(sconto);
        }

        public double getSconto() {
            return sconto.get();
        }

        public DoubleProperty scontoProperty() {
            return sconto;
        }

        public void setSconto(double sconto) {
            this.sconto.set(sconto);
        }
    }


}
