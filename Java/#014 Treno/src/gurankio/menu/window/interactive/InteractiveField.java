package gurankio.menu.window.interactive;

import gurankio.io.text.TextSerializer;
import gurankio.menu.Menu;
import gurankio.util.CharPacks;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class InteractiveField implements Interactive {

    private final Field field;

    public InteractiveField(Field field) {
        this.field = field;
    }

    @Override
    public void render(Consumer<String> consumer, Object instance) {
        try {
            String name = TextSerializer.serialize(field);
            String value = TextSerializer.serialize(field.get(instance)).lines()
                    .map(line -> CharPacks.selected.getSpacer(name.length() + 3) + line)
                    .collect(Collectors.joining("\n"))
                    .stripLeading();
            consumer.accept(name + " = " + value);
        } catch (IllegalAccessException e) {
            consumer.accept(TextSerializer.serialize(field));
            // e.printStackTrace();
        }
    }

    @Override
    public List<String> getNames() {
        return Arrays.asList(field.getName());
    }

    @Override
    public Object call(Object instance) {
        try {
            Menu.console.println("Entering '" + TextSerializer.serialize(field) + "'");
            Object o = field.get(instance);
            if (o == null) {
                Menu.console.println("Field is null.");
                o = Menu.console.read(field);
                field.set(instance, o);
            }
            Menu.console.arrowln(o);
            return o;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }
}
