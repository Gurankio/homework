package gurankio;

import gurankio.container.Container;
import gurankio.container.Refrigerato;
import gurankio.terminal.Deposito;
import gurankio.terminal.Stazione;
import gurankio.terminal.Treno;
import gurankio.util.DynamicMenu;
import gurankio.util.GenericScanner;
import gurankio.util.Logger;
import gurankio.util.Persistent;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

	public static final List<Treno> treni = Persistent.stream(Treno.class).collect(Collectors.toCollection(LinkedList::new));
	public static final List<Deposito> depositi = Persistent.stream(Deposito.class).collect(Collectors.toCollection(LinkedList::new));

	public static void main(String[] args) {
		new DynamicMenu() {

			@Override
			protected void setup() {
				title("Container");
				space();
				message("Treni:");
				options(() -> {
					StringBuilder builder = new StringBuilder();
					IntStream.range(0, treni.size()).forEach(i -> builder.append("[T").append(i).append("] ").append(treni.get(i)).append("\n"));
					return builder.toString();
				}, String.class, (s) -> {
					    Treno selectedTreno = treni.get(Integer.parseInt(s.substring(1)));
						println();
						println(selectedTreno);
						println("Containers:");
						StringBuilder builder = new StringBuilder();
						selectedTreno.getContainers().forEach(container -> builder.append(container).append("\n"));
						wrapln(builder.toString(), " + ");
						return true;
				});
				message("Depositi:");

				sub(() -> {
					StringBuilder builder = new StringBuilder();
					IntStream.range(0, depositi.size()).forEach(i -> builder.append("[").append(depositi.get(i) instanceof Stazione ? "S" : "D").append(i).append("] ").append(depositi.get(i)).append("\n"));
					return builder.toString();
				}, String.class, (s) -> new DynamicMenu() {
					private Deposito selectedDeposito;

					@Override
					protected void setup() {
						selectedDeposito = depositi.get(Integer.parseInt(s.substring(1)));
						message(selectedDeposito::toString);
						message("Carico Totale: " + selectedDeposito.computeCaricoTotale());
						message("Carico Totale Refrigerato: " + selectedDeposito.computeCaricoTotaleRefrigerato());
						message(this::listContainer);
						option("[A]ggiungi Container", char.class, c -> {
							boolean refrigerato = GenericScanner.next(boolean.class, "Container Refrigerato?", "Inserire 'true' o 'false'.");
							Container container;
							if (refrigerato) container = GenericScanner.next(Refrigerato.class, "Dati del container: ", "Dati invalidi.");
							else container = GenericScanner.next(Container.class, "Dati del container: ", "Dati invalidi.");
							container.asave();
							Integer postazione = GenericScanner.next(Integer.class, "Inserire la postazione del container.", s -> !selectedDeposito.getContainersMap().containsKey(s), "Postazione gia esistente o invalida.");
							selectedDeposito.addContainer(postazione, container);
							println();
							println(container);
							println("Container aggiunto!");
							return true;
						});
						option("[R]imuovi Container", char.class, c -> {
							Integer postazione = GenericScanner.next(Integer.class, "Inserire la postazione del container.", s -> selectedDeposito.getContainersMap().containsKey(s), "Postazione inesistente o invalida.");
							selectedDeposito.removeContainer(postazione).getFile().deleteOnExit();
							println("Container rimosso!");
							return true;
						});
						option("[C]erca Container", char.class, c -> {
							if (selectedDeposito.getContainersMap().size() == 0) {
								warnln("Deposito vuoto.");
								return true;
							}
							Integer postazione = GenericScanner.next(Integer.class, "Inserire la postazione del container.", s -> selectedDeposito.getContainersMap().containsKey(s), "Postazione inesistente o invalida.");
							println(selectedDeposito.getContainer(postazione));
							return true;
						});
						if (selectedDeposito instanceof Stazione) {
							space();
							message("Opzioni stazione: ");
							option("[C]arica [T]reno", String.class, c -> {
								println();
								wrapln(listTreni(), " + ");
								int index = GenericScanner.nextRange(int.class, "Inserire il treno da cui caricare container.", 0, treni.size());
								Treno treno = treni.get(index);
								List<Integer> codiciDeposito = selectedDeposito.getContainersMap().values().stream().map(Container::getCodice).collect(Collectors.toUnmodifiableList());
								if (codiciDeposito.size() == 0) {
									warnln("Deposito vuoto.");
									return true;
								}
								println();
								String codiciDepositoString = "Lista dei codici nella stazione:\n" + codiciDeposito.stream().map(Objects::toString).collect(Collectors.joining("\n"));
								wrapln(codiciDepositoString, " + ");
								int[] codici = GenericScanner.next(int[].class, "Inserire i codici da caricare, separati da virgola.", ints -> Arrays.stream(ints).allMatch(codiciDeposito::contains), "Codice/i non validi o inesistenti.");
								Stazione stazione = (Stazione) selectedDeposito;
								stazione.caricaTreno(treno, codici);
								println("Container caricati!");
								return true;
							});
							option("[S]carica [T]reno", String.class, c -> {
								println();
								wrapln(listTreni(), " + ");
								int index = GenericScanner.nextRange(int.class, "Inserire il treno da cui scaricare container.", 0, treni.size());
								Treno treno = treni.get(index);
								List<Integer> codiciTreno = treno.getContainers().stream().map(Container::getCodice).collect(Collectors.toUnmodifiableList());
								if (codiciTreno.size() == 0) {
									warnln("Treno vuoto.");
									return true;
								}
								println();
								String codiceTrenoString = "Lista dei codici nel treno:\n" + codiciTreno.stream().map(Objects::toString).collect(Collectors.joining("\n"));
								println(Logger.wrap(codiceTrenoString, " + "));
								int[] codici = GenericScanner.next(int[].class, "Inserire i codici da scaricare, separati da virgola.", ints -> Arrays.stream(ints).allMatch(codiciTreno::contains), "Codice/i non validi o inesistenti.");
								Stazione stazione = (Stazione) selectedDeposito;
								stazione.scaricaTreno(treno, codici);
								println("Container scaricati!");
								return true;
							});
						}
						space();
						close("[E]sci", char.class);
					}

					private String listContainer() {
						StringBuilder builder = new StringBuilder();
						if (selectedDeposito.getContainersMap().size() != 0) builder.append("Containers:\n");
						selectedDeposito.getContainersMap().forEach((piazzamento, container) -> builder.append("'").append(piazzamento).append("' -> ").append(container).append("\n"));
						return builder.toString();
					}

					private String listTreni() {
						StringBuilder builder = new StringBuilder();
						if (treni.size() != 0) builder.append("Treni:\n");
						IntStream.range(0, treni.size()).forEach(i -> builder.append("[").append(i).append("] ").append(treni.get(i)).append("\n"));
						return builder.deleteCharAt(builder.length()-1).toString();
					}
				});

				option("Aggiungi [T]reno", char.class, (s) -> {
					Treno t = new Treno();
					t.asave();
					treni.add(t);
					println("Treno aggiunto!");
					return true;
				});
				option("Aggiungi [D]eposito", char.class, (s) -> {
					Deposito d = new Deposito();
					d.asave();
					depositi.add(d);
					println("Deposito aggiunto!");
					return true;
				});
				option("Aggiungi [S]tazione", char.class, (s) -> {
					Deposito d = new Stazione();
					d.asave();
					depositi.add(d);
					println("Stazione aggiunta!");
					return true;
				});
				close("[E]sci", String.class);
			}
		}.run();
	}
}
