package gurankio.model;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.Stack;

public abstract class Cliente implements PersistentCSV {

    private final StringProperty nome;
    private final StringProperty cognome;
    private final ObjectProperty<LocalDate> dataNascita;
    private final DoubleProperty sconto;

    public Cliente(String nome, String cognome, LocalDate dataNascita) {
        this.nome = new SimpleStringProperty(nome);
        this.cognome = new SimpleStringProperty(cognome);
        this.dataNascita = new SimpleObjectProperty<>(dataNascita);
        this.sconto = new SimpleDoubleProperty();
    }

    public Cliente() {
        this.nome = new SimpleStringProperty();
        this.cognome = new SimpleStringProperty();
        this.dataNascita = new SimpleObjectProperty<>();
        this.sconto = new SimpleDoubleProperty();
    }

    public String getNome() {
        return nome.get();
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getCognome() {
        return cognome.get();
    }

    public StringProperty cognomeProperty() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome.set(cognome);
    }

    public LocalDate getDataNascita() {
        return dataNascita.get();
    }

    public ObjectProperty<LocalDate> dataNascitaProperty() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita.set(dataNascita);
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

    // Funzioni per IO su file.

    @Override
    public void serialize(Stack<String> tokens) {
        tokens.push(getNome());
        tokens.push(getCognome());
        tokens.push(getDataNascita().format(PersistenceManager.DATE_TIME_FORMATTER));
    }

    @Override
    public boolean parse(Stack<String> tokens) {
        setNome(tokens.pop());
        setCognome(tokens.pop());
        setDataNascita(LocalDate.parse(tokens.pop(), PersistenceManager.DATE_TIME_FORMATTER));
        return true;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataNascita=" + dataNascita +
                '}';
    }
}
