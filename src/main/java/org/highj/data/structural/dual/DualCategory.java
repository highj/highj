package org.highj.data.structural.dual;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.Hkt;
import org.highj.data.structural.Dual;
import org.highj.typeclass2.arrow.Category;

import static org.highj.Hkt.asDual;
import static org.highj.data.structural.Dual.µ;

public interface DualCategory<M> extends Category<__<µ, M>> {

    Category<M> category();

    @Override
    default <A> Dual<M, A, A> identity() {
        return new Dual<>(category().identity());
    }

    @Override
    default <A, B, C> Dual<M, A, C> dot(__2<__<µ, M>, B, C> bc, __2<__<µ, M>, A, B> ab) {
        Dual<M, B, C> bcDual = asDual(bc);
        Dual<M, A, B> abDual = asDual(ab);
        return new Dual<>(category().dot(abDual.get(), bcDual.get()));
    }
}
