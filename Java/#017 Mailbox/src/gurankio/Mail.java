package gurankio;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mail extends Persistent {

    private String mittente;
    private String oggetto;
    private LocalDateTime dateTime;
    private String testo;

    public Mail(String mittente, String oggetto, LocalDateTime dateTime, String testo) {
        this.mittente = mittente;
        this.oggetto = oggetto;
        this.dateTime = dateTime;
        this.testo = testo;
        save(); // auto save
    }

    @Main.Menu(prompts = {"Inserire l'oggetto: ", "Inserire il mittente: ", "Inserire il testo della mail: "})
    public Mail(String mittente, String oggetto, String testo) {
        this(mittente, oggetto, LocalDateTime.now(), testo);
    }

    public Mail(File file) {
        super(file);
    }

    public String getMittente() {
        return mittente;
    }

    public void setMittente(String mittente) {
        this.mittente = mittente;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String toString(boolean extended) {
        if (!extended) return toString();
        else return String.format("Mittente: %s\nOggetto: %s\nData: %s\nTesto:\n%s", mittente, oggetto, dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")), testo);
    }

    @Override
    public String toString() {
        return String.format("'%s' da '%s' del %s alle %s", oggetto, mittente, dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }
}
