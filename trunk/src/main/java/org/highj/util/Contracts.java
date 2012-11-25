package org.highj.util;

import java.lang.reflect.Constructor;

/**
 * A helper class for providing runtime checks of pre- and postconditions.
 * The methods are similar to assert, but can't be turned off.
 */
public enum Contracts {
    ;

    /**
     * Checks if a precondition holds.
     *
     * @param condition precondition that should hold before an operation takes place
     * @param message   error message
     * @param varargs   optional arguments that should be included
     * @throws IllegalArgumentException
     */
    @SafeVarargs
    public static void require(boolean condition, String message, Object... varargs) {
        if (!condition) {
            throw new IllegalArgumentException(String.format(message, varargs));
        }
    }

    /**
     * Checks if a precondition holds.
     *
     * @param condition precondition that should hold before an operation takes place
     * @param exClass   class of the exception to be thrown
     * @param message   error message
     * @param varargs   optional arguments that should be included
     * @throws RuntimeException of type exClass
     */
    @SafeVarargs
    public static void require(boolean condition, Class<? extends RuntimeException> exClass, String message, Object... varargs) {
        if (!condition) {
            try {
                Constructor<? extends RuntimeException> constructor = exClass.getConstructor(String.class);
                throw constructor.newInstance(String.format(message, varargs));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Checks if a postcondition holds.
     *
     * @param condition postcondition that should hold after an operation took place
     * @param message   error message
     * @param varargs   optional arguments that should be included
     * @throws AssertionError
     */
    @SafeVarargs
    public static void ensure(boolean condition, String message, Object... varargs) {
        if (!condition) {
            throw new AssertionError(String.format(message, varargs));
        }
    }

    /**
     * Checks if a postcondition holds.
     *
     * @param condition precondition that should hold before an operation takes place
     * @param exClass   class of the exception to be thrown
     * @param message   error message
     * @param varargs   optional arguments that should be included
     * @throws RuntimeException of type exClass
     */
    @SafeVarargs
    public static void ensure(boolean condition, Class<? extends RuntimeException> exClass, String message, Object... varargs) {
        require(condition, exClass, message, varargs);
    }

    /**
     * Unconditional fail (e.g. default branch in switch that shouldn't be called).
     *
     * @param message error message
     * @param varargs optional arguments that should be included
     * @throws AssertionError
     */
    @SafeVarargs
    public static void fail(String message, Object... varargs) {
        ensure(false, message, varargs);
    }
}
