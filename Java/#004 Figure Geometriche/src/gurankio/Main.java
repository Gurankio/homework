package gurankio;

public class Main {

	public static void main(String[] args) {
		Triangolo t = new Triangolo(new Punto(-1.87, 0), new Punto(5, 1.5), new Punto(1.54, 5));
		System.out.println(t);
		System.out.flush();

		try {
			Triangolo et = new Triangolo(new Punto(0, 0), new Punto(5, 0), new Punto(10, 0));
			System.out.println(et);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		Cerchio c = new Cerchio(new Punto(0, 0), 4);
		System.out.println(c);
		System.out.flush();

		try {
			Cerchio ec = new Cerchio(new Punto(0, 0), -5.4);
			System.out.println(ec);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

}
