package gurankio;

import gurankio.util.Persistent;

import java.util.List;
import java.util.stream.Collectors;

public class CentroSportivo {

    private final List<Cliente> clienti;

    public CentroSportivo() {
        clienti = Persistent.stream(Cliente.class).collect(Collectors.toList());
    }

    public int nextNumeroTessera() {
        return clienti.stream().mapToInt(Cliente::getNumeroTessera).max().orElse(0) + 1;
    }

    public void registraCliente(Cliente cliente) {
        clienti.add(cliente);
    }

    public Cliente getCliente(int numeroTessera) {
        return clienti.stream()
                .filter(cliente -> cliente.getNumeroTessera() == numeroTessera)
                .findFirst()
                .orElse(null);
    }

    public void cancellaCliente(Cliente cliente) {
        clienti.remove(cliente);
        cliente.getFile().deleteOnExit();
    }

    @Override
    public String toString() {
        return clienti.stream()
                .map(cliente -> String.format("[%02d] Cliente %s '%s %s', abbonamento %s nella disciplina %s a %.2f€", cliente.getNumeroTessera(), cliente.getClass().getSimpleName(), cliente.getNome(), cliente.getCognome(), cliente.getAbbonamento().getTipo(), cliente.getDisciplina(), cliente.calcolaTassa()))
                .collect(Collectors.joining("\n"));
    }
}
