package gurankio;

import gurankio.util.AutoPersistentCSV;
import gurankio.util.PersistentCSV;

import java.time.LocalDate;
import java.util.Queue;
import java.util.Stack;

public abstract class Cliente implements AutoPersistentCSV {

    private String nome;
    private String cognome;
    private LocalDate dataNascita;

    public Cliente(String nome, String cognome, LocalDate dataNascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
    }

    public Cliente() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    // Metodo per generalizzare lo sconto delle sottoclassi.
    abstract double getSconto();

    // Funzioni per IO su file.

    /*
    @Override
    public void serialize(Queue<String> tokens) {
        tokens.add(nome);
        tokens.add(cognome);
        tokens.add(dataNascita.format(Main.DATE_TIME_FORMATTER));
    }

    @Override
    public boolean parse(Queue<String> tokens) {
        nome = tokens.remove();
        cognome = tokens.remove();
        dataNascita = LocalDate.parse(tokens.remove(), Main.DATE_TIME_FORMATTER);
        return true;
    }
    */

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataNascita=" + dataNascita +
                '}';
    }
}
