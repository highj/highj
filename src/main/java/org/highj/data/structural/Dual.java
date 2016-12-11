package org.highj.data.structural;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.derive4j.hkt.__3;
import org.highj.Hkt;
import org.highj.data.structural.dual.DualCategory;
import org.highj.typeclass0.group.Group;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass2.arrow.Category;

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

    public static <M, A, B> __2<M, B, A> get(__<__<__<µ, M>, A>, B> dual) {
        return asDual(dual).get();
    }

    public static <M, A, B> Semigroup<__<__<__<µ, M>, A>, B>> semigroup(Semigroup<__2<M, B, A>> mSemigroup) {
        return (x, y) -> new Dual<>(mSemigroup.apply(Dual.get(x), Dual.get(y)));
    }

    public static <M, A, B> Monoid<__<__<__<µ, M>, A>, B>> monoid(Monoid<__2<M, B, A>> mMonoid) {
        return Monoid.create(new Dual<>(mMonoid.identity()),
                (x, y) -> new Dual<>(mMonoid.apply(Dual.get(x), Dual.get(y))));
    }

    public static <M, A, B> Group<__<__<__<µ, M>, A>, B>> group(Group<__2<M, B, A>> mGroup) {
        return Group.create(new Dual<>(mGroup.identity()),
                (x, y) -> new Dual<>(mGroup.apply(Dual.get(x), Dual.get(y))),
                z -> new Dual<>(mGroup.inverse(Dual.get(z))));
    }

    public static <M> DualCategory<M> category(final Category<M> category) {
        return () -> category;
    }

}
