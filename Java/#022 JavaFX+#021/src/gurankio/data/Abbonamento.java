package gurankio.data;

import gurankio.Main;
import gurankio.util.Logger;
import gurankio.util.PersistentCSV;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Stack;

public class Abbonamento implements PersistentCSV {

    private final IntegerProperty numeroTessera;
    private final ObjectProperty<LocalDate> dataCreazione; // posso calcolare la data di scadenza.
    private final ObjectProperty<Tipologia> tipologia;
    private final ObjectProperty<Cliente> cliente;
    private final DoubleProperty costo;

    public Abbonamento() {
        this.numeroTessera = new SimpleIntegerProperty();
        this.dataCreazione = new SimpleObjectProperty<>();
        this.tipologia = new SimpleObjectProperty<>();
        this.cliente = new SimpleObjectProperty<>();

        this.costo = new SimpleDoubleProperty();
        this.tipologia.addListener((observableValue, a, b) -> costo.set(getTipologia().getCosto() - (getTipologia().getCosto() * getCliente().getSconto())));
        this.cliente.addListener((observableValue, a, b) -> costo.set(getTipologia().getCosto() - (getTipologia().getCosto() * getCliente().getSconto())));
    }

    public Abbonamento(int numeroTessera, LocalDate dataCreazione, Tipologia tipologia, Cliente cliente) {
        this.numeroTessera = new SimpleIntegerProperty(numeroTessera);
        this.dataCreazione = new SimpleObjectProperty<>(dataCreazione);
        this.tipologia = new SimpleObjectProperty<>(tipologia);
        this.cliente = new SimpleObjectProperty<>(cliente);

        this.costo = new SimpleDoubleProperty();
        this.tipologia.addListener((observableValue, a, b) -> costo.set(getTipologia().getCosto() - (getTipologia().getCosto() * getCliente().getSconto())));
        this.cliente.addListener((observableValue, a, b) -> costo.set(getTipologia().getCosto() - (getTipologia().getCosto() * getCliente().getSconto())));
    }

    public int getNumeroTessera() {
        return numeroTessera.get();
    }

    public IntegerProperty numeroTesseraProperty() {
        return numeroTessera;
    }

    public void setNumeroTessera(int numeroTessera) {
        this.numeroTessera.set(numeroTessera);
    }

    public LocalDate getDataCreazione() {
        return dataCreazione.get();
    }

    public ObjectProperty<LocalDate> dataCreazioneProperty() {
        return dataCreazione;
    }

    public void setDataCreazione(LocalDate dataCreazione) {
        this.dataCreazione.set(dataCreazione);
    }

    public Tipologia getTipologia() {
        return tipologia.get();
    }

    public ObjectProperty<Tipologia> tipologiaProperty() {
        return tipologia;
    }

    public void setTipologia(Tipologia tipologia) {
        this.tipologia.set(tipologia);
    }

    public Cliente getCliente() {
        return cliente.get();
    }

    public ObjectProperty<Cliente> clienteProperty() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente.set(cliente);
    }

    public double getCosto() {
        return costo.get();
    }

    public DoubleProperty costoProperty() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo.set(costo);
    }

    // Controlla se l'abbonamento è scaduto.
    public boolean isScaduto() {
        return getDataCreazione().plusMonths(getTipologia().getDurata()).isBefore(LocalDate.now());
    }

    // Funzioni per IO su file.

    @Override
    public void serialize(Stack<String> tokens) {
        tokens.push(String.valueOf(numeroTessera));
        tokens.push(getDataCreazione().format(Main.DATE_TIME_FORMATTER));
        tokens.push(getTipologia().toString());
        tokens.push(getCliente().getClass().getName());
        getCliente().serialize(tokens);
    }

    @Override
    public boolean parse(Stack<String> tokens) {
        try {
            setNumeroTessera(Integer.parseInt(tokens.pop()));
            setDataCreazione(LocalDate.parse(tokens.pop(), Main.DATE_TIME_FORMATTER));
            setTipologia(Tipologia.valueOf(tokens.pop()));
            setCliente((Cliente) Class.forName(tokens.pop()).getConstructor().newInstance());
            getCliente().parse(tokens);
            return true;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            Logger.DEBUG.exception(e);
            return false;
        }
    }

    @Override
    public String toString() {
        return "Abbonamento{" +
                "numeroTessera=" + numeroTessera +
                ", dataCreazione=" + dataCreazione +
                ", tipologia=" + tipologia +
                ", cliente=" + cliente +
                '}';
    }

    // Struttura Dati

    public enum Tipologia {
        MENSILE(1, 20.0),
        TRIMESTRALE(3, 50.0),
        ANNUALE(12, 300.0);

        private final IntegerProperty durata; // in mesi.
        private final DoubleProperty costo;

        Tipologia(int durata, double costo) {
            this.durata = new SimpleIntegerProperty(durata);
            this.costo = new SimpleDoubleProperty(costo);
        }

        public int getDurata() {
            return durata.get();
        }

        public IntegerProperty durataProperty() {
            return durata;
        }

        public void setDurata(int durata) {
            this.durata.set(durata);
        }

        public double getCosto() {
            return costo.get();
        }

        public DoubleProperty costoProperty() {
            return costo;
        }

        public void setCosto(double costo) {
            this.costo.set(costo);
        }
    }
}
