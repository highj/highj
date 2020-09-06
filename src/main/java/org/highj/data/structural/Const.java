package org.highj.data.structural;

import org.derive4j.hkt.HktConfig;
import org.derive4j.hkt.__2;
import org.highj.data.eq.Eq;
import org.highj.data.eq.Eq1;
import org.highj.data.structural.constant.*;
import org.highj.typeclass0.group.Group;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.contravariant.Contravariant;
import org.highj.typeclass1.contravariant.Divisible;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.monad.Apply;
import org.highj.typeclass2.bifoldable.Bifoldable;
import org.highj.typeclass2.bifunctor.Biapplicative;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * The Const functor.
 * <p>
 * It can be seen as a wrapper type with an additional phantom type attached.
 *
 * @param <S> the constant type
 * @param <A> the phantom type
 */
@HktConfig(typeEqMethodName = "const_")
public class Const<S, A> implements __2<Const.µ, S, A>, Supplier<S> {

    public interface µ {
    }

    private final S value;

    private Const(S value) {
        this.value = value;
    }

    /**
     * The constructor method.
     *
     * @param value the constant value
     * @param <S>   the constant type
     * @param <A>   the phantom type
     * @return the {@link Const}
     */
    public static <S, A> Const<S, A> Const(S value) {
        return new Const<>(value);
    }

    public S get() {
        return value;
    }

    /**
     * Maps the wrapped value to another type, but keeps the phantom type.
     *
     * @param <T> the new constant type
     * @return the {@link Const}
     */
    public <T> Const<T, A> map1(Function<S, T> fn) {
        return Const(fn.apply(value));
    }

    /**
     * Creates an {@link Eq} instance for {@link Const}.
     *
     * @param eqS the {@link Eq} of the constant type
     * @param <S> the constant type
     * @param <A> the phantom type
     * @return the instance
     */
    public static <S, A> Eq<Const<S, A>> eq(Eq<? super S> eqS) {
        return (one, two) -> eqS.eq(one.get(), two.get());
    }

    /**
     * The {@link Functor} instance.
     *
     * @param <S> the constant type
     * @return the instance
     */
    public static <S> ConstFunctor<S> functor() {
        return new ConstFunctor<S>() {
        };
    }

    /**
     * The {@link Apply} instance.
     *
     * @param <S> the constant type
     * @return the instance
     */
    public static <S> ConstApply<S> apply(final Semigroup<S> semigroup) {
        return () -> semigroup;
    }

    /**
     * The {@link Applicative} instance.
     *
     * @param <S> the constant type
     * @return the instance
     */
    public static <S> ConstApplicative<S> applicative(final Monoid<S> monoid) {
        return () -> monoid;
    }

    /**
     * The {@link Contravariant} instance.
     *
     * @param <S> the constant type
     * @return the instance
     */
    public static <S> ConstContravariant<S> contravariant() {
        return new ConstContravariant<S>() {
        };
    }

    /**
     * The {@link Divisible} instance.
     *
     * @param <S> the constant type
     * @return the instance
     */
    public static <S> ConstDivisible<S> divisible(Monoid<S> monoid) {
        return () -> monoid;
    }

    /**
     * The {@link Biapplicative} instance.
     */
    public static final ConstBiapplicative biapplicative = new ConstBiapplicative() {
    };

    /**
     * The {@link Eq1} instance.
     * Note that the argument for the {@link Eq1#eq1} method is ignored, so you can pass null.
     *
     * @param <S> the constant type
     * @return the instance
     */
    public static <S> ConstEq1<S> eq1(Eq<? super S> eqS) {
        return () -> eqS;
    }

    /**
     * The {@link Semigroup} instance.
     *
     * @param semigroup the semigroup of the constant type
     * @param <S>       the constant type
     * @param <A>       the phantom type
     * @return the instance
     */
    public static <S, A> ConstSemigroup<S, A> semigroup(Semigroup<S> semigroup) {
        return () -> semigroup;
    }

    /**
     * The {@link Monoid} instance.
     *
     * @param monoid the monoid of the constant type
     * @param <S>    the constant type
     * @param <A>    the phantom type
     * @return the instance
     */
    public static <S, A> ConstMonoid<S, A> monoid(Monoid<S> monoid) {
        return () -> monoid;
    }

    /**
     * The {@link Group} instance.
     *
     * @param group the group of the constant type
     * @param <S>   the constant type
     * @param <A>   the phantom type
     * @return the instance
     */
    public static <S, A> ConstGroup<S, A> group(Group<S> group) {
        return () -> group;
    }

    //note that this method can't check for different A type parameters
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Const)) return false;
        Const<?, ?> aConst = (Const<?, ?>) o;
        return value.equals(aConst.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    /**
     * The {@link Bifoldable} instance of {@link Const}
     */
    public static final ConstBifoldable bifoldable = new ConstBifoldable() {
    };
}
