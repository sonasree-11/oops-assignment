package testrunner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple test runner.
 * Usage:
 *   java testrunner.TestRunner testrunner.SampleTests
 * or provide multiple test class names:
 *   java testrunner.TestRunner testrunner.SampleTests testrunner.OtherTests
 */
public class TestRunner {

    static class Result {
        String className;
        String methodName;
        boolean passed;
        Throwable throwable; // null if passed
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java testrunner.TestRunner <fully.qualified.TestClassName> [more classes...]");
            System.out.println("Example: java testrunner.TestRunner testrunner.SampleTests");
            return;
        }

        List<Result> results = new ArrayList<>();

        for (String className : args) {
            try {
                Class<?> cls = Class.forName(className);
                Method[] methods = cls.getDeclaredMethods();
                for (Method m : methods) {
                    if (m.getAnnotation(Test.class) != null) {
                        Result r = runTestMethod(cls, m);
                        results.add(r);
                        // Print immediate result for readability
                        if (r.passed) {
                            System.out.printf("PASS: %s#%s%n", r.className, r.methodName);
                        } else {
                            System.out.printf("FAIL: %s#%s -> %s: %s%n",
                                r.className, r.methodName, r.throwable.getClass().getSimpleName(), r.throwable.getMessage());
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Class not found: " + className + " (skipping)");
            } catch (Throwable t) {
                System.out.println("Error loading class " + className + ": " + t);
            }
        }

        // Summary
        long passed = results.stream().filter(r -> r.passed).count();
        long failed = results.size() - passed;
        System.out.println("---------------------------------------------------");
        System.out.printf("Total tests: %d, Passed: %d, Failed: %d%n", results.size(), passed, failed);

        // If there were failures, show more detail
        if (failed > 0) {
            System.out.println("\nFailures detail:");
            for (Result r : results) {
                if (!r.passed) {
                    System.out.printf("- %s#%s: %s%n", r.className, r.methodName, r.throwable);
                }
            }
        }
    }

    private static Result runTestMethod(Class<?> cls, Method method) {
        Result result = new Result();
        result.className = cls.getName();
        result.methodName = method.getName();

        try {
            // Create a fresh instance per test (like JUnit)
            Object instance = cls.getDeclaredConstructor().newInstance();
            method.setAccessible(true);
            method.invoke(instance);
            result.passed = true;
            result.throwable = null;
        } catch (java.lang.reflect.InvocationTargetException ite) {
            // The invoked method threw an exception; unwrap it
            Throwable cause = ite.getCause();
            result.passed = false;
            result.throwable = cause != null ? cause : ite;
        } catch (Throwable t) {
            result.passed = false;
            result.throwable = t;
        }

        return result;
    }
}
