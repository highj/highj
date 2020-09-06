package org.highj.typeclass0.group;

import com.pholser.junit.quickcheck.Property;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public interface GroupContract<T> extends MonoidContract<T> {

    @Override
    Group<T> subject();

    @Property
    default void leftInverse(T a) {
        Group<T> group = subject();
        T result = group.apply(group.inverse(a), a);
        assertThat(result).isEqualTo(group.identity());
    }

    @Property
    default void rightInverse(T a) {
            Group<T> group = subject();
            T result = group.apply(a, group.inverse(a));
            assertThat(result).isEqualTo(group.identity());
    }

    @Test
    default void identityInverse() {
        Group<T> group = subject();
        T result = group.inverse(group.identity());
        assertThat(result).isEqualTo(group.identity());
    }
}
