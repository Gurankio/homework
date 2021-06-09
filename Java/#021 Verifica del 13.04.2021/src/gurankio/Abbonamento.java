package gurankio;

import gurankio.util.AutoPersistentCSV;
import gurankio.util.Logger;
import gurankio.util.PersistentCSV;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Queue;
import java.util.Stack;

public class Abbonamento implements AutoPersistentCSV {

    private int numeroTessera;
    private LocalDate dataCreazione; // posso calcolare la data di scadenza.
    private Tipologia tipologia;
    private Cliente cliente;

    public Abbonamento(int numeroTessera, LocalDate dataCreazione, Tipologia tipologia, Cliente cliente) {
        this.numeroTessera = numeroTessera;
        this.dataCreazione = dataCreazione;
        this.tipologia = tipologia;
        this.cliente = cliente;
    }

    public Abbonamento() {
    }

    public int getNumeroTessera() {
        return numeroTessera;
    }

    public void setNumeroTessera(int numeroTessera) {
        this.numeroTessera = numeroTessera;
    }

    public LocalDate getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(LocalDate dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public Tipologia getTipologia() {
        return tipologia;
    }

    public void setTipologia(Tipologia tipologia) {
        this.tipologia = tipologia;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // Controlla se l'abbonamento è scaduto.
    public boolean isScaduto() {
        return dataCreazione.plusMonths(tipologia.durata).isBefore(LocalDate.now());
    }

    // Funzioni per IO su file.

    /*
    @Override
    public void serialize(Queue<String> tokens) {
        tokens.add(String.valueOf(numeroTessera));
        tokens.add(dataCreazione.format(Main.DATE_TIME_FORMATTER));
        tokens.add(tipologia.toString());
        tokens.add(cliente.getClass().getName());
        cliente.serialize(tokens);
    }

    @Override
    public boolean parse(Queue<String> tokens) {
        try {
            numeroTessera = Integer.parseInt(tokens.remove());
            dataCreazione = LocalDate.parse(tokens.remove(), Main.DATE_TIME_FORMATTER);
            tipologia = Tipologia.valueOf(tokens.remove());
            cliente = (Cliente) Class.forName(tokens.remove()).getConstructor().newInstance();
            cliente.parse(tokens);
            return true;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            Logger.DEBUG.exception(e);
            return false;
        }
    }
*/

    @Override
    public String toString() {
        return "Abbonamento{" +
                "numeroTessera=" + numeroTessera +
                ", dataCreazione=" + dataCreazione +
                ", tipologia=" + tipologia +
                ", cliente=" + cliente +
                '}';
    }

    // Utilità per calcolare la tassa di un abbonamento,
    // sono statiche perchè mi serviranno nel menu prima della creazione di un oggetto Abbonamento.
    public static double calcolaTassa(Abbonamento abbonamento) {
        return abbonamento.getTipologia().costo - (abbonamento.getTipologia().costo * abbonamento.getCliente().getSconto());
    }

    public static double calcolaTassa(Tipologia tipologia, Cliente cliente) {
        return tipologia.costo - (tipologia.costo * cliente.getSconto());
    }

    // Struttura Dati

    public enum Tipologia {
        MENSILE(1, 20.0),
        TRIMESTRALE(3, 50.0),
        ANNUALE(12, 300.0);

        private final int durata; // in mesi.
        private final double costo;

        Tipologia(int durata, double costo) {
            this.durata = durata;
            this.costo = costo;
        }
    }
}
