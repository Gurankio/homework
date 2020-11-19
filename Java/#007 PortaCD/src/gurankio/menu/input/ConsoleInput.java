package gurankio.menu.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;

/**
 *  Classe per l'aquisizione di numeri da stdin.
 */
public class ConsoleInput {

    private static final String ERROR_MESSAGE = "Valore non valido. Reinseriscilo!";
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Legge un intero da una riga di stdin.
     * @param prompt Messaggio da stampare all'utente.
     * @return Valore intero digitato.
     */
    public static int readInt(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(READER.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println(ERROR_MESSAGE);
            return readInt(prompt);
        }
    }

    /**
     * Legge un intero da una riga di stdin.
     * @return Valore intero digitato.
     */
    public static int readInt() {
        return readInt("");
    }

    /**
     * Legge un double da una riga di stdin.
     * @param prompt Messaggio da stampare all'utente.
     * @return Valore decimale digitato.
     */
    public static double readDouble(String prompt) {
        System.out.print(prompt);
        try {
            return Double.parseDouble(READER.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println(ERROR_MESSAGE);
            return readDouble(prompt);
        }
    }

    /**
     * Legge un double da una riga di stdin.
     * @return Valore decimale digitato.
     */
    public static double readDouble() {
        return readDouble("");
    }

    /**
     * Legge una stringa da una riga di stdin.
     * @param prompt Messaggio da stampare all'utente.
     * @return Riga digitata.
     */
    public static String readString(String prompt) {
        System.out.print(prompt);
        try {
            String s = READER.readLine();
            if (!s.equals("")) return s;
            System.out.println(ERROR_MESSAGE);
            return readString(prompt);
        } catch (IOException e) {
            System.out.println(ERROR_MESSAGE);
            return readString(prompt);
        }
    }

    /**
     * Legge una stringa da una riga di stdin.
     * @return Riga digitata.
     */
    public static String readString() {
        return readString("");
    }
}
