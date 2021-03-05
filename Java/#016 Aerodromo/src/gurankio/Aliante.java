package gurankio;

import java.io.File;

public class Aliante extends Aeromobile {

    private double efficienza;

    public Aliante(File file) {
        super(file);
    }

    public Aliante(String sigla, double efficienza) {
        super(sigla);
        this.efficienza = efficienza;
    }

    public double getEfficienza() {
        return efficienza;
    }

    public void setEfficienza(double efficienza) {
        this.efficienza = efficienza;
    }

    @Override
    public int compareTo(Aeromobile o) {
        if (o == null) throw new NullPointerException();
        return (int) (this.getEfficienza() - ((Aliante) o).getEfficienza());
    }

    @Override
    public String toString() {
        return String.format("Aliante \"%s\" con efficienza del %.2f%%", getSigla(), getEfficienza() * 100);
    }
}
