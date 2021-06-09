package gurankio.container;

import gurankio.util.GenericScanner;

import java.io.File;
import java.util.Objects;

public class Refrigerato extends Container {

    private double temperatura;

    @GenericScanner.Prompts({"Inserire il codice del container.", "Inserire la stazza del container.", "Inserire il carico del container.", "Inserire la temperatura di mantenimento del container."})
    public Refrigerato(int codice, double stazza, double carico, double temperatura) {
        super(codice, stazza, carico);
        this.temperatura = temperatura;
    }

    public Refrigerato(File file) {
        super(file);
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    @Override
    public String toString() {
        return String.format("Container refrigerato '%03d', di stazza %.2f con %.2f di carico a temperatura %.2f.", getCodice(), getStazza(), getCarico(), getTemperatura());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Refrigerato)) return false;
        Refrigerato that = (Refrigerato) o;
        return Double.compare(that.temperatura, temperatura) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(temperatura);
    }
}
