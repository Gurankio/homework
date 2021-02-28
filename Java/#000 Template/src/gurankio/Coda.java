package gurankio;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Coda<T> implements Iterable<T> {

    private static class Nodo<T> {

        T data;
        Nodo<T> next;
        Nodo<T> prev;

        public Nodo(T data) {
            this.data = data;
        }

        public long size() {
            return 1 + (next != null ? next.size() : 0);
        }

        @Override
        public String toString() {
            return data.toString() + (next != null ? " <- " + next.toString() : "");
        }
    }

    private Nodo<T> testa;
    private Nodo<T> coda;

    public Coda(T data) {
        this.testa = new Nodo<>(data);
        this.coda = this.testa;
    }

    public T pop() {
        T data = testa.data;
        testa = testa.next;
        return data;
    }

    public void push(T data) {
        Nodo<T> nodo = new Nodo<>(data);
        nodo.prev = coda;
        coda.next = nodo;
        coda = nodo;
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

            private Nodo<T> current = testa;

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

    public Iterator<T> reverseIterator() {
        return new Iterator<>() {

            private Nodo<T> current = coda;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T data = current.data;
                current = current.prev;
                return data;
            }
        };
    }


    public Stream<T> stream() {
        return StreamSupport.stream(this::spliterator, 0, false);
    }

}