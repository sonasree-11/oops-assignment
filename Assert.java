package testrunner;

/**
 * Very small assertion utility.
 * Throws AssertionError on failure (same as JUnit behaviour).
 */
public final class Assert {
    private Assert() {}

    public static void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("Expected condition to be true but was false");
        }
    }

    public static void assertFalse(boolean condition) {
        if (condition) {
            throw new AssertionError("Expected condition to be false but was true");
        }
    }

    public static void assertEquals(Object expected, Object actual) {
        if (expected == null) {
            if (actual != null) {
                throw new AssertionError("Expected null, but was: " + actual);
            }
        } else if (!expected.equals(actual)) {
            throw new AssertionError("Expected: " + expected + ", but was: " + actual);
        }
    }

    public static void assertEquals(int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError("Expected: " + expected + ", but was: " + actual);
        }
    }

    public static void fail(String message) {
        throw new AssertionError("Fail: " + message);
    }
}
