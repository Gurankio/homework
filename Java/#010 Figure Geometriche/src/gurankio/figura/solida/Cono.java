package gurankio.figura.solida;

public class Cono extends FiguraSolida {

    private double raggio;
    private double altezza;

    public Cono(double raggio, double altezza) {
        this.raggio = raggio;
        this.altezza = altezza;
        this.nVertici = 1;
        this.nFacce = 2;
    }

    public double getRaggio() {
        return raggio;
    }

    public void setRaggio(double raggio) {
        this.raggio = raggio;
    }

    public double getAltezza() {
        return altezza;
    }

    public void setAltezza(double altezza) {
        this.altezza = altezza;
    }

    @Override
    public double area() {
        return Math.PI * Math.pow(raggio, 2) + Math.sqrt(Math.pow(altezza, 2) + Math.pow(raggio, 2)) * Math.PI * 2;
    }

    @Override
    public double volume() {
        return (Math.PI * Math.pow(raggio, 2) * altezza) / 3;
    }

    @Override
    public String toString() {
        return "Cono{" +
                "nVertici=" + nVertici +
                ", raggio=" + raggio +
                ", altezza=" + altezza +
                ", nFacce=" + nFacce +
                '}';
    }
}
