package org.highj.data;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.eq.Eq;
import org.highj.data.eq.Eq1;
import org.highj.typeclass1.monad.ApplicativeLaw;
import org.highj.typeclass1.functor.FunctorLaw;
import org.highj.typeclass1.monad.MonadLaw;
import org.highj.util.Gen;
import org.highj.util.Gen1;
import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class MemoTest {

    private static final Eq1<Memo.µ> EQ1 = new Eq1<Memo.µ>() {
        @Override
        public <T> Eq<__<Memo.µ, T>> eq1(Eq<? super T> eq) {
            return (m1, m2) -> eq.eq(Hkt.asMemo(m1).get(), Hkt.asMemo(m2).get());
        }
    };
    private static final Function<String, Integer> FAILING_FUNCTION = s -> {
        fail("not lazy");
        return 0;
    };
    private static final Supplier<String> FAILING_SUPPLIER = () -> {
        fail("not lazy");
        return "";
    };
    private static final Gen1<Memo.µ> GEN1 = new Gen1<Memo.µ>() {
        @Override
        public <T> Gen<__<Memo.µ, T>> gen(Gen<T> gen) {
            return gen.map(Memo::from);
        }
    };

    @Test
    public void ap() {
        assertThat(Memo.of(() -> "foo").ap(Memo.from(String::length)).get()).isEqualTo(3);

        //Laziness
        Memo.of(FAILING_SUPPLIER).ap(Memo.from(String::length));
        Memo.of(() -> "foo").ap(() -> FAILING_FUNCTION);

        //Memoization
        int[] c = {0};
        Memo<Integer> memo = countingMemo(c).ap(() -> String::length);
        memo.get();
        memo.get();
        memo.get();
        assertThat(c[0]).isEqualTo(1);
        assertThat(memo.get()).isEqualTo(3);
    }

    @Test
    public void applicative() {
        new ApplicativeLaw<>(Memo.memoApplicative, GEN1, EQ1).test();
    }

    @Test
    public void bind() {
        assertThat(Memo.of(() -> "foo").bind(s -> Memo.of(s::length)).get()).isEqualTo(3);

        //Laziness
        Memo.of(FAILING_SUPPLIER).bind(s -> Memo.of(s::length));
        Memo.of(() -> "foo").bind(FAILING_FUNCTION.andThen(Memo::from));

        //Memoization
        int[] c = {0};
        Memo<Integer> memo = countingMemo(c).bind(s -> Memo.of(s::length));
        memo.get();
        memo.get();
        memo.get();
        assertThat(c[0]).isEqualTo(1);
        assertThat(memo.get()).isEqualTo(3);
    }

    @Test
    public void from() {
        assertThat(Memo.from("x").get()).isEqualTo("x");
    }

    @Test
    public void functor() {
        new FunctorLaw<>(Memo.memoFunctor, GEN1, EQ1).test();
    }

    @Test
    public void map() {
        assertThat(Memo.of(() -> "foo").map(String::length).get()).isEqualTo(3);

        //Laziness
        Memo.of(FAILING_SUPPLIER).map(String::length);
        Memo.of(() -> "foo").map(FAILING_FUNCTION);

        //Memoization
        int[] c = {0};
        Memo<Integer> memo = Memo.of(countingMemo(c)).map(String::length);
        memo.get();
        memo.get();
        memo.get();
        assertThat(c[0]).isEqualTo(1);
        assertThat(memo.get()).isEqualTo(3);
    }

    @Test
    public void monad() {
        new MonadLaw<>(Memo.memoMonad, GEN1, EQ1).test();
    }

    @Test
    public void of() {
        assertThat(Memo.of(() -> "x").get()).isEqualTo("x");

        //Laziness
        Memo.of(FAILING_SUPPLIER);

        //Memoization
        int[] c = {0};
        Memo<String> memo = countingMemo(c);
        memo.get();
        memo.get();
        memo.get();
        assertThat(c[0]).isEqualTo(1);
        assertThat(memo.get()).isEqualTo("foo");
    }

    private static Memo<String> countingMemo(int[] c) {
        return Memo.of(() -> {
            c[0]++;
            return "foo";
        });
    }
}