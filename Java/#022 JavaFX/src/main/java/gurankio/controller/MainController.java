package gurankio.controller;

import gurankio.Main;
import gurankio.ViewManager;
import gurankio.model.*;
import gurankio.util.MapBindings;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import jfxtras.styles.jmetro.JMetroStyleClass;

import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainController {

    @FXML
    private MenuItem nuovo;
    @FXML
    private MenuItem apri;
    @FXML
    private Menu recenti;
    @FXML
    private MenuItem aggiungi;
    @FXML
    private MenuItem cerca;
    @FXML
    private MenuItem rinnova;
    @FXML
    private MenuItem elimina;
    @FXML
    private MenuItem undo;
    @FXML
    private MenuItem redo;

    @FXML
    private Label current;

    @FXML
    private TableView<Abbonamento> tabella;

    @FXML
    private TableColumn<Abbonamento, Number> colonnaID;
    @FXML
    private TableColumn<Abbonamento, Abbonamento.Tipologia> colonnaTipo;
    @FXML
    private TableColumn<Abbonamento, LocalDate> colonnaCreazione;
    @FXML
    private TableColumn<Abbonamento, Number> colonnaCosto;
    @FXML
    private TableColumn<Abbonamento, String> colonnaSconto;
    @FXML
    private TableColumn<Abbonamento, String> colonnaNome;
    @FXML
    private TableColumn<Abbonamento, String> colonnaCognome;
    @FXML
    private TableColumn<Abbonamento, LocalDate> colonnaNascita;
    @FXML
    private TableColumn<Abbonamento, String> colonnaExtra;

    @FXML
    private Button aggiungiFloating;

    @FXML
    private void initialize() {
        nuovo.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Nuovo");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text File", "*.txt"),
                    new FileChooser.ExtensionFilter("CSV File", "*.csv"));
            File file = fileChooser.showSaveDialog(Main.stage);
            if (file != null) PersistenceManager.open(file);
        });
        nuovo.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));

        apri.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Apri");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                    new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));
            File file = fileChooser.showOpenDialog(Main.stage);
            if (file != null) PersistenceManager.open(file);
        });
        apri.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));

        ObservableList<PersistenceManager.FileWrapper> filtered = FXCollections.observableArrayList(new LinkedList<>());
        filtered.addAll(PersistenceManager.files);
        filtered.removeIf(fileWrapper -> fileWrapper.getFile().equals(PersistenceManager.open.get()));
        filtered.removeIf(fileWrapper -> filtered.indexOf(fileWrapper) > 5);
        PersistenceManager.open.addListener((v, o, n) -> {
            filtered.clear();
            filtered.addAll(PersistenceManager.files);
            filtered.removeIf(fileWrapper -> fileWrapper.getFile().equals(n));
            filtered.removeIf(fileWrapper -> filtered.indexOf(fileWrapper) > 5);
        });
        MapBindings.mapContent(recenti.getItems(), filtered, (fileWrapper) -> {
            File file = fileWrapper.getFile();
            MenuItem item = new MenuItem(file.getName());
            item.setOnAction(event -> PersistenceManager.open(file));
            return item;
        });

        aggiungi.setOnAction(actionEvent -> {
            ViewManager.addController.add();
        });
        aggiungi.setAccelerator(KeyCombination.keyCombination("Ctrl+Alt+A"));

        cerca.setOnAction(actionEvent -> {
            // cerca();
        });
        cerca.setAccelerator(KeyCombination.keyCombination("Ctrl+Alt+F"));

        rinnova.setOnAction(actionEvent -> {
            Abbonamento abbonamento = tabella.getSelectionModel().getSelectedItem();
            if (abbonamento == null) {
                // cerca();
            }
            ViewManager.renewController.renew(abbonamento);
        });
        rinnova.setAccelerator(KeyCombination.keyCombination("Ctrl+Alt+R"));
        rinnova.disableProperty().bind(tabella.getSelectionModel().selectedItemProperty().isNull());

        elimina.setOnAction(actionEvent -> {
            Abbonamento abbonamento = tabella.getSelectionModel().getSelectedItem();
            if (abbonamento == null) {
                // cerca();
            }
            DatabaseManager.delete(abbonamento);
        });
        elimina.setAccelerator(KeyCombination.keyCombination("Ctrl+Alt+D"));
        elimina.disableProperty().bind(tabella.getSelectionModel().selectedItemProperty().isNull());

        undo.setOnAction(actionEvent -> {
            DatabaseManager.undo();
        });
        undo.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
        undo.disableProperty().bind(Bindings.createBooleanBinding(DatabaseManager::canUndo, DatabaseManager.indexProperty()).not());

        redo.setOnAction(actionEvent -> {
            DatabaseManager.redo();
        });
        redo.setAccelerator(KeyCombination.keyCombination("Ctrl+Y"));
        redo.disableProperty().bind(Bindings.createBooleanBinding(DatabaseManager::canRedo, DatabaseManager.indexProperty()).not());

        MapBindings.map(current.textProperty(), PersistenceManager.open, file -> {
            if (file != null) {
                return file.getName();
            } else {
                return "Nessuno.";
            }
        });
        current.setOnMouseClicked(event -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(PersistenceManager.open.get().getParentFile());
                } catch (Exception ignored) {
                }
            }
        });

        current.setOnMouseEntered(event -> current.setTextFill(Color.rgb(0x00, 0x78, 0xd7)));
        current.setOnMouseExited(event -> current.setTextFill(Color.BLACK));

        colonnaID.setCellValueFactory(cell -> cell.getValue().numeroTesseraProperty());
        colonnaTipo.setCellValueFactory(cell -> cell.getValue().tipologiaProperty());
        colonnaCreazione.setCellValueFactory(cell -> cell.getValue().dataCreazioneProperty());
        colonnaCosto.setCellValueFactory(cell -> Bindings.createDoubleBinding(() -> Abbonamento.calcolaPrezzoScontato(cell.getValue().getTipologia(), cell.getValue().getCliente()), cell.getValue().tipologiaProperty(), cell.getValue().clienteProperty()));
        colonnaSconto.setCellValueFactory(cell -> cell.getValue().getCliente().scontoProperty().multiply(100).asString("%.0f%%"));

        colonnaNome.setCellValueFactory(cell -> cell.getValue().getCliente().nomeProperty());
        colonnaCognome.setCellValueFactory(cell -> cell.getValue().getCliente().cognomeProperty());
        colonnaNascita.setCellValueFactory(cell -> cell.getValue().getCliente().dataNascitaProperty());
        colonnaExtra.setCellValueFactory(cell -> cell.getValue().getCliente() instanceof Adulto ? ((Adulto) cell.getValue().getCliente()).sessoProperty().asString() : ((Minore) cell.getValue().getCliente()).categoriaProperty().asString());

        final List<TableColumn<Abbonamento, ?>> colonne = tabella.getColumns().stream().map(TableColumn::getColumns).flatMap(Collection::stream).collect(Collectors.toUnmodifiableList());
        final int[] proporzioni = {5, 6, 5, 4, 4, 6, 6, 5, 6};
        final int totale = Arrays.stream(proporzioni).sum();
        final double[] proporzioniPercentuale = Arrays.stream(proporzioni).mapToDouble(n -> (double) n / totale).toArray();
        assert colonne.size() == proporzioniPercentuale.length;
        tabella.widthProperty().addListener((v, o, n) -> IntStream.range(0, proporzioni.length).forEachOrdered(i -> colonne.get(i).setPrefWidth((n.doubleValue() + 1) * proporzioniPercentuale[i])));

        tabella.setRowFactory(
                tabella -> {
                    final AbbonamentoTableRow row = new AbbonamentoTableRow();

                    MenuItem rinnova = new MenuItem("Rinnova");
                    rinnova.setOnAction(event -> {
                        ViewManager.renewController.renew(row.getItem());
                    });

                    MenuItem elimina = new MenuItem("Elimina");
                    elimina.setOnAction(event -> {
                        DatabaseManager.delete(row.getItem());
                    });

                    final ContextMenu rowMenu = new ContextMenu();
                    rowMenu.getItems().add(rinnova);
                    rowMenu.getItems().add(elimina);

                    // only display context menu for non-empty rows:
                    row.contextMenuProperty().bind(
                            Bindings.when(row.emptyProperty())
                                    .then((ContextMenu) null)
                                    .otherwise(rowMenu));
                    return row;
                });

        tabella.itemsProperty().bindBidirectional(DatabaseManager.databaseProperty());
        tabella.getStyleClass().addAll(JMetroStyleClass.ALTERNATING_ROW_COLORS, JMetroStyleClass.TABLE_GRID_LINES);

        aggiungiFloating.setOnAction(event -> ViewManager.addController.add());
    }

    private static class AbbonamentoTableRow extends TableRow<Abbonamento> {

        @Override
        public void updateSelected(boolean selected) {
            super.updateSelected(selected);
            updateFormatting();
        }

        @Override
        protected void updateItem(Abbonamento item, boolean empty) {
            super.updateItem(item, empty);
            updateFormatting();
        }

        private void updateFormatting() {
            if (!isEmpty() && getItem() != null && getItem().isScaduto()) {
                if (isSelected()) {
                    String style = "color: red;" +
                                   "cell_selected_color: derive(color, 90%);" +
                                   "cell_selected_hover_color: derive(color, 70%);" +
                                   "cell_selected_pressed_color: derive(color, 60%);";
                    getChildren().forEach(node -> node.setStyle(style));
                } else {
                    Platform.runLater(() -> { // Fix per startup.
                        Background background = getBackground();
                        if (background == null) return;
                        Paint paint = background.getFills().get(0).getFill();
                        if (!(paint instanceof Color)) return;
                        Color color = (Color) paint;
                        String style = String.format("-fx-background-color: rgba(%.0f, 0, 0, 0.2);", color.getRed() * 255);
                        getChildren().forEach(node -> node.setStyle(style));
                    });
                }
            } else {
                // defaults
                String style = "cell_selected_color: derive(accent_color, 90%);" +
                               "cell_selected_hover_color: derive(accent_color, 70%);" +
                               "cell_selected_pressed_color: derive(accent_color, 60%);";
                getChildren().forEach(node -> node.setStyle(style));
            }
        }
    }
}
