package org.highj.data.tuple;

import org.highj._;
import org.highj.__;
import org.highj.data.compare.Eq;
import org.highj.data.functions.F1;
import org.highj.data.functions.Fs;
import org.highj.data.functions.Integers;
import org.highj.data.functions.Strings;
import org.highj.typeclass1.monad.Monad;
import org.junit.Test;

import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class T2Test {
    @Test
    public void pairTest() throws Exception {
        T2<String,Integer> pair = Tuple.of("foo", 42);
        assertEquals("(foo,42)", pair.toString());
    }

    @Test
    public void pairThunkTest() throws Exception {
        Supplier<String> stringThunk = () -> "foo";
        Supplier<Integer> intThunk = () -> 42;
        T2<String,Integer> pair = Tuple.of(stringThunk, intThunk);
        assertEquals("(foo,42)", pair.toString());
    }

    @Test
    public void swapTest() throws Exception {
        T2<String,Integer> pair = Tuple.of("foo", 42);
        T2<Integer, String> swapped = pair.swap();
        assertEquals("(42,foo)", swapped.toString());
    }

    @Test
    public void monadTest() throws Exception {
        Monad<__.µ<T2.µ,String>> monad = T2.monad(Strings.group);
        T2<String, Integer> answer = Tuple.narrow2(monad.pure(42));
        assertEquals("(,42)", answer.toString());
        T2<String, Integer> foo = Tuple.of("foo", 14);
        F1<Integer, T2<String, Integer>> doubleBar = value -> Tuple.of("bar", 2 * value);
        F1<Integer, _<__.µ<T2.µ,String>,Integer>> castedDoubleBar = Fs.<Integer,_<__.µ<T2.µ,String>,Integer>,T2<String, Integer>>contravariant(doubleBar);
        T2<String, Integer> fooBar = Tuple.narrow2(monad.bind(foo, castedDoubleBar));
        assertEquals("(foobar,28)", fooBar.toString());
    }

    @Test
    public void eqTest() throws Exception {
        Eq<T2<String,Integer>> eq = Tuple.eq(Strings.eq, Integers.eq);
        T2<String,Integer> a1 = Tuple.of("a", 1);
        T2<String,Integer> a2 = Tuple.of("a", 2);
        T2<String,Integer> b1 = Tuple.of("b", 1);
        assertTrue(eq.eq(a2, a2));
        assertTrue(eq.eq(a2, Tuple.of("a", 2)));
        assertFalse(eq.eq(a1, a2));
        assertFalse(eq.eq(a2, a1));
        assertFalse(eq.eq(a1, b1));
        assertFalse(eq.eq(a2, b1));
        assertFalse(eq.eq(b1, a2));

   }
}
