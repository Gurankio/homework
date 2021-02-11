package gurankio;

import gurankio.menu.Menu;

public class Main {

	public static void main(String[] args) {
		new Menu(Main::new).run();
	}

}
