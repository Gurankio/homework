package gurankio.menu.window.interactive;

import gurankio.menu.io.ConsoleInput;
import gurankio.menu.io.ConsoleOutput;
import gurankio.menu.io.util.CharPacks;
import gurankio.menu.io.util.StringPrettify;

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
            String name = StringPrettify.toPrettyString(field);
            String value = StringPrettify.toPrettyString(field.get(instance)).lines()
                    .map(line -> CharPacks.selected.getSpacer(name.length() + 3) + line)
                    .collect(Collectors.joining("\n"))
                    .stripLeading();
            consumer.accept(name + " = " + value);
        } catch (IllegalAccessException e) {
            consumer.accept(StringPrettify.toPrettyString(field));
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
            ConsoleOutput.println("Entering '" + StringPrettify.toPrettyString(field) + "'");
            Object o = field.get(instance);
            if (o == null) {
                ConsoleOutput.println("Field is null.");
                o = ConsoleInput.read(field);
                field.set(instance, o);
            }
            ConsoleOutput.arrowln(o);
            return o;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }
}
