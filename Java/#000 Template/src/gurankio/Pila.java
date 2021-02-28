package gurankio;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Pila<T> implements Iterable<T> {

    private static class Nodo<T> {

        T data;
        Nodo<T> next;

        public Nodo(T data) {
            this.data = data;
        }

        public long size() {
            return 1 + (next != null ? next.size() : 0);
        }

        @Override
        public String toString() {
            return data.toString() + (next != null ? " -> " + next.toString() : "");
        }
    }

    private Nodo<T> testa;

    public Pila(T data) {
        this.testa = new Pila.Nodo<>(data);
    }

    public T pop() {
        T data = testa.data;
        testa = testa.next;
        return data;
    }

    public void push(T data) {
        Pila.Nodo<T> nodo = new Pila.Nodo<>(data);
        nodo.next = testa;
        testa = nodo;
    }

    public long size() {
        return testa.size();
    }

    @Override
    public String toString() {
        return testa.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {

            private Pila.Nodo<T> current = testa;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
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