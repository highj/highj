package org.highj.typeclass1;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T1;

/**
 * All types in Haskell are lazy by default, which leads to elegant implementations of
 * some methods that would be otherwise quite ugly in a strict language.
 * LazifyH is a type class that represents a high order type that are self lazy-embedding capable.
 * Libraries like derive4j can auto-generate self lazy-embeddedness automatically for an ADT.
 */
public interface LazifyH<F> {
    <A> __<F,A> lazifyH(T1<__<F,A>> a);
}
