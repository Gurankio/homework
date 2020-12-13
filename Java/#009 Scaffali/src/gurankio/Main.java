package gurankio;

import gurankio.menu.Menu;
import gurankio.menu.MenuOptions;
import gurankio.scaffale.Scaffale;

public class Main {

	@MenuOptions.Hide
	public static boolean DEBUG = false;

	public static Scaffale scaffale;

	public static void main(String[] args) {
		scaffale = new Scaffale();
		scaffale.setLibro(new Libro("Hello", "World", 12431), 0,0);
		new Menu(Main::new).console();
	}

}
