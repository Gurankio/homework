package gurankio;

public class Main {

	public static void main(String[] args) {
		Angolo a1 = new Angolo(-100, 0, 0);
		Angolo a2 = new Angolo(55, 123, 232);
		System.out.println(a1);

		a1.aggiungiGradi(1);
		System.out.println(a1);

		a1.aggiungiPrimi(-32482312);
		System.out.println(a1);

		a1.aggiungiSecondi(12);
		System.out.println(a1);

		a1.setSecondi(a2.getSecondi());
		System.out.println(a1);

		System.out.println(a1.differenzaSecondi(a2));

		a1.sommaAngolo(a2);
		System.out.println(a1);
	}

}
