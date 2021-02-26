package gurankio;

import gurankio.io.file.FileInterface;
import gurankio.io.file.TextFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

	}

	private static class Pila<T> implements Iterable<T> {

		private static class Nodo<T> {

			T data;
			Nodo<T> next;

			public Nodo(T data) {
				this.data = data;
			}

		}

		private Nodo<T> testa;

		public Pila(T data) {
			this.testa = new Nodo<>(data);
		}

		public T pop() {
			T data = testa.data;
			testa = testa.next;
			return data;
		}

		public void push(T data) {
			testa.next = new Nodo<>(data);
			testa = testa.next;
		}

		public long length() {
			long l = 0;
			for (T e : this) l++;
			return l;
		}

		@Override
		public Iterator<T> iterator() {
			return new Iterator<>() {

				private Nodo<T> current = testa;

				@Override
				public boolean hasNext() {
					return current.next != null;
				}

				@Override
				public T next() {
					T data = current.data;
					current = current.next;
					return data;
				}
			};
		}

		public Stream<T> stream() {
			return StreamSupport.stream(this::spliterator, 0, false);
		}

	}

}
