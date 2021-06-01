package gurankio;

import java.util.Random;

public class Main {

	public static void main(String[] args) {
		Ponte ponte = new Ponte();
		for (int i = 0; i < 5; i++) {
			new Camion("C" + i, ponte).start();
		}
		for (int i = 0; i < 5; i++) {
			new Macchina("MD" + i, ponte, Macchina.Direzione.DESTRA).start();
			new Macchina("MS" + i, ponte, Macchina.Direzione.SINISTRA).start();
		}
	}
}