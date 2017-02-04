package org.highj.data.transformer.stream_arrow;

import org.derive4j.hkt.__;
import org.highj.data.transformer.StreamArrow;
import org.highj.typeclass2.arrow.Category;

/**
 *
 * @author clintonselke
 */
public interface StreamArrowCategory<A> extends StreamArrowSemigroupoid<A>, Category<__<StreamArrow.µ,A>> {
    
    @Override
    Category<A> a();

    @Override
    default <B> StreamArrow<A, B, B> identity() {
        return StreamArrow.streamArrow(a().identity());
    }
}
