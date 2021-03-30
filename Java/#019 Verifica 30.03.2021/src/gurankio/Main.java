package gurankio;

import gurankio.util.GenericScanner;
import gurankio.util.Logger;
import gurankio.util.Menu;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main extends Menu {

	private final CentroSportivo centroSportivo;

	public Main() {
		this.centroSportivo = new CentroSportivo();
	}

	@Override
	protected void setup() {
		title("Centro");
		title("  Sportivo");
		space();
		any(centroSportivo::toString, int.class, this::dettagliCliente);
		space();
		first("[R]egistra Cliente", char.class, this::aggiungiCliente);
		first("[E]sci", char.class, (c) -> false);
	}

	public boolean dettagliCliente(int numeroTessera) {
		Cliente cliente = centroSportivo.getCliente(numeroTessera);
		new Menu() {
			@Override
			protected void setup() {
				message("Dati del cliente " + cliente.getClass().getSimpleName() + ": ");
				message(cliente::toString, "   ");
				space();
				first(cliente.getAbbonamento().isScaduto() ? "[R]innova Abbonamento" : "[M]odifica Abbonamento", char.class, c -> {
					gestisciAbbonamento(cliente, c);
					return true;
				});
				first("[C]ancella Cliente", char.class, c -> {
					centroSportivo.cancellaCliente(cliente);
					return false;
				});
				first("[E]sci", char.class, (c) -> false);
			}
		}.run();
		return true;
	}

	public void gestisciAbbonamento(Cliente cliente, char c) {
		if (c == 'R') {
			cliente.getAbbonamento().setCreazione(LocalDateTime.now());
			Logger.WARN.println("Abbonamento rinnovato.");
		} else {
			Abbonamento.Tipo tipo = GenericScanner.next(Abbonamento.Tipo.class, "Inserire il nuovo tipo dell'abbonamento.", "Inserire un tipo valido.");
			cliente.setAbbonamento(new Abbonamento(tipo, LocalDateTime.now()));
		}
	}

	public boolean aggiungiCliente(char c) {
		boolean agonista = GenericScanner.next(Boolean.class, "Cliente agonista?", "Inserire 'true' o 'false'");
		String nome = GenericScanner.next("Inserire il nome del cliente.");
		String cognome = GenericScanner.next("Inserire il cognome del cliente.");
		LocalDateTime nascita = GenericScanner.next( LocalDateTime.class,"Inserire la data di nascita del cliente.", "Inserire una data nel formato: dd.mm.yyyy");
		Abbonamento.Tipo tipo = GenericScanner.next(Abbonamento.Tipo.class, "Inserire il tipo dell'abbonamento.\nDeve essere uno tra: " + Arrays.stream(Abbonamento.Tipo.values()).map(Object::toString).collect(Collectors.joining(", ")), "Inserire un tipo valido.");
		Abbonamento abbonamento = new Abbonamento(tipo, LocalDateTime.now());
		Disciplina disciplina = GenericScanner.next(Disciplina.class, "Inserire la disciplina.\nDeve essere uno tra: " + Arrays.stream(Disciplina.values()).map(Object::toString).collect(Collectors.joining(", ")), "Inserire una disciplina valida.");
		Cliente cliente;
		if (agonista) {
			cliente = new Agonista(nome, cognome, nascita, disciplina, centroSportivo.nextNumeroTessera(), abbonamento);
		} else {
			Amatoriale.Esperienza esperienza = GenericScanner.next(Amatoriale.Esperienza.class, "Inserire l'esperienza del cliente nella disciplina.\nDeve essere uno tra: " + Arrays.stream(Amatoriale.Esperienza.values()).map(Object::toString).collect(Collectors.joining(", ")), "Inserire unn livello vdi esperienza valido.");
			cliente = new Amatoriale(nome, cognome, nascita, disciplina, centroSportivo.nextNumeroTessera(), abbonamento, esperienza);
		}
		cliente.asave();
		centroSportivo.registraCliente(cliente);
		return true;
	}

	public static LocalDateTime parseDateTime(String s) {
		Integer[] tokens = Arrays.stream(s.split("\\.")).map(string -> GenericScanner.parse(string, int.class).orElseThrow(IllegalArgumentException::new)).toArray(Integer[]::new);
		return LocalDateTime.of(tokens[2], tokens[1], tokens[0], 0, 0);
	}

	public static void main(String[] args) {
		GenericScanner.registerParser(LocalDateTime.class, Main::parseDateTime);
		new Main().run();
	}

}