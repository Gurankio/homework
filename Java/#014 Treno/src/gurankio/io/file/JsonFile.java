package gurankio.io.file;
import com.google.gson.*;
import gurankio.io.exception.InvalidExtensionException;

import java.io.*;

public class JsonFile implements FileInterface{

    private static final String EXTENSION = ".json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void save(Object object, File file) {
        if (!file.getName().endsWith(EXTENSION)) throw new InvalidExtensionException(file, EXTENSION);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(gson.toJson(object));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> T load(File file, Class<T> target) throws FileNotFoundException {
        if (!file.getName().endsWith(EXTENSION)) throw new InvalidExtensionException(file, EXTENSION);
        try (BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file))) {
            return gson.fromJson(reader.toString(), target);
        } catch (FileNotFoundException e) {
          throw e;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
