package gurankio;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		/*
		//Creazione dei dati di esempio.
		new AereoMotore("AM1", 122).save();
		new AereoMotore("AM2", 9).save();
		new AereoMotore("AM3", 561).save();
		new AereoMotore("AM4", 25).save();
		new AereoMotore("AM5", 13).save();
		new AereoMotore("AM6", 75).save();

		new Aliante("AL1", .634).save();
		new Aliante("AL2", .860).save();
		new Aliante("AL3", .234).save();
		new Aliante("AL4", .980).save();
		new Aliante("AL5", .300).save();
		new Aliante("AL6", .124).save();
		*/

		Aerodromo aerodromo = new Aerodromo();
		System.out.println(aerodromo.toString());

		Scanner scanner = new Scanner(System.in);

		System.out.print("Sigla del primo aeromobili da confrontare: ");
		String a = scanner.next();

		System.out.print("Sigla del secondo aeromobili da confrontare: ");
		String b = scanner.next();

		try {
			int d = aerodromo.compare(a, b);
			if (d < 0) System.out.println("Il secondo aeromobile è il migliore.");
			if (d == 0) System.out.println("I due aeromobile sono equivalenti.");
			if (d > 0) System.out.println("Il primo aeromobile è il migliore.");
		} catch (MissingSiglaException exception) {
			System.out.println("Aeromobili inesistente.");
		} catch (ClassCastException exception) {
			System.out.println("Aeromobili non confrontabili.");
		} catch (NullPointerException exception) {
			System.out.println("Non dovrei esistere.");
		}

	}

}