package gurankio;

import gurankio.menu.Menu;

public class Main {

	public static PortaCd portaCdA;
	public static PortaCd portaCdB;
	public static Options options;

	public static class Options {

		public static void README() {
			System.out.println("Hello, World!");
		}

		public static void LICENSE() {
			System.out.println("Hello, World!");
		}

	}

	public static void main(String[] args) {
		new Menu(Main.class).start();
	}
}
