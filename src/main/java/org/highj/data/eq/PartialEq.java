package org.highj.data.eq;

import org.derive4j.hkt.__;

public interface PartialEq<F> {
    <T> Eq<__<F, T>> deriveEq(Eq<? super T> eq);
}
