package gurankio.menu.window;

import gurankio.menu.io.ConsoleInput;
import gurankio.menu.io.ConsoleOutput;
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
        ConsoleOutput.println(menu);
        Integer choice;
        do {
            choice = ConsoleInput.readInt("Input: ");
            if (!actions.containsKey(choice)) ConsoleOutput.println("Invalid input.\n");
        } while (!actions.containsKey(choice));
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
