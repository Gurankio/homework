package gurankio.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *  Classe per l'aquisizione di numeri da stdin.
 */
public class ConsoleInput {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Legge un intero da una riga di stdin.
     * @return Valore intero digitato.
     */
    public static int readInt() {
        try {
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Valore non valido. Reinseriscilo!");
            return readInt();
        }
    }

    /**
     * Legge un intero da una riga di stdin.
     * @param prompt Messaggio da stampare all'utente.
     * @return Valore intero digitato.
     */
    public static int readInt(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Valore non valido. Reinseriscilo!");
            return readInt(prompt);
        }
    }

    /**
     * Legge un double da una riga di stdin.
     * @return Valore decimale digitato.
     */
    public static double readDouble() {
        try {
            return Double.parseDouble(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Valore non valido. Reinseriscilo!");
            return readDouble();
        }
    }

    /**
     * Legge un double da una riga di stdin.
     * @param prompt Messaggio da stampare all'utente.
     * @return Valore decimale digitato.
     */
    public static double readDouble(String prompt) {
        System.out.print(prompt);
        try {
            return Double.parseDouble(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Valore non valido. Reinseriscilo!");
            return readDouble(prompt);
        }
    }


    /**
     * Legge una stringa da una riga di stdin.
     * @return Riga digitata.
     */
    public static String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Valore non valido. Reinseriscilo!");
            return readLine();
        }
    }

    /**
     * Legge una stringa da una riga di stdin.
     * @param prompt Messaggio da stampare all'utente.
     * @return Riga digitata.
     */
    public static String readLine(String prompt) {
        System.out.print(prompt);
        try {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Valore non valido. Reinseriscilo!");
            return readLine(prompt);
        }
    }
}
