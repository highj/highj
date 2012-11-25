package org.highj.data.compare;

public interface Eq<T> {
    public boolean eq(T one, T two);

    //uses Object.equalsFn() instead of custom Eq
    public static class JavaEq<T> implements Eq<T> {

        @Override
        public boolean eq(T one, T two) {
            return one == null ? two == null : one.equals(two);
        }
    }
}
