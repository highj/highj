package org.highj.typeclass2.arrow;

import com.pholser.junit.quickcheck.Property;
import org.derive4j.hkt.__2;
import org.highj.Hkt;
import org.highj.data.tuple.T2;
import org.highj.function.F1;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public interface ArrowContract<A> {

    Arrow<A> subject();

    <B, C> boolean areEqual(__2<A, B, C> one, __2<A, B, C> two, B b);

    // arr id = id
    @Property
    default void arrowIdentity(String s) {
        Arrow<A> arrow = subject();
        __2<A, String, String> arrowOfId = arrow.arr(Function.identity());
        __2<A, String, String> arrowIdentity = arrow.identity();
        assertThat(areEqual(arrowOfId, arrowIdentity, s)).isTrue();
    }


    // arr (f >>> g) = arr f >>> arr g
    @Property
    default void composition(String s) {
        Arrow<A> arrow = subject();
        Function<String, Integer> f1 = String::length;
        Function<Integer, Integer> f2 = x -> x * x;
        __2<A, String, Integer> arrOfComposed = arrow.arr(f1.andThen(f2));
        __2<A, String, Integer> composedArr = arrow.then(arrow.arr(f1), arrow.arr(f2));
        assertThat(areEqual(arrOfComposed, composedArr, s)).isTrue();
    }

    // first (arr f) = arr (first f)
    @Property
    default void arrFirst(T2<String, Boolean> t2) {
        Arrow<A> arrow = subject();
        F1<String, Integer> fn = String::length;
        __2<A, T2<String, Boolean>, T2<Integer, Boolean>> firstArr = arrow.first(arrow.arr(fn));
        __2<A, T2<String, Boolean>, T2<Integer, Boolean>> arrFirst = arrow.arr(F1.arrow.first(fn));
        assertThat(areEqual(firstArr, arrFirst, t2)).isTrue();
    }

    // first (f >>> g) = first f >>> first g
    @Property
    default void firstComposition(T2<String, Boolean> t2) {
        Arrow<A> arrow = subject();
        __2<A, String, Integer> f = arrow.arr(String::length);
        __2<A, Integer, Integer> g = arrow.arr(x -> x * x);
        __2<A, T2<String, Boolean>, T2<Integer, Boolean>> firstOfComposed =
            arrow.first(arrow.then(f, g));
        __2<A, T2<String, Boolean>, T2<Integer, Boolean>> composedFirst =
            arrow.then(arrow.first(f), arrow.first(g));
        assertThat(areEqual(firstOfComposed, composedFirst, t2)).isTrue();
    }

    // first f >>> arr fst = arr fst >>> f
    @Property
    default void t2_1(T2<String, Boolean> t2) {
        Arrow<A> arrow = subject();
        __2<A, String, Integer> f = arrow.arr(String::length);

        __2<A, T2<String, Boolean>, Integer> firstThenT2 = arrow.then(arrow.first(f), arrow.arr(T2::_1));
        __2<A, T2<String, Boolean>, Integer> T2ThenF = arrow.then(arrow.arr(T2::_1), f);

        assertThat(areEqual(firstThenT2, T2ThenF, t2)).isTrue();
    }

    // first f >>> arr (id *** g) = arr (id *** g) >>> first f
    @Property
    default void split(T2<String, Long> t2) {
        Arrow<A> arrow = subject();
        __2<A, String, Integer> f = arrow.arr(String::length);
        __2<F1.µ, Long, Long> g = (F1<Long, Long>) x -> x * x;

        __2<A, T2<Integer, Long>, T2<Integer, Long>> g1 = arrow.arr(Hkt.asF1(F1.arrow.split(F1.arrow.identity(), g)));
        __2<A, T2<String, Long>, T2<Integer, Long>> firstSplitted = arrow.then(arrow.first(f), g1);

        __2<A, T2<String, Long>, T2<String, Long>> g2 = arrow.arr(Hkt.asF1(F1.arrow.split(F1.arrow.identity(), g)));
        __2<A, T2<String, Long>, T2<Integer, Long>> splittedFirst = arrow.then(g2, arrow.first(f));

        assertThat(areEqual(firstSplitted, splittedFirst, t2)).isTrue();
    }

    // first (first f) >>> arr assoc = arr assoc >>> first f  -- where assoc ((a,b),c) = (a,(b,c))
    @Property
    default void assoc(T2<T2<String, Boolean>, Long> nestedT2) {
        Arrow<A> arrow = subject();
        __2<A, String, Integer> f = arrow.arr(String::length);

        __2<A, T2<T2<String, Boolean>, Long>, T2<T2<Integer, Boolean>, Long>> firstFirst =
            arrow.first(arrow.first(f));
        __2<A, T2<T2<Integer, Boolean>, Long>, T2<Integer, T2<Boolean, Long>>> arrAssoc1 =
            arrow.arr(t2 -> T2.of(t2._1()._1(), T2.of(t2._1()._2(), t2._2())));
        __2<A, T2<T2<String, Boolean>, Long>, T2<Integer, T2<Boolean, Long>>> firstAssoc =
            arrow.then(firstFirst, arrAssoc1);

        __2<A, T2<T2<String, Boolean>, Long>, T2<String, T2<Boolean, Long>>> arrAssoc2 =
            arrow.arr(t2 -> T2.of(t2._1()._1(), T2.of(t2._1()._2(), t2._2())));
        __2<A, T2<String, T2<Boolean, Long>>, T2<Integer, T2<Boolean, Long>>> first =
            arrow.first(f);
        __2<A, T2<T2<String, Boolean>, Long>, T2<Integer, T2<Boolean, Long>>> assocFirst =
            arrow.then(arrAssoc2, first);

        assertThat(areEqual(firstAssoc, assocFirst, nestedT2)).isTrue();
    }

}
