package gurankio;

public class Statua {

    private String titolo;
    private String autore;
    private String epoca;

    public Statua(String titolo, String autore, String epoca) {
        this.titolo = titolo;
        this.autore = autore;
        this.epoca = epoca;
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

    public String getEpoca() {
        return epoca;
    }

    public void setEpoca(String epoca) {
        this.epoca = epoca;
    }

    public boolean uguale(Statua s) {
        return getTitolo().equals(s.getTitolo()) && getAutore().equals(s.getAutore()) && getEpoca().equals(s.getEpoca());
    }

    @Override
    public String toString() {
        return "Statua{" +
                "titolo='" + titolo + '\'' +
                ", autore='" + autore + '\'' +
                ", epoca='" + epoca + '\'' +
                '}';
    }

}
