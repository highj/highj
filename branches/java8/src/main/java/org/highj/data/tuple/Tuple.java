package org.highj.data.tuple;

import org.highj.function.*;
import org.highj.function.repo.Objects;

public enum Tuple {
    ;

    /**
     * The nullary tuple - just for consistency
     * @return the nullary tuple, which is the unit value
     */
    public static T0 of() {
        return T0.unit;
    }

    /**
     * The unary tuple, a.k.a "identity" or "cell"
     * @param a the wrapped value
     * @param <A> the wrapped type
     * @return the unary tuple
     */
    public static <A> T1<A> of(final A a) {
        return new T1<A>() {

            @Override
            public A _1() {
                return a;
            }
        };
    }

    /**
     * The binary tuple, a.k.a. "pair"
     * @param a first wrapped value
     * @param b second wrapped value
     * @param <A> first wrapped type
     * @param <B> second wrapped type
     * @return the binary tuple
     */
    public static <A, B> T2<A, B> of(final A a, final B b) {
        assert Objects.notNull(a, b);
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
     * @param thunkA  the thunk for the first wrapped value
     * @param thunkB  the thunk for the second wrapped value
     * @param <A> first wrapped type
     * @param <B>  second wrapped type
     * @return the lazy binary tuple
     */
    public static <A, B> T2<A, B> of(final T1<A> thunkA, final T1<B> thunkB) {
        return new T2<A, B>() {

            @Override
            public A _1() {
                return thunkA._1();
            }

            @Override
            public B _2() {
                return thunkB._1();
            }
        };
    }

    /**
     * The ternary tuple, a.k.a. "triple"
     * @param a first wrapped value
     * @param b second wrapped value
     * @param c third wrapped value
     * @param <A>  first wrapped type
     * @param <B>  second wrapped type
     * @param <C>  third wrapped type
     * @return the ternary tuple
     */
    public static <A, B, C> T3<A, B, C> of(final A a, final B b, final C c) {
        assert Objects.notNull(a, b, c);
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
     * @param thunkA the thunk for the first wrapped value
     * @param thunkB the thunk for the second wrapped value
     * @param thunkC the thunk for the third wrapped value
     * @param <A>  first wrapped type
     * @param <B>  second wrapped type
     * @param <C>  third wrapped type
     * @return the lazy ternary tuple
     */
    public static <A, B, C> T3<A, B, C> of(final T1<A> thunkA, final T1<B> thunkB, final T1<C> thunkC) {
        return new T3<A, B, C>() {

            @Override
            public A _1() {
                return thunkA._1();
            }

            @Override
            public B _2() {
                return thunkB._1();
            }

            @Override
            public C _3() {
                return thunkC._1();
            }
        };
    }

    /**
     * The quaternary tuple, a.k.a. "quadrupel"
     * @param a first wrapped value
     * @param b second wrapped value
     * @param c third wrapped value
     * @param d fourth wrapped value
     * @param <A>  first wrapped type
     * @param <B>  second wrapped type
     * @param <C>  third wrapped type
     * @param <D>  fourth wrapped type
     * @return  the quaternary tuple
     */
    public static <A, B, C, D> T4<A, B, C, D> of(final A a, final B b, final C c, final D d) {
        assert Objects.notNull(a, b, c, d);
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
     * @param thunkA the thunk for the first wrapped value
     * @param thunkB the thunk for the second wrapped value
     * @param thunkC the thunk for the third wrapped value
     * @param thunkD the thunk for the fourth wrapped value
     * @param <A>  first wrapped type
     * @param <B>  second wrapped type
     * @param <C>  third wrapped type
     * @param <D>  fourth wrapped type
     * @return the lazy quaternary tuple
     */
    public static <A, B, C, D> T4<A, B, C, D> of(final T1<A> thunkA, final T1<B> thunkB, final T1<C> thunkC, final T1<D> thunkD) {
        return new T4<A, B, C, D>() {

            @Override
            public A _1() {
                return thunkA._1();
            }

            @Override
            public B _2() {
                return thunkB._1();
            }

            @Override
            public C _3() {
                return thunkC._1();
            }

            @Override
            public D _4() {
                return thunkD._1();
            }
        };
    }

    /**
     * Cell function
     * @param <A> the wrapped type
     * @return a function for creating a cell
     */
    public static <A> F1<A,T1<A>> cell() {
        return new F1<A,T1<A>>(){

            @Override
            public T1<A> $(A a) {
                return of(a);
            }
        };
    }

    /**
     * Pair function
     * @param <A> first wrapped type
     * @param <B> second wrapped type
     * @return  a function for creating a pair
     */
    public static <A, B> F2<A,B,T2<A,B>> pair() {
        return new F2<A,B,T2<A,B>>(){

            @Override
            public T2<A,B> $(A a, B b) {
                return of(a, b);
            }
        };
    }

    /**
     * Triple function
     * @param <A>  first wrapped type
     * @param <B>  second wrapped type
     * @param <C>  third wrapped type
     * @return a function for creating a triple
     */
    public static <A,B,C> F3<A,B,C,T3<A,B,C>> triple() {
        return new F3<A, B, C, T3<A, B, C>>() {
            @Override
            public T3<A, B, C> $(A a, B b, C c) {
                return of(a,b,c);
            }
        };
    }

    /**
     * Quadruple function
     * @param <A>  first wrapped type
     * @param <B>  second wrapped type
     * @param <C>  third wrapped type
     * @param <D>  third wrapped type
     * @return a function for creating a quadruple
     */
    public static <A,B,C, D> F4<A,B,C,D,T4<A,B,C,D>> quadruple() {
        return new F4<A, B, C, D, T4<A, B, C, D>>() {
            @Override
            public T4<A, B, C, D> $(A a, B b, C c, D d) {
                return of(a,b,c,d);
            }
        };
    }

    /**
     * Swap function
     * @param <A>  first wrapped type
     * @param <B>  second wrapped type
     * @return A function which returns a tuple with swapped elements
     */
    public static <A,B> F1<T2<A,B>,T2<B,A>> swap() {
        return new F1<T2<A,B>,T2<B,A>>(){
            public T2<B, A> $(T2<A, B> t2) {
                return t2.swap();
            }
        };
    }

    /**
     * Function for extracting the element of a Cell
     * @param <A> first wrapped type
     * @return A function that returns the element
     */
    public static <A> F1<T1<A>,A> fst1() {
        return new F1<T1<A>,A>() {

            @Override
            public A $(T1<A> t1) {
                return t1._1();
            }
        };
    }

    /**
     * Function for extracting the first element of a Pair
     * @param <A> first wrapped type
     * @return A function that returns the first element
     */
    public static <A> F1<T2<A,?>,A> fst() {
        return new F1<T2<A,?>,A>() {

            @Override
            public A $(T2<A, ?> t2) {
                return t2._1();
            }
        };
    }

    /**
     * Function for extracting the second element of a Pair
     * @param <B>  second wrapped type
     * @return A function that returns the second element
     */
    public static <B> F1<T2<?,B>,B> snd() {
        return new F1<T2<?,B>,B>() {

            @Override
            public B $(T2<?, B> t2) {
                return t2._2();
            }
        };
    }

    /**
     * Function for extracting the first element of a Triple
     * @param <A> first wrapped type
     * @return A function that returns the first element
     */
    public static <A> F1<T3<A,?,?>,A> fst3() {
        return new F1<T3<A,?,?>,A>() {

            @Override
            public A $(T3<A, ?, ?> t3) {
                return t3._1();
            }
        };
    }

    /**
     * Function for extracting the second element of a Triple
     * @param <B> second wrapped type
     * @return A function that returns the second element
     */
    public static <B> F1<T3<?, B, ?>,B> snd3() {
        return new F1<T3<?, B, ?>,B>() {

            @Override
            public B $(T3<?, B,?> t3) {
                return t3._2();
            }
        };
    }

    /**
     * Function for extracting the third element of a Triple
     * @param <C> third wrapped type
     * @return A function that returns the third element
     */
    public static <C> F1<T3<?, ?, C>,C> third3() {
        return new F1<T3<?, ?, C>,C>() {

            @Override
            public C $(T3<?, ?, C> t3) {
                return t3._3();
            }
        };
    }

    /**
     * Function for extracting the first element of a Quadruple
     * @param <A> first wrapped type
     * @return A function that returns the first element
     */
    public static <A> F1<T4<A,?,?,?>,A> fst4() {
        return new F1<T4<A,?,?,?>,A>() {

            @Override
            public A $(T4<A, ?, ?,?> t4) {
                return t4._1();
            }
        };
    }

    /**
     * Function for extracting the second element of a Quadruple
     * @param <B> second wrapped type
     * @return A function that returns the second element
     */
    public static <B> F1<T4<?, B, ?,?>,B> snd4() {
        return new F1<T4<?, B, ?,?>,B>() {

            @Override
            public B $(T4<?, B,?,?> t4) {
                return t4._2();
            }
        };
    }

    /**
     * Function for extracting the third element of a Quadruple
     * @param <C> third wrapped type
     * @return A function that returns the third element
     */
    public static <C> F1<T4<?, ?, C,?>,C> third4() {
        return new F1<T4<?, ?, C,?>,C>() {

            @Override
            public C $(T4<?, ?, C,?> t4) {
                return t4._3();
            }
        };
    }

    /**
     * Function for extracting the fourth element of a Quadruple
     * @param <D> fourth wrapped type
     * @return A function that returns the third element
     */
    public static <D> F1<T4<?, ?, ?,D>,D> fourth4() {
        return new F1<T4<?, ?, ?,D>,D>() {

            @Override
            public D $(T4<?, ?, ?, D> t4) {
                return t4._4();
            }
        };
    }
}
