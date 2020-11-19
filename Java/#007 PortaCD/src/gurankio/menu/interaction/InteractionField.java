package gurankio.menu.interaction;

import gurankio.menu.input.GenericFactory;
import gurankio.menu.window.Window;

import java.lang.reflect.Field;

public class InteractionField implements Interactable {

    private final Field field;

    public InteractionField(Field field) {
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    @Override
    public Object call(Object instance) {
        try {
            Object o = field.get(instance);
            if (o == null) {
                System.out.println("Field is null. Creating...");
                o = GenericFactory.create(field.getType());
                field.set(instance, o);
            }
            System.out.println(o);
            return o;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return instance;
    }
}
