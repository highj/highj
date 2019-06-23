package org.highj.data.instance.list;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.List;
import org.highj.data.ord.Ord;
import org.highj.data.ord.Ord1;
import org.highj.data.ord.Ordering;

import static org.highj.data.List.µ;

public interface ListOrd1 extends Ord1<µ> {

    @Override
    default <A> Ord<__<List.µ, A>> cmp(Ord<? super A> ord) {
        return (x,y) -> {
            List<A> xs = Hkt.asList(x);
            List<A> ys = Hkt.asList(y);
            while(! xs.isEmpty() && ! ys.isEmpty()) {
                Ordering ordering = ord.cmp(xs.head(), ys.head());
                if (ordering != Ordering.EQ) {
                    return ordering;
                }
                xs = xs.tail();
                ys = ys.tail();
            }
            if (xs.isEmpty() && ys.isEmpty()) {
                return Ordering.EQ;
            }
            return xs.isEmpty() ? Ordering.LT : Ordering.GT;
        };
    }
}
