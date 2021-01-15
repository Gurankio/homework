package gurankio.carrello;

import gurankio.menu.io.ConsoleInput;

public enum TipoDiMerce {
    FRESCHI(Freschi.class),
    CONSERVATI(Conservati.class),
    ABBIGLIAMENTO(Abbigliamento.class);

    private final Class<?> target;

    TipoDiMerce(Class<?> target) {
        this.target = target;
    }

    public Class<?> getTarget() {
        return target;
    }

    public static Merce crea() {
        return (Merce) ConsoleInput.read("A",
                ((TipoDiMerce) ConsoleInput.read("", TipoDiMerce.class)).getTarget());
    }
}
