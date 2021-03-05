package gurankio;

import java.io.File;

public abstract class Aeromobile extends Persistent implements Comparable<Aeromobile> {

    private String sigla;
    public Aeromobile(File file) {
        super(file);
    }

    public Aeromobile(String sigla) {
        this.sigla = sigla;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

}
