package org.highj.data.instance.list;

import org.derive4j.hkt.__;
import org.highj.data.List;
import org.highj.typeclass1.comonad.Extend;

import static org.highj.Hkt.asList;

public interface ListExtend extends ListFunctor, Extend<List.µ> {
    @Override
    default <A> List<__<List.µ, A>> duplicate(__<List.µ, A> nestedA) {
        //init . tails
        List<A> listA = asList(nestedA);
        List<List<A>> result = listA.tailsLazy().initLazy();
        return List.<__<List.µ, A>,List<A>>contravariant(result);
    }
}
