package gurankio;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gurankio.input.ConsoleInput;

public class Partita {

	private static final Random random = new Random();

	private Giocatore giocatoreA;
	private Giocatore giocatoreB;
	private List<Personaggio> mazzo;
	private int numeroPersonaggi;
	private int risultato;
	
	// Mi aspetto un mazzo con valori contigui e completamente pieno, con più di 4 carte.
	public Partita(Giocatore giocatoreA, Giocatore giocatoreB, List<Personaggio> mazzo) {
		this.giocatoreA = giocatoreA;
		this.giocatoreB = giocatoreB;
		this.mazzo = new ArrayList<>(mazzo);

		this.giocatoreA.aggiungiPersonaggio(rimuoviPersonaggioCasuale());
		this.giocatoreA.aggiungiPersonaggio(rimuoviPersonaggioCasuale());
		this.giocatoreB.aggiungiPersonaggio(rimuoviPersonaggioCasuale());
		this.giocatoreB.aggiungiPersonaggio(rimuoviPersonaggioCasuale());
	}

	public Giocatore getGiocatoreA() {
		return giocatoreA;
	}

	public void setGiocatoreA(Giocatore giocatoreA) {
		this.giocatoreA = giocatoreA;
	}

	public Giocatore getGiocatoreB() {
		return giocatoreB;
	}

	public void setGiocatoreB(Giocatore giocatoreB) {
		this.giocatoreB = giocatoreB;
	}

	private Personaggio getPersonaggio(int index) {
		return mazzo.get(index);
	}
	
	private Personaggio rimuoviPersonaggio(int index) {
		return mazzo.remove(index);
	}
	
	private Personaggio rimuoviPersonaggioCasuale() {
		return rimuoviPersonaggio(random.nextInt(numeroPersonaggi));
	}

	private void aggiornaRisultato() {
	}
	
	public int getRisultato() {
		return risultato;
	}
	
	public boolean turno() {
		Personaggio personaggioA = chiediPersonaggio(giocatoreA);
		Personaggio personaggioB = chiediPersonaggio(giocatoreB);

		System.out.println(personaggioA.getNome() + " vs " + personaggioB.getNome());
		int r = personaggioA.sfida(personaggioB);
		if (r > 0) {
			giocatoreA.incrementaPunteggio(r);
			System.out.println(personaggioA.getNome() + " vince la sfida.");
			System.out.println(giocatoreA.getNome() + " guadagna " + r + " punti.");
			System.out.println();
		} else {
			r = Math.abs(r);
			giocatoreB.incrementaPunteggio(r);
			System.out.println(personaggioB.getNome() + " vince la sfida.");
			System.out.println(giocatoreB.getNome() + " guadagna " + r + " punti.");
			System.out.println();
		}

		// Aggiorno risultato
		risultato = giocatoreA.getPunteggio() - giocatoreB.getPunteggio();

		// Assumo un mazzo sempre pari. Non ci sono casi in cui posso dare il nuovo personaggio solo ad un giocatore.
		if (numeroPersonaggi > 1) {
			giocatoreA.aggiungiPersonaggio(rimuoviPersonaggioCasuale());
			giocatoreB.aggiungiPersonaggio(rimuoviPersonaggioCasuale());
		}
		return giocatoreA.getNumeroPersonaggi() != 0 && giocatoreB.getNumeroPersonaggi() != 0;
	}

	private Personaggio chiediPersonaggio(Giocatore giocatore) {
		System.out.println(giocatoreA);
		int indexA;
		do {
			indexA = ConsoleInput.readInt("Scegli un personaggio: ");
			if (indexA < 0 || indexA >= giocatoreA.getNumeroPersonaggi()) {
				System.out.println("Personaggio non valido. Inserire un numero tra 0 e " + (giocatoreA.getNumeroPersonaggi()-1));
			}
		} while (indexA < 0 || indexA >= giocatoreA.getNumeroPersonaggi());
		System.out.println();
		return giocatoreA.rimuoviPersonaggio(indexA);
	}

}
