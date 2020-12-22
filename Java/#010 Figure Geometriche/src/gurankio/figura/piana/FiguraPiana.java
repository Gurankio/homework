package gurankio.figura.piana;

import gurankio.figura.FiguraGeometrica;

public abstract class FiguraPiana extends FiguraGeometrica {

    protected int nLati;

    public int getNLati() {
        return nLati;
    }

    public abstract double perimetro();

    @Override
    public String toString() {
        return "FiguraPiana{" +
                "nVertici=" + nVertici +
                ", nLati=" + nLati +
                '}';
    }
}
