package gurankio;

public class Main {

	public static void main(String[] args) {
		Cd h = new Cd("Hello,", "World!", 1, 1);
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
		System.out.println(portaCdA.getCd(portaCdA.cercaCdPerTitolo("Hello,")));
		System.out.println(portaCdA.confrontaCollezione(portaCdB));
	}
}
