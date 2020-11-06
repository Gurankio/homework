package gurankio;

public class Triangolo {

    private Punto p1;
    private Punto p2;
    private Punto p3;

    public Triangolo(Punto p1, Punto p2, Punto p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        if (!controllaTriangolo()) throw new IllegalArgumentException("Punto non valido.");
    }

    public Punto getP1() {
        return p1;
    }

    public void setP1(Punto p1) {
        this.p1 = p1;
        if (!controllaTriangolo()) throw new IllegalArgumentException("Punto non valido.");
    }

    public Punto getP2() {
        return p2;
    }

    public void setP2(Punto p2) {
        this.p2 = p2;
        if (!controllaTriangolo()) throw new IllegalArgumentException("Punto non valido.");
    }

    public Punto getP3() {
        return p3;
    }

    public void setP3(Punto p3) {
        this.p3 = p3;
        if (!controllaTriangolo()) throw new IllegalArgumentException("Punto non valido.");
    }

    public double area() {
        double s = perimetro() / 2;
        return Math.sqrt(s * (s - p1.distanza(p2)) * (s - p2.distanza(p3)) * (s - p3.distanza(p1)));
    }

    public double perimetro() {
        return p1.distanza(p2) + p2.distanza(p3) + p3.distanza(p1);
    }

    private boolean controllaTriangolo() {
        double m1 = (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
        double m2 = (p2.getY() - p3.getY()) / (p2.getX() - p3.getX());
        return m1 != m2;
    }

    @Override
    public String toString() {
        return "Triangolo{" +
                "p1=" + p1 +
                ", p2=" + p2 +
                ", p3=" + p3 +
                ", perimetro=" + perimetro() +
                ", area=" + area() +
                '}';
    }
}
