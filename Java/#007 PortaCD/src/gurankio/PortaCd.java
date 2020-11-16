package gurankio;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class PortaCd {

    private final int N;
    private Cd[] cds;

    public PortaCd(int n) {
        N = n;
        cds = new Cd[N];
    }

    public Cd getCd(int index) {
        if (index < 0 || index >= N) return null;
        return cds[index];
    }

    public boolean setCd(int index, Cd cd) {
        if (index < 0 || index >= N) return false;
        cds[index] = cd;
        return true;
    }

    public boolean killCd(int index) {
        if (index < 0 || index >= N) return false;
        cds[index] = null;
        return true;
    }

    public int getN() {
        return N;
    }

    public int cercaCdPerTitolo(String titolo) {
        for (int i=0; i<N; i++) if (cds[i].getTitolo().equals(titolo)) return i;
        return -1;
    }

    public int confrontaCollezione(PortaCd portaCd) {
        return (int) Stream.of(cds)
                .filter(Objects::nonNull)
                .filter((x) -> Arrays.asList(portaCd.cds).contains(x))
                .count();
        /*
        int uguali = 0;
        for (int i=0; i<N; i++) {
            if (cds[i] == null) continue;
            for (int j=0; j<portaCd.N; j++) {
                if (portaCd.cds[j] == null) continue;
                if (cds[i].equals(portaCd.cds[j])) {
                    uguali++;
                    break;
                }
            }
        }
        return uguali;
        */
    }

    @Override
    public String toString() {
        return "PortaCd{" +
                "N=" + N +
                ", cds=" + Arrays.toString(cds) +
                '}';
    }
}
