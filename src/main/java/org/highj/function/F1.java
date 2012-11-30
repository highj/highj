package org.highj.function;

import org.highj._;
import org.highj.__;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.T3;
import org.highj.data.tuple.T4;
import org.highj.data.tuple.Tuple;
import org.highj.typeclass.group.MonoidAbstract;
import org.highj.typeclass.monad.Applicative;
import org.highj.typeclass.monad.ApplicativeAbstract;
import org.highj.typeclass.arrow.Arrow;
import org.highj.typeclass.arrow.ArrowAbstract;
import org.highj.typeclass.group.Monoid;

/**
 * A class representing an unary function.
 */
public abstract class F1<A, B> extends __<F1.µ, A, B> {

    private static final µ hidden = new µ();

    public static class µ {
        private µ() {
        }
    }

    public F1() {
        super(hidden);
    }

    public abstract B $(A a);

    public F0<B> lazy(A a) {
        return F0.lazy(this, a);
    }

    @SuppressWarnings("unchecked")
    public static <A, B> F1<A, B> narrow(_<__.µ<µ, A>, B> function) {
        return (F1) function;
    }

    @SuppressWarnings("unchecked")
    public static <A, Super_B, B extends Super_B> F1<A, Super_B> contravariant(F1<A, B> function) {
        return (F1) function;
    }

    public <C> F1<A, C> andThen(F1<B, C> that) {
        return compose(that, this);
    }

    public static <A> F1<A, A> id() {
        return new F1<A, A>() {
            @Override
            public A $(A a) {
                return a;
            }
        };
    }

    public static <A, B> F1<A, B> constant(final B b) {
        return new F1<A, B>() {
            @Override
            public B $(A a) {
                return b;
            }
        };
    }

    public static <A, B> F1<A, B> constant(final F0<B> thunk) {
        return new F1<A, B>() {
            @Override
            public B $(A a) {
                return thunk.$();
            }
        };
    }

    public static <A, B, C> F1<A, C> compose(final F1<? super B, ? extends C> f, final F1<? super A, ? extends B> g) {
        return new F1<A, C>() {

            @Override
            public C $(A a) {
                return f.$(g.$(a));
            }
        };
    }

    public static <A, B> F1<F1<A, B>, B> flip(final A a) {
        return new F1<F1<A, B>, B>() {

            @Override
            public B $(F1<A, B> fn) {
                return fn.$(a);
            }
        };
    }

    public static <A> Monoid<_<__.µ<µ, A>, A>> endoMonoid() {
        return new MonoidAbstract<_<__.µ<µ, A>, A>>(
                new F2<_<__.µ<µ, A>, A>, _<__.µ<µ, A>, A>,_<__.µ<µ, A>, A>>() {
            @Override
            public _<__.µ<µ, A>, A> $(_<__.µ<µ, A>, A> x, _<__.µ<µ, A>, A> y) {
                F1<A, A> fx = narrow(x);
                F1<A, A> fy = narrow(y);
                return fx.andThen(fy);
            }
        }, F1.<A>id());
    }

    @Override
    public String toString(){
        return "<F1>";
    }

    public static <A,B> F2<F1<A,B>,A,B> apply() {
       return new F2<F1<A,B>,A,B>() {
           @Override
           public B $(F1<A, B> fn, A a) {
               return fn.$(a);
           }
       };
    }

    public static <A,B> F2<A,F1<A,B>,B> flipApply() {
        return new F2<A,F1<A,B>,B>() {
            @Override
            public B $(A a,F1<A, B> fn) {
                return fn.$(a);
            }
        };
    }

    public static final <R> Applicative<__.µ<µ,R>> applicative() {
        return new ApplicativeAbstract<__.µ<µ,R>>(){

            @Override
            public <A> _<__.µ<µ, R>, A> pure(A a) {
                //pure = const
                return constant(a);
            }

            @Override
            public <A, B> _<__.µ<µ, R>, B> ap(_<__.µ<µ, R>, F1<A, B>> fn, _<__.µ<µ, R>, A> nestedA) {
                //(<*>) f g x = f x (g x)
                final F1<R,F1<A,B>> fRAB = narrow(fn);
                final F1<R,A> fRA = narrow(nestedA);
                return new F1<R,B>(){

                    @Override
                    public B $(R r) {
                        return fRAB.$(r).$(fRA.$(r));
                    }
                };
            }

            @Override
            public <A, B> _<__.µ<µ, R>, B> map(F1<A, B> fAB, _<__.µ<µ, R>, A> nestedA) {
                return narrow(nestedA).andThen(fAB);
            }
        };
    }

    public static <A,B,C> F1<A,T2<B,C>> fanout(final F1<A,B> fab, final F1<A,C> fac) {
        return new F1<A,T2<B,C>>(){
            @Override
            public T2<B, C> $(A a) {
                return Tuple.of(fab.$(a), fac.$(a));
            }
        };
    }

    public static <A,B,C,D> F1<A,T3<B,C,D>> fanout(final F1<A,B> fab, final F1<A,C> fac, final F1<A,D> fad) {
        return new F1<A,T3<B,C,D>>(){
            @Override
            public T3<B, C, D> $(A a) {
                return Tuple.of(fab.$(a), fac.$(a), fad.$(a));
            }
        };
    }

    public static <A,B,C,D,E> F1<A,T4<B,C,D,E>> fanout(final F1<A,B> fab, final F1<A,C> fac, final F1<A,D> fad, final F1<A,E> fae) {
        return new F1<A,T4<B,C,D,E>>(){
            @Override
            public T4<B, C, D, E> $(A a) {
                return Tuple.of(fab.$(a), fac.$(a), fad.$(a), fae.$(a));
            }
        };
    }


    public static final Arrow<µ> arrow = new ArrowAbstract<µ>() {
        @Override
        public <A, B> __<µ, A, B> arr(F1<A, B> fn) {
            return fn;
        }

        @Override
        public <A, B, C> __<µ, T2<A, C>, T2<B, C>> first(__<µ, A, B> arrow) {
            final F1<A,B> fn = narrow(arrow);
            return new F1<T2<A,C>, T2<B, C>>(){

                @Override
                public T2<B, C> $(T2<A, C> pair) {
                    return Tuple.of(fn.$(pair._1()), pair._2());
                }
            };
        }

        @Override
        public <A, B, C> __<µ, T2<C, A>, T2<C, B>> second(__<µ, A, B> arrow) {
            final F1<A,B> fn = narrow(arrow);
            return new F1<T2<C,A>, T2<C,B>>(){

                @Override
                public T2<C,B> $(T2<C,A> pair) {
                    return Tuple.of(pair._1(), fn.$(pair._2()));
                }
            };
        }

        @Override
        public <A, B, C> __<µ, A, T2<B, C>> fanout(__<µ, A, B> arr1, __<µ, A, C> arr2) {
            F1<A,B> fab = narrow(arr1);
            F1<A,C> fac = narrow(arr2);
            return F1.fanout(fab, fac);
        }

        @Override
        public <A> __<µ, A, A> identity() {
            return F1.id();
        }

        @Override
        public <A, B, C> __<µ, A, C> dot(__<µ, B, C> bc, __<µ, A, B> ab) {
            return narrow(ab).andThen(narrow(bc));
        }
    };

}
