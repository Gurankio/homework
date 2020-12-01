package gurankio;

public class Giocatore {
	
	private String nome;
	private int punteggio;
	private Personaggio[] personaggi;
	private int numeroPersonaggi;
	
	public Giocatore(String nome) {
		this.nome = nome;
		this.punteggio = 0;
		this.personaggi = new Personaggio[10];
		this.numeroPersonaggi = 0;
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
		if (index < 0 || index > numeroPersonaggi) return null;
		return personaggi[index];
	}

	public void aggiungiPersonaggio(Personaggio personaggio) {
		personaggi[numeroPersonaggi] = personaggio;
		numeroPersonaggi++;
	}
	
	public Personaggio rimuoviPersonaggio(int index) {
		Personaggio p = getPersonaggio(index);
		if (p != null) {
			for (int i=index; i<numeroPersonaggi-1; i++) personaggi[i] = personaggi[i+1];
			numeroPersonaggi--;
		}
		return p;
	}

	public int getNumeroPersonaggi() {
		return numeroPersonaggi;
	}

	@Override
	public String toString() {
		String string = nome + " il tuo mazzo è: \n";
		for (int i=0; i<numeroPersonaggi; i++) {
			string += personaggi[i] + (i != numeroPersonaggi-1 ? "\n" : "");
		}
		return string;
	}
	
}
