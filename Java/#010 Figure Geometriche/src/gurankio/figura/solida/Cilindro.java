package gurankio.figura.solida;

public class Cilindro extends FiguraSolida {

    private double raggio;
    private double altezza;

    public Cilindro(double raggio, double altezza) {
        this.raggio = raggio;
        this.altezza = altezza;
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
        return 2 * Math.PI * Math.pow(raggio, 2) + 2 * Math.PI * altezza;
    }

    @Override
    public double volume() {
        return Math.PI * Math.pow(raggio, 2) * altezza;
    }

    @Override
    public String toString() {
        return "Cilindro{" +
                "nVertici=" + nVertici +
                ", raggio=" + raggio +
                ", altezza=" + altezza +
                ", nFacce=" + nFacce +
                '}';
    }
}
