package gurankio.menu.interaction;

import gurankio.menu.input.ConsoleOutput;
import gurankio.menu.input.GenericFactory;

import java.lang.reflect.Field;
import java.util.Arrays;

public class InteractionField implements Interactable {

    private final Field field;

    public InteractionField(Field field) {
        this.field = field;
    }

    @Override
    public Object call(Object instance) {
        try {
            ConsoleOutput.println("Entering '" + field.getName() + (field.getClass().isArray() ? "[]" : "") + "'");
            Object o = field.get(instance);
            if (o == null) {
                ConsoleOutput.println("Field is null. Creating...");
                o = GenericFactory.create(field.getType());
                field.set(instance, o);
            }
            if (o.getClass().isArray()) ConsoleOutput.println("─> ", Arrays.toString((Object[])o));
            else ConsoleOutput.println("─> ", o);
            return o;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return instance;
    }
}
