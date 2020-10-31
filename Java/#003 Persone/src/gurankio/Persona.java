package gurankio;

public class Persona {

    private int eta;
    private String nome;
    private boolean maschio;
    private String professione;

    public Persona(int eta, String nome, boolean maschio, String professione) {
        this.eta = eta;
        this.nome = nome;
        this.maschio = maschio;
        this.professione = professione;
    }

    public int getEta() {
        return eta;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isMaschio() {
        return maschio;
    }

    public void setMaschio(boolean maschio) {
        this.maschio = maschio;
    }

    public String getProfessione() {
        return professione;
    }

    public void setProfessione(String professione) {
        this.professione = professione;
    }

    @Override
    public String toString() {
        return "Sono una persona di nome: %s, sesso: %s, eta: %s, professione: %s".formatted(nome, maschio ? "maschio" : "femmina", eta, professione);
    }
}
