package gurankio;

public class Angolo {

    private int secondi;

    public Angolo(int gradi, int primi, int secondi) {
        setSecondi(((gradi * 60 + primi) * 60 + secondi));
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
        this.secondi = secondi % (360 * 60 * 60);
        if (this.secondi < 0) this.secondi += (360 * 60 * 60);
    }

    public void aggiungiGradi(int gradi) {
        setSecondi(getSecondi() + gradi * 60 * 60);
    }

    public void aggiungiPrimi(int primi) {
        setSecondi(getSecondi() + primi * 60);
    }

    public void aggiungiSecondi(int secondi) {
        setSecondi(getSecondi() + secondi);
    }

    public int differenzaSecondi(Angolo a) {
        int result = secondi - a.getSecondi();
        return result + result < 0 ? (360 * 60 * 60) : 0;
    }

    public void sommaAngolo(Angolo a) {
        setSecondi(getSecondi() + a.getSecondi());
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
