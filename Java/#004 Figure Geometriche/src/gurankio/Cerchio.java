package gurankio;

public class Cerchio {

    private Punto centro;
    private double raggio;

    public Cerchio(Punto centro, double raggio) {
        this.centro = centro;
        this.raggio = raggio;
        if (!controllaCerchio()) throw new IllegalArgumentException("Cerchio non valido.");
    }

    public Punto getCentro() {
        return centro;
    }

    public void setCentro(Punto centro) {
        this.centro = centro;
    }

    public double getRaggio() {
        return raggio;
    }

    public void setRaggio(double raggio) {
        this.raggio = raggio;
        if (!controllaCerchio()) throw new IllegalArgumentException("Cerchio non valido.");
    }

    public double area() {
        return Math.PI * Math.pow(raggio, 2);
    }

    public double perimetro() {
        return Math.PI * raggio * 2;
    }

    private boolean controllaCerchio() {
        return raggio > 0;
    }

    @Override
    public String toString() {
        return "Cerchio{" +
                "centro=" + centro +
                ", raggio=" + raggio +
                ", perimetro=" + perimetro() +
                ", area=" + area() +
                '}';
    }
}
