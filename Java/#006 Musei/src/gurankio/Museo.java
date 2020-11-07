package gurankio;

import java.util.Arrays;

public class Museo {

    private String nome;
    private String citta;
    private String indirizzo;
    private Statua[] statue;
    private int numeroStatue;
    private Quadro[] quadri;
    private int numeroQuadri;

    public Museo(String nome, String citta, String indirizzo, Statua statua, Quadro quadro) {
        this.nome = nome;
        this.citta = citta;
        this.indirizzo = indirizzo;
        this.statue = new Statua[5];
        this.statue[0] = statua;
        this.numeroStatue = 1;
        this.quadri = new Quadro[2];
        this.quadri[0] = quadro;
        this.numeroQuadri = 1;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public boolean aggiungiStatua(Statua statua) {
        if (numeroStatue >= statue.length) return false;
        statue[numeroStatue] = statua;
        numeroStatue++;
        return true;
    }

    public boolean eliminaStatua(int index) {
        if (index < 0 || index >= numeroStatue) return false;
        for (int i=index; i<numeroStatue-1; i++) statue[i] = statue[i+1];
        statue[numeroStatue-1] = null;
        numeroStatue--;
        return true;
    }

    public boolean eliminaStatua(Statua statua) {
        for (int i=0; i<numeroStatue; i++) if (statue[i].uguale(statua)) return eliminaStatua(i);
        return false;
    }

    public boolean aggiungiQuadro(Quadro quadro) {
        if (numeroQuadri >= quadri.length) return false;
        quadri[numeroQuadri] = quadro;
        numeroQuadri++;
        return true;
    }

    public boolean eliminaQuadro(int index) {
        if (index < 0 || index >= numeroQuadri) return false;
        for (int i=index; i<numeroQuadri-1; i++) quadri[i] = quadri[i+1];
        quadri[numeroQuadri-1] = null;
        numeroQuadri--;
        return true;
    }

    public boolean eliminaQuadro(Quadro quadro) {
        for (int i=0; i<numeroQuadri; i++) if (quadri[i].uguale(quadro)) return eliminaQuadro(i);
        return false;
    }

    @Override
    public String toString() {
        return "Museo{" +
                "nome='" + nome + '\'' +
                ", citta='" + citta + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                ", statue=" + Arrays.toString(statue) +
                ", quadri=" + Arrays.toString(quadri) +
                '}';
    }
}
