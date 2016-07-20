package org.highj.optic;

import java.util.function.Function;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Either;
import org.highj.function.F1;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass2.arrow.Arrow;

/**
 * A {@link Getter} can be seen as a glorified get method between a type S and a type A.
 *
 * A {@link Getter} is also a valid {@link Fold}
 *
 * @param <S> the source of a {@link Getter}
 * @param <A> the target of a {@link Getter}
 */
public abstract class Getter<S, A> implements __2<Getter.µ, S, A> {

    public static final class µ {
    }

    Getter() {
        super();
    }

    /** Get the target of a {@link Getter}
     * @param s the source value
     * @return the target value
     */
    public abstract A get(S s);

    /** Join two {@link Getter} with the same target
     *  @param other the second {@link Getter}
     *  @param <S1> the source type of the second {@link Getter}
     *  @return the combined {@link Getter}
     */
    public final <S1> Getter<Either<S, S1>, A> sum(final Getter<S1, A> other) {
        return getter(e -> e.either(this::get, other::get));
    }

    /** Pair two disjoint {@link Getter}s
     *  @param other the second {@link Getter}
     *  @param <S1> the source type of the second {@link Getter}
     *  @param <A1> the target type of the second {@link Getter}
     *  @return the combined {@link Getter}
     */
    public final <S1, A1> Getter<T2<S, S1>, T2<A, A1>> product(final Getter<S1, A1> other) {
        return getter(t2 -> T2.of(this.get(t2._1()), other.get(t2._2())));
    }

    public final <B> Getter<T2<S, B>, T2<A, B>> first() {
        return getter(p -> T2.of(this.get(p._1()), p._2()));
    }

    public final <B> Getter<T2<B, S>, T2<B, A>> second() {
        return getter(p -> T2.of(p._1(), this.get(p._2())));
    }

    /* ************************************************************/
    /* * Compose methods between a {@link Getter} and another Optics */
    /* ************************************************************/

    /** Compose a {@link Getter} with a {@link Fold}
     * @param other the {@link Fold}
     * @param <B> the target type of the {@link Fold}
     * @return the composed {@link Getter}
     */
    public final <B> Fold<S, B> composeFold(final Fold<A, B> other) {
        return asFold().composeFold(other);
    }

    /** Compose a {@link Getter} with another {@link Getter}
     * @param other the second {@link Getter}
     * @param <B> the target type of the second {@link Getter}
     * @return the composed {@link Getter}
     */
    public final <B> Getter<S, B> composeGetter(final Getter<A, B> other) {
        return getter(s -> other.get(get(s)));
    }

    /** Compose a {@link Getter} with a {@link POptional}
     * @param other the {@link POptional}
     * @param <B> the modified source type of the {@link POptional}
     * @param <C> the target type of the {@link POptional}
     * @param <D> the modified target type of the {@link POptional}
     * @return the composed {@link Getter}
     */
    public final <B, C, D> Fold<S, C> composeOptional(final POptional<A, B, C, D> other) {
        return asFold().composeOptional(other);
    }

    /** Compose a {@link Getter} with a {@link PPrism}
     * @param other the {@link PPrism}
     * @param <B> the modified source type of the {@link PPrism}
     * @param <C> the target type of the {@link PPrism}
     * @param <D> the modified target type of the {@link PPrism}
     * @return the composed {@link Getter}
     */
    public final <B, C, D> Fold<S, C> composePrism(final PPrism<A, B, C, D> other) {
        return asFold().composePrism(other);
    }

    /** Compose a {@link Getter} with a {@link PLens}
     * @param other the {@link PLens}
     * @param <B> the modified source type of the {@link PLens}
     * @param <C> the target type of the {@link PLens}
     * @param <D> the modified target type of the {@link PLens}
     * @return the composed {@link Getter}
     */
    public final <B, C, D> Getter<S, C> composeLens(final PLens<A, B, C, D> other) {
        return composeGetter(other.asGetter());
    }

    /** Compose a {@link Getter} with a {@link PIso}
     * @param other the {@link PIso}
     * @param <B> the modified source type of the {@link PIso}
     * @param <C> the target type of the {@link PIso}
     * @param <D> the modified target type of the {@link PIso}
     * @return the composed {@link Getter}
     */
    public final <B, C, D> Getter<S, C> composeIso(final PIso<A, B, C, D> other) {
        return composeGetter(other.asGetter());
    }

    /* *********************************************************************/
    /* * Transformation methods to view a {@link Getter} as another Optics */
    /* *********************************************************************/

    /** view a {@link Getter} with a {@link Fold}
     * @return the {@link Fold}
     */
    public final Fold<S, A> asFold() {
        return new Fold<S, A>() {
            @Override
            public <B> F1<S, B> foldMap(final Monoid<B> m, final Function<A, B> f) {
                return s -> f.apply(get(s));
            }
        };
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <S, A> Getter<S, A> narrow(final __<__<µ, S>, A> value) {
        return (Getter) value;
    }

    public static <A> Getter<A, A> id() {
        return PIso.<A, A> pId().asGetter();
    }

    public static final <A> Getter<Either<A, A>, A> codiagonal() {
        return getter(e -> e.either(F1.id(), F1.id()));
    }

    public static final <S, A> Getter<S, A> getter(final Function<S, A> get) {
        return new Getter<S, A>() {

            @Override
            public A get(final S s) {
                return get.apply(s);
            }
        };
    }

    public static final Arrow<Getter.µ> getterArrow = new Arrow<Getter.µ>() {

        @Override
        public <B, C, D> __2<µ, B, D> dot(final __2<µ, C, D> cd, final __2<µ, B, C> bc) {
            return narrow(bc).composeGetter(narrow(cd));
        }

        @Override
        public <B> __2<µ, B, B> identity() {
            return id();
        }

        @Override
        public <B, C, D> __2<µ, T2<B, D>, T2<C, D>> first(final __2<µ, B, C> arrow) {
            return narrow(arrow).first();
        }

        @Override
        public <B, C, D> __2<µ, T2<D, B>, T2<D, C>> second(final __2<µ, B, C> arrow) {
            return narrow(arrow).second();
        }

        @Override
        public <B, C> __2<µ, B, C> arr(final Function<B, C> fn) {
            return getter(fn);
        }

        @Override
        public <B, C, BB, CC> __2<µ, T2<B, BB>, T2<C, CC>> split(final __2<µ, B, C> arr1,
                final __2<µ, BB, CC> arr2) {
            return narrow(arr1).product(narrow(arr2));
        }
    };
}
