package gurankio;

import java.time.LocalDate;
import java.util.Stack;

public class Minore extends Cliente {

    private Categoria categoria;

    public Minore(String nome, String cognome, LocalDate dataNascita, Categoria categoria) {
        super(nome, cognome, dataNascita);
        this.categoria = categoria;
    }

    public Minore() {
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    double getSconto() {
        return categoria.sconto;
    }

    // Funzioni per IO su file.

    @Override
    public void serialize(Stack<String> tokens) {
        tokens.add(categoria.toString());
        super.serialize(tokens);
    }

    @Override
    public boolean parse(Stack<String> tokens) {
        categoria = Categoria.valueOf(tokens.pop());
        return super.parse(tokens);
    }

    // Struttura Dati

    public enum Categoria {
        BAMBINO(0.65),
        RAGAZZO(0.45),
        TEENAGER(0.35);

        private final double sconto;

        Categoria(double sconto) {
            this.sconto = sconto;
        }
    }
}
