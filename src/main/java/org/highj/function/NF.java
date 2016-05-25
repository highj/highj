package org.highj.function;

import org.derive4j.hkt.__;

/**
 * Natural Transformation
 * @param <F> Source Context
 * @param <G> Target Context
 */
public interface NF<F,G> {
    <A> __<G,A> apply(__<F,A> a);
}
