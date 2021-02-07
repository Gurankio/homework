package gurankio.menu.window.interactive;

import gurankio.menu.Menu;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class InteractiveGetter extends InteractiveMethod {

    public InteractiveGetter(Method method) {
        super(method);
    }

    @Override
    public Object call(Object instance) {
        Object r = super.call(instance);
        if (r == instance) {
            Menu.console.arrowln(null);
            try {
                InteractiveSetter setter = new InteractiveSetter(instance.getClass().getDeclaredMethod(getMethod().getName().replace("get", "set"), getMethod().getReturnType()));
                String confirmInput = Menu.console.read("Did you mean to call " + getMethod().getName().replace("get", "set") + "? Y/N");
                if (confirmInput.matches("[yY].*")) {
                    return setter.call(instance);
                }
            } catch (NoSuchMethodException ignored) {
                return instance;
            }
        }
        return r;
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>(super.getNames());
        names.add(getMethod().getName().replace("get", ""));
        return names;
    }
}
