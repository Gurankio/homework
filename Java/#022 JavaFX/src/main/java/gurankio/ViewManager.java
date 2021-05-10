package gurankio;

import gurankio.controller.AddController;
import gurankio.controller.MainController;
import gurankio.controller.RenewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;

public class ViewManager {

    // Cache dei file caricati e dei loro controller.
    public static Parent main;
    public static MainController mainController;
    public static Parent add;
    public static AddController addController;
    public static Parent renew;
    public static RenewController renewController;

    public static Style style = Style.LIGHT;

    public static void init() throws IOException {
        FXMLLoader mainLoader = new FXMLLoader(Main.class.getResource("views/main.fxml"));
        main = mainLoader.load();
        new JMetro(main, style);
        mainController = mainLoader.getController();

        FXMLLoader addLoader = new FXMLLoader(Main.class.getResource("views/add.fxml"));
        add = addLoader.load();
        new JMetro(add, style);
        addController = addLoader.getController();

        FXMLLoader renewLoader = new FXMLLoader(Main.class.getResource("views/renew.fxml"));
        renew = renewLoader.load();
        new JMetro(renew, style);
        renewController = renewLoader.getController();
    }

}
