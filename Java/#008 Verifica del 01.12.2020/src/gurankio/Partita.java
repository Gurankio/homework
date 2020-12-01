package gurankio;

import java.util.Random;

import gurankio.input.ConsoleInput;

public class Partita {
	
	private Giocatore giocatoreA;
	private Giocatore giocatoreB;
	private Personaggio[] mazzo;
	private int numeroPersonaggi;
	private int risultato;
	
	// Mi aspetto un mazzo con valori contigui e completamente pieno, con più di 4 carte.
	public Partita(Giocatore giocatoreA, Giocatore giocatoreB, Personaggio[] mazzo) {
		this.giocatoreA = giocatoreA;
		this.giocatoreB = giocatoreB;
		this.mazzo = mazzo.clone();
		this.numeroPersonaggi = mazzo.length;
		
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
		if (index < 0 || index > numeroPersonaggi) return null;
		return mazzo[index];
	}
	
	private Personaggio rimuoviPersonaggio(int index) {
		Personaggio p = getPersonaggio(index);
		if (p != null) {
			for (int i=index; i<numeroPersonaggi-1; i++) mazzo[i] = mazzo[i+1];
			numeroPersonaggi--;
		}
		return p;
	}
	
	private Personaggio rimuoviPersonaggioCasuale() {
		return rimuoviPersonaggio(new Random().nextInt(numeroPersonaggi));
	}
	
	
	private void aggiornaRisultato() {
		risultato = giocatoreA.getPunteggio() - giocatoreB.getPunteggio();
	}
	
	public int getRisultato() {
		return risultato;
	}
	
	public boolean turno() {
		System.out.println(giocatoreA);
		int indexA;
		do {
			indexA = ConsoleInput.readInt("Scegli un personaggio: ");
			if (indexA < 0 || indexA >= giocatoreA.getNumeroPersonaggi()) {
				System.out.println("Personaggio non valido. Inserire un numero tra 0 e " + (giocatoreA.getNumeroPersonaggi()-1));
			}
		} while (indexA < 0 || indexA >= giocatoreA.getNumeroPersonaggi());
		Personaggio personaggioA = giocatoreA.rimuoviPersonaggio(indexA);
		System.out.println("");

		System.out.println(giocatoreB);
		int indexB;
		do {
			indexB = ConsoleInput.readInt("Scegli un personaggio: ");
			if (indexB < 0 || indexB >= giocatoreB.getNumeroPersonaggi()) {
				System.out.println("Personaggio non valido. Inserire un numero tra 0 e " + (giocatoreB.getNumeroPersonaggi()-1));
			}
		} while (indexB < 0 || indexB >= giocatoreB.getNumeroPersonaggi());
		Personaggio personaggioB = giocatoreB.rimuoviPersonaggio(indexA);
		System.out.println("");

		System.out.println(personaggioA.getNome() + " vs " + personaggioB.getNome());
		int r = personaggioA.sfida(personaggioB);
		if (r > 0) {
			giocatoreA.incrementaPunteggio(r);
			System.out.println(personaggioA.getNome() + " vince la sfida.");
			System.out.println(giocatoreA.getNome() + " guadagna " + r + " punti.");
			System.out.println("");
		} else {
			r = Math.abs(r);
			giocatoreB.incrementaPunteggio(r);
			System.out.println(personaggioB.getNome() + " vince la sfida.");
			System.out.println(giocatoreB.getNome() + " guadagna " + r + " punti.");
			System.out.println("");
		}
		aggiornaRisultato();
		
		// Assumo un mazzo sempre pari. Non ci sono casi in cui posso dare il nuovo personaggio solo ad un giocatore.
		if (numeroPersonaggi > 1) {
			giocatoreA.aggiungiPersonaggio(rimuoviPersonaggioCasuale());
			giocatoreB.aggiungiPersonaggio(rimuoviPersonaggioCasuale());
		}
		return giocatoreA.getNumeroPersonaggi() != 0 && giocatoreB.getNumeroPersonaggi() != 0;
	}
	
}
