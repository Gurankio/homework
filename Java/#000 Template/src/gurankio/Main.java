package gurankio;

import gurankio.input.ConsoleInput;

public class Main {

	public static void main(String[] args) {
		int centesimi = ConsoleInput.readInt("Quanti centesimi? ");

		double euro;
		do {
			euro = ConsoleInput.readDouble("Quanti euro sono 75 centesimi? ");
			if (euro != 0.75) System.out.println("I conti non toranano!");
		} while (euro != 0.75);

		String chi = ConsoleInput.readLine("A chi devi questi soldi? ");

		System.out.printf("Devi %.2f a %s%n", centesimi * 0.01 + euro, chi);
	}

}
