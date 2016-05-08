package org.highj.data.structural.dual;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.structural.Dual;
import org.highj.typeclass2.arrow.Category;

import static org.highj.data.structural.Dual.narrow;
import static org.highj.data.structural.Dual.µ;

public interface DualCategory<M> extends Category<__<µ, M>> {

    public Category<M> category();

    @Override
    public default <A> Dual<M, A, A> identity() {
        return new Dual<>(category().<A>identity());
    }

    @Override
    public default <A, B, C> Dual<M, A, C> dot(__2<__<µ, M>, B, C> bc, __2<__<µ, M>, A, B> ab) {
        Dual<M, B, C> bcDual = narrow(bc);
        Dual<M, A, B> abDual = narrow(ab);
        return new Dual<>(category().dot(abDual.get(), bcDual.get()));
    }
}
