package gurankio;

public class Main {

	public static void main(String[] args) {
		Statua s1 = new Statua("Nike di Samotracia", "Anonimo", "Grecia Classica");
		Quadro q1 = new Quadro("Hello,", "World!", "1900");
		Museo pecci = new Museo("Pecci", "Prato", "Viale della Repubblica", s1, q1);
		pecci.aggiungiStatua(new Statua("A", "B", "C"));
		pecci.aggiungiQuadro(new Quadro("A", "B", "C"));
		System.out.println(pecci);
		pecci.eliminaStatua(0);
		Quadro q2 = new Quadro("E", "F", "G");
		pecci.aggiungiQuadro(q2);
		System.out.println(pecci);
		pecci.eliminaQuadro(q2);
		System.out.println(pecci);
	}

}
