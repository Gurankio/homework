package gurankio;

import gurankio.carrello.*;
import gurankio.menu.Menu;
import gurankio.menu.MenuOptions;
import gurankio.menu.io.ConsoleInput;

public class Main {

	@MenuOptions.Hide
	public static boolean DEBUG = false;

	public static Carrello carrello = new Carrello();

 	public static void main(String[] args) {
 		ConsoleInput.overrides.put(Merce.class, TipoDiMerce::crea);
		new Menu(Main::new).console();
	}

}
