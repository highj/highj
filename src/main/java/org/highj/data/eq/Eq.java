package org.highj.data.eq;

@FunctionalInterface
public interface Eq<T> {

    boolean eq(T one, T two);

    static <T> Eq<T> fromEquals() {
        return (one, two) -> one == null ? two == null : one.equals(two);
    }
}
