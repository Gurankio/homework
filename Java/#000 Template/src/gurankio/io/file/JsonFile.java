package gurankio.io.file;
import com.google.gson.*;

import java.io.*;

public class JsonFile implements FileInterface{

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void save(Object object, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(gson.toJson(object));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> T load(File file, Class<T> target) throws FileNotFoundException {
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
