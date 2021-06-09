package gurankio;

import java.util.Random;

public class Main {

	public static void main(String[] args) {
		Buffer<Integer> buffer = new Buffer<>(new Integer[5]);
		for (int i = 0; i < 3; i++) new Consumatore<>(buffer, 500, Main::consumatore).start();
		for (int i = 0; i < 3; i++) new Produttore<>(buffer, 500, Main::produttore).start();
	}

	private static final Random d = new Random();

	private static Integer produttore() {
		// System.out.println("*] Scritto: " + t);
		return d.nextInt(100);
	}

	private static void consumatore(int d) {
		// System.out.println("*) Letto: " + d);
	}

}