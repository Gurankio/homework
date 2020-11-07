package gurankio;

public class Quadro {

    private String titolo;
    private String autore;
    private String epoca;

    public Quadro(String titolo, String autore, String epoca) {
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

    public boolean uguale(Quadro q) {
        return getTitolo().equals(q.getTitolo()) && getAutore().equals(q.getAutore()) && getEpoca().equals(q.getEpoca());
    }

    @Override
    public String toString() {
        return "Quadro{" +
                "titolo='" + titolo + '\'' +
                ", autore='" + autore + '\'' +
                ", epoca='" + epoca + '\'' +
                '}';
    }

}
