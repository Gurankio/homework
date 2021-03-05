package gurankio;

import java.io.File;

public class AereoMotore extends Aeromobile {

    private double cavalli;

    public AereoMotore(File file) {
        super(file);
    }

    public AereoMotore(String sigla, double cavalli) {
        super(sigla);
        this.cavalli = cavalli;
    }

    public double getCavalli() {
        return cavalli;
    }

    public void setCavalli(double cavalli) {
        this.cavalli = cavalli;
    }

    @Override
    public int compareTo(Aeromobile o) {
        if (o == null) throw new NullPointerException();
        return (int) (this.getCavalli() - ((AereoMotore) o).getCavalli());
    }

    @Override
    public String toString() {
        return String.format("Aereo a Motore \"%s\" con %.2f cavalli", getSigla(), getCavalli());
    }
}
