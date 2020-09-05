package org.highj.do_;

import org.derive4j.hkt.TypeEq;
import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.List;
import org.highj.data.Maybe;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.highj.Hkt.*;

/**
 * @author clintonselke
 */
public class DoTest {

    @Test
    public void testNondeterminism() {
        List<String> results = asList(
            Do_.<List.µ>do_()
                .pushM(List.range(1, 1, 3))
                .pushM(List.range(1, 1, 3))
                .map2(
                    TypeEq.refl(),
                    (Integer a, Integer b) ->
                        "" + a + " x " + b + " = " + (a * b)
                )
                .runWithResultNoTailRec(TypeEq.refl(), List.monadPlus)
        );
        assertThat(results).containsExactly(
            "1 x 1 = 1", "1 x 2 = 2", "1 x 3 = 3",
            "2 x 1 = 2", "2 x 2 = 4", "2 x 3 = 6",
            "3 x 1 = 3", "3 x 2 = 6", "3 x 3 = 9"
        );
    }

    @Test
    public void testDoBlock() {
        Maybe<String> onetwo = asMaybe(
            Do_.<Maybe.µ>do_()
                .push("one")
                .push("two")
                .map2(TypeEq.refl(), (String a, String b) -> a + b)
                .runWithResult(TypeEq.refl(), Maybe.monad)
        );
        assertThat(onetwo.get()).isEqualTo("onetwo");

        Maybe<String> empty = asMaybe(
            Do_.<Maybe.µ>do_()
                .push("one")
                .pushM(Maybe.<String>Nothing())
                .map2(TypeEq.refl(), (String a, String b) -> a + b)
                .runWithResult(TypeEq.refl(), Maybe.monad)
        );
        assertThat(empty.isNothing()).isTrue();
    }

    @Test
    public void testBind() {
        Either<String, Integer> handSum = asEither(
            Do_.<__<Either.µ, String>>do_()
                .push(6)
                .push(7)
                .pushBind2(TypeEq.refl(), (Integer a, Integer b) -> {
                    int r = a + b;
                    if (r > 10) {
                        return Either.Left("Not enough fingers!");
                    } else {
                        return Either.Right(r);
                    }
                })
                .runWithResult(TypeEq.refl(), Either.monad())
        );
        assertThat(handSum.toString()).isEqualTo("Left(Not enough fingers!)");
    }
}
