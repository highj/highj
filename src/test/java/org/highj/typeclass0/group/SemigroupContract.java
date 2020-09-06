package org.highj.typeclass0.group;

import com.pholser.junit.quickcheck.Property;

import static org.assertj.core.api.Assertions.assertThat;

public interface SemigroupContract<T> {

    Semigroup<T> subject();

    @Property default void associativity(T a, T b, T c) {
        Semigroup<T> sg = subject();
        T r1 = sg.apply(a, sg.apply(b, c));
        T r2 = sg.apply(sg.apply(a, b), c);
        assertThat(r1).isEqualTo(r2);
    }
}
