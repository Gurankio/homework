package gurankio.util;

import javafx.beans.WeakListener;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class MapBindings {

    public static <E, F> void map(Property<F> mapped, Property<? extends E> source,
                                  Function<? super E, ? extends F> mapper) {
        final ContentMapping<E, F> contentMapping = new ContentMapping<>(mapped, mapper);
        mapped.setValue(mapper.apply(source.getValue()));
        source.removeListener(contentMapping);
        source.addListener(contentMapping);
    }

    private static class ContentMapping<E, F> implements ChangeListener<E> {
        private final WeakReference<Property<F>> mappedRef;
        private final Function<? super E, ? extends F> mapper;

        public ContentMapping(Property<F> mapped, Function<? super E, ? extends F> mapper) {
            this.mappedRef = new WeakReference<>(mapped);
            this.mapper = mapper;
        }

        @Override
        public void changed(ObservableValue<? extends E> observable, E oldValue, E newValue) {
            final Property<F> mapped = mappedRef.get();
            if (mapped == null) {
                observable.removeListener(this);
            } else {
                mapped.setValue(mapper.apply(newValue));
            }
        }
    }


    public static <E, F> void mapContent(ObservableList<F> mapped, ObservableList<? extends E> source,
                                         Function<? super E, ? extends F> mapper) {
        final ListContentMapping<E, F> contentMapping = new ListContentMapping<>(mapped, mapper);
        mapped.setAll(source.stream().map(mapper).collect(toList()));
        source.removeListener(contentMapping);
        source.addListener(contentMapping);
    }

    private static class ListContentMapping<E, F> implements ListChangeListener<E>, WeakListener {
        private final WeakReference<List<F>> mappedRef;
        private final Function<? super E, ? extends F> mapper;

        public ListContentMapping(List<F> mapped, Function<? super E, ? extends F> mapper) {
            this.mappedRef = new WeakReference<>(mapped);
            this.mapper = mapper;
        }

        @Override
        public void onChanged(Change<? extends E> change) {
            final List<F> mapped = mappedRef.get();
            if (mapped == null) {
                change.getList().removeListener(this);
            } else {
                while (change.next()) {
                    if (change.wasPermutated()) {
                        mapped.subList(change.getFrom(), change.getTo()).clear();
                        mapped.addAll(change.getFrom(), change.getList().subList(change.getFrom(), change.getTo())
                                .stream().map(mapper).collect(toList()));
                    } else {
                        if (change.wasRemoved()) {
                            mapped.subList(change.getFrom(), change.getFrom() + change.getRemovedSize()).clear();
                        }
                        if (change.wasAdded()) {
                            mapped.addAll(change.getFrom(), change.getAddedSubList()
                                    .stream().map(mapper).collect(toList()));
                        }
                    }
                }
            }
        }

        @Override
        public boolean wasGarbageCollected() {
            return mappedRef.get() == null;
        }

        @Override
        public int hashCode() {
            final List<F> list = mappedRef.get();
            return (list == null) ? 0 : list.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            final List<F> mapped1 = mappedRef.get();
            if (mapped1 == null) {
                return false;
            }

            if (obj instanceof ListContentMapping) {
                final ListContentMapping<?, ?> other = (ListContentMapping<?, ?>) obj;
                final List<?> mapped2 = other.mappedRef.get();
                return mapped1 == mapped2;
            }
            return false;
        }
    }
}