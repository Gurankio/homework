package gurankio;

import java.lang.annotation.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
		/*
		// Dati di esempio.
		Random r = new Random();
		new Mail("A", "oggA", IntStream.generate(() -> 'a' + r.nextInt('z' - 'a')).limit(100).mapToObj(integer -> String.valueOf((char) integer)).collect(Collectors.joining()));
		new Mail("B", "oggB", IntStream.generate(() -> 'a' + r.nextInt('z' - 'a')).limit(100).mapToObj(integer -> String.valueOf((char) integer)).collect(Collectors.joining()));
		new Mail("C", "oggC", IntStream.generate(() -> 'a' + r.nextInt('z' - 'a')).limit(100).mapToObj(integer -> String.valueOf((char) integer)).collect(Collectors.joining()));
		new Mail("D", "tstD", IntStream.generate(() -> 'a' + r.nextInt('z' - 'a')).limit(100).mapToObj(integer -> String.valueOf((char) integer)).collect(Collectors.joining()));
		new Mail("E", "tstE", IntStream.generate(() -> 'a' + r.nextInt('z' - 'a')).limit(100).mapToObj(integer -> String.valueOf((char) integer)).collect(Collectors.joining()));
		new Mail("F", "asdF", IntStream.generate(() -> 'a' + r.nextInt('z' - 'a')).limit(100).mapToObj(integer -> String.valueOf((char) integer)).collect(Collectors.joining()));
		new Mail("G", "asdG", IntStream.generate(() -> 'a' + r.nextInt('z' - 'a')).limit(100).mapToObj(integer -> String.valueOf((char) integer)).collect(Collectors.joining()));
		 */

		Mailbox mailbox = new Mailbox();
		while (menu(mailbox, scanner)) {
			scanner.nextLine();
		}
	}

	private static boolean menu(Mailbox mailbox, Scanner scanner) throws IllegalAccessException, InvocationTargetException, InstantiationException {
		System.out.println();
		System.out.println(mailbox);
		System.out.println();
		System.out.println(" - Inserire un indice per visualizzare il contenuto di una mail.");
		System.out.println(" - ");
		System.out.println(" - ");
		System.out.println(" - ");
		System.out.println(" - ");

		HashMap<Class<?>, HashMap<?, Function<Mailbox, Boolean>>> actions = new HashMap<>();
		HashMap<String, Function<Mailbox, Boolean>> strings = new HashMap<>();
		actions.put(String.class, strings);
		HashMap<Integer, Function<Mailbox, Boolean>> ints = new HashMap<>();
		actions.put(int.class, ints);

		// intro();

		// space()
		//
		// space()
		strings.put("[asdrubale] Aggiungi Mail", Main::aggiungi);
		strings.put("[R]icerca Mail", Main::ricerca);
		strings.put("[E]limina Mail", Main::elimina);
		strings.put("[C]hiudi", Main::close);

		IntStream.range(0, mailbox.size()).forEach(i -> ints.put(i, (x) -> {
			try {
				System.out.println(Arrays.stream(mailbox.get(i).toString(true).split("\n")).map(mail -> "   " + mail).collect(Collectors.joining("\n")));
			} catch (IndexOutOfBoundsException e) {
				System.out.println(" ! Mail inesistente.");
			}
			return true;
		}));

		String in = input(null, scanner::nextLine);

		try {
			int i = Integer.parseInt(in);
			return actions.get(int.class).get(i).get();
		} catch (NumberFormatException e) {
			return actions.get(String.class).getOrDefault(in, () -> {
				System.out.println(" ! Opzione sconosciuta.");
				return true;
			}).get();
		}
	}

	private static boolean aggiungi(Mailbox mailbox) {
		mailbox.aggiungiMail(createGeneric(mailbox));
		System.out.println(" ! Mail aggiunta.");
		return true;
	}

	private static boolean elimina(Mailbox mailbox) {
		try {
			int i = Integer.parseInt(input(" - Inserire l'indice da eliminare: ", scanner::nextLine));
			mailbox.eliminaMail(i);
			System.out.println(" ! Mail eliminata.");
		} catch (NumberFormatException ignored) {
			System.out.println(" ! Numero non valido.");
		} catch (IndexOutOfBoundsException e) {
			System.out.println(" ! Mail inesistente.");
		}
		return true;
	}

	private static boolean ricerca(Mailbox mailbox) {
		System.out.println(mailbox
				.ricercaOggetto(input(" - Inserire il testo da ricercare: ", scanner::nextLine))
				.stream()
				.map(mail -> "   " + mail.toString())
				.collect(Collectors.joining("\n")));
		return true;
	}

	private static boolean close(Object any) {
		return false;
	}

	private static Mail createGeneric(Mailbox mailbox) {
		try {
			Class<?> type = Mail.class;

			Constructor<?> constructor = Arrays.stream(type.getConstructors())
					.filter(c -> Arrays.stream(c.getAnnotations())
							.anyMatch(a -> a.annotationType().equals(Menu.class)))
					.findFirst()
					.orElseThrow();

			String[] prompts = constructor.getAnnotation(Menu.class).prompts();
			Parameter[] parameters = constructor.getParameters();

			assert prompts.length == parameters.length;
			Object[] values = IntStream.range(0, parameters.length)
					.mapToObj(i -> input(prompts[i], supplier(parameters[i].getType())))
					.toArray();

			return (Mail) constructor.newInstance(values);
		} catch (Exception e) {
			// Die.
			return null;
		}
	}

	private static Supplier<?> supplier(Class<?> type) {
		List<Class<?>> types = List.of(String.class, int.class, Integer.class);
		List<Supplier<?>> suppliers = List.of(scanner::nextLine, scanner::nextInt, scanner::nextInt);
		return suppliers.get(types.indexOf(type));
	}

	private static <T> T input(String prompt, Supplier<T> supplier) {
		if (prompt != null) System.out.println(prompt);
		System.out.print(" -> ");
		return supplier.get();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.CONSTRUCTOR)
	@interface Menu {
		String[] prompts();
	}

}