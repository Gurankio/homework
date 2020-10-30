package gurankio;

public class Angolo {

    private int secondi;

    public Angolo(int gradi, int primi, int secondi) {
        this.secondi = (gradi * 60 + primi) * 60 + secondi;
    }

    public Angolo(Angolo a) {
        this.secondi = a.angoloSecondi();
    }

    public void visualizzaAngolo() {
        int primi = secondi / 60;
        int gradi = primi / 60;
        System.out.printf("%dª%d'%d\"%n", gradi % 360, primi % 60, secondi % 60);
    }

    public void aggiungiGradi(int n) {
        secondi += n * 60 * 60;
    }

    public void aggiungiPrimi(int n) {
        secondi += n * 60;
    }

    public void aggiungiSecondi(int n) {
        secondi += n;
    }

    public int angoloSecondi() {
        return secondi;
    }

    public void secondiAngolo(int n) {
        secondi = n;
    }

    public int differenzaSecondi(Angolo a) {
        return secondi - a.angoloSecondi();
    }

    public void sommaAngolo(Angolo a) {
        secondi += a.angoloSecondi();
    }




}
