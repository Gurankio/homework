package gurankio;

import gurankio.menu.Menu;
import gurankio.menu.MenuOptions;
import gurankio.scuola.Scuola;
import gurankio.scuola.TipiDiScuola;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	@MenuOptions.Hide
	public static boolean DEBUG = false;

	public static ArrayList<Scuola> scuole = new ArrayList<>();

 	public static void main(String[] args) {
		scuole.addAll(Arrays.asList(
				TipiDiScuola.ELEMENTARE.creaScuola("asd", "asd", "asd", "asd", 12, 34, 56, 78),
				TipiDiScuola.MEDIA.creaScuola("asd", "asd", "asd", "asd", 12, 34, 56, 78),
				TipiDiScuola.TECNICO.creaScuola("asd", "asd", "asd", "asd", 12, 34, 56, 78),
				TipiDiScuola.PROFESSIONALE.creaScuola("asd", "asd", "asd", "asd", 12, 34, 56, 78),
				TipiDiScuola.LICEO.creaScuola("asd", "asd", "asd", "asd", 12, 34, 56, 78)
		));

		new Menu(Main::new).console();
	}

	// per il menu.
	public static Scuola aggiungiScuola(TipiDiScuola tipo, String codice, String denominazione, String indirizzo, String citta, int studenti, int classi, int sediAggiuntive, int laboratori) {
 		Scuola scuola = tipo.creaScuola(codice, denominazione, indirizzo, citta, studenti, classi, sediAggiuntive, laboratori);
 		scuole.add(scuola);
		return scuola;
	}

	public static TipiDiScuola modificaTipoDiScuola(TipiDiScuola tipo) {
 		return tipo;
	}

}
