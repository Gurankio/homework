package gurankio.terminal;

import gurankio.container.Container;
import gurankio.container.Refrigerato;
import gurankio.util.Persistent;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Deposito extends Persistent {

    private Map<String, Container> containersMap;

    public Deposito() {
        this.containersMap = new HashMap<>();
    }

    public Deposito(File file) {
        super(file);
    }

    public Map<String, Container> getContainersMap() {
        return containersMap;
    }

    public Container getContainer(String postazione) {
        return containersMap.getOrDefault(postazione, null);
    }

    public String findContainer(int codice) {
        return containersMap.entrySet().stream().filter(c -> c.getValue().getCodice() == codice).map(Map.Entry::getKey).findFirst().orElse(null);
    }

    public Container removeContainer(String postazione) {
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

    public void setContainersMap(Map<String, Container> containersMap) {
        this.containersMap = containersMap;
    }

    public void addContainer(Container container) {
        String postazione = IntStream.generate(() -> 'A' + new Random().nextInt('Z' - 'A')).limit(6).mapToObj(c -> String.valueOf((char) c)).collect(Collectors.joining());
        addContainer(postazione, container);
    }

    public void addContainer(String postazione, Container container) {
        containersMap.putIfAbsent(postazione, container);
        asave();
    }

    @Override
    public String toString() {
        return String.format("Deposito con %d container.", containersMap.size());
    }
}
