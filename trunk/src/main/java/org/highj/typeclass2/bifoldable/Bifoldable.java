package org.highj.typeclass2.bifoldable;

/*
class Bifoldable p where
  bifold :: Monoid m => p m m -> m
  bifold = bifoldMap id id
  {-# INLINE bifold #-}

  bifoldMap :: Monoid m => (a -> m) -> (b -> m) -> p a b -> m
  bifoldMap f g = bifoldr (mappend . f) (mappend . g) mempty
  {-# INLINE bifoldMap #-}

  bifoldr :: (a -> c -> c) -> (b -> c -> c) -> c -> p a b -> c
  bifoldr f g z t = appEndo (bifoldMap (Endo . f) (Endo . g) t) z
  {-# INLINE bifoldr #-}

  bifoldl :: (c -> a -> c) -> (c -> b -> c) -> c -> p a b -> c
  bifoldl f g z t = appEndo (getDual (bifoldMap (Dual . Endo . flip f) (Dual . Endo . flip g) t)) z
  {-# INLINE bifoldl #-}
* */


import org.highj.__;
import org.highj.data.functions.Functions;
import org.highj.typeclass0.group.Monoid;

import java.util.function.Function;

public interface Bifoldable<P> {

    public default <M> M bifold(Monoid<M> monoid, __<P, M, M> nestedM) {
        //bifold :: Monoid m => p m m -> m
        //bifold = bifoldMap id id
        return bifoldMap(monoid, Function.identity(), Function.identity(), nestedM);
    }

    public default <M, A, B> M bifoldMap(Monoid<M> monoid, Function<A,M> fn1, Function<B,M> fn2, __<P,A,B> nestedAB) {
        //bifoldMap :: Monoid m => (a -> m) -> (b -> m) -> p a b -> m
        //bifoldMap f g = bifoldr (mappend . f) (mappend . g) mempty
        return bifoldr(a -> m -> monoid.dot(fn1.apply(a), m), b -> m -> monoid.dot(fn2.apply(b), m), monoid.identity(), nestedAB);
    }

    public default <A,B,C> C bifoldr(Function<A, Function<C,C>> fn1, Function<B, Function<C,C>> fn2, C start, __<P,A,B> nestedAB) {
        //bifoldr :: (a -> c -> c) -> (b -> c -> c) -> c -> p a b -> c
        //bifoldr f g z t = appEndo (bifoldMap (Endo . f) (Endo . g) t) z
        //return bifoldMap(Endo.monoid(), a -> new Endo<>(fn1.apply(a)), b -> new Endo<>(fn2.apply(b)), nestedAB).appEndo(start);
        return bifoldMap(Functions.endoMonoid(), a -> fn1.apply(a), b -> fn2.apply(b), nestedAB).apply(start);
    }

    public default <A,B,C> C bifoldl(Function<C, Function<A,C>> fn1, Function<C, Function<B,C>> fn2, C start, __<P,A,B> nestedAB) {
        //bifoldl :: (c -> a -> c) -> (c -> b -> c) -> c -> p a b -> c
        //bifoldl f g z t = appEndo (getDual (bifoldMap (Dual . Endo . flip f) (Dual . Endo . flip g) t)) z
        return this.<Function<C,C>,A,B>bifoldMap(Monoid.dual(Functions.<C>endoMonoid()),
                a -> c -> fn1.apply(c).apply(a),
                b -> c -> fn2.apply(c).apply(b),
                nestedAB).apply(start);
    }
}
