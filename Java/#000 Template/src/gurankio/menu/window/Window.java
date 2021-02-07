package gurankio.menu.window;

import gurankio.menu.Menu;
import gurankio.menu.window.interactive.Interactive;
import gurankio.menu.window.interactive.InteractiveExit;
import gurankio.menu.window.interactive.InteractiveWindow;
import gurankio.util.Iosifovitch;
import gurankio.util.TreeBuilder;

import java.util.*;
import java.util.function.Supplier;

import java.util.stream.Collectors;

public class Window {

    private final Map<String, Interactive> interactiveMap;

    public Window(List<Interactive> interactives) {
        this.interactiveMap = new HashMap<>();
        interactives.forEach(i -> i.getNames().forEach(n -> this.interactiveMap.put(n, i)));
    }

    public Map<String, Interactive> getInteractivesMap() {
        return interactiveMap;
    }

    public List<Interactive> getInteractives() {
        List<String> order = Arrays.asList("fields", "getters", "setters", "methods");
        return interactiveMap.values()
                .stream()
                .distinct()
                .sorted((o1, o2) -> {
                    if (o1 instanceof InteractiveExit) return 1;
                    if (o2 instanceof InteractiveExit) return -1;
                    if (order.contains(o1.getNames().get(0)) && order.contains(o2.getNames().get(0))) return order.indexOf(o1.getNames().get(0)) - order.indexOf(o2.getNames().get(0));
                    return o1.getNames().get(0).compareTo(o2.getNames().get(0)); // improve so that order is field > getters > setters > methods.
                })
                .collect(Collectors.toUnmodifiableList());
    }

    public Set<String> getInteractivesNames() {
        return interactiveMap.keySet();
    }

    private Interactive getNestedInteractive(String input) {
        Interactive interactive = interactiveMap.get(input);
        if (interactive instanceof InteractiveWindow && !interactive.getNames().get(0).equals(input)) {
            interactive = ((InteractiveWindow) interactive).getWindow().getInteractivesMap().get(input);
        }
        return interactive;
    }

    public Interactive loop(Supplier<TreeBuilder> builderSupplier, Object instance) {
        display(builderSupplier, instance);

        String temp = Menu.console.read("Choice: ");
        boolean confirm = temp.contains("\t");
        String input = temp.replace("\t", "");;

        // Regex
        /*
        Matcher matcher = Pattern.compile("/(.*)/").matcher(input);
        if (matcher.matches()) {
            try {
                Pattern pattern = Pattern.compile(matcher.group(1));
                List<Interactive> filtered = getInteractivesNames().stream()
                        .filter(s -> pattern.matcher(s).matches())
                        .map(this::getNestedInteractive)
                        .filter(i -> !(i instanceof InteractiveExit))
                        .distinct()
                        .collect(Collectors.toUnmodifiableList());
                TreeBuilder builder = builderSupplier.get();

                if (filtered.size() == 0) {
                    ConsoleMenu.console.arrowln("No match.");
                    return loop(builderSupplier, instance);
                }

                filtered.forEach(i -> i.render(builder::arrowCounted, instance));
                ConsoleMenu.console.println(builder.endAll().toString());

                int index;
                do {
                    index = ConsoleMenu.console.read("Choice", Integer.class);
                    if (index < 1 || index > filtered.size()) ConsoleMenu.console.println("Insert a number between 1 and " + filtered.size() + ".");
                } while (index < 1 || index > filtered.size());
                return filtered.get(index - 1);
            } catch (PatternSyntaxException e) {
                ConsoleMenu.console.println(ERROR_MESSAGE + " " + e.getMessage());
                return loop(builderSupplier, instance);
            }
        }
        */

        // Exact match
        if (interactiveMap.containsKey(input)) return getNestedInteractive(input);

        // Closest match (prefix + levenshtein)
        String suggestion = getInteractivesNames().stream().min((a, b) -> {
            if (a.contains(input) && b.contains(input)) return a.compareTo(b);
            if (a.contains(input) && !b.contains(input)) return -1;
            if (!a.contains(input) && b.contains(input)) return 1;
            return Iosifovitch.levenshtein(a, input).compareTo(Iosifovitch.levenshtein(b, input));
        }).orElse(null);

        if (suggestion != null) {
            if (!confirm) {
                Menu.console.incrementIndentation();
                String confirmInput = Menu.console.read("Did you mean " + suggestion + "? Y/N");
                Menu.console.decrementIndentation();
                confirm = confirmInput.matches("[yY].*");
            }
            if (confirm) return getNestedInteractive(suggestion);
        } else {
            Menu.console.println("Invalid input.");
        }

        Menu.console.println();
        return loop(builderSupplier, instance);
    }

    private void display(Supplier<TreeBuilder> builderSupplier, Object instance) {
        TreeBuilder builder = builderSupplier.get();
        getInteractives().forEach(i -> i.render(i instanceof InteractiveExit ? builder::arrowReverse : builder::arrow, instance));
        Menu.console.println(builder.endAll().toString());
    }

}
