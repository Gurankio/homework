package gurankio.carrello;

import gurankio.menu.io.util.StringPrettify;
import gurankio.menu.io.util.TreeBuilder;

public class Abbigliamento extends Merce {

    private double scontoUomo;
    private double scontoDonna;
    private char sesso;
    private String taglia;
    private String tipologia;

    public Abbigliamento(String codice, double prezzoUnitario, double scontoUomo, double scontoDonna, char sesso, String taglia, String tipologia) {
        super(codice, prezzoUnitario);
        this.scontoUomo = scontoUomo;
        this.scontoDonna = scontoDonna;
        this.sesso = sesso;
        this.taglia = taglia;
        this.tipologia = tipologia;
    }

    public double getScontoUomo() {
        return scontoUomo;
    }

    public void setScontoUomo(double scontoUomo) {
        this.scontoUomo = scontoUomo;
    }

    public double getScontoDonna() {
        return scontoDonna;
    }

    public void setScontoDonna(double scontoDonna) {
        this.scontoDonna = scontoDonna;
    }

    public char getSesso() {
        return sesso;
    }

    public void setSesso(char sesso) {
        this.sesso = sesso;
    }

    public String getTaglia() {
        return taglia;
    }

    public void setTaglia(String taglia) {
        this.taglia = taglia;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    @Override
    public double calcolaImporto() {
        return getPrezzoUnitario() - (getSesso() == 'M' ? getScontoUomo() : getScontoDonna());
    }

    @Override
    public String toString() {
        return new TreeBuilder("Abbigliamento")
                .arrow("scontoUomo = " + StringPrettify.toPrettyString(scontoUomo))
                .arrow("scontoDonna = " + StringPrettify.toPrettyString(scontoDonna))
                .arrow("sesso = " + StringPrettify.toPrettyString(sesso))
                .arrow("taglia = " + StringPrettify.toPrettyString(taglia))
                .arrow("tipologia = " + StringPrettify.toPrettyString(tipologia))
                .arrowReverse(super.toString())
                .endAll()
                .toString();
    }
}
