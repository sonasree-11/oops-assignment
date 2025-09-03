package testrunner;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Marker annotation for test methods.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
    // For simplicity this is a marker annotation (no fields).
}
