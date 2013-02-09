package org.highj.typeclass1.monad;

import org.highj._;
import org.highj.data.collection.List;
import org.highj.data.tuple.T0;
import org.highj.util.Mutable;

import java.util.function.Function;

public interface Monad<µ> extends Applicative<µ>, Bind<µ> {

    // mapM (Control.Monad)
    public default <A, B> Function<List<A>, _<µ, List<B>>> mapM(final Function<A, _<µ, B>> fn) {
        return list -> sequence(list.map(fn));
    }

    // mapM_ (Control.Monad)
    public default <A, B> Function<List<A>, _<µ, T0>> mapM_(final Function<A, _<µ, B>> fn) {
        return list -> sequence_(list.map(fn));
    }

    //foldM (Control.Monad)
    public default <A, B> Function<A, Function<List<B>, _<µ, A>>> foldM(final Function<A, Function<B, _<µ, A>>> fn) {
        return a -> listB -> {
            _<µ, A> result = pure(a);
            final Mutable<B> b = new Mutable<>();
            Function<A, _<µ, A>> fnBind = x -> fn.apply(x).apply(b.get());
            while (!listB.isEmpty()) {
                b.set(listB.head());
                listB = listB.tail();
                result = bind(result, fnBind);
            }
            return result;
        };
    }

    //foldM_ (Control.Monad)
    public default <A, B> Function<A, Function<List<B>, _<µ, T0>>> foldM_(final Function<A, Function<B, _<µ, A>>> fn) {
        return a -> listB -> {
            _<µ, A> result = pure(a);
            final Mutable<B> b = Mutable.Mutable();
            Function<A, _<µ, A>> fnBind = x -> fn.apply(x).apply(b.get());
            while (!listB.isEmpty()) {
                b.set(listB.head());
                listB = listB.tail();
                result = bind(result, fnBind);
            }
            return pure(T0.unit);
        };
    }

    //replicateM (Control.Monad)
    public default <A> _<µ, List<A>> replicateM(int n, _<µ, A> nestedA) {
        return sequence(List.replicate(n, nestedA));
    }

    //replicateM_ (Control.Monad)
    public default <A> _<µ, T0> replicateM_(int n, _<µ, A> nestedA) {
        return sequence_(List.replicate(n, nestedA));
    }

    //sequence (Control.Monad)
    public default <A> _<µ, List<A>> sequence(List<_<µ, A>> list) {
        //  sequence ms = foldr (liftM2 (:)) (return []) ms
        Function<_<µ, A>, Function<_<µ, List<A>>, _<µ, List<A>>>> f2 = lift2((A a) -> (List<A> as) -> List.<A>cons(a, as));
        return list.foldr(x -> y -> f2.apply(x).apply(y), pure(List.<A>nil()));
    }

    //sequence_ (Control.Monad)
    public default <A> _<µ, T0> sequence_(List<_<µ, A>> list) {
        sequence(list);
        return pure(T0.unit);
    }

}
