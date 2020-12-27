package gurankio.scuola;

public enum TipiDiScuola {

    ELEMENTARE(125,0, 9000, 0),
    MEDIA(150,0, 9000, 1100),
    LICEO(150,0, 0, 1100),
    TECNICO(0,3500, 0, 6000),
    PROFESSIONALE(0,2400, 0, 3000);

    private int contributiStudenti;
    private int contributiClassi;
    private int contributiSediAggiuntive;
    private int contributiLaboratori;

    TipiDiScuola(int contributiStudenti, int contributiClassi, int contributiSediAggiuntive, int contributiLaboratori) {
        this.contributiStudenti = contributiStudenti;
        this.contributiClassi = contributiClassi;
        this.contributiSediAggiuntive = contributiSediAggiuntive;
        this.contributiLaboratori = contributiLaboratori;
    }

    public int getContributiStudenti() {
        return contributiStudenti;
    }

    public void setContributiStudenti(int contributiStudenti) {
        this.contributiStudenti = contributiStudenti;
    }

    public int getContributiClassi() {
        return contributiClassi;
    }

    public void setContributiClassi(int contributiClassi) {
        this.contributiClassi = contributiClassi;
    }

    public int getContributiSediAggiuntive() {
        return contributiSediAggiuntive;
    }

    public void setContributiSediAggiuntive(int contributiSediAggiuntive) {
        this.contributiSediAggiuntive = contributiSediAggiuntive;
    }

    public int getContributiLaboratori() {
        return contributiLaboratori;
    }

    public void setContributiLaboratori(int contributiLaboratori) {
        this.contributiLaboratori = contributiLaboratori;
    }

    public Scuola creaScuola(String codice, String denominazione, String indirizzo, String citta, int studenti, int classi, int sediAggiuntive, int laboratori) {
        return new Scuola(codice, denominazione, indirizzo, citta,
                new NumeroConContributi(studenti, this::getContributiStudenti),
                new NumeroConContributi(classi, this::getContributiClassi),
                new NumeroConContributi(sediAggiuntive, this::getContributiSediAggiuntive),
                new NumeroConContributi(laboratori, this::getContributiLaboratori)
        );
    }

}
