package org.highj.typeclass0.group;

import com.pholser.junit.quickcheck.Property;
import org.highj.data.List;

import static org.assertj.core.api.Assertions.assertThat;

public interface MonoidContract<T> extends SemigroupContract<T> {

    @Override
    Monoid<T> subject();

    @Property
    default void leftIdentity(T a) {
        Monoid<T> monoid = subject();
        T result = monoid.apply(monoid.identity(), a);
        assertThat(result).isEqualTo(a);
    }

    @Property
    default void rightIdentity(T a) {
        Monoid<T> monoid = subject();
        T result = monoid.apply(a, monoid.identity());
        assertThat(result).isEqualTo(a);
    }

    @Property
    default void fold(java.util.List<T> values) {
        Monoid<T> monoid = subject();
        List<T> as = List.fromJavaList(values);
        T monoidFoldResult = monoid.fold(as);
        T listFoldResult = as.foldr(monoid, monoid.identity());
        assertThat(monoidFoldResult).isEqualTo(listFoldResult);
    }
}
