package gurankio.terminal;

import gurankio.container.Container;
import gurankio.container.Refrigerato;
import gurankio.util.Persistent;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Deposito extends Persistent {

    private Map<Integer, Container> containersMap;

    public Deposito() {
        this.containersMap = new HashMap<>();
    }

    public Deposito(File file) {
        super(file);
    }

    public Map<Integer, Container> getContainersMap() {
        return containersMap;
    }

    public Container getContainer(Integer postazione) {
        return containersMap.getOrDefault(postazione, null);
    }

    public Integer findContainer(int codice) {
        return containersMap.entrySet().stream().filter(c -> c.getValue().getCodice() == codice).map(Map.Entry::getKey).findFirst().orElse(null);
    }

    public Container removeContainer(Integer postazione) {
        Container container = containersMap.remove(postazione);
        asave();
        return container;
    }

    public double computeCaricoTotale() {
        return containersMap.values().stream().mapToDouble(Container::getCarico).sum();
    }

    public double computeCaricoTotaleRefrigerato() {
        return containersMap.values().stream().filter(c -> c instanceof Refrigerato).mapToDouble(Container::getCarico).sum();
    }

    public void setContainersMap(Map<Integer, Container> containersMap) {
        this.containersMap = containersMap;
    }

    public void addContainer(Container container) {
        addContainer(containersMap.keySet().stream().max(Integer::compareTo).orElse(0) + 1, container);
    }

    public void addContainer(Integer postazione, Container container) {
        containersMap.putIfAbsent(postazione, container);
        asave();
    }

    @Override
    public String toString() {
        return String.format("Deposito con %d container.", containersMap.size());
    }
}
