package gurankio;

public class Angolo {

    private int secondi;

    public Angolo(int gradi, int primi, int secondi) {
        this.secondi = (gradi * 60 + primi) * 60 + secondi;
    }

    public Angolo(Angolo a) {
        this.secondi = a.getSecondi();
    }

    // angoloSecondi()
    public int getSecondi() {
        return secondi;
    }

    // secondiAngolo()
    public void setSecondi(int secondi) {
        this.secondi = secondi;
    }

    public void aggiungiGradi(int gradi) {
        this.secondi += gradi * 3600;
    }

    public void aggiungiPrimi(int primi) {
        this.secondi += primi * 60;
    }

    public void aggiungiSecondi(int secondi) {
        this.secondi += secondi;
    }

    public int differenzaSecondi(Angolo a) {
        return secondi - a.getSecondi();
    }

    public void sommaAngolo(Angolo a) {
        secondi += a.getSecondi();
    }

    // visualizzaAngolo()
    @Override
    public String toString() {
        return "%dª%d'%d\"".formatted(
                (secondi / 3600) % 360,
                (secondi / 60) % 60,
                secondi % 60
        );
    }

}
