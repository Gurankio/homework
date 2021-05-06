package gurankio.data;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.Stack;

public class Minore extends Cliente {

    private final ObjectProperty<Categoria> categoria;

    public Minore() {
        this.categoria = new SimpleObjectProperty<>();
        this.scontoProperty().bind(getCategoria().scontoProperty());
    }

    public Minore(String nome, String cognome, LocalDate dataNascita, Categoria categoria) {
        super(nome, cognome, dataNascita);
        this.categoria = new SimpleObjectProperty<>(categoria);
        this.scontoProperty().bind(getCategoria().scontoProperty());
    }

    public Categoria getCategoria() {
        return categoria.get();
    }

    public ObjectProperty<Categoria> categoriaProperty() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria.set(categoria);
    }

    // Funzioni per IO su file.

    @Override
    public void serialize(Stack<String> tokens) {
        tokens.add(getCategoria().toString());
        super.serialize(tokens);
    }

    @Override
    public boolean parse(Stack<String> tokens) {
        setCategoria(Categoria.valueOf(tokens.pop()));
        return super.parse(tokens);
    }

    // Struttura Dati

    public enum Categoria {
        BAMBINO(0.65),
        RAGAZZO(0.45),
        TEENAGER(0.35);

        private final DoubleProperty sconto;

        Categoria(double sconto) {
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
