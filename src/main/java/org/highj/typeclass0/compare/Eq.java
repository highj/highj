package org.highj.typeclass0.compare;

public interface Eq<T> {
    boolean eq(T one, T two);

    //uses Object.equalsFn() instead of custom Eq
    class JavaEq<T> implements Eq<T> {

        @Override
        public boolean eq(T one, T two) {
            return one == null ? two == null : one.equals(two);
        }
    }
}
