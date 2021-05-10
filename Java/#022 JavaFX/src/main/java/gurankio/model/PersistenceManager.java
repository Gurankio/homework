package gurankio.model;

import gurankio.Main;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class PersistenceManager {

    // Come le date saranno serializzate da tutto il programma.
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static List<FileWrapper> files = new ArrayList<>();
    public static ObjectProperty<File> open = new SimpleObjectProperty<>();

    public static void init() {
        files.addAll(PersistentCSV.load(new File("./files.txt"))
                .filter(Objects::nonNull)
                .filter(o -> o instanceof FileWrapper)
                .map(o -> (FileWrapper) o)
                .collect(Collectors.toList()));
        if (files.size() > 0) {
            open(files.get(0).getFile());
        }
    }

    public static void saves() {
        if (open.get() == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Salva");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text File", "*.txt"),
                    new FileChooser.ExtensionFilter("CSV File", "*.csv"));
            File file = fileChooser.showSaveDialog(Main.stage);
            if (file == null) return;
            files.add(0,  new FileWrapper(file));
            PersistentCSV.save(new File("./files.txt"), files.stream());
            open.set(file);
        }
        PersistentCSV.save(open.get(), DatabaseManager.getDatabase().parallelStream());
    }

    public static void open(File file) {
        FileWrapper wrapper = new FileWrapper(file);
        files.remove(wrapper);
        files.add(0, wrapper);
        PersistentCSV.save(new File("./files.txt"), files.stream());
        open.set(file);
        DatabaseManager.reset();
        DatabaseManager.getDatabase().clear();
        DatabaseManager.getDatabase().addAll(PersistentCSV.load(file)
                .filter(o -> o instanceof Abbonamento)
                .map(o -> (Abbonamento) o)
                .sorted(Comparator.comparing(Abbonamento::getNumeroTessera))
                .collect(Collectors.toList()));
    }

    public static class FileWrapper implements PersistentCSV {
        private File file;

        public FileWrapper(File file) {
            this.file = file;
        }

        public FileWrapper() {
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        @Override
        public void serialize(Stack<String> tokens) {
            tokens.push(file.getAbsolutePath());
        }

        @Override
        public boolean parse(Stack<String> tokens) {
            file = new File(tokens.pop());
            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof FileWrapper)) return false;
            return Objects.equals(file, ((FileWrapper) o).file);
        }

        @Override
        public int hashCode() {
            return Objects.hash(file);
        }
    }
}
