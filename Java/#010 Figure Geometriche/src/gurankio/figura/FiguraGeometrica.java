package gurankio.figura;

public abstract class FiguraGeometrica {

    protected int nVertici;

    public int getNVertici() {
        return nVertici;
    }

    public abstract double area();

    @Override
    public String toString() {
        return "FiguraGeometrica{" +
                "nVertici=" + nVertici +
                '}';
    }
}
