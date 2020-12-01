package gurankio;

public class Personaggio {
	
	private String nome;
	private int forza;
	private int esperienza;
	
	public Personaggio(String nome, int forza, int esperienza) {
		this.nome = nome;
		this.forza = forza;
		this.esperienza = esperienza;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getForza() {
		return forza;
	}

	public void setForza(int forza) {
		this.forza = forza;
	}

	public int getEsperienza() {
		return esperienza;
	}

	public void setEsperienza(int esperienza) {
		this.esperienza = esperienza;
	}
	
	public int sfida(Personaggio p) {
		if (forza - p.forza != 0) return forza - p.forza;
		else return p.esperienza - esperienza;
	}

	@Override
	public String toString() {
		return String.format("%12s -> %d / %d", nome, forza, esperienza);
	}

}
