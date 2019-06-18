package org.highj.data.structural;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.derive4j.hkt.__3;
import org.highj.data.structural.dual.DualCategory;
import org.highj.data.structural.dual.DualGroup;
import org.highj.data.structural.dual.DualMonoid;
import org.highj.data.structural.dual.DualSemigroup;
import org.highj.typeclass0.group.Group;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass2.arrow.Category;

import java.util.Objects;

import static org.highj.Hkt.asDual;

public class Dual<M, A, B> implements __3<Dual.µ, M, A, B> {

    public interface µ {
    }

    private final __2<M, B, A> value;

    public Dual(__2<M, B, A> value) {
        this.value = value;
    }

    public __2<M, B, A> get() {
        return value;
    }

    public static <M, A, B> Dual<M, A, B> of(__2<M, B, A> value) {
        return new Dual<>(value);
    }

    public static <M, A, B> __2<M, B, A> get(__<__<__<µ, M>, A>, B> dual) {
        return asDual(dual).get();
    }

    public static <M, A, B> DualSemigroup<M, A, B> semigroup(Semigroup<__2<M, B, A>> mSemigroup) {
        return () -> mSemigroup;
    }

    public static <M, A, B> DualMonoid<M, A, B> monoid(Monoid<__2<M, B, A>> mMonoid) {
        return () -> mMonoid;
    }

    public static <M, A, B>DualGroup<M, A, B> group(Group<__2<M, B, A>> mGroup) {
        return () -> mGroup;
    }

    public static <M> DualCategory<M> category(final Category<M> category) {
        return () -> category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dual<?, ?, ?> dual = (Dual<?, ?, ?>) o;
        return value.equals(dual.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Dual(" + value.toString() + ")";
    }
}
