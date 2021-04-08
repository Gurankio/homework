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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

	public static final List<Treno> treni = Persistent.stream(Treno.class).collect(Collectors.toCollection(LinkedList::new));
	public static Treno selectedTreno;
	public static final List<Deposito> depositi = Persistent.stream(Deposito.class).collect(Collectors.toCollection(LinkedList::new));
	public static Deposito selectedDeposito;

	public static void main(String[] args) {
		DynamicMenu deposito = new DynamicMenu() {
			@Override
			protected void setup() {
				message(selectedDeposito::toString);
				message(() -> "Carico Totale: " + selectedDeposito.computeCaricoTotale());
				message(() -> "Carico Totale Refrigerato: " + selectedDeposito.computeCaricoTotaleRefrigerato());
				message(this::listContainer, " + ");
				single("[A]ggiungi Container", char.class, c -> {
					boolean refrigerato = GenericScanner.next(boolean.class, "Container Refrigerato?", "Inserire 'true' o 'false'.");
					Container container;
					if (refrigerato) container = GenericScanner.next(Refrigerato.class, "Dati del container: ", "Dati invalidi.");
					else container = GenericScanner.next(Container.class, "Dati del container: ", "Dati invalidi.");
					container.asave();
					String postazione = GenericScanner.next(String.class, "Inserire la postazione del container.", s -> !selectedDeposito.getContainersMap().containsKey(s), "Postazione gia esistente o invalida.");
					selectedDeposito.addContainer(postazione, container);
					Logger.INFO.println();
					Logger.INFO.println(container);
					Logger.INFO.println("Container aggiunto!");
					return true;
				});
				single("[R]imuovi Container", char.class, c -> {
					String postazione = GenericScanner.next(String.class, "Inserire la postazione del container.", s -> selectedDeposito.getContainersMap().containsKey(s), "Postazione inesistente o invalida.");
					selectedDeposito.removeContainer(postazione).getFile().deleteOnExit();
					Logger.INFO.println("Container rimosso!");
					return true;
				});
				single("[C]erca Container", char.class, c -> {
					if (selectedDeposito.getContainersMap().size() == 0) {
						Logger.WARN.println("Deposito vuoto.");
						return true;
					}
					String postazione = GenericScanner.next(String.class, "Inserire la postazione del container.", s -> selectedDeposito.getContainersMap().containsKey(s), "Postazione inesistente o invalida.");
					Logger.INFO.println(selectedDeposito.getContainer(postazione));
					return true;
				});
				if (selectedDeposito instanceof Stazione) {
					single("[TC] Carica Treno", String.class, c -> {
						Logger.DIRECT.println();
						Logger.DIRECT.print(Logger.wrap(listTreni(), " + "));
						int index = GenericScanner.nextRange(int.class, "Inserire il treno da cui caricare container.", 0, treni.size());
						Treno treno = treni.get(index);
						List<Integer> codiciDeposito = selectedDeposito.getContainersMap().values().stream().map(Container::getCodice).collect(Collectors.toUnmodifiableList());
						if (codiciDeposito.size() == 0) {
							Logger.WARN.println("Deposito vuoto.");
							return true;
						}
						Logger.DIRECT.println();
						String codiciDepositoString = "Lista dei codici nella stazione:\n" + codiciDeposito.stream().map(Objects::toString).collect(Collectors.joining("\n"));
						Logger.DIRECT.println(Logger.wrap(codiciDepositoString, " + "));
						int[] codici = GenericScanner.next(int[].class, "Inserire i codici da caricare, separati da virgola.", ints -> Arrays.stream(ints).allMatch(codiciDeposito::contains), "Codice/i non validi o inesistenti.");
						Stazione stazione = (Stazione) selectedDeposito;
						stazione.caricaTreno(treno, codici);
						Logger.INFO.println("Container caricati!");
						return true;
					});
					single("[TS] Scarica Treno", String.class, c -> {
						Logger.DIRECT.println();
						Logger.DIRECT.print(Logger.wrap(listTreni(), " + "));
						int index = GenericScanner.nextRange(int.class, "Inserire il treno da cui scaricare container.", 0, treni.size());
						Treno treno = treni.get(index);
						List<Integer> codiciTreno = treno.getContainers().stream().map(Container::getCodice).collect(Collectors.toUnmodifiableList());
						if (codiciTreno.size() == 0) {
							Logger.WARN.println("Treno vuoto.");
							return true;
						}
						Logger.DIRECT.println();
						String codiceTrenoString = "Lista dei codici nel treno:\n" + codiciTreno.stream().map(Objects::toString).collect(Collectors.joining("\n"));
						Logger.DIRECT.println(Logger.wrap(codiceTrenoString, " + "));
						int[] codici = GenericScanner.next(int[].class, "Inserire i codici da scaricare, separati da virgola.", ints -> Arrays.stream(ints).allMatch(codiciTreno::contains), "Codice/i non validi o inesistenti.");
						Stazione stazione = (Stazione) selectedDeposito;
						stazione.scaricaTreno(treno, codici);
						Logger.INFO.println("Container scaricati!");
						return true;
					});
				}
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
				return builder.toString();
			}
		};

		new DynamicMenu() {

			Pattern trenoPattern = Pattern.compile("T(\\d+)");
			Pattern depositoPattern = Pattern.compile("[D|S](\\d+)");

			@Override
			protected void setup() {
				title("Container");
				space();
				multiple(this::listTreniDepositi, String.class, (s) -> {
					Matcher trenoMatcher = trenoPattern.matcher(s);
					if (trenoMatcher.matches()) {
						selectedTreno = treni.get(Integer.parseInt(trenoMatcher.group(1)));
						Logger.INFO.println();
						Logger.INFO.println(selectedTreno);
						Logger.INFO.println(Logger.wrap(listContainer(), " + "));
					}
					Matcher depositoMatcher = depositoPattern.matcher(s);
					if (depositoMatcher.matches()) {
						selectedDeposito = depositi.get(Integer.parseInt(depositoMatcher.group(1)));
						deposito.run();
					}
					return true;
				});
				single("Aggiungi [T]reno", char.class, (s) -> {
					Treno t = new Treno();
					t.asave();
					treni.add(t);
					Logger.INFO.println("Treno aggiunto!");
					return true;
				});
				single("Aggiungi [D]eposito", char.class, (s) -> {
					Deposito d = new Deposito();
					d.asave();
					depositi.add(d);
					Logger.INFO.println("Deposito aggiunto!");
					return true;
				});
				single("Aggiungi [S]tazione", char.class, (s) -> {
					Deposito d = new Stazione();
					d.asave();
					depositi.add(d);
					Logger.INFO.println("Stazione aggiunta!");
					return true;
				});
				close("[E]sci", String.class);
			}

			private String listContainer() {
				StringBuilder builder = new StringBuilder();
				builder.append("Containers:\n");
				selectedTreno.getContainers().forEach(container -> builder.append(container).append("\n"));
				return builder.toString();
			}

			private String listTreniDepositi() {
				StringBuilder builder = new StringBuilder();
				if (treni.size() != 0) builder.append("Treni:\n");
				IntStream.range(0, treni.size()).forEach(i -> builder.append("[T").append(i).append("] ").append(treni.get(i)).append("\n"));
				if (depositi.size() != 0) builder.append("Depositi:\n");
				IntStream.range(0, depositi.size()).forEach(i -> builder.append("[").append(depositi.get(i) instanceof Stazione ? "S" : "D").append(i).append("] ").append(depositi.get(i)).append("\n"));
				return builder.toString();
			}
		}.run();
	}
}
