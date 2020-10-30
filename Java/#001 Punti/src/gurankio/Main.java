package gurankio;

public class Main {

	public static void main(String[] args) {
		Punto p1 = new Punto(-1.5, 6);
		Punto p2 = new Punto(1, 1);

		System.out.printf("Ordinate dei punti: %.2f e %.2f%n", p1.getY(), p2.getY());
		System.out.println(p1);

		p2.setY(-400);
		System.out.println(p2);

		Punto p3 = new Punto(12);
		System.out.println(p3);
	}

}
