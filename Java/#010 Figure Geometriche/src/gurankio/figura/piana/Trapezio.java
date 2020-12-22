package gurankio.figura.piana;

public class Trapezio extends FiguraPiana {

    private double baseMinore;
    private double baseMaggiore;
    private double latoObliquoDestro;
    private double latoObliquoSinistro;
    private double altezza;

    public Trapezio(double baseMinore, double baseMaggiore, double latoObliquoDestro, double latoObliquoSinistro, double altezza) {
        this.baseMinore = baseMinore;
        this.baseMaggiore = baseMaggiore;
        this.latoObliquoDestro = latoObliquoDestro;
        this.latoObliquoSinistro = latoObliquoSinistro;
        this.altezza = altezza;
    }

    public double getBaseMinore() {
        return baseMinore;
    }

    public void setBaseMinore(double baseMinore) {
        this.baseMinore = baseMinore;
    }

    public double getBaseMaggiore() {
        return baseMaggiore;
    }

    public void setBaseMaggiore(double baseMaggiore) {
        this.baseMaggiore = baseMaggiore;
    }

    public double getLatoObliquoDestro() {
        return latoObliquoDestro;
    }

    public void setLatoObliquoDestro(double latoObliquoDestro) {
        this.latoObliquoDestro = latoObliquoDestro;
    }

    public double getLatoObliquoSinistro() {
        return latoObliquoSinistro;
    }

    public void setLatoObliquoSinistro(double latoObliquoSinistro) {
        this.latoObliquoSinistro = latoObliquoSinistro;
    }

    public double getAltezza() {
        return altezza;
    }

    public void setAltezza(double altezza) {
        this.altezza = altezza;
    }

    @Override
    public double area() {
        return (baseMinore + baseMaggiore) * altezza / 2;
    }

    @Override
    public double perimetro() {
        return baseMinore + baseMaggiore + latoObliquoDestro + latoObliquoSinistro;
    }

    @Override
    public String toString() {
        return "Trapezio{" +
                "nVertici=" + nVertici +
                ", nLati=" + nLati +
                ", baseMinore=" + baseMinore +
                ", baseMaggiore=" + baseMaggiore +
                ", altezza=" + altezza +
                '}';
    }
}
