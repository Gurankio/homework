package gurankio.container;

import gurankio.util.GenericScanner;
import gurankio.util.Persistent;

import java.io.File;

public class Container extends Persistent {

    private int codice;
    private double stazza;
    private double carico;

    @GenericScanner.Prompts({"Inserire il codice del container.", "Inserire la stazza del container.", "Inserire il carico del container."})
    public Container(int codice, double stazza, double carico) {
        this.codice = codice;
        this.stazza = stazza;
        this.carico = carico;
    }

    public Container(File file) {
        super(file);
    }

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public double getStazza() {
        return stazza;
    }

    public void setStazza(double stazza) {
        this.stazza = stazza;
    }

    public double getCarico() {
        return carico;
    }

    public void setCarico(double carico) {
        this.carico = carico;
    }

    @Override
    public String toString() {
        return String.format("Container '%03d', di stazza %.2f con %.2f di carico.", getCodice(), getStazza(), getCarico());
    }
}
