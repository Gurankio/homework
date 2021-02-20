package gurankio;

import gurankio.io.file.FileInterface;
import gurankio.io.file.TextFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {

	public static class Qwerty {
		private transient int a;
		private Double b;
		private String[] cs;

		public Qwerty() {
		}

		public Qwerty(int a, Double b, String[] cs) {
			this.a = a;
			this.b = b;
			this.cs = cs;
		}

		@Override
		public String toString() {
			return "Qwerty{" +
					"a=" + a +
					", b=" + b +
					", cs=" + Arrays.toString(cs) +
					'}';
		}
	}

	public static class Asdf {
		private transient int a;
		private Double b;
		private Qwerty[] cs;

		public Asdf() {
		}

		public Asdf(int a, Double b, Qwerty[] cs) {
			this.a = a;
			this.b = b;
			this.cs = cs;
		}

		@Override
		public String toString() {
			return "Asdf{" +
					"a=" + a +
					", b=" + b +
					", cs=" + Arrays.toString(cs) +
					'}';
		}
	}

	public static class Test {
		private int a;
		private double b;
		private String c;
		private double[] ds;
		private Asdf[] es;

		public Test() {
		}

		public Test(int a, double b, String c, double[] ds, Asdf[] es) {
			this.a = a;
			this.b = b;
			this.c = c;
			this.ds = ds;
			this.es = es;
		}

		@Override
		public String toString() {
			return "Test{" +
					"a=" + a +
					", b=" + b +
					", c='" + c + '\'' +
					", ds=" + Arrays.toString(ds) +
					", es=" + Arrays.toString(es) +
					'}';
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("test.txt");
		if (file.exists()) file.delete();
		FileInterface fileInterface = new TextFile();
		fileInterface.save(new Test(1, 12.34, "sasso", new double[]{4.1, 23.5, 34.53, 765425.2}, new Asdf[]{
				new Asdf(1, 2.3, new Qwerty[]{
						new Qwerty(4, 4.5, new String[]{"a", "b", "c", "d"}),
						new Qwerty(54, 4.5, new String[]{"a", "b", "c", "d"})
				}),
				new Asdf(2, 4363.3, new Qwerty[]{
						new Qwerty(4, 4.5, new String[]{"e", "f", "g", "h"}),
						new Qwerty(54, 4.5, new String[]{"a", "b", "c", "d"})
				}),
				new Asdf(3, 0.00000000024, new Qwerty[]{
						new Qwerty(4, 4.5, new String[]{"i", "l", "m", "n"}),
						new Qwerty(54, 4.5, new String[]{"a", "b", "c", "d"})
				})
		}), file);
		Test test = fileInterface.load(file, Test.class);
		System.out.println(test);
	}

}
