package org.highj.typeclass.monad;

import org.highj._;
import org.highj.data.collection.List;
import org.highj.data.tuple.T0;
import org.highj.function.F1;
import org.highj.function.F2;
import org.highj.util.Mutable;

public interface Monad<mu> extends Applicative<mu>, Bind<mu> {

    // mapM (Control.Monad)
    public default <A, B> F1<List<A>, _<mu, List<B>>> mapM(final F1<A, _<mu, B>> fn) {
        return new F1<List<A>, _<mu, List<B>>>() {
            @Override
            public _<mu, List<B>> $(List<A> list) {
                return sequence(list.map(fn));
            }
        };
    }

    // mapM_ (Control.Monad)
    public default <A, B> F1<List<A>, _<mu, T0>> mapM_(final F1<A, _<mu, B>> fn) {
        return new F1<List<A>, _<mu, T0>>() {
            @Override
            public _<mu, T0> $(List<A> list) {
                return sequence_(list.map(fn));
            }
        };
    }

    //foldM (Control.Monad)
    public default <A, B> F2<A, List<B>, _<mu, A>> foldM(final F1<A, F1<B, _<mu, A>>> fn) {
        return new F2<A, List<B>, _<mu, A>>() {
            @Override
            public _<mu, A> $(A a, List<B> listB) {
                _<mu, A> result = pure(a);
                final Mutable<B> b = new Mutable<B>();
                F1<A, _<mu, A>> fnBind = new F1<A, _<mu, A>>() {
                    @Override
                    public _<mu, A> $(A a) {
                        return fn.$(a).$(b.get());
                    }
                };
                while (!listB.isEmpty()) {
                    b.set(listB.head());
                    listB = listB.tail();
                    result = bind(result, fnBind);
                }
                return result;
            }
        };
    }

    //foldM_ (Control.Monad)
    public default <A, B> F2<A, List<B>, _<mu, T0>> foldM_(final F1<A, F1<B, _<mu, A>>> fn) {
        return new F2<A, List<B>, _<mu, T0>>() {
            @Override
            public _<mu, T0> $(A a, List<B> listB) {
                _<mu, A> result = pure(a);
                final Mutable<B> b = Mutable.Mutable();
                F1<A, _<mu, A>> fnBind = new F1<A, _<mu, A>>() {
                    @Override
                    public _<mu, A> $(A a) {
                        return fn.$(a).$(b.get());
                    }
                };
                while (!listB.isEmpty()) {
                    b.set(listB.head());
                    listB = listB.tail();
                    result = bind(result, fnBind);
                }
                return pure(T0.unit);
            }
        };
    }

    //replicateM (Control.Monad)
    public default <A> _<mu, List<A>> replicateM(int n, _<mu, A> nestedA) {
        return sequence(List.replicate(n, nestedA));
    }

    //replicateM_ (Control.Monad)
    public default <A> _<mu, T0> replicateM_(int n, _<mu, A> nestedA) {
        return sequence_(List.replicate(n, nestedA));
    }

    //sequence (Control.Monad)
    public default <A> _<mu, List<A>> sequence(List<_<mu, A>> list) {
        //  sequence ms = foldr (liftM2 (:)) (return []) ms
        F2<_<mu, A>, _<mu, List<A>>, _<mu, List<A>>> f2 = lift2(List.<A>cons());
        return list.foldr(f2, pure(List.<A>Nil()));
    }

    //sequence_ (Control.Monad)
    public default <A> _<mu, T0> sequence_(List<_<mu, A>> list) {
        sequence(list);
        return pure(T0.unit);
    }

}
