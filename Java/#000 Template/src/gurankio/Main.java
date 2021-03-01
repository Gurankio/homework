package gurankio;

import gurankio.io.file.Persistent;

import java.io.FileNotFoundException;
import java.util.Objects;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Pila<Integer> pila = new Pila<>(0);
		pila.push(1);
		pila.push(2);
		pila.push(3);
		pila.push(4);
		pila.push(5);
		pila.push(6);

		for (Integer x : pila) {
			System.out.println(x);
		}

		System.out.println(pila.toString());
		System.out.println(pila.size());
		pila.pop();
		System.out.println(pila);

		System.out.println(pila.stream().reduce(Integer::sum).orElse(0));
	}

	private static class TestA implements Persistent {

		private int a;
		private int b;
		private String c;
		private TestA testA;
		private TestB[] testBS;

		public TestA(int a, int b, String c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof TestA)) return false;
			TestA testA = (TestA) o;
			return a == testA.a && b == testA.b && Objects.equals(c, testA.c);
		}

		@Override
		public int hashCode() {
			return Objects.hash(a, b, c);
		}
	}

	private static class TestB implements Persistent {

		private int a;
		private int b;
		private String c;
		private TestC testC;

		public TestB(int a, int b, String c, TestC testC) {
			this.a = a;
			this.b = b;
			this.c = c;
			this.testC = testC;
		}

	}

	private static class TestC implements Persistent {

		private int a;
		private int b;
		private String c;

		public TestC(int a, int b, String c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}
	}

}