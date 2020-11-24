package gurankio;

import gurankio.menu.Menu;

public class Main {

	public static PortaCd[] portaCds;

	// TODO: Replace toString() with a custom one. Probably using some tree like construction.
	public static void main(String[] args) {
		Cd h = new Cd("Hello,", "World!", 1, 1);
		Cd a = new Cd("A", "T", 645, 163);
	    Cd b = new Cd("A", "T", 645, 163);
	    Cd c = new Cd("C", "T", 124, 575);

	    portaCds = new PortaCd[10];

		PortaCd portaCdA = new PortaCd(5);
 		portaCdA.setCd(0, h);
 		portaCdA.setCd(1, a);
 		portaCdA.setCd(3, c);
 		portaCds[0] = portaCdA;

		PortaCd portaCdB = new PortaCd(10);
 		portaCdB.setCd(1, a);
 		portaCdB.setCd(2, b);
 		portaCdB.setCd(3, c);
 		portaCdB.killCd(1);
 		portaCds[1] = portaCdB;

		new Menu(Main.class).console();
	}
}
