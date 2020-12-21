package gurankio;

public abstract class FiguraSolida extends FiguraGeometrica {

    protected int nFacce;

    public int getNFacce() {
        return nFacce;
    }

    public abstract double volume();

    @Override
    public String toString() {
        return "FiguraSolida{" +
                "nVertici=" + nVertici +
                ", nFacce=" + nFacce +
                '}';
    }
}
