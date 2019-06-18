package org.highj.data.structural.dual;

import org.derive4j.hkt.__2;
import org.derive4j.hkt.__3;
import org.highj.data.structural.Dual;
import org.highj.typeclass0.group.Semigroup;

public interface DualSemigroup<M, A, B> extends Semigroup<__3<Dual.µ, M, A, B>> {

    Semigroup<__2<M, B, A>> get();

    @Override
    default __3<Dual.µ, M, A, B> apply(__3<Dual.µ, M, A, B> x, __3<Dual.µ, M, A, B> y) {
        return new Dual<>(get().apply(Dual.get(y), Dual.get(x)));
    }
}
