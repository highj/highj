package org.highj.typeclass1.monad;

import org.derive4j.hkt.__;
import org.highj.data.List;
import org.highj.data.tuple.T0;
import org.highj.util.Mutable;

import java.util.function.Function;

/**
 * @param <M> the monadic type
 * @author Daniel Gronau
 * @author Clinton Selke
 */
public interface Monad<M> extends Applicative<M>, Bind<M> {

    // mapM (Control.Monad)
    default <A, B> Function<List<A>, __<M, List<B>>> mapM(final Function<A, __<M, B>> fn) {
        return list -> sequence(list.map(fn));
    }

    // mapM_ (Control.Monad)
    default <A, B> Function<List<A>, __<M, T0>> mapM_(final Function<A, __<M, B>> fn) {
        return list -> sequence_(list.map(fn));
    }

    //foldM (Control.Monad)
    default <A, B> Function<A, Function<List<B>, __<M, A>>> foldM(final Function<A, Function<B, __<M, A>>> fn) {
        return a -> listB -> {
            __<M, A> result = pure(a);
            final Mutable<B> b = new Mutable<>();
            Function<A, __<M, A>> fnBind = x -> fn.apply(x).apply(b.get());
            while (!listB.isEmpty()) {
                b.set(listB.head());
                listB = listB.tail();
                result = bind(result, fnBind);
            }
            return result;
        };
    }

    //foldM_ (Control.Monad)
    default <A, B> Function<A, Function<List<B>, __<M, T0>>> foldM_(final Function<A, Function<B, __<M, A>>> fn) {
        return a -> listB -> {
            __<M, A> result = pure(a);
            final Mutable<B> b = Mutable.newMutable();
            Function<A, __<M, A>> fnBind = x -> fn.apply(x).apply(b.get());
            while (!listB.isEmpty()) {
                b.set(listB.head());
                listB = listB.tail();
                result = bind(result, fnBind);
            }
            return pure(T0.unit);
        };
    }

    //replicateM (Control.Monad)
    default <A> __<M, List<A>> replicateM(int n, __<M, A> nestedA) {
        return sequence(List.replicate(n, nestedA));
    }

    //replicateM_ (Control.Monad)
    default <A> __<M, T0> replicateM_(int n, __<M, A> nestedA) {
        return sequence_(List.replicate(n, nestedA));
    }

    //sequence (Control.Monad)
    default <A> __<M, List<A>> sequence(List<__<M, A>> list) {
        //  sequence ms = foldr (liftM2 (:)) (return []) ms
        Function<__<M, A>, Function<__<M, List<A>>, __<M, List<A>>>> f2 = lift2((A a) -> (List<A> as) -> List.<A>Cons(a, as));
        return list.foldr((x, y) -> f2.apply(x).apply(y), pure(List.<A>Nil()));
    }

    //sequence_ (Control.Monad)
    default <A> __<M, T0> sequence_(List<__<M, A>> list) {
        return list.foldr((ma, mu) -> this.rightSeq(ma, mu), pure(T0.of()));
    }

}