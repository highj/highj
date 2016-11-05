package org.highj.util;

import org.highj.data.List;
import org.highj.data.Stream;

import java.util.Random;
import java.util.function.Function;

public interface Gen<A> {

    Random rnd = new Random();

    Iterable<A> get(int maxSize);

    default <B> Gen<B> map(Function<A,B> fn) {
        return maxSize -> List.of(Gen.this.get(maxSize)).map(fn);
    }

    Gen<String> stringGen = maxSize -> Stream.unfold(s -> {
        StringBuilder sb = new StringBuilder();
        while (rnd.nextDouble() < 0.8) {
            sb.append((char) (rnd.nextInt('z' - 'a') + 'a'));
        }
        return sb.toString();
    }, "").take(maxSize);

    Gen<Integer> intGen = maxSize -> Stream.unfold(i -> rnd.nextInt(), 0).take(maxSize);

}
