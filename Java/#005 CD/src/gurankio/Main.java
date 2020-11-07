package gurankio;

public class Main {

	public static void main(String[] args) {
		CD cd1 = new CD("Hello,", "World!", 6, 1200);
		System.out.println(cd1);

		CD cd2 = new CD("AAAA", "BBBB", 612013, 1);
		System.out.println(cd2);

		System.out.println("Compare: " + cd1.compareDurata(cd2));
	}

}
