package gurankio.figura.solida;

public class Parallelepipedo extends FiguraSolida {

    private double larghezza;
    private double altezza;
    private double profondita;

    public Parallelepipedo(double larghezza, double altezza, double profondita) {
        this.larghezza = larghezza;
        this.altezza = altezza;
        this.profondita = profondita;
        this.nVertici = 8;
        this.nFacce = 6;
    }

    public double getLarghezza() {
        return larghezza;
    }

    public void setLarghezza(double larghezza) {
        this.larghezza = larghezza;
    }

    public double getAltezza() {
        return altezza;
    }

    public void setAltezza(double altezza) {
        this.altezza = altezza;
    }

    public double getProfondita() {
        return profondita;
    }

    public void setProfondita(double profondita) {
        this.profondita = profondita;
    }

    @Override
    public double area() {
        return (larghezza * altezza) * 2 + (larghezza * profondita) * 2 + (profondita * altezza) * 2;
    }

    @Override
    public double volume() {
        return larghezza * altezza * profondita;
    }

    @Override
    public String toString() {
        return "Parallelepipedo{" +
                "nVertici=" + nVertici +
                ", nFacce=" + nFacce +
                ", larghezza=" + larghezza +
                ", altezza=" + altezza +
                ", profondita=" + profondita +
                '}';
    }
}
