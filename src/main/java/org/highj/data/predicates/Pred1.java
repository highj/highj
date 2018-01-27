package org.highj.data.predicates;

import org.derive4j.hkt.__;

public interface Pred1<M> {

    <A> Pred<__<M, A>> pred1(Pred<A> pred);

}
