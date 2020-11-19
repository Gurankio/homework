package gurankio.menu.window;

import gurankio.menu.input.ConsoleInput;
import gurankio.menu.interaction.Interactable;

import java.util.Map;

public class Window {

    private final String menu;
    private final Map<Integer, Interactable> actions; // TODO: search by name.

    public Window(String menu, Map<Integer, Interactable> actions) {
        this.menu = menu;
        this.actions = actions;
    }

    public Interactable loop() {
        System.out.print(menu);
        Integer choice = ConsoleInput.readInt("Scelta: ");
        if (!actions.containsKey(choice)) {
            System.out.println("Scelta non valida.");
            return loop();
        }
        return actions.get(choice);
    }

    @Override
    public String toString() {
        return "Window{" +
                "menu='" + menu + '\'' +
                ", actions=" + actions +
                '}';
    }
}
