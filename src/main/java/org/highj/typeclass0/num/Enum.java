package org.highj.typeclass0.num;

import org.highj.data.List;
import org.highj.data.Stream;

/**
 * @author clintonselke
 */
public interface Enum<A> {

    A toEnum(int a);

    int fromEnum(A a);

    default A succ(A a) {
        return toEnum(fromEnum(a) + 1);
    }

    default A pred(A a) {
        return toEnum(fromEnum(a) - 1);
    }

    default Stream<A> enumFrom(A a) {
        return Stream.range(fromEnum(a)).map(this::toEnum);
    }

    default Stream<A> enumFromThen(A a, A b) {
        int x = fromEnum(a);
        int y = fromEnum(b);
        return Stream.range(x, y - x).map(this::toEnum);
    }

    default List<A> enumFromTo(A a, A b) {
        return List.range(fromEnum(a), 1, fromEnum(b)).map(this::toEnum);
    }

    default List<A> enumFromThenTo(A a, A b, A c) {
        if (a.equals(b) && b.equals(c)) {
            return List.cycle(a);
        }
        int x = fromEnum(a);
        int y = fromEnum(b);
        int z = fromEnum(c);
        return List.range(x, y - x, z).map(this::toEnum);
    }
}
