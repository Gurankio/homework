package gurankio.controller;

import gurankio.ViewManager;
import gurankio.model.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import jfxtras.styles.jmetro.JMetro;

import java.time.LocalDate;

import static gurankio.model.Abbonamento.calcolaPrezzoScontato;

public class AddController {

    private boolean done;

    @FXML
    private TextField nome;
    @FXML
    private TextField cognome;
    @FXML
    private DatePicker nascita;

    private final ObjectProperty<Abbonamento.Tipologia> tipologia = new SimpleObjectProperty<>();
    @FXML
    private RadioButton mensile;
    @FXML
    private RadioButton trimestrale;
    @FXML
    private RadioButton annuale;

    @FXML
    private Label costo;

    private ObjectProperty<Adulto.Sesso> sesso = new SimpleObjectProperty<>();
    @FXML
    private RadioButton uomo;
    @FXML
    private RadioButton donna;

    private ObjectProperty<Minore.Categoria> categoria = new SimpleObjectProperty<>();
    @FXML
    private RadioButton bambino;
    @FXML
    private RadioButton ragazzo;
    @FXML
    private RadioButton teenager;

    @FXML
    private Label scontato;

    @FXML
    private TextField id;

    @FXML
    private void initialize() {
        ToggleGroup toggle = new ToggleGroup();
        mensile.setToggleGroup(toggle);
        mensile.armedProperty().addListener((observable, oldValue, newValue) -> tipologia.set(Abbonamento.Tipologia.MENSILE));

        trimestrale.setToggleGroup(toggle);
        trimestrale.armedProperty().addListener((observable, oldValue, newValue) -> tipologia.set(Abbonamento.Tipologia.TRIMESTRALE));

        annuale.setToggleGroup(toggle);
        annuale.armedProperty().addListener((observable, oldValue, newValue) -> tipologia.set(Abbonamento.Tipologia.ANNUALE));

        costo.textProperty().bind(Bindings.createStringBinding(() -> tipologia.get() == null ? "" : "Prezzo di Base: " + tipologia.get().getCosto(), tipologia));

        ToggleGroup extra = new ToggleGroup();
        uomo.setToggleGroup(extra);
        uomo.setVisible(false);
        uomo.armedProperty().addListener((v, o, n) -> sesso.set(Adulto.Sesso.UOMO));

        donna.setToggleGroup(extra);
        donna.setVisible(false);
        donna.armedProperty().addListener((v, o, n) -> sesso.set(Adulto.Sesso.DONNA));

        bambino.setToggleGroup(extra);
        bambino.setVisible(false);
        bambino.armedProperty().addListener((v, o, n) -> categoria.set(Minore.Categoria.BAMBINO));

        ragazzo.setToggleGroup(extra);
        ragazzo.setVisible(false);
        ragazzo.armedProperty().addListener((v, o, n) -> categoria.set(Minore.Categoria.RAGAZZO));

        teenager.setToggleGroup(extra);
        teenager.setVisible(false);
        teenager.armedProperty().addListener((v, o, n) -> categoria.set(Minore.Categoria.TEENAGER));

        scontato.textProperty().bind(Bindings.createStringBinding(() -> {
            try {
                return "Prezzo Scontato: " + calcolaPrezzoScontato(tipologia.get(), getCliente());
            } catch (RuntimeException ignored) {
                return "";
            }
        }, tipologia, nascita.valueProperty(), sesso, categoria));

        nascita.valueProperty().addListener((v, o, n) -> {
            if (n == null) return;
            if (n.plusYears(18).isBefore(LocalDate.now())) {
                uomo.setVisible(true);
                donna.setVisible(true);
                bambino.setVisible(false);
                ragazzo.setVisible(false);
                teenager.setVisible(false);
            } else {
                uomo.setVisible(false);
                donna.setVisible(false);
                bambino.setVisible(true);
                ragazzo.setVisible(true);
                teenager.setVisible(true);
            }
        });
    }

    private Cliente getCliente() {
        Cliente cliente;
        if (nascita.getValue().plusYears(18).isBefore(LocalDate.now())) {
            cliente = new Adulto(nome.getText(), cognome.getText(), nascita.getValue(), sesso.get());
        } else {
            cliente = new Minore(nome.getText(), cognome.getText(), nascita.getValue(), categoria.get());
        }
        return cliente;
    }

    public void add() {
        done = false;
        Dialog<Abbonamento> dialog = new Dialog<>();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.setResultConverter(buttonType -> {
            if (buttonType.equals(ButtonType.FINISH)) {
                if (nome.getText().equals("")) {
                    // ERRORE
                    Platform.runLater(() -> error("Attenzione!", "Inserire il nome."));
                    return null;
                }

                if (cognome.getText().equals("")) {
                    // ERRORE
                    Platform.runLater(() -> error("Attenzione!","Inserire il cognome."));
                    return null;
                }

                if (nascita.getValue() == null) {
                    // ERRORE
                    Platform.runLater(() -> error("Attenzione!","Inserire la data di nascita."));
                    return null;
                }

                if (nascita.getValue().isAfter(LocalDate.now())) {
                    // ERRORE
                    nascita.setValue(null);
                    Platform.runLater(() -> error("Errore!","Data non valida."));
                    return null;
                }

                if (tipologia.get() == null) {
                    // ERRORE
                    Platform.runLater(() -> error("Attenzione!","Selezionare la tipologia."));
                    return null;
                }

                if (sesso.get() == null && categoria.get() == null) {
                    // ERRORE
                    if (nascita.getValue().plusYears(18).isBefore(LocalDate.now())) Platform.runLater(() -> error("Attenzione!","Selezionare il sesso."));
                    else Platform.runLater(() -> error("Attenzione!","Selezionare la categoria."));
                    return null;
                }

                if (id.getText().equals("")) {
                    // ERRORE
                    Platform.runLater(() -> error("Attenzione!","Inserire un ID."));
                    return null;
                }

                int numeroTessera;

                try {
                    numeroTessera = Integer.parseInt(id.getText());
                } catch (RuntimeException ignored) {
                    // ERRORE
                    id.textProperty().set("");
                    Platform.runLater(() -> error("Errore!","L'ID inserito non è un numero."));
                    return null;
                }

                if (numeroTessera < 0) {
                    // ERRORE
                    id.textProperty().set("");
                    Platform.runLater(() -> error("Errore!","ID non valido."));
                    return null;
                }

                if (DatabaseManager.getDatabase().stream().mapToInt(Abbonamento::getNumeroTessera).anyMatch(value -> value == numeroTessera)) {
                    // ERRORE
                    id.textProperty().set("");
                    Platform.runLater(() -> error("Errore!","ID gia esistente."));
                    return null;
                }

                Abbonamento abbonamento = new Abbonamento(numeroTessera, LocalDate.now(), tipologia.get(), getCliente());
                done = true;
                return abbonamento;
            } else {
                done = true;
                return null;
            }
        });
        if (done) reset();
        id.textProperty().set(String.valueOf(DatabaseManager.getDatabase().stream().mapToInt(Abbonamento::getNumeroTessera).max().orElse(0) + 1));
        DialogPane pane = new DialogPane();
        new JMetro(pane, ViewManager.style);
        pane.setContent(ViewManager.add);
        pane.getButtonTypes().addAll(ButtonType.CLOSE, ButtonType.FINISH);
        dialog.setDialogPane(pane);
        dialog.showAndWait().ifPresent(DatabaseManager::add);
    }

    private void reset() {
        nome.setText("");
        cognome.setText("");
        nascita.setValue(null);

        tipologia.set(null);
        mensile.setSelected(false);
        trimestrale.setSelected(false);
        annuale.setSelected(false);

        sesso.set(null);
        uomo.setSelected(false);
        donna.setSelected(false);

        categoria.set(null);
        bambino.setSelected(false);
        ragazzo.setSelected(false);
        teenager.setSelected(false);

        id.setText("");
    }

    private void error(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        new JMetro(alert.getDialogPane(), ViewManager.style);
        alert.setTitle(header);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
        add();
    }


}
