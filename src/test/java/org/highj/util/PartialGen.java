package org.highj.util;

import org.derive4j.hkt.__;

public interface PartialGen<F> {
    <T> Gen<__<F,T>> deriveGen(Gen<T> gen);
}
