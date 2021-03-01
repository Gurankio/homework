package gurankio.io.data;

import gurankio.menu.MenuOptions;

import java.util.UUID;

public interface PersistentUnique extends Persistent {

    @Override
    @MenuOptions.Hide
    default String getUUID() {
        return UUID.nameUUIDFromBytes(getClass().getName().getBytes()).toString();
    }

}
