package org.highj.data.structural;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.List;
import org.highj.data.Maybe;
import org.highj.data.eq.Eq;
import org.highj.data.num.Integers;
import org.highj.data.structural.compose.*;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class ComposeTest {
    @Test
    public void get() {
        List<__<Maybe.µ, Integer>> list = List.of(Maybe.Just(1), Maybe.Nothing());
        Compose<List.µ, Maybe.µ, Integer> compose = new Compose<>(list);
        assertThat(compose.get()).isSameAs(list);
    }

    @Test
    public void equals() {
        List<__<Maybe.µ, Integer>> list = List.of(Maybe.Just(1), Maybe.Nothing());
        Compose<List.µ, Maybe.µ, Integer> compose1 = new Compose<>(list);
        Compose<List.µ, Maybe.µ, Integer> compose2 = new Compose<>(list);
        Compose<List.µ, Maybe.µ, Integer> compose3 = new Compose<>(list.tail());
        assertThat(compose1).isEqualTo(compose2);
        assertThat(compose1).isNotEqualTo(compose3);
    }

    @Test
    public void testToString() {
        List<__<Maybe.µ, Integer>> list = List.of(Maybe.Just(1), Maybe.Nothing());
        Compose<List.µ, Maybe.µ, Integer> compose = new Compose<>(list);
        assertThat(compose.toString()).isEqualTo("Compose(" + list.toString() + ")");
    }

    @Test
    public void functor() {
        ComposeFunctor<List.µ, Maybe.µ> functor = Compose.functor(List.monadPlus, Maybe.monad);
        Compose<List.µ, Maybe.µ, Integer> compose = new Compose<>(List.of(Maybe.Just(1), Maybe.Nothing()));
        Compose<List.µ, Maybe.µ, String> mappedCompose = functor.map(i -> i + "!", compose);
        assertThat(mappedCompose.get()).isEqualTo(List.of(Maybe.Just("1!"), Maybe.Nothing()));
    }

    @Test
    public void foldable() {
        ComposeFoldable<List.µ, Maybe.µ> foldable = Compose.foldable(List.traversable, Maybe.traversable);
        Compose<List.µ, Maybe.µ, Integer> compose = new Compose<>(List.of(Maybe.Just(1), Maybe.Nothing(), Maybe.Just(4)));
        Integer folded = foldable.fold(Integers.additiveGroup, compose);
        assertThat(folded).isEqualTo(5);
    }

    @Test
    public void apply() {
        ComposeApply<List.µ, Maybe.µ> apply = Compose.apply(List.monadPlus, Maybe.monad);
        Compose<List.µ, Maybe.µ, Integer> compose = new Compose<>(List.of(Maybe.Just(1), Maybe.Nothing(), Maybe.Just(2)));
        Compose<List.µ, Maybe.µ, Function<Integer, String>> fns =
            new Compose<>(List.of(Maybe.Just(i -> i + "!"), Maybe.Nothing(), Maybe.Just(i -> i + "?")));
        Compose<List.µ, Maybe.µ, String> apCompose = apply.ap(fns, compose);
        assertThat(Hkt.asList(apCompose.get())).containsExactly(
            Maybe.Just("1!"), Maybe.Nothing(), Maybe.Just("2!"),
            Maybe.Nothing(), Maybe.Nothing(), Maybe.Nothing(),
            Maybe.Just("1?"), Maybe.Nothing(), Maybe.Just("2?"));
    }

    @Test
    public void applicative() {
        ComposeApplicative<List.µ, Maybe.µ> applicative = Compose.applicative(List.monadPlus, Maybe.monad);
        __<List.µ, __<Maybe.µ, Integer>> pure = applicative.pure(42).get();
        assertThat(pure).isEqualTo(List.of(Maybe.Just(42)));
    }

    @Test
    public void eq1() {
        ComposeEq1<List.µ, Maybe.µ> eq1 = Compose.eq1(List.eq1, Maybe.eq1);
        Eq<__<__<__<Compose.µ, List.µ>, Maybe.µ>, Integer>> eqInt = eq1.eq1(Eq.fromEquals());
        Compose<List.µ, Maybe.µ, Integer> compose1 = new Compose<>(List.of(Maybe.Just(1), Maybe.Nothing(), Maybe.Just(2)));
        Compose<List.µ, Maybe.µ, Integer> compose2 = new Compose<>(List.of(Maybe.Just(1), Maybe.Nothing(), Maybe.Just(2)));
        Compose<List.µ, Maybe.µ, Integer> compose3 = new Compose<>(List.of(Maybe.Nothing(), Maybe.Just(1), Maybe.Just(2)));
        Compose<List.µ, Maybe.µ, Integer> compose4 = new Compose<>(List.of(Maybe.Just(1), Maybe.Nothing()));
        assertThat(eqInt.eq(compose1, compose2)).isTrue();
        assertThat(eqInt.eq(compose1, compose3)).isFalse();
        assertThat(eqInt.eq(compose1, compose4)).isFalse();
    }

    @Test
    public void alternative() {
        ComposeAlternative<List.µ, Maybe.µ> alternative = Compose.alternative(List.monadPlus, Maybe.monad);
        assertThat(alternative.mzero().get()).isEqualTo(List.empty());

        Compose<List.µ, Maybe.µ, Integer> compose1 = new Compose<>(List.of(Maybe.Just(1), Maybe.Nothing(), Maybe.Just(2)));
        Compose<List.µ, Maybe.µ, Integer> compose2 = new Compose<>(List.of(Maybe.Just(10)));
        List<__<Maybe.µ, Integer>> added = Hkt.asList(alternative.mplus(compose1, compose2).get());
        assertThat(added).containsExactly(Maybe.Just(1), Maybe.Nothing(), Maybe.Just(2), Maybe.Just(10));
    }

    @Test
    public void traversable() {
        ComposeTraversable<List.µ, Maybe.µ> traversable = Compose.traversable(List.traversable, Maybe.traversable);
        List<__<Maybe.µ, __<Maybe.µ, Integer>>> maybeList = List.of(Maybe.Just(Maybe.Just(1)), Maybe.Just(Maybe.Just(2)), Maybe.Nothing());
        Compose<List.µ, Maybe.µ, __<Maybe.µ, Integer>> compose1 = new Compose<>(maybeList);
        Compose<List.µ, Maybe.µ, __<Maybe.µ, Integer>> compose2 = new Compose<>(maybeList.plus(Maybe.Just(Maybe.Nothing())));
        assertThat(traversable.sequenceA(Maybe.monad, compose1)).isEqualTo(Maybe.Just(new Compose<>(List.of(Maybe.Just(1), Maybe.Just(2), Maybe.Nothing()))));
        assertThat(traversable.sequenceA(Maybe.monad, compose2)).isEqualTo(Maybe.Nothing());
    }
}