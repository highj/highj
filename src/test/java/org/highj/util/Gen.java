package org.highj.util;

import org.highj.data.List;
import org.highj.data.Stream;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.T3;

import java.util.Random;
import java.util.function.Function;

/**
 * A generator of values for testing purposes.
 *
 * @param <A> the tested type
 */
public interface Gen<A> {

    Random rnd = new Random();

    Iterable<A> get(int maxSize);

    default <B> Gen<B> map(Function<A, B> fn) {
        return maxSize -> List.fromIterable(get(maxSize)).map(fn);
    }

    Gen<String> stringGen = maxSize -> Stream.unfold(s -> {
        StringBuilder sb = new StringBuilder();
        while (rnd.nextDouble() < 0.8) {
            sb.append((char) (rnd.nextInt('z' - 'a') + 'a'));
        }
        return sb.toString();
    }, "").take(maxSize);

    Gen<Integer> intGen = maxSize -> Stream.unfold(i -> rnd.nextInt(), rnd.nextInt()).take(maxSize);

    Gen<Long> longGen = maxSize -> Stream.unfold(i -> rnd.nextLong(), rnd.nextLong()).take(maxSize);

    Gen<Boolean> boolGen = maxSize -> Stream.unfold(i -> rnd.nextBoolean(), rnd.nextBoolean()).take(maxSize);

    static <A, B> Gen<T2<A, B>> zip(Gen<A> first, Gen<B> second) {
        return maxSize -> List.zip(
            List.fromIterable(first.get(maxSize)),
            List.fromIterable(second.get(maxSize)));
    }

    static <A, B, C> Gen<T3<A, B, C>> zip(Gen<A> first, Gen<B> second, Gen<C> third) {
        return maxSize -> List.zip(
            List.fromIterable(first.get(maxSize)),
            List.fromIterable(second.get(maxSize)),
            List.fromIterable(third.get(maxSize)));
    }

}
