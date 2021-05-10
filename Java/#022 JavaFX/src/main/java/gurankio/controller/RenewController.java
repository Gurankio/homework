package gurankio.controller;


import gurankio.ViewManager;
import gurankio.model.Abbonamento;
import gurankio.model.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.time.LocalDate;

import static gurankio.model.Abbonamento.calcolaPrezzoScontato;

public class RenewController {

    @FXML
    private Label warning;
    @FXML
    private RadioButton mensile;
    @FXML
    private RadioButton trimestrale;
    @FXML
    private RadioButton annuale;
    @FXML
    private Label costo;
    @FXML
    private Label scontato;

    private Abbonamento abbonamento;
    private Abbonamento.Tipologia tipologia;

    @FXML
    private void initialize() {
        ToggleGroup toggle = new ToggleGroup();
        mensile.setToggleGroup(toggle);
        mensile.armedProperty().addListener((observable, oldValue, newValue) -> {
            tipologia = Abbonamento.Tipologia.MENSILE;
            costo.textProperty().set("Prezzo di Base: " + tipologia.getCosto());
            scontato.textProperty().set("Prezzo Scontato: " + calcolaPrezzoScontato(tipologia, abbonamento.getCliente()));
        });
        trimestrale.setToggleGroup(toggle);
        trimestrale.armedProperty().addListener((observable, oldValue, newValue) -> {
            tipologia = Abbonamento.Tipologia.TRIMESTRALE;
            costo.textProperty().set("Prezzo di Base: " + tipologia.getCosto());
            scontato.textProperty().set("Prezzo Scontato: " + calcolaPrezzoScontato(tipologia, abbonamento.getCliente()));
        });
        annuale.setToggleGroup(toggle);
        annuale.armedProperty().addListener((observable, oldValue, newValue) -> {
            tipologia = Abbonamento.Tipologia.ANNUALE;
            costo.textProperty().set("Prezzo di Base: " + tipologia.getCosto());
            scontato.textProperty().set("Prezzo Scontato: " + calcolaPrezzoScontato(tipologia, abbonamento.getCliente()));
        });
    }

    public void renew(Abbonamento abbonamento) {
        if (abbonamento == null) return;
        this.abbonamento = abbonamento;
        warning.setVisible(!abbonamento.isScaduto());

        Dialog<Abbonamento.Tipologia> dialog = new Dialog<>();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.setResultConverter(buttonType -> buttonType.equals(ButtonType.FINISH) ? tipologia : null);
        DialogPane pane = new DialogPane();
        new JMetro(pane, ViewManager.style);
        pane.setContent(ViewManager.renew);
        pane.getButtonTypes().addAll(ButtonType.CLOSE, ButtonType.FINISH);
        dialog.setDialogPane(pane);
        dialog.showAndWait().ifPresent(tipologia -> {
            Abbonamento t = new Abbonamento();
            t.setNumeroTessera(abbonamento.getNumeroTessera());
            t.setTipologia(tipologia);
            t.setDataCreazione(LocalDate.now());
            DatabaseManager.update(t);
        });
    }
}
