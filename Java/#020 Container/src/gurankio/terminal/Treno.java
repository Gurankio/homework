package gurankio.terminal;

import gurankio.container.Container;
import gurankio.util.Persistent;

import java.io.File;
import java.util.LinkedList;
import java.util.Optional;

public class Treno extends Persistent {

    private LinkedList<Container> containers;

    public Treno() {
        containers = new LinkedList<>();
    }

    public Treno(File file) {
        super(file);
    }

    public LinkedList<Container> getContainers() {
        return containers;
    }

    public Container scaricaContainer(int codice) {
        Optional<Container> container = containers.stream().filter(c -> c.getCodice() == codice).findFirst();
        if (container.isPresent()) {
            containers.remove(container.get());
            asave();
            return container.get();
        }
        return null;
    }

    public void setContainers(LinkedList<Container> containers) {
        this.containers = containers;
    }

    public void caricaContainer(Container container) {
        containers.add(container);
        asave();
    }

    @Override
    public String toString() {
        return String.format("Treno con %d container.", containers.size());
    }
}
