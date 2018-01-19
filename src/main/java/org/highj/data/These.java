package org.highj.data;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.instance.these.TheseBifunctor;
import org.highj.data.instance.these.TheseFunctor;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.monad.Applicative;

import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class These<A, B> implements __2<These.µ, A, B> {
    private These() {
    }

    public interface µ {
    }

    /**
     * The catamorphism of These.
     *
     * @param <C> the result type
     * @return the result
     */
    public abstract <C> C these(Function<A, C> thisFn, Function<B, C> thatFn, BiFunction<A, B, C> bothFn);

    /**
     * Tests if the These is a This
     *
     * @return test result
     */
    public boolean isThis() {
        return these(a -> true, b -> false, (a, b) -> false);
    }

    /**
     * Tests if the These is a That
     *
     * @return test result
     */
    public boolean isThat() {
        return these(a -> false, b -> true, (a, b) -> false);
    }

    /**
     * Test if the These is a Both
     *
     * @return test result
     */
    public boolean isBoth() {
        return these(a -> false, b -> false, (a, b) -> true);
    }

    public A getFirst() throws NoSuchElementException {
        return these(a -> a,
                b -> {
                    throw new NoSuchElementException();
                },
                (a, b) -> a);
    }

    public B getSecond() throws NoSuchElementException {
        return these(a -> {
                    throw new NoSuchElementException();
                },
                b -> b,
                (a, b) -> b);
    }

    public T2<A, B> getBoth() throws NoSuchElementException {
        return these(a -> {
                    throw new NoSuchElementException();
                },
                b -> {
                    throw new NoSuchElementException();
                },
                T2::of);
    }

    /**
     * Constructs a This instance
     *
     * @param a   the value
     * @param <A> the first element type
     * @param <B> the second element type
     * @return the This instance
     */
    public static <A, B> These<A, B> This(A a) {
        return new These<A, B>() {
            @Override
            public <C> C these(Function<A, C> thisFn, Function<B, C> thatFn, BiFunction<A, B, C> bothFn) {
                return thisFn.apply(a);
            }
        };
    }

    /**
     * Constructs a That instance
     *
     * @param b   the value
     * @param <A> the first element type
     * @param <B> the second element type
     * @return the That instance
     */
    public static <A, B> These<A, B> That(B b) {
        return new These<A, B>() {
            @Override
            public <C> C these(Function<A, C> thisFn, Function<B, C> thatFn, BiFunction<A, B, C> bothFn) {
                return thatFn.apply(b);
            }
        };
    }

    /**
     * Constructs a Both instance
     *
     * @param a   the first value
     * @param b   the second value
     * @param <A> this first element type
     * @param <B> this second element type
     * @return the Both instance
     */
    public static <A, B> These<A, B> Both(A a, B b) {
        return new These<A, B>() {
            @Override
            public <C> C these(Function<A, C> thisFn, Function<B, C> thatFn, BiFunction<A, B, C> bothFn) {
                return bothFn.apply(a, b);
            }
        };
    }

    /**
     * Takes two default values and produces a tuple.
     *
     * @param defaultA default value for A
     * @param defaultB default value for B
     * @return the tuple
     */
    public T2<A, B> fromThese(A defaultA, B defaultB) {
        return these(a -> T2.of(a, defaultB),
                b -> T2.of(defaultA, b),
                T2::of);
    }

    /**
     * Coalesce with the provided operation.
     *
     * @param these   the These instance
     * @param mergeFn the merge function (if it is a Both)
     * @param <A>     the result type
     * @return the result
     */
    public static <A> A mergeThese(These<A, A> these, BiFunction<A, A, A> mergeFn) {
        return these.these(a -> a, b -> b, mergeFn);
    }

    /**
     * Bimap and coalesce results with the provided operation.
     *
     * @param aFn     the first map function
     * @param bFn     the second map function
     * @param mergeFn the merge function (if it is a Both)
     * @param <C>     the result type
     * @return the result
     */
    public <C> C mergeTheseWith(Function<A, C> aFn, Function<B, C> bFn, BiFunction<C, C, C> mergeFn) {
        return mergeThese(mapThese(aFn, bFn), mergeFn);
    }

    /**
     * Extracts a value from a This
     *
     * @return maybe the value
     */
    public Maybe<A> justThis() {
        return these(Maybe::Just,
                b -> Maybe.<A> Nothing(),
                (a, b) -> Maybe.<A> Nothing());
    }

    /**
     * Extracts a value from a That
     *
     * @return maybe the value
     */
    public Maybe<B> justThat() {
        return these(a -> Maybe.<B> Nothing(),
                Maybe::Just,
                (a, b) -> Maybe.<B> Nothing());
    }

    /**
     * Extracts the values from a Both
     *
     * @return maybe the values
     */
    public Maybe<T2<A, B>> justBoth() {
        return these(b -> Maybe.<T2<A, B>> Nothing(),
                b -> Maybe.<T2<A, B>> Nothing(),
                (a, b) -> Maybe.Just(T2.of(a, b)));
    }

    /**
     * Bimap over the instance
     *
     * @param aFn  the first map function
     * @param bFn  the second map function
     * @param <A1> the new first element type
     * @param <B1> the new second element type
     * @return the result of the mapping
     */
    public <A1, B1> These<A1, B1> mapThese(Function<A, A1> aFn, Function<B, B1> bFn) {
        return these(a -> This(aFn.apply(a)),
                b -> That(bFn.apply(b)),
                (a, b) -> Both(aFn.apply(a), bFn.apply(b)));
    }

    /**
     * Map over the first value
     *
     * @param aFn  the first map function
     * @param <A1> the new first element type
     * @return the result of the mapping
     */
    public <A1> These<A1, B> mapThis(Function<A, A1> aFn) {
        return these(a -> This(aFn.apply(a)),
                These::<A1, B>That,
                (a, b) -> Both(aFn.apply(a), b));
    }

    /**
     * Map over the second value
     *
     * @param bFn  the second map function
     * @param <B1> the new second element type
     * @return the result of the mapping
     */
    public <B1> These<A, B1> mapThat(Function<B, B1> bFn) {
        return these(These::This,
                b -> That(bFn.apply(b)),
                (a, b) -> Both(a, bFn.apply(b)));
    }

    public These<B, A> flip() {
        return these(These::That, These::This, (a, b) -> Both(b, a));
    }

    public <F, A1> __<F, These<A1, B>> here(Applicative<F> applicative, Function<A, __<F, A1>> fn) {
        return these(a -> applicative.map(These::This, fn.apply(a)),
                b -> applicative.pure(These.That(b)),
                (a, b) -> applicative.map(a1 -> Both(a1, b), fn.apply(a)));
    }

    public <F, B1> __<F, These<A, B1>> there(Applicative<F> applicative, Function<B, __<F, B1>> fn) {
        return these(a -> applicative.pure(These.This(a)),
                b -> applicative.map(These::That, fn.apply(b)),
                (a, b) -> applicative.map(b1 -> Both(a, b1), fn.apply(b)));
    }

    public static <A> List<A> catThis(List<These<A, ?>> list) {
        return list.filter(These::isThis).map(These::getFirst);
    }

    public static <B> List<B> catThat(List<These<?, B>> list) {
        return list.filter(These::isThat).map(These::getSecond);
    }

    public static <A> List<A> catFirst(List<These<A, ?>> list) {
        return list.filter(t -> !t.isThat()).map(These::getFirst);
    }

    public static <B> List<B> catSecond(List<These<?, B>> list) {
        return list.filter(t -> !t.isThis()).map(These::getSecond);
    }

    public static <A, B> List<T2<A, B>> catBoth(List<These<A, B>> list) {
        return list.filter(These::isBoth).map(These::getBoth);
    }

    public static <A, B> Semigroup<These<A, B>> semigroup(Semigroup<A> semigroupA, Semigroup<B> semigroupB) {
        return (t1, t2) ->
                t1.these(
                        a1 -> t2.these(
                                a2 -> This(semigroupA.apply(a1, a2)),
                                b2 -> Both(a1, b2),
                                (a2, b2) -> Both(semigroupA.apply(a1, a2), b2)),
                        b1 -> t2.these(
                                a2 -> Both(a2, b1),
                                b2 -> That(semigroupB.apply(b1, b2)),
                                (a2, b2) -> Both(a2, semigroupB.apply(b1, b2))),
                        (a1, b1) -> t2.these(
                                a2 -> Both(semigroupA.apply(a1, a2), b1),
                                b2 -> Both(a1, semigroupB.apply(b1, b2)),
                                (a2, b2) -> Both(semigroupA.apply(a1, a2), semigroupB.apply(b1, b2)))
                );
    }

    public static <F> TheseFunctor<F> functor() {
        return new TheseFunctor<F>() {
        };
    }

    public final TheseBifunctor bifunctor = new TheseBifunctor() {
    };


/*


-- | Select each constructor and partition them into separate lists.

instance Foldable (These a) where
    foldr _ z (This _) = z
    foldr f z (That x) = f x z
    foldr f z (These _ x) = f x z

instance Traversable (These a) where
    traverse _ (This a) = pure $ This a
    traverse f (That x) = That <$> f x
    traverse f (These a x) = These a <$> f x
    sequenceA (This a) = pure $ This a
    sequenceA (That x) = That <$> x
    sequenceA (These a x) = These a <$> x

instance Bifoldable These where
    bifold = these id id mappend
    bifoldr f g z = these (`f` z) (`g` z) (\x y -> x `f` (y `g` z))
    bifoldl f g z = these (z `f`) (z `g`) (\x y -> (z `f` x) `g` y)

instance Bifoldable1 These where
    bifold1 = these id id (<>)

instance Bitraversable These where
    bitraverse f _ (This x) = This <$> f x
    bitraverse _ g (That x) = That <$> g x
    bitraverse f g (These x y) = These <$> f x <*> g y

instance Bitraversable1 These where
    bitraverse1 f _ (This x) = This <$> f x
    bitraverse1 _ g (That x) = That <$> g x
    bitraverse1 f g (These x y) = These <$> f x <.> g y

instance (Semigroup a) => Apply (These a) where
    This  a   <.> _         = This a
    That    _ <.> This  b   = This b
    That    f <.> That    x = That (f x)
    That    f <.> These b x = These b (f x)
    These a _ <.> This  b   = This (a <> b)
    These a f <.> That    x = These a (f x)
    These a f <.> These b x = These (a <> b) (f x)

instance (Semigroup a) => Applicative (These a) where
    pure = That
    (<*>) = (<.>)

instance (Semigroup a) => Bind (These a) where
    This  a   >>- _ = This a
    That    x >>- k = k x
    These a x >>- k = case k x of
                          This  b   -> This  (a <> b)
                          That    y -> These a y
                          These b y -> These (a <> b) y

instance (Semigroup a) => Monad (These a) where
    return = pure
    (>>=) = (>>-)


instance (FromJSON a, FromJSON b) => FromJSON (These a b) where
    parseJSON = Aeson.withObject "These a b" (p . HM.toList)
      where
        p [("This", a), ("That", b)] = These <$> parseJSON a <*> parseJSON b
        p [("That", b), ("This", a)] = These <$> parseJSON a <*> parseJSON b
        p [("This", a)] = This <$> parseJSON a
        p [("That", b)] = That <$> parseJSON b
        p _  = fail "Expected object with 'This' and 'That' keys only"

instance Arbitrary2 These where
    liftArbitrary2 arbA arbB = oneof
        [ This <$> arbA
        , That <$> arbB
        , These <$> arbA <*> arbB
        ]

    liftShrink2  shrA _shrB (This x) = This <$> shrA x
    liftShrink2 _shrA  shrB (That y) = That <$> shrB y
    liftShrink2  shrA  shrB (These x y) =
        [This x, That y] ++ [These x' y' | (x', y') <- liftShrink2 shrA shrB (x, y)]

instance (Arbitrary a) => Arbitrary1 (These a) where
    liftArbitrary = liftArbitrary2 arbitrary
    liftShrink = liftShrink2 shrink

instance (Arbitrary a, Arbitrary b) => Arbitrary (These a b) where
    arbitrary = arbitrary1
    shrink = shrink1

instance (Function a, Function b) => Function (These a b) where
  function = functionMap g f
    where
      g (This a)    = Left a
      g (That b)    = Right (Left b)
      g (These a b) = Right (Right (a, b))

      f (Left a)               = This a
      f (Right (Left b))       = That b
      f (Right (Right (a, b))) = These a b

instance (CoArbitrary a, CoArbitrary b) => CoArbitrary (These a b)
    * */
}
