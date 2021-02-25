package gurankio.io.file;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Interfaccia generica per il salvataggio e caricamento di un oggetto su un file,
 *   indipendentemente dalla codifica utilizzata.
 *
 * @author Gionatan Buono
 * @author Jacopo Del Granchio
 */
public interface FileInterface {

    /**
     * Salva un oggetto nel file.
     *
     * @param object da salvare.
     * @param file dove salvare lo stato dell'oggetto.
     * @return se il salvataggio ha avuto successo.
     * @throws FileNotFoundException se il file è una cartella, non esiste e non puo essere creato, o non puo essere aperto.
     * @throws InvalidExtensionException se il file non ha la corretta estensione.
     */
    boolean save(Object object, File file) throws FileNotFoundException, InvalidExtensionException;

    /**
     * Crea un'instanza della classe, leggendo dal file.
     *
     * @param file da cui viene letto lo stato dell'oggetto.
     * @param target la classe di cui si vuole un'istanza.
     * @param <T> il tipo dell'instanza.
     * @return una istanza della classe specificata. Se si verificano errori sulla lettura, ritorna null.
     * @throws FileNotFoundException se il file non esiste, non è leggibile o è una cartella.
     * @throws InvalidExtensionException se il file non ha la corretta estensione.
     */
    <T> T load(File file, Class<T> target) throws FileNotFoundException, InvalidExtensionException;

}
