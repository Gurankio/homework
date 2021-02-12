package gurankio;

import gurankio.io.file.XmlFile;

import java.io.File;

public class Main {

	public static class A {

		private int b = 2;
		private transient int c = 3;

		public A() {
		}

		public A(int b, int c) {
			this.b = b;
			this.c = c;
		}

		public int getB() {
			return b;
		}

		public void setB(int b) {
			this.b = b;
		}

		public int getC() {
			return c;
		}

		public void setC(int c) {
			this.c = c;
		}
	}

	public static void main(String[] args) {
		new XmlFile().save(new A(1, 2), new File("./test.xml"));
	}

}
