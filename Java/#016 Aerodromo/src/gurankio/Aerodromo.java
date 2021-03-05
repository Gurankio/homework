package gurankio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Aerodromo {

    private List<Aeromobile> aeromobili;

    public Aerodromo() {
        aeromobili = new ArrayList<>();
        aeromobili.addAll(Persistent.list(AereoMotore.class));
        aeromobili.addAll(Persistent.list(Aliante.class));
    }

    public int compare(String a, String b) throws NullPointerException, ClassCastException, MissingSiglaException {
        Aeromobile x = aeromobili.stream().filter(aeromobile -> aeromobile.getSigla().equalsIgnoreCase(a)).findFirst().orElseThrow(MissingSiglaException::new);
        Aeromobile y = aeromobili.stream().filter(aeromobile -> aeromobile.getSigla().equalsIgnoreCase(b)).findFirst().orElseThrow(MissingSiglaException::new);
        return x.compareTo(y);
    }

    @Override
    public String toString() {
        return "Gli aeromobili disponibili sono: \n" + aeromobili.stream().map(aeromobile -> " - " + aeromobile.toString()).collect(Collectors.joining("\n"));
    }
}
