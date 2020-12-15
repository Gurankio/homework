package gurankio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Giocatore {
	
	private String nome;
	private int punteggio;
	private List<Personaggio> personaggi;

	public Giocatore(String nome) {
		this.nome = nome;
		this.punteggio = 0;
		this.personaggi = new ArrayList<>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getPunteggio() {
		return punteggio;
	}

	public void azzeraPunteggio() {
		this.punteggio = 0;
	}
	
	public void incrementaPunteggio(int quantita) {
		this.punteggio += quantita;
	}

	public Personaggio getPersonaggio(int index) {
		return personaggi.get(index);
	}

	public void aggiungiPersonaggio(Personaggio personaggio) {
		personaggi.add(personaggio);
	}
	
	public Personaggio rimuoviPersonaggio(int index) {
		return personaggi.remove(index);
	}

	public int getNumeroPersonaggi() {
		return personaggi.size();
	}

	@Override
	public String toString() {
		return personaggi.stream()
				.map(Personaggio::toString)
				.collect(Collectors.joining("\n"));
	}
	
}
