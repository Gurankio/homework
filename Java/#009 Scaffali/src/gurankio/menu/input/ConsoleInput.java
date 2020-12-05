package gurankio.menu.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInput {

    public static final String ERROR_MESSAGE = "Invalid input.";
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static int readInt(String prompt) {
        ConsoleOutput.print(prompt);
        try {
            return Integer.parseInt(READER.readLine());
        } catch (IOException | NumberFormatException e) {
            ConsoleOutput.println(ERROR_MESSAGE);
            return readInt(prompt);
        }
    }

    public static int readInt() {
        return readInt("");
    }

    public static double readDouble(String prompt) {
        ConsoleOutput.print(prompt);
        try {
            return Double.parseDouble(READER.readLine());
        } catch (IOException | NumberFormatException e) {
            ConsoleOutput.println(ERROR_MESSAGE);
            return readDouble(prompt);
        }
    }

    public static double readDouble() {
        return readDouble("");
    }

    public static String readString(String prompt) {
        ConsoleOutput.print(prompt);
        try {
            return READER.readLine();
        } catch (IOException e) {
            ConsoleOutput.println(ERROR_MESSAGE);
            return readString(prompt);
        }
    }

    public static String readString() {
        return readString("");
    }
}
