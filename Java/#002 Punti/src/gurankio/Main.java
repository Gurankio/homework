package gurankio;

public class Main {

	public static void main(String[] args) {
		Angolo a1 = new Angolo(100, 12, 12);
		Angolo a2 = new Angolo(55, 123, 232);
		a1.visualizzaAngolo();

		a1.aggiungiGradi(1);
		a1.visualizzaAngolo();

		a1.aggiungiPrimi(12);
		a1.visualizzaAngolo();

		a1.aggiungiSecondi(12);
		a1.visualizzaAngolo();

		a2.visualizzaAngolo();
		a1.secondiAngolo(a2.angoloSecondi());
		a1.visualizzaAngolo();

		System.out.println(a1.differenzaSecondi(a2));

		a1.sommaAngolo(a2);
		a1.visualizzaAngolo();
	}

}
