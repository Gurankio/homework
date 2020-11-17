package gurankio;

import gurankio.input.ConsoleInput;
import gurankio.input.ConsoleMenu;

public class Main {

	public PortaCd portaCdA;
	public PortaCd portaCdB;

	public static void test() {
		System.out.println("asdasd");
	}

	public static void main(String[] args) {
		new ConsoleMenu<>(Main.class).start();

		/*
		Cd h = new Cd(
				ConsoleInput.readString("Titolo: "),
				ConsoleInput.readString("Autore: "),
				ConsoleInput.readInt("Numero Brani: "),
				ConsoleInput.readInt("Durata: ")
		);
		Cd a = new Cd("A", "T", 645, 163);
		Cd b = new Cd("A", "T", 645, 163);
		Cd c = new Cd("C", "T", 124, 575);
		System.out.println(a.equals(a));
		System.out.println(a.equals(b));
		System.out.println(a.equals(1));

		PortaCd portaCdA = new PortaCd(5);
		portaCdA.setCd(0, h);
		portaCdA.setCd(1, a);
		portaCdA.setCd(3, c);
		System.out.println(portaCdA);

		PortaCd portaCdB = new PortaCd(10);
		portaCdB.setCd(1, a);
		portaCdB.setCd(2, b);
		portaCdB.setCd(3, c);
		portaCdB.killCd(1);
		System.out.println(portaCdB);

		System.out.println(portaCdA.getCd(0));
		System.out.println(portaCdA.getCd(portaCdA.cercaCdPerTitolo("C")));
		System.out.println(portaCdA.confrontaCollezione(portaCdB));

		 */
	}
}
