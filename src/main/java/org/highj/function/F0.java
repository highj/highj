package org.highj.function;

import org.highj.data.tuple.T0;
import org.highj.data.tuple.T1;

/**
 * A class representing a null-ary function, a.k.a. "thunk".
 * Note that F0 isn't derived from F1 but from T1.
 * You may convert using F0.toF1() and F0s.fromFunction()
 */
public abstract class F0<A> extends T1<A> {

    public abstract A $();

    @Override
    public A _1(){
        return $();
    }

    public F1<T0, A> toF1() {
        return new F1<T0, A>() {

            @Override
            public A $(T0 unit) {
                return F0.this.$();
            }
        };
    }

    @Override
    public <B> F0<B> map(final F1<A,B> fn) {
        return new F0<B>() {

            @Override
            public B $() {
                return fn.$(F0.this.$());
            }
        };
    }

    public static <A> F0<A> fromF1(final F1<T0, A> fn) {
        return new F0<A>() {

            @Override
            public A $() {
                return fn.$(T0.unit);
            }
        };
    }

    public static <A> F0<A> constant(final A a) {
        return new F0<A>() {
            @Override
            public A $() {
                return a;
            }
        };
    }

    public static <A> F0<A> error(final String message) {
        return new F0<A>() {
            @Override
            public A $() {
                throw new RuntimeException(message);
            }
        };
    }

    public static <A> F0<A> error(final Class<? extends RuntimeException> exClass) {
        return new F0<A>() {
            @Override
            public A $() {
                RuntimeException exception;
                try {
                    exception = exClass.newInstance();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                throw exception;
            }
        };
    }

    public static <A> F0<A> error(final Class<? extends RuntimeException> exClass, final String message) {
        return new F0<A>() {
            @Override
            public A $() {
                RuntimeException exception;
                try {
                    exception =  exClass.getConstructor(String.class).newInstance(message);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                throw exception;
            }
        };
    }

    public static <A, B> F0<B> lazy(final F1<A, B> fn, final A a) {
        return new F0<B>() {

            @Override
            public B $() {
                return fn.$(a);
            }
        };
    }

    public static <A, B, C> F0<C> lazy(final F2<A, B, C> fn, final A a, final B b) {
        return new F0<C>() {

            @Override
            public C $() {
                return fn.$(a, b);
            }
        };
    }

    public static <A, B, C, D> F0<D> lazy(final F3<A, B, C, D> fn, final A a, final B b, final C c) {
        return new F0<D>() {

            @Override
            public D $() {
                return fn.$(a, b, c);
            }
        };
    }

    //use a method as subclasses could switch memoizing behavior at runtime
    public boolean isMemoizing() { return false; }

    public F0<A> memoized() {
        return isMemoizing() ? this : new F0<A>() {
            @Override
            public boolean isMemoizing() { return true; }

            private A a = null;

            @Override
            public A $() {
                if (a == null) {
                    a = F0.this.$();
                }
                assert (a != null);
                return a;
            }
        };
    }
}
