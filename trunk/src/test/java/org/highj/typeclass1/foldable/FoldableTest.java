package org.highj.typeclass1.foldable;

import org.highj.data.collection.List;
import org.highj.data.collection.Maybe;
import org.highj.data.functions.Integers;
import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class FoldableTest {

    private final Function<String, Function<String, String>> wrapFn = a -> b -> "(" + a + "," + b + ")";

    @Test
    public void testFold() throws Exception {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        int result = List.foldable.fold(Integers.multiplicativeMonoid, numbers);
        assertEquals(120, result);
    }

    @Test
    public void testFoldMap() throws Exception {
        List<String> strings = List.of("a", "bb", "ccc", "dddd", "eeeee");
        int result = List.foldable.foldMap(Integers.multiplicativeMonoid, String::length, strings);
        assertEquals(120, result);
    }

    @Test
    public void testFoldr() throws Exception {
        List<String> strings = List.of("a", "e", "i", "o");
        String result = List.foldable.foldr(wrapFn, "u", strings);
        assertEquals("(a,(e,(i,(o,u))))", result);
    }

    @Test
    public void testFoldl() throws Exception {
        List<String> strings = List.of("e", "i", "o", "u");
        String result = List.foldable.foldl(wrapFn, "a", strings);
        assertEquals("((((a,e),i),o),u)", result);
    }

    @Test
    public void testFoldr1() throws Exception {
        List<String> strings = List.of("a", "e", "i", "o", "u");
        Maybe<String> result = List.foldable.foldr1(wrapFn, strings);
        assertEquals("(a,(e,(i,(o,u))))", result.get());
        List<String> noStrings = List.of();
        assertTrue(List.foldable.foldr1(wrapFn, noStrings).isNothing());
    }

    @Test
    public void testFoldl1() throws Exception {
        List<String> strings = List.of("a", "e", "i", "o", "u");
        Maybe<String> result = List.foldable.foldl1(wrapFn, strings);
        assertEquals("((((a,e),i),o),u)", result.get());
        List<String> noStrings = List.of();
        assertTrue(List.foldable.foldl1(wrapFn, noStrings).isNothing());
    }
}
