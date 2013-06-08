package org.highj.data.tuple;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.____;
import org.highj.data.compare.Eq;

import java.util.function.Supplier;

public enum Tuple {
    ;

    /**
     * The nullary tuple - just for consistency
     *
     * @return the nullary tuple, which is the unit value
     */
    public static T0 of() {
        return T0.unit;
    }

    /**
     * The unary tuple, a.k.a "identity" or "cell"
     *
     * @param a   the wrapped value
     * @param <A> the wrapped type
     * @return the unary tuple
     */
    public static <A> T1<A> of(A a) {
        return new T1<A>() {
            @Override
            public A _1() {
                return a;
            }
        };
    }

    /**
     * The binary tuple, a.k.a. "pair"
     *
     * @param a   first wrapped value
     * @param b   second wrapped value
     * @param <A> first wrapped type
     * @param <B> second wrapped type
     * @return the binary tuple
     */
    public static <A, B> T2<A, B> of(A a, B b) {
        assert a != null && b != null;
        return new T2<A, B>() {

            @Override
            public A _1() {
                return a;
            }

            @Override
            public B _2() {
                return b;
            }
        };
    }

    /**
     * The lazy version of the binary tuple, a.k.a. "pair"
     *
     * @param thunkA the thunk for the first wrapped value
     * @param thunkB the thunk for the second wrapped value
     * @param <A>    first wrapped type
     * @param <B>    second wrapped type
     * @return the lazy binary tuple
     */
    public static <A, B> T2<A, B> of(Supplier<A> thunkA, Supplier<B> thunkB) {
        return new T2<A, B>() {

            @Override
            public A _1() {
                return thunkA.get();
            }

            @Override
            public B _2() {
                return thunkB.get();
            }
        };
    }

    /**
     * The ternary tuple, a.k.a. "triple"
     *
     * @param a   first wrapped value
     * @param b   second wrapped value
     * @param c   third wrapped value
     * @param <A> first wrapped type
     * @param <B> second wrapped type
     * @param <C> third wrapped type
     * @return the ternary tuple
     */
    public static <A, B, C> T3<A, B, C> of(A a, B b, C c) {
        assert a != null && b != null && c != null;
        return new T3<A, B, C>() {

            @Override
            public A _1() {
                return a;
            }

            @Override
            public B _2() {
                return b;
            }

            @Override
            public C _3() {
                return c;
            }
        };
    }

    /**
     * The lazy version of the ternary tuple, a.k.a. "triple"
     *
     * @param thunkA the thunk for the first wrapped value
     * @param thunkB the thunk for the second wrapped value
     * @param thunkC the thunk for the third wrapped value
     * @param <A>    first wrapped type
     * @param <B>    second wrapped type
     * @param <C>    third wrapped type
     * @return the lazy ternary tuple
     */
    public static <A, B, C> T3<A, B, C> of(Supplier<A> thunkA, Supplier<B> thunkB, Supplier<C> thunkC) {
        return new T3<A, B, C>() {

            @Override
            public A _1() {
                return thunkA.get();
            }

            @Override
            public B _2() {
                return thunkB.get();
            }

            @Override
            public C _3() {
                return thunkC.get();
            }
        };
    }

    /**
     * The quaternary tuple, a.k.a. "quadrupel"
     *
     * @param a   first wrapped value
     * @param b   second wrapped value
     * @param c   third wrapped value
     * @param d   fourth wrapped value
     * @param <A> first wrapped type
     * @param <B> second wrapped type
     * @param <C> third wrapped type
     * @param <D> fourth wrapped type
     * @return the quaternary tuple
     */
    public static <A, B, C, D> T4<A, B, C, D> of(A a, B b, C c, D d) {
        assert a != null && b != null && c != null && d != null;
        return new T4<A, B, C, D>() {

            @Override
            public A _1() {
                return a;
            }

            @Override
            public B _2() {
                return b;
            }

            @Override
            public C _3() {
                return c;
            }

            @Override
            public D _4() {
                return d;
            }
        };
    }

    /**
     * The lazy version of the quaternary tuple, a.k.a. "quadrupel"
     *
     * @param thunkA the thunk for the first wrapped value
     * @param thunkB the thunk for the second wrapped value
     * @param thunkC the thunk for the third wrapped value
     * @param thunkD the thunk for the fourth wrapped value
     * @param <A>    first wrapped type
     * @param <B>    second wrapped type
     * @param <C>    third wrapped type
     * @param <D>    fourth wrapped type
     * @return the lazy quaternary tuple
     */
    public static <A, B, C, D> T4<A, B, C, D> of(Supplier<A> thunkA, Supplier<B> thunkB, Supplier<C> thunkC, Supplier<D> thunkD) {
        return new T4<A, B, C, D>() {

            @Override
            public A _1() {
                return thunkA.get();
            }

            @Override
            public B _2() {
                return thunkB.get();
            }

            @Override
            public C _3() {
                return thunkC.get();
            }

            @Override
            public D _4() {
                return thunkD.get();
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static <A> T1<A> narrow(_<T1.µ, A> value) {
        return (T1) value;
    }


    public static <A> Eq<T1<A>> eq(final Eq<? super A> eqA) {
        return (one, two) -> eqA.eq(one._1(), two._1());
    }

    @SuppressWarnings("unchecked")
    public static <A, B> T2<A, B> narrow2(_<__.µ<T2.µ, A>, B> value) {
        return (T2) value;
    }

    public static <A, B> Eq<T2<A, B>> eq(final Eq<? super A> eqA, final Eq<? super B> eqB) {
        return (one, two) -> eqA.eq(one._1(), two._1()) && eqB.eq(one._2(), two._2());
    }

    @SuppressWarnings("unchecked")
    public static <A, B, C> T3<A, B, C> narrow3(_<__.µ<___.µ<T3.µ, A>, B>, C> value) {
        return (T3) value;
    }

    @SuppressWarnings("unchecked")
    public static <A, B, C, D> T4<A, B, C, D> narrow4(_<__.µ<___.µ<____.µ<T4.µ, A>, B>, C>, D> value) {
        return (T4) value;
    }


}
