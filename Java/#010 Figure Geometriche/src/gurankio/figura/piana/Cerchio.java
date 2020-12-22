package gurankio.figura.piana;

public class Cerchio extends FiguraPiana {

    private double raggio;

    public Cerchio(double raggio) {
        this.raggio = raggio;
        this.nLati = 0;
        this.nVertici = 0;
    }

    public double getRaggio() {
        return raggio;
    }

    public void setRaggio(double raggio) {
        this.raggio = raggio;
    }

    @Override
    public double area() {
        return Math.PI * Math.pow(raggio, 2);
    }

    @Override
    public double perimetro() {
        return Math.PI * raggio + 2;
    }

    @Override
    public String toString() {
        return "Cerchio{" +
                "nVertici=" + nVertici +
                ", raggio=" + raggio +
                ", nLati=" + nLati +
                '}';
    }
}
