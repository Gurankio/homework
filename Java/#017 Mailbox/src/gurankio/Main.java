package gurankio;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		/*
		// Dati di esempio.
		Random r = new Random();
		new Mail("A", "oggA", IntStream.generate(() -> 'a' + r.nextInt('z' - 'a')).limit(100).mapToObj(integer -> String.valueOf((char) integer)).collect(Collectors.joining()));
		new Mail("B", "oggB", IntStream.generate(() -> 'a' + r.nextInt('z' - 'a')).limit(100).mapToObj(integer -> String.valueOf((char) integer)).collect(Collectors.joining()));
		new Mail("C", "oggC", IntStream.generate(() -> 'a' + r.nextInt('z' - 'a')).limit(100).mapToObj(integer -> String.valueOf((char) integer)).collect(Collectors.joining()));
		new Mail("D", "tstD", IntStream.generate(() -> 'a' + r.nextInt('z' - 'a')).limit(100).mapToObj(integer -> String.valueOf((char) integer)).collect(Collectors.joining()));
		new Mail("E", "tstE", IntStream.generate(() -> 'a' + r.nextInt('z' - 'a')).limit(100).mapToObj(integer -> String.valueOf((char) integer)).collect(Collectors.joining()));
		new Mail("F", "asdF", IntStream.generate(() -> 'a' + r.nextInt('z' - 'a')).limit(100).mapToObj(integer -> String.valueOf((char) integer)).collect(Collectors.joining()));
		new Mail("G", "asdG", IntStream.generate(() -> 'a' + r.nextInt('z' - 'a')).limit(100).mapToObj(integer -> String.valueOf((char) integer)).collect(Collectors.joining()));
		 */

		Mailbox mailbox = new Mailbox();
		Scanner scanner = new Scanner(System.in);
		while (menu(mailbox, scanner));
	}

	private static boolean menu(Mailbox mailbox, Scanner scanner) {
		System.out.println();
		System.out.println(mailbox);
		System.out.println();
		System.out.println(" - Inserire un indice per visualizzare il contenuto di ua mail.");
		System.out.println(" - (A)ggiungi Mail");
		System.out.println(" - (R)icerca Mail");
		System.out.println(" - (E)limina Mail");
		System.out.println(" - (C)hiudi");
		System.out.print(" -> ");

		String in = scanner.nextLine();

		if (in.equalsIgnoreCase("A")) {
			System.out.println(" - Inserire l'oggetto: ");
			System.out.print(" -> ");
			String mittente = scanner.nextLine();
			System.out.println(" - Inserire il mittente: ");
			System.out.print(" -> ");
			String oggetto = scanner.nextLine();
			System.out.println(" - Inserire il testo della mail: ");
			System.out.print(" -> ");
			String testo = scanner.nextLine();
			mailbox.aggiungiMail(new Mail(mittente, oggetto, testo));
			System.out.println(" ! Mail aggiunta.");
			scanner.nextLine();
			return true;
		}

		if (in.equalsIgnoreCase("R")) {
			System.out.println(" - Inserire il testo da ricercare: ");
			System.out.print(" -> ");
			System.out.println(mailbox.ricercaOggetto(scanner.nextLine()).stream().map(mail -> "   " + mail.toString()).collect(Collectors.joining("\n")));
			scanner.nextLine();
			return true;
		}

		if (in.equalsIgnoreCase("E")) {
			try {
				System.out.println(" - Inserire l'indice da eliminare: ");
				System.out.print(" -> ");
				int i = Integer.parseInt(scanner.nextLine());
				mailbox.eliminaMail(i);
				System.out.println(" ! Mail eliminata.");
				scanner.nextLine();
			} catch (NumberFormatException ignored) {
				System.out.println(" ! Numero non valido.");
			} catch (IndexOutOfBoundsException e) {
				System.out.println(" ! Mail inesistente.");
			}
			return true;
		}

		if (in.equalsIgnoreCase("C")) {
			return false;
		}

		try {
			int i = Integer.parseInt(in);
			System.out.println();
			System.out.println(Arrays.stream(mailbox.get(i).toString(true).split("\n")).map(mail -> "   " + mail.toString()).collect(Collectors.joining("\n")));
			scanner.nextLine();
			return true;
		} catch (NumberFormatException ignored) {
		} catch (IndexOutOfBoundsException e) {
			System.out.println(" ! Mail inesistente.");
		}

		System.out.println(" ! Opzione sconosciuta.");
		return true;
	}

}