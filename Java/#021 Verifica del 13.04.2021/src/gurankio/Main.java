package gurankio;

import gurankio.util.DynamicMenu;
import gurankio.util.GenericScanner;
import gurankio.util.PersistentCSV;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

	/* Testo dell'esercizio.
	 * Un cinema multisala deve gestire, con un sistema informatico,
	 *   i propri clienti abbonati suddivisi per tipo (adulti, minori)
	 * Per ogni cliente devono essere memorizzate le seguenti informazioni:
	 *    nome;
	 *    cognome;
	 *    data di nascita;
	 *    numero della tessera;
	 *    data di scadenza dell’abbonamento.
	 * Per gli adulti è inoltre necessario memorizzare la categoria di appartenenza in base al sesso (uomo, donna).
	 * Per i clienti minori deve essere memorizzata la categoria di appartenenza in base all’età (bambino, ragazzo, teenager).
	 * Tutti i clienti devono pagare una tassa fissa (modificabile nel tempo),
	 *   che hanno la possibilità di scegliere tra diverse tipologie di abbonamento
	 *   (mensile 20,00€, trimestrale 50,00€, annuale 300,00€)
	 *   e che tutti i clienti usufruiscono di alcuni sconti sul costo dell’abbonamento:
	 *    adulti: uomo 25% di sconto, donna 30% di sconto;
	 *    minori: bambini 65% di sconto, ragazzi 45% di sconto, teenager 35% di sconto;
	 */


	private static final List<Abbonamento> abbonamenti = PersistentCSV.loadClass("database.txt", Abbonamento.class).collect(Collectors.toList());

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	public static void main(String[] args) {
		new DynamicMenu() {
			@Override
			protected void hello() {
				super.hello();
			}

			@Override
			protected void setup() {
				title("Abbonamenti");
				space();
				message("Abbonamenti: ");
				// Stampo tutti gli abbonamenti a schermo e aggiungo le funzioni di rinnovo e eliminazione.
				sub(() -> {
						StringBuilder builder = new StringBuilder();
						abbonamenti.sort(Comparator.comparing(Abbonamento::isScaduto));
						IntStream.range(0, abbonamenti.size()).forEach(i -> builder.append("[").append(i).append("] ").append(String.format("Abbonamento numero %02d, %s %s, cliente '%s %s'", abbonamenti.get(i).getNumeroTessera(), abbonamenti.get(i).getTipologia(), abbonamenti.get(i).isScaduto() ? "SCADUTO" : "VALIDO", abbonamenti.get(i).getCliente().getNome(), abbonamenti.get(i).getCliente().getCognome())).append("\n"));
						return builder.toString();
					}, int.class, (i) -> new DynamicMenu() {
					private Abbonamento abbonamento = abbonamenti.get(i);

					@Override
					protected void hello() {
					}

					@Override
					protected void setup() {
						message(() -> String.format("Abbonamento numero %02d, %s %s, creato il %s", abbonamento.getNumeroTessera(), abbonamento.getTipologia(), abbonamenti.get(i).isScaduto() ? "SCADUTO" : "VALIDO", abbonamento.getDataCreazione().format(Main.DATE_TIME_FORMATTER)));
						message(() -> String.format("Costo: %.2f, Sconto gia applicato: %.0f%%", Abbonamento.calcolaTassa(abbonamento), abbonamento.getCliente().getSconto() * 100));
						message(() -> String.format("Cliente '%s %s' nato il %s", abbonamento.getCliente().getNome(), abbonamento.getCliente().getCognome(), abbonamento.getCliente().getDataNascita().format(Main.DATE_TIME_FORMATTER)));
						space();
						if (abbonamento.isScaduto()) {
							sub("[R]innova Abbonamento", String.class, s -> new DynamicMenu() {
								@Override
								protected void hello() {
								}

								@Override
								protected void setup() {
									println(String.format("Ci sono 3 tipologie di abbonamento. I prezzi sono gia scontati del %.0f%%", abbonamento.getCliente().getSconto() * 100));
									option(() -> String.format("[1] MENSILE: %.2f€", Abbonamento.calcolaTassa(Abbonamento.Tipologia.MENSILE, abbonamento.getCliente())), int.class, (i) -> {
										abbonamento.setTipologia(Abbonamento.Tipologia.MENSILE);
										abbonamento.setDataCreazione(LocalDate.now());
										return false;
									});
									option(() -> String.format("[3] TRIMESTRALE: %.2f€", Abbonamento.calcolaTassa(Abbonamento.Tipologia.TRIMESTRALE, abbonamento.getCliente())), int.class, (i) -> {
										abbonamento.setTipologia(Abbonamento.Tipologia.TRIMESTRALE);
										abbonamento.setDataCreazione(LocalDate.now());
										return false;
									});
									option(() -> String.format("[12] ANNUALE: %.2f€", Abbonamento.calcolaTassa(Abbonamento.Tipologia.ANNUALE, abbonamento.getCliente())), int.class, (i) -> {
										abbonamento.setTipologia(Abbonamento.Tipologia.ANNUALE);
										abbonamento.setDataCreazione(LocalDate.now());
										return false;
									});
								}
							});
						}
						option("[C]ancella Abbonamento", String.class, s -> {
							String input = GenericScanner.next("Sei sicuro? [s/N]");
							if (input.toLowerCase(Locale.ROOT).startsWith("s")) {
								println("Abbonamento cancellato.");
								abbonamenti.remove(abbonamento);
							} else {
								println("Operazione annullata.");
							}
							return true;
						});
						close("[E]sci", String.class);
					}
				});
				option("[A]ggiungi Abbonamento", String.class, s -> {
					String nome = GenericScanner.next("Inserire il nome del cliente.");
					String cognome = GenericScanner.next("Inserire il cognome del cliente.");
					String input = GenericScanner.next("Inserire la data di nascita del cliente. (Formato: dd.MM.yyyy)");
					LocalDate nascita = LocalDate.parse(input, Main.DATE_TIME_FORMATTER);
					Cliente cliente;
					if (nascita.plusYears(18).isBefore(LocalDate.now())) {
						Adulto.Sesso sesso = GenericScanner.next(Adulto.Sesso.class, "Inserire il sesso del cliente. (UOMO, DONNA)", "Sesso non valido.");
						cliente = new Adulto(nome, cognome, nascita, sesso);
					} else {
						Minore.Categoria categoria = GenericScanner.next(Minore.Categoria.class, "Inserire la categoria del cliente. (BAMBINO, RAGAZZO, TEENAGER)", "Categoria non valida.");
						cliente = new Minore(nome, cognome, nascita, categoria);
					}
					int numeroTessera = GenericScanner.next(int.class, "Inserire il numero della tessera.", integer -> !abbonamenti.stream().map(Abbonamento::getNumeroTessera).collect(Collectors.toList()).contains(integer), "Numero di tessera invalido o gia esistente.");
					println(String.format("Ci sono 3 tipologie di abbonamento. I prezzi sono gia scontati del %.0f%%", cliente.getSconto() * 100));
					println(String.format("MENSILE: %.2f€", Abbonamento.calcolaTassa(Abbonamento.Tipologia.MENSILE, cliente)));
					println(String.format("TRIMESTRALE: %.2f€", Abbonamento.calcolaTassa(Abbonamento.Tipologia.TRIMESTRALE, cliente)));
					println(String.format("ANNUALE: %.2f€", Abbonamento.calcolaTassa(Abbonamento.Tipologia.ANNUALE, cliente)));
					String tipologia = GenericScanner.next(String.class, "Inserire la tipologia di abbonamento.", string -> Arrays.stream(Abbonamento.Tipologia.values()).map(Object::toString).collect(Collectors.toList()).contains(string), "Tipologia non valida.");
					abbonamenti.add(new Abbonamento(numeroTessera, LocalDate.now(), Abbonamento.Tipologia.valueOf(tipologia), cliente));
					return true;
				});
				option("[S]alva su file", String.class, s -> {
					try {
						PersistentCSV.save("database.txt", abbonamenti.stream());
						println("Salvataggio avvenuto con successo.");
					} catch (IOException e) {
						println("Salvataggio fallito.");
					}
					return true;
				});
				close("[E]sci", String.class);
			}
		}.run();
	}

}