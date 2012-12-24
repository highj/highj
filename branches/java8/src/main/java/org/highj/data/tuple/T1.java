package org.highj.data.tuple;

import org.highj._;
import org.highj.data.compare.Eq;
import org.highj.function.F1;
import org.highj.function.F2;
import org.highj.typeclass.comonad.Comonad;
import org.highj.typeclass.group.*;
import org.highj.typeclass.monad.Monad;

import static org.highj.data.tuple.Tuple.of;

/**
 * A tuple of arity 1, a.k.a. "cell" mplus "Id".
 * Note that <code>F0</code> is a subclass of <code>T1</code>, which allows lazy behavior.
 */
public abstract class T1<A> extends _<T1.µ, A> {
    private static final µ hidden = new µ();

    public static class µ {
        private µ() {
        }
    }

    protected T1() {
        super(hidden);
    }

    public static final Monad<µ> monad = new Monad<µ>() {
        @Override
        public <A, B> _<µ, B> map(F1<A, B> fn, _<µ, A> nestedA) {
            return narrow(nestedA).map(fn);
        }

        @Override
        public <A> _<µ, A> pure(A a) {
            return of(a);
        }

        @Override
        public <A, B> _<µ, B> ap(_<µ, F1<A, B>> nestedFn, _<µ, A> nestedA) {
            return narrow(nestedA).ap(narrow(nestedFn));
        }

        @Override
        public <A, B> _<µ, B> bind(_<µ, A> nestedA, F1<A, _<µ, B>> fn) {
            return fn.$(narrow(nestedA)._1());
        }
    };

    public static Comonad<µ> comonad() {
        return new Comonad<µ>() {
            @Override
            public <A> _<µ, _<µ, A>> duplicate(_<µ, A> nestedA) {
                return Tuple.of(nestedA);
            }

            @Override
            public <A> A extract(_<µ, A> nestedA) {
                return narrow(nestedA)._1();
            }

            @Override
            public <A, B> _<µ, B> map(F1<A, B> fn, _<µ, A> nestedA) {
                return narrow(nestedA).map(fn);
            }
        };
    }

    public abstract A _1();

    @Override
    public String toString() {
        return String.format("(%s)", _1());
    }

    @SuppressWarnings("unchecked")
    public static <A> T1<A> narrow(_<µ, A> value) {
        return (T1) value;
    }

    public <B> T1<B> map(F1<A, B> fn) {
        return of(fn.$(_1()));
    }

    public <B> T1<B> ap(T1<F1<A, B>> nestedFn) {
        return map(narrow(nestedFn)._1());
    }

    public static <A> F1<A, T1<A>> t1() {
        return new F1<A, T1<A>>() {

            @Override
            public T1<A> $(A a) {
                return Tuple.of(a);
            }
        };
    }

    @Override
    public int hashCode() {
        return _1().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof T1) {
            T1<?> that = (T1) o;
            return this._1().equals(that._1());
        }
        return false;
    }

    public static <A> Eq<T1<A>> eq(final Eq<? super A> eqA) {
        return new Eq<T1<A>>() {

            @Override
            public boolean eq(T1<A> one, T1<A> two) {
                return eqA.eq(one._1(), two._1());
            }
        };
    }

    private static <T> F2<T1<T>, T1<T>, T1<T>> dotFn(final Semigroup<T> semigroup) {
        return new F2<T1<T>, T1<T>, T1<T>>() {

            @Override
            public T1<T> $(T1<T> x, T1<T> y) {
                return Tuple.of(semigroup.dot(x._1(), y._1()));
            }
        };
    }

    private static <T> F1<T1<T>, T1<T>> inverseFn(final Group<T> group) {
        return new F1<T1<T>, T1<T>>() {

            @Override
            public T1<T> $(T1<T> x) {
                return Tuple.of(group.inverse(x._1()));
            }
        };
    }

    public static <T> Semigroup<T1<T>> semigroup(Semigroup<T> semigroup) {
        return () ->  dotFn(semigroup);
    }

    public static <T> Monoid<T1<T>> monoid(Monoid<T> monoid) {
        return new Monoid<T1<T>>(){
            private T1<T> id = Tuple.of(monoid.identity());

            @Override
            public T1<T> identity() {
                return id;
            }

            @Override
            public F2<T1<T>, T1<T>, T1<T>> dot() {
                return new F2<T1<T>, T1<T>, T1<T>>() {
                    @Override
                    public T1<T> $(T1<T> x, T1<T> y) {
                        return Tuple.of(monoid.dot(x._1(),y._1()));
                    }
                };
            }
        };
    }

    public static <T> Group<T1<T>> group(Group<T> group) {
        return new Group<T1<T>>(){

            private T1<T> id = Tuple.of(group.identity());

            @Override
            public F1<T1<T>, T1<T>> inverse() {
                return inverseFn(group);
            }

            @Override
            public T1<T> identity() {
                return id;
            }

            @Override
            public F2<T1<T>, T1<T>, T1<T>> dot() {
                return dotFn(group);
            }
        };
    }
}
