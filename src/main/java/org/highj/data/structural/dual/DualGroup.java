package org.highj.data.structural.dual;

import org.derive4j.hkt.__2;
import org.derive4j.hkt.__3;
import org.highj.Hkt;
import org.highj.data.structural.Dual;
import org.highj.typeclass0.group.Group;

public interface DualGroup<M, A, B> extends DualMonoid<M, A, B>, Group<__3<Dual.µ, M, A, B>> {

    @Override
    Group<__2<M, B, A>> get();

    @Override
    default __3<Dual.µ, M, A, B> inverse(__3<Dual.µ, M, A, B> value) {
        return new Dual<>(get().inverse(Hkt.asDual(value).get()));
    }
}
