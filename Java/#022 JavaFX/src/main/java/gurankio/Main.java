package gurankio;

import gurankio.model.PersistenceManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    // TODO: cose che potrebbero mancare: Modifica e Filtro.
    // TODO: cosa divertende da fare: riscrivere con RxJava e RxJavaFx

    // Deve essere accessibile per i FileChooser.
    public static Stage stage;

    @Override
    public void init() throws Exception {
        PersistenceManager.init();
        ViewManager.init();
    }

    @Override
    public void start(Stage stage) {
        Main.stage = stage;

        Scene scene = new Scene(ViewManager.main);
        stage.setMinWidth(1100);
        stage.setMinHeight(600);
        stage.setTitle("Questa schermata è offerta da Java, un linguaggio di alto livello!");
        stage.setOnCloseRequest(event -> System.exit(0)); // fix per il mio macOS
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
