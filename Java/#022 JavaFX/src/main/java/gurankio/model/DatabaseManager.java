package gurankio.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class DatabaseManager {

    private static final ObjectProperty<ObservableList<Abbonamento>> database = new SimpleObjectProperty<>(FXCollections.observableList(new ArrayList<>()));

    private static final List<Action> actions = new LinkedList<>();
    private static final List<Abbonamento> data = new LinkedList<>();
    private static final IntegerProperty index = new SimpleIntegerProperty();

    static {
        reset();
    }

    public static ObservableList<Abbonamento> getDatabase() {
        return database.get();
    }

    public static ObjectProperty<ObservableList<Abbonamento>> databaseProperty() {
        return database;
    }

    public static int getIndex() {
        return index.get();
    }

    public static IntegerProperty indexProperty() {
        return index;
    }

    public static void add(Abbonamento abbonamento) {
        actions.removeIf(x -> actions.indexOf(x) > index.get());
        actions.add(Action.ADD);
        data.removeIf(x -> data.indexOf(x) > index.get());
        data.add(abbonamento);
        Action.ADD.action.accept(abbonamento);
        PersistenceManager.saves();
        index.set(index.get() + 1);
    }

    public static void delete(Abbonamento abbonamento) {
        actions.removeIf(x -> actions.indexOf(x) > index.get());
        actions.add(Action.DELETE);
        data.removeIf(x -> data.indexOf(x) > index.get());
        data.add(abbonamento);
        Action.DELETE.action.accept(abbonamento);
        PersistenceManager.saves();
        index.set(index.get() + 1);
    }

    public static void update(Abbonamento abbonamento) {
        actions.removeIf(x -> actions.indexOf(x) > index.get());
        actions.add(Action.UPDATE);
        data.removeIf(x -> data.indexOf(x) > index.get());
        Action.UPDATE.action.accept(abbonamento);
        PersistenceManager.saves();
        index.set(index.get() + 1);
    }

    public static void undo() {
        actions.get(index.get()).opposite.accept(data.get(index.get()));
        PersistenceManager.saves();
        index.set(index.get() - 1);
    }

    public static boolean canUndo() {
        return index.get() >= 0;
    }

    public static void redo() {
        actions.get(index.get() + 1).action.accept(data.get(index.get() + 1));
        PersistenceManager.saves();
        index.set(index.get() + 1);
    }

    public static boolean canRedo() {
        return index.get() < actions.size() - 1;
    }

    public static void reset() {
        actions.clear();
        data.clear();
        index.set(-1);
    }

    private enum Action {
        ADD(abbonamento -> {
            database.get().add(abbonamento);
        }, abbonamento -> {
            database.get().remove(abbonamento);
        }),

        DELETE(abbonamento -> {
            database.get().remove(abbonamento);
        }, abbonamento -> {
            database.get().add(abbonamento);
        }),

        UPDATE(toSet -> {
            database.get().stream().filter(x -> x.getNumeroTessera() == toSet.getNumeroTessera()).findFirst().ifPresent(x -> {
                Abbonamento t = new Abbonamento();
                t.setNumeroTessera(x.getNumeroTessera());
                t.setTipologia(x.getTipologia());
                t.setDataCreazione(x.getDataCreazione());
                if (index.get() + 1 == data.size()) data.add(null);
                data.set(index.get() + 1, t);

                x.setTipologia(toSet.getTipologia());
                x.setDataCreazione(toSet.getDataCreazione());
            });
        }, toSet -> {
            database.get().stream().filter(x -> x.getNumeroTessera() == toSet.getNumeroTessera()).findFirst().ifPresent(x -> {
                Abbonamento t = new Abbonamento();
                t.setNumeroTessera(x.getNumeroTessera());
                t.setTipologia(x.getTipologia());
                t.setDataCreazione(x.getDataCreazione());
                data.set(index.get(), t);

                x.setTipologia(toSet.getTipologia());
                x.setDataCreazione(toSet.getDataCreazione());
            });
        });

        Consumer<Abbonamento> action;
        Consumer<Abbonamento> opposite;

        Action(Consumer<Abbonamento> action, Consumer<Abbonamento> opposite) {
            this.action = action;
            this.opposite = opposite;
        }
    }
}
