package gurankio;

import gurankio.util.Persistent;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Cliente extends Persistent {

    private String nome;
    private String cognome;
    private LocalDateTime nascita;
    private Disciplina disciplina;
    private int numeroTessera;
    private Abbonamento abbonamento;

    public Cliente(String nome, String cognome, LocalDateTime nascita, Disciplina disciplina, int numeroTessera, Abbonamento abbonamento) {
        this.nome = nome;
        this.cognome = cognome;
        this.nascita = nascita;
        this.disciplina = disciplina;
        this.numeroTessera = numeroTessera;
        this.abbonamento = abbonamento;
    }

    public Cliente(File file) {
        super(file);
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

    public LocalDateTime getNascita() {
        return nascita;
    }

    public void setNascita(LocalDateTime nascita) {
        this.nascita = nascita;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public int getNumeroTessera() {
        return numeroTessera;
    }

    public void setNumeroTessera(int numeroTessera) {
        this.numeroTessera = numeroTessera;
    }

    public Abbonamento getAbbonamento() {
        return abbonamento;
    }

    public void setAbbonamento(Abbonamento abbonamento) {
        this.abbonamento = abbonamento;
    }

    public double calcolaTassa() {
        double totale = getAbbonamento().getTipo().getCosto();
        return totale - (totale * calcolaSconto());
    }

    protected abstract double calcolaSconto();

    @Override
    public String toString() {
        String abbonamento = getAbbonamento().isScaduto() ? "Abbonamento scaduto." : String.format("Abbonamento %s nella disciplina %s", getAbbonamento().getTipo(), getDisciplina());
        return String.format("'%s %s' nato il %s\n%s\nTasso di %.2f€ con %.0f%% di sconto.\nNumero di tessera: %02d", nome, cognome, nascita.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), abbonamento, calcolaTassa(), calcolaSconto() * 100, numeroTessera);
    }
}
