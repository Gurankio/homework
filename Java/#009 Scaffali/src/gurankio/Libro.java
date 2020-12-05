package gurankio;

public class Libro {

    private String titolo;
    private String autore;
    private int numeroPagine;
    private static double costoPagine = 0.05;
    private static final double COSTO_FISSO = 5.5;

    public Libro(String titolo, String autore, int numeroPagine) {
        this.titolo = titolo;
        this.autore = autore;
        this.numeroPagine = numeroPagine;
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

    public int getNumeroPagine() {
        return numeroPagine;
    }

    public void setNumeroPagine(int numeroPagine) {
        this.numeroPagine = numeroPagine;
    }

    public static void setCostoPagine(double costoPagine) {
        Libro.costoPagine = costoPagine;
    }

    public double prezzo() {
        return COSTO_FISSO + numeroPagine * costoPagine;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "titolo='" + titolo + '\'' +
                ", autore='" + autore + '\'' +
                ", numeroPagine=" + numeroPagine +
                '}';
    }
}
