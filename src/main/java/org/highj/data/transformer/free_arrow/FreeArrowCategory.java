package org.highj.data.transformer.free_arrow;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.transformer.FreeArrow;
import org.highj.typeclass2.arrow.Category;

public interface FreeArrowCategory<F,ARR> extends FreeArrowSemigroupoid<F,ARR>, Category<__<__<FreeArrow.µ,F>,ARR>> {

    @Override
    public default <B> __2<__<__<FreeArrow.µ, F>, ARR>, B, B> identity() {
        return FreeArrow.id();
    }
}
