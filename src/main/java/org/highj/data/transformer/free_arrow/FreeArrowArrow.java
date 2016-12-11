package org.highj.data.transformer.free_arrow;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.transformer.FreeArrow;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.Arrow;

import static org.highj.Hkt.asFreeArrow;

public interface FreeArrowArrow<F,ARR> extends FreeArrowCategory<F,ARR>, Arrow<__<__<FreeArrow.µ,F>,ARR>> {

    @Override
    default <B, C> __2<__<__<FreeArrow.µ, F>, ARR>, B, C> arr(Function<B, C> fn) {
        return FreeArrow.arr((B x) -> fn.apply(x));
    }

    @Override
    default <B, C, D> __2<__<__<FreeArrow.µ, F>, ARR>, T2<B, D>, T2<C, D>> first(__2<__<__<FreeArrow.µ, F>, ARR>, B, C> arrow) {
        return FreeArrow.first(asFreeArrow(arrow));
    }
}
