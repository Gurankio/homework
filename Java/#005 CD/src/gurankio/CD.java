package gurankio;

public class CD {

    private String titolo;
    private String autore;
    private int numeroBrani;
    private int durata; // in secondi interi.

    public CD(String titolo, String autore, int numeroBrani, int durata) {
        this.titolo = titolo;
        this.autore = autore;
        this.numeroBrani = numeroBrani;
        this.durata = durata;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public int getNumeroBrani() {
        return numeroBrani;
    }

    public void setNumeroBrani(int numeroBrani) {
        this.numeroBrani = numeroBrani;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public int compareDurata(CD cd) {
        if (getDurata() == cd.getDurata()) return 0;
        return getDurata() > cd.getDurata() ? 1 : -1;
    }

    @Override
    public String toString() {
        return "CD{" +
                "titolo='" + titolo + '\'' +
                ", autore='" + autore + '\'' +
                ", numeroBrani=" + numeroBrani +
                ", durata=" + durata +
                '}';
    }
}
