package org.highj.data.transformer.free_arrow;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.transformer.FreeArrow;
import org.highj.typeclass2.arrow.Semigroupoid;

import static org.highj.Hkt.asFreeArrow;

public interface FreeArrowSemigroupoid<F,ARR> extends Semigroupoid<__<__<FreeArrow.µ,F>,ARR>> {

    @Override
    public default <B, C, D> __2<__<__<FreeArrow.µ, F>, ARR>, B, D> dot(__2<__<__<FreeArrow.µ, F>, ARR>, C, D> cd, __2<__<__<FreeArrow.µ, F>, ARR>, B, C> bc) {
        return FreeArrow.compose(asFreeArrow(cd), asFreeArrow(bc));
    }
}
