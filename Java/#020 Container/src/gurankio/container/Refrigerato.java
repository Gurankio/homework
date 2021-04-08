package gurankio.container;

import gurankio.util.GenericScanner;

import java.io.File;

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
}
