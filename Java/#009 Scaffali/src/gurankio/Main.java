package gurankio;

import gurankio.menu.Menu;
import gurankio.scaffale.Scaffale;

public class Main {

	public static Scaffale scaffale;

	public static void main(String[] args) {
		new Menu(Main.class).console();
	}

}
