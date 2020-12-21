package gurankio;

import gurankio.menu.Menu;
import gurankio.menu.MenuOptions;

public class Main {

	@MenuOptions.Hide
	public static boolean DEBUG = false;

	public static Rettangolo rettangolo;

	public static void main(String[] args) {
		new Menu(Main::new).console();
	}

}
