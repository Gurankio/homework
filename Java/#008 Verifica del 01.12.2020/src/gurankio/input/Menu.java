package gurankio.input;

import gurankio.Giocatore;
import gurankio.Partita;
import gurankio.Personaggio;

import java.util.Arrays;
import java.util.List;

public class Menu {
	
	private static final List<Personaggio> mazzo = Arrays.asList(
			new Personaggio("Brodo",      4,  5),
			new Personaggio("Bulbo",      4,  8),
			new Personaggio("Fandalf",   10, 10),
			new Personaggio("Rollum",     7,  9),
			new Personaggio("Legoland",   8,  8),
			new Personaggio("Faruman",    8, 10),
			new Personaggio("Mauron",     9, 10),
			new Personaggio("Gargalbero", 7, 10),
			new Personaggio("Gerry",      5,  6),
			new Personaggio("Tipino",     5,  5)
	);
	
	private static Giocatore giocatoreA;
	private static Giocatore giocatoreB;

	public static void start() {
		giocatoreA = new Giocatore(ConsoleInput.readString("Inserire il nome del primo giocatore: ")); 
		giocatoreB = new Giocatore(ConsoleInput.readString("Inserire il nome del secondo giocatore: ")); 
		System.out.println();

		// Loop
		int scelta;
		do {
			stampa();
			do {
				scelta = ConsoleInput.readInt("Scelta: ");
				if (scelta < 0 || scelta > 4) System.out.println("Scelta non valida.");
			} while (scelta < 0 || scelta > 4);
			
			System.out.println();
			esegui(scelta);
		} while (scelta != 0);
	}
	
	private static void stampa() {
		System.out.println();
		System.out.println("01) Nuova Partita");
		System.out.println("02) Modifica Nome Giocatori");
		System.out.println("03) Stampa Mazzo");
		System.out.println("04) Modifica Mazzo");
		System.out.println("00) Esci");
	}
	
	private static void esegui(int scelta) {
		int s;
		switch (scelta) {
			case 1:
				Partita partita = new Partita(giocatoreA, giocatoreB, mazzo);
				
				int contatore = 1;
				do {
					System.out.println("Turno " + contatore);
					contatore++;
				} while (partita.turno());
				
				int risultato = partita.getRisultato();
				
				if (risultato > 0) {
					System.out.println(giocatoreA.getNome() + " vince la partita con " + giocatoreA.getPunteggio() + " punti contro " + giocatoreB.getPunteggio() + " punti.");
				}
				if (risultato == 0) {
					System.out.println("E' un pareggio!");
				}
				if (risultato < 0) {
					System.out.println(giocatoreB.getNome() + " vince la partita con " + giocatoreB.getPunteggio() + " punti contro " + giocatoreA.getPunteggio() + " punti.");
				}
				
				giocatoreA.azzeraPunteggio();
				giocatoreB.azzeraPunteggio();
				break;
				
			case 2:
				do {
					s = ConsoleInput.readInt("Di chi vuoi modificare il nome? " + giocatoreA.getNome() + " (0) o " + giocatoreB.getNome() + " (1) ? ");
					if (s < 0 || s > 1) System.out.println("Inserire 0 o 1.");
				} while (s < 0 || s > 1);
				String nuovo = ConsoleInput.readString("Inserire nuovo nome: ");
				if (s == 0) giocatoreA.setNome(nuovo);
				else giocatoreB.setNome(nuovo);				
				break;
				
			case 3:
				mazzo.forEach(System.out::println);
				break;
				
			case 4:
				mazzo.forEach(System.out::println);
				do {
					s = ConsoleInput.readInt("Chi vuoi modificare? ");
					if (s < 0 || s > mazzo.size()) System.out.println("Inserire un numero tra 0 e " + mazzo.size());
				} while (s < 0 || s > mazzo.size());
				Personaggio p = mazzo.get(s);
				
				do {
					s = ConsoleInput.readInt("Cosa vuoi modificare? Nome (0), Forza (1) o Esperianza (2) ? ");
					if (s < 0 || s > 2) System.out.println("Inserire un numero tra 0, 1 o 2");
				} while (s < 0 || s > 2);
				
				switch (s) {
				case 0:
					p.setNome(ConsoleInput.readString("Inserire il nuovo nome: "));
					break;

				case 1:
					p.setForza(ConsoleInput.readInt("Inserire la nuova forza: "));
					break;
					
				case 2:
					p.setEsperienza(ConsoleInput.readInt("Inserire la nuova esperienza: "));
					break;
				}
				
				break;

		}
	}

}
