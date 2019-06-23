package org.highj.data.instance.list;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.List;
import org.highj.data.eq.Eq;
import org.highj.data.eq.Eq1;

import static org.highj.data.List.*;

public interface ListEq1 extends Eq1<µ> {

    @Override
    default <T> Eq<__<List.µ, T>> eq1(Eq<? super T> eq) {
        return (one, two) -> {
            List<T> listOne = Hkt.asList(one);
            List<T> listTwo = Hkt.asList(two);
            while(! listOne.isEmpty() && ! listTwo.isEmpty()) {
               if (! eq.eq(listOne.head(), listTwo.head())) {
                   return false;
               }
               listOne = listOne.tail();
               listTwo = listTwo.tail();
            }
            return listOne.isEmpty() && listTwo.isEmpty();
        };
    }
}
