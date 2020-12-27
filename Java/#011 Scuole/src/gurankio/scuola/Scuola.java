package gurankio.scuola;

public class Scuola {

    private String codice;
    private String denominazione;
    private String indirizzo;
    private String citta;
    private NumeroConContributi studenti;
    private NumeroConContributi classi;
    private NumeroConContributi sediAggiuntive;
    private NumeroConContributi laboratori;

    public Scuola(String codice, String denominazione, String indirizzo, String citta, NumeroConContributi studenti, NumeroConContributi classi, NumeroConContributi sediAggiuntive, NumeroConContributi laboratori) {
        this.codice = codice;
        this.denominazione = denominazione;
        this.indirizzo = indirizzo;
        this.citta = citta;
        this.studenti = studenti;
        this.classi = classi;
        this.sediAggiuntive = sediAggiuntive;
        this.laboratori = laboratori;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public Integer getStudenti() {
        return studenti.getDato();
    }

    public void setStudenti(Integer studenti) {
        this.studenti.setDato(studenti);
    }

    public Integer getClassi() {
        return classi.getDato();
    }

    public void setClassi(Integer classi) {
        this.classi.setDato(classi);
    }

    public Integer getSediAggiuntive() {
        return sediAggiuntive.getDato();
    }

    public void setSediAggiuntive(Integer sediAggiuntive) {
        this.sediAggiuntive.setDato(sediAggiuntive);
    }

    public Integer getLaboratori() {
        return laboratori.getDato();
    }

    public void setLaboratori(Integer laboratori) {
        this.laboratori.setDato(laboratori);
    }

    public int calcolaContributi() {
        return studenti.getContributi() + classi.getContributi() + sediAggiuntive.getContributi() + laboratori.getContributi();
    }

    @Override
    public String toString() {
        return "Scuola{" +
                "codice='" + codice + '\'' +
                ", denominazione='" + denominazione + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                ", citta='" + citta + '\'' +
                ", studenti=" + studenti +
                ", classi=" + classi +
                ", sediAggiuntive=" + sediAggiuntive +
                ", laboratori=" + laboratori +
                '}';
    }
}


