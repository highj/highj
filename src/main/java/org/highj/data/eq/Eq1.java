package org.highj.data.eq;

import org.derive4j.hkt.__;

public interface Eq1<F> {
    <T> Eq<__<F, T>> eq1(Eq<? super T> eq);
}
