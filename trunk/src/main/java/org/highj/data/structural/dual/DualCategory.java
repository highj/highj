package org.highj.data.structural.dual;

import org.highj._;
import org.highj.__;
import org.highj.data.structural.Dual;
import org.highj.typeclass2.arrow.Category;

import static org.highj.data.structural.Dual.narrow;
import static org.highj.data.structural.Dual.µ;

public interface DualCategory<M> extends Category<_<Dual.µ, M>> {

    public Category<M> category();

    @Override
    public default <A> Dual<M, A, A> identity() {
        return new Dual<>(category().<A>identity());
    }

    @Override
    public default <A, B, C> Dual<M, A, C> dot(__<_<µ, M>, B, C> bc, __<_<µ, M>, A, B> ab) {
        Dual<M, B, C> bcDual = narrow(bc);
        Dual<M, A, B> abDual = narrow(ab);
        return new Dual<>(category().dot(abDual.get(), bcDual.get()));
    }
}
