package org.highj.typeclass1.monad;

import org.highj._;
import org.highj.data.collection.List;
import org.highj.data.tuple.T0;
import org.highj.util.Mutable;

import java.util.function.Function;

public interface Monad<M> extends Applicative<M>, Bind<M> {

    // mapM (Control.Monad)
    public default <A, B> Function<List<A>, _<M, List<B>>> mapM(final Function<A, _<M, B>> fn) {
        return list -> sequence(list.map(fn));
    }

    // mapM_ (Control.Monad)
    public default <A, B> Function<List<A>, _<M, T0>> mapM_(final Function<A, _<M, B>> fn) {
        return list -> sequence_(list.map(fn));
    }

    //foldM (Control.Monad)
    public default <A, B> Function<A, Function<List<B>, _<M, A>>> foldM(final Function<A, Function<B, _<M, A>>> fn) {
        return a -> listB -> {
            _<M, A> result = pure(a);
            final Mutable<B> b = new Mutable<>();
            Function<A, _<M, A>> fnBind = x -> fn.apply(x).apply(b.get());
            while (!listB.isEmpty()) {
                b.set(listB.head());
                listB = listB.tail();
                result = bind(result, fnBind);
            }
            return result;
        };
    }

    //foldM_ (Control.Monad)
    public default <A, B> Function<A, Function<List<B>, _<M, T0>>> foldM_(final Function<A, Function<B, _<M, A>>> fn) {
        return a -> listB -> {
            _<M, A> result = pure(a);
            final Mutable<B> b = Mutable.Mutable();
            Function<A, _<M, A>> fnBind = x -> fn.apply(x).apply(b.get());
            while (!listB.isEmpty()) {
                b.set(listB.head());
                listB = listB.tail();
                result = bind(result, fnBind);
            }
            return pure(T0.unit);
        };
    }

    //replicateM (Control.Monad)
    public default <A> _<M, List<A>> replicateM(int n, _<M, A> nestedA) {
        return sequence(List.replicate(n, nestedA));
    }

    //replicateM_ (Control.Monad)
    public default <A> _<M, T0> replicateM_(int n, _<M, A> nestedA) {
        return sequence_(List.replicate(n, nestedA));
    }

    //sequence (Control.Monad)
    public default <A> _<M, List<A>> sequence(List<_<M, A>> list) {
        //  sequence ms = foldr (liftM2 (:)) (return []) ms
        Function<_<M, A>, Function<_<M, List<A>>, _<M, List<A>>>> f2 = lift2((A a) -> (List<A> as) -> List.<A>cons(a, as));
        return list.foldr(x -> y -> f2.apply(x).apply(y), pure(List.<A>nil()));
    }

    //sequence_ (Control.Monad)
    public default <A> _<M, T0> sequence_(List<_<M, A>> list) {
        sequence(list);
        return pure(T0.unit);
    }

    @Override
    public default <A, B> _<M, B> ap(_<M, Function<A, B>> fn, _<M, A> nestedA) {
        //ap mf m = do f <- mf; x <- m; return (f x)
        return bind(fn, f -> this.<A,B>bind(nestedA, x -> this.<B>pure(f.apply(x))));
    }

}
