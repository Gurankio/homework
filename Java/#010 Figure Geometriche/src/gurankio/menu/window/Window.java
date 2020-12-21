package gurankio.menu.window;

import gurankio.menu.io.ConsoleInput;
import gurankio.menu.io.ConsoleOutput;
import gurankio.menu.io.util.Iosifovitch;
import gurankio.menu.io.util.TreeBuilder;
import gurankio.menu.window.interactive.Interactive;
import gurankio.menu.window.interactive.InteractiveExit;
import gurankio.menu.window.interactive.InteractiveWindow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import static gurankio.menu.io.ConsoleInput.ERROR_MESSAGE;

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
        return interactiveMap.values()
                .stream()
                .distinct()
                .sorted((o1, o2) -> {
                    if (o1 instanceof InteractiveExit) return 1;
                    if (o2 instanceof InteractiveExit) return -1;
                    return o1.getNames().get(0).compareTo(o2.getNames().get(0)); // improve so that order is field > getters > setters > methods.
                })
                .collect(Collectors.toUnmodifiableList());
    }

    public Set<String> getInteractivesNames() {
        return interactiveMap.keySet();
    }

    private Interactive getNestedInteractive(String input) {
        Interactive interactive = interactiveMap.get(input);
        if (interactive instanceof InteractiveWindow) {
            interactive = ((InteractiveWindow) interactive).getWindow().getInteractivesMap().get(input);
        }
        return interactive;
    }

    public Interactive loop(Supplier<TreeBuilder> builderSupplier, Object instance) {
        ConsoleOutput.println();
        display(builderSupplier, instance);

        String temp = ConsoleInput.read("Choice");
        boolean confirm = temp.contains("\t");
        String input = temp.replace("\t", "");;

        // Regex
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
                    ConsoleOutput.arrowln("No match.");
                    return loop(builderSupplier, instance);
                }

                filtered.forEach(i -> i.render(builder::arrowCounted, instance));
                ConsoleOutput.println(builder.endAll().toString());

                int index;
                do {
                    index = (int) ConsoleInput.read("Choice", Integer.class);
                    if (index < 1 || index > filtered.size()) ConsoleOutput.println("Insert a number between 1 and " + filtered.size() + ".");
                } while (index < 1 || index > filtered.size());
                return filtered.get(index - 1);
            } catch (PatternSyntaxException e) {
                ConsoleOutput.println(ERROR_MESSAGE + " " + e.getMessage());
                return loop(builderSupplier, instance);
            }
        }

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
                String confirmInput = ConsoleInput.read("Did you mean " + suggestion + "? Y/N");
                confirm = confirmInput.matches("[yY].*");
            }
            if (confirm) return getNestedInteractive(suggestion);
        } else {
            ConsoleOutput.println(ERROR_MESSAGE);
        }

        ConsoleOutput.println();
        return loop(builderSupplier, instance);
    }

    private void display(Supplier<TreeBuilder> builderSupplier, Object instance) {
        TreeBuilder builder = builderSupplier.get();
        getInteractives().forEach(i -> i.render(i instanceof InteractiveExit ? builder::arrowReverse : builder::arrow, instance));
        ConsoleOutput.println(builder.endAll().toString());
    }

}
