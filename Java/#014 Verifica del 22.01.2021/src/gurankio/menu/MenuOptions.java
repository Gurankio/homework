package gurankio.menu;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public @interface MenuOptions {

    @Retention(RetentionPolicy.RUNTIME)
    @interface Hide {
    }

    /*
    @interface Shortcut {

        @Retention(RetentionPolicy.RUNTIME)
        @interface Auto {

        }

        @Retention(RetentionPolicy.RUNTIME)
        @interface Fixed {
            int number();
        }
    }
     */
}
