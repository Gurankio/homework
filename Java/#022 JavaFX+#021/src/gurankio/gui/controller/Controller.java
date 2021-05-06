package gurankio.gui.controller;

import gurankio.Main;
import gurankio.data.Abbonamento;
import gurankio.util.PersistentCSV;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class Controller {

    @FXML private TableView<Abbonamento> tabella;

    @FXML private TableColumn<Abbonamento, Number> colonnaID;
    @FXML private TableColumn<Abbonamento, Abbonamento.Tipologia> colonnaTipo;
    @FXML private TableColumn<Abbonamento, LocalDate> colonnaCreazione;
    @FXML private TableColumn<Abbonamento, String> colonnaNome;
    @FXML private TableColumn<Abbonamento, String> colonnaCognome;
    @FXML private TableColumn<Abbonamento, LocalDate> colonnaNascita;
    @FXML private TableColumn<Abbonamento, Number> colonnaCosto;
    @FXML private TableColumn<Abbonamento, Number> colonnaSconto;

    @FXML private TextField id;
    @FXML private TextField tipo;
    @FXML private TextField creazione;
    @FXML private TextField nome;
    @FXML private TextField cognome;
    @FXML private TextField nascita;
    @FXML private TextField costo;
    @FXML private TextField sconto;

    @FXML private Button aggiungi;

    // Add a public no-args constructor
    public Controller() {
    }

    @FXML
    private void initialize() {
        colonnaID.setCellValueFactory(cell -> cell.getValue().numeroTesseraProperty());
        colonnaTipo.setCellValueFactory(cell -> cell.getValue().tipologiaProperty());
        colonnaCreazione.setCellValueFactory(cell -> cell.getValue().dataCreazioneProperty());
        colonnaNome.setCellValueFactory(cell -> cell.getValue().getCliente().nomeProperty());
        colonnaCognome.setCellValueFactory(cell -> cell.getValue().getCliente().cognomeProperty());
        colonnaNascita.setCellValueFactory(cell -> cell.getValue().getCliente().dataNascitaProperty());
        colonnaCosto.setCellValueFactory(cell -> cell.getValue().costoProperty());
        colonnaSconto.setCellValueFactory(cell -> cell.getValue().getCliente().scontoProperty());

        tabella.setItems(FXCollections.observableList(PersistentCSV.load(Main.file)
                .filter(o -> o instanceof Abbonamento)
                .map(o -> (Abbonamento) o)
                .collect(Collectors.toList())));
    }

}
