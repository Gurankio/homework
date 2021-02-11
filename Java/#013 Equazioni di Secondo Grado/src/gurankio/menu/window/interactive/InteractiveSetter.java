package gurankio.menu.window.interactive;

import java.lang.reflect.Method;

public class InteractiveSetter extends InteractiveMethod {

    public InteractiveSetter(Method method) {
        super(method);
    }

    @Override
    public Object call(Object instance) {
        return super.call(instance);
    }
}
