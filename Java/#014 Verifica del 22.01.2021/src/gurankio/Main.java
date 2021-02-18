package gurankio;

import gurankio.menu.Menu;
import gurankio.menu.io.ConsoleInput;
import gurankio.menu.io.ConsoleOutput;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class Main {

    public enum TipiDiVoli {

        LINEA(Linea.class),
        CHARTER(Charter.class);

        private final Class<?> TARGET;

        TipiDiVoli(Class<?> target) {
            this.TARGET = target;
        }
    }

    public static Volo creaVolo() {
        TipiDiVoli tipoDiVolo = (TipiDiVoli) ConsoleInput.read("", TipiDiVoli.class);
        return (Volo) ConsoleInput.read("", tipoDiVolo.TARGET);
    }

    public static LocalDateTime creaData() {
        do {
            try {
                return LocalDateTime.of((Integer) ConsoleInput.read("anno", Integer.class), (Integer) ConsoleInput.read("mese", Integer.class), (Integer) ConsoleInput.read("giorno", Integer.class), 0, 0);
            } catch (DateTimeException exception) {
                ConsoleOutput.arrowln(exception.getMessage());
            }
        } while(true);
    }

    private static Aereoporto creaAereoportoPerTesting() {
        Aereoporto aereoporto = new Aereoporto();
        aereoporto.inserisciVolo(new Linea(LocalDateTime.of(2021, 1, 1, 0, 0),
                "DEF1", "Sassari", "Sasso", 12));
        aereoporto.inserisciVolo(new Charter(LocalDateTime.of(2020, 12, 12, 0, 0),
                "ABC1", "Sassari", "Sasso", "Sassi", 12));
        return aereoporto;
    }

    public static void main(String[] args) {
        ConsoleInput.overrides.put(LocalDateTime.class, Main::creaData);
        ConsoleInput.overrides.put(Volo.class, Main::creaVolo);
        new Menu(Main::creaAereoportoPerTesting).console();
    }

}
