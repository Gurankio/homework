package gurankio;

import gurankio.util.DynamicMenu;
import gurankio.util.PersistentCSV;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {

		ArrayList<A> as = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			as.add(new B(i, i*1.62197489127));
		}
		for (int i = 0; i < 20; i++) {
			as.add(new C(i, "asd"));
		}
		System.out.println("Saved");
		System.out.println(as);
		PersistentCSV.save(new File("test.txt"), as.stream());
		System.out.println("Loaded");
		System.out.println(PersistentCSV.load(new File("test.txt")).collect(Collectors.toList()));

		Class<?> c = DynamicMenu.choose("Tipo di A?", List.of("[B]", "[C]"), String.class, List.of(B.class, C.class));
		System.out.println(c);
	}

}