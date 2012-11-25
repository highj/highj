package org.highj.data.collection;

import org.highj._;
import org.highj.data.collection.Maybe;
import org.highj.function.Integers;
import org.highj.typeclass.monad.Monad;
import org.highj.data.collection.List;
import org.highj.data.compare.Eq;
import org.highj.function.F0;
import org.highj.function.F1;
import org.highj.function.Strings;
import org.highj.typeclass.monad.MonadPlus;
import org.junit.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.highj.data.collection.Maybe.*;
import static org.junit.Assert.*;

public class MaybeTest {
    @Test
    public void testCata() {
        Maybe<String> nothing = Nothing();
        assertEquals("foo", nothing.cata("foo", F1.<String, String>constant("bar")));
        Maybe<String> baz = Just("baz");
        assertEquals("bar", baz.cata("foo", F1.<String, String>constant("bar")));
        assertEquals("baz", baz.cata("foo", F1.<String>id()));
    }

    @Test
    public void testCataThunk() {
        F0<String> thunk = F0.constant("foo");
        Maybe<String> nothing = Nothing();
        assertEquals("foo", nothing.cata(thunk, F1.<String, String>constant("bar")));
        Maybe<String> baz = Just("baz");
        assertEquals("bar", baz.cata(thunk, F1.<String, String>constant("bar")));
        assertEquals("baz", baz.cata(thunk, F1.<String>id()));
    }

    @Test
    public void testNothing() {
        Maybe<String> nothing = Nothing();
        assertEquals("Nothing", nothing.toString());
    }

    @Test
    public void testJust() {
        Maybe<String> bar = Just("bar");
        assertEquals("Just(bar)", bar.toString());
    }

    @Test
    public void testJustFn() {
        F1<String, Maybe<String>> just = just();
        Maybe<String> bar = just.$("bar");
        assertEquals("Just(bar)", bar.toString());
    }

    @Test
    public void testJustThunk() {
        F0<String> thunk = F0.constant("bar");
        Maybe<String> bar = Just(thunk);
        assertEquals("Just(bar)", bar.toString());
    }

    @Test
    public void testIsEmpty() {   //note that isNothing is just a synonym 
        Maybe<String> nothing = Nothing();
        assertTrue(nothing.isNothing());
        Maybe<String> bar = Just("bar");
        assertFalse(bar.isNothing());
    }

    @Test
    public void testIsJust() {
        Maybe<String> nothing = Nothing();
        assertFalse(nothing.isJust());
        Maybe<String> bar = Just("bar");
        assertTrue(bar.isJust());
    }

    @Test
    public void testGetOrElse() {
        Maybe<String> nothing = Nothing();
        assertEquals("foo", nothing.getOrElse("foo"));
        Maybe<String> bar = Just("bar");
        assertEquals("bar", bar.getOrElse("foo"));
    }

    @Test
    public void testGetOrElseThunk() {
        F0<String> thunk = F0.constant("foo");
        Maybe<String> nothing = Nothing();
        assertEquals("foo", nothing.getOrElse(thunk));
        Maybe<String> bar = Just("bar");
        assertEquals("bar", bar.getOrElse(thunk));
    }

    @Test
    public void testGet() throws Exception {
        Maybe<String> bar = Just("bar");
        assertEquals("bar", bar.get());
        try {
           Maybe<String> nothing = Nothing();
           nothing.get();
        } catch (RuntimeException ex) {
            return;
        }
        fail("error not thrown");
    }

    @Test
    public void testGetOrErrorMsg() {
        Maybe<String> bar = Just("bar");
        assertEquals("bar", bar.getOrError("argh!"));
        try {
            Maybe<String> nothing = Nothing();
            nothing.getOrError("argh!");
        } catch (RuntimeException ex) {
            assertEquals("argh!", ex.getMessage());
            return;
        }
        fail("error not thrown");
    }

    @Test
    public void testGetOrErrorClass() {
        Maybe<String> bar = Just("bar");
        assertEquals("bar", bar.getOrError(NoSuchElementException.class));
        try {
            Maybe<String> nothing = Nothing();
            nothing.getOrError(NoSuchElementException.class);
        } catch (NoSuchElementException ex) {
            return;
        }
        fail("error not thrown");
    }

    @Test
    public void testGetOrErrorClassMsg() throws Exception {
        Maybe<String> bar = Just("bar");
        assertEquals("bar", bar.getOrError(NoSuchElementException.class, "argh!"));
        try {
            Maybe<String> nothing = Nothing();
            nothing.getOrError(NoSuchElementException.class, "argh!");
        } catch (NoSuchElementException ex) {
            assertEquals("argh!", ex.getMessage());
            return;
        }
        fail("error not thrown");

    }

    @Test
    public void testOrElse() {
        Maybe<String> foo = Just("foo");
        Maybe<String> bar = Just("bar");
        Maybe<String> nothing = Nothing();
        assertEquals("Just(foo)", foo.orElse(bar).toString());
        assertEquals("Just(foo)", foo.orElse(nothing).toString());
        assertEquals("Just(bar)", nothing.orElse(bar).toString());
        assertEquals("Nothing", nothing.orElse(nothing).toString());
    }

    @Test
    public void testIterator() throws Exception {
        Maybe<String> nothing = Nothing();
        for(String s : nothing) {
            fail("Nothing() iterates");
        }
        Maybe<String> bar = Just("bar");
        java.util.List<String> strings = new ArrayList<String>();
        for(String s: bar) {
            strings.add(s);
        }
        assertEquals(1, strings.size());
        assertEquals("bar", strings.get(0));
    }

    @Test
    public void testEq() throws Exception {
        Eq<Maybe<String>> eq = eq(new Eq.JavaEq<String>());
        Maybe<String> nothing1 = Nothing();
        Maybe<String> nothing2 = Nothing();
        Maybe<String> foo1 = Just("foo");
        Maybe<String> foo2 = Just("foo");
        Maybe<String> bar = Just("bar");
        assertTrue(eq.eq(nothing1, nothing2));
        assertTrue(eq.eq(foo1, foo2));
        assertFalse(eq.eq(nothing1, foo1));
        assertFalse(eq.eq(foo1, bar));
    }

    @Test
    public void testMap() throws Exception {
        Maybe<String> nothing = Nothing();
        assertEquals("Nothing", nothing.map(Strings.length).toString());
        Maybe<String> foo = Just("foo");
        assertEquals("Just(3)", foo.map(Strings.length).toString());
    }
    
    @Test
    public void testMonad() throws Exception {
        Monad<Maybe.µ> monad = Maybe.monad;
        //pure
        assertEquals("Just(foo)", monad.pure("foo").toString());
        //map
        Maybe<String> nothing = Nothing();
        assertEquals("Nothing", monad.map(Strings.length, nothing).toString());
        Maybe<String> foo = Just("foo");
        assertEquals("Just(3)", monad.map(Strings.length, foo).toString());
        //ap
        Maybe<F1<String,Integer>> noFn = Nothing();
        Maybe<F1<String,Integer>> lenFn = Just(Strings.length);
        assertEquals("Nothing", monad.ap(noFn, nothing).toString());
        assertEquals("Nothing", monad.ap(lenFn, nothing).toString());
        assertEquals("Nothing", monad.ap(noFn, foo).toString());
        assertEquals("Just(3)", monad.ap(lenFn, foo).toString());
        //bind
        Maybe<String> fool = Just("fool");
        F1<String, _<Maybe.µ, Integer>> lenIfEven = new F1<String, _<Maybe.µ, Integer>>() {
            @Override
            public _<Maybe.µ, Integer> $(String s) {
                int len = s.length();
                return len % 2 == 0 ? Just(len) : Maybe.<Integer>Nothing();
            }
        };
        assertEquals("Nothing", monad.bind(nothing, lenIfEven).toString());
        assertEquals("Nothing", monad.bind(foo, lenIfEven).toString());
        assertEquals("Just(4)", monad.bind(fool, lenIfEven).toString());
    }

    @Test
    public void testMonadPlus() throws Exception {
        MonadPlus<µ> monad = Maybe.monad;
        Maybe<String> foo = Just("foo");
        Maybe<String> bar = Just("bar");
        Maybe<String> baz = Just("baz");
        Maybe<String> nothing = Nothing();
        //mzero
        assertEquals("Nothing", monad.mzero().toString());
        //mplus
        assertEquals("Just(foo)", monad.mplus(foo, bar).toString());
        assertEquals("Just(foo)", monad.mplus(foo, nothing).toString());
        assertEquals("Just(bar)", monad.mplus(nothing, bar).toString());
        assertEquals("Nothing", monad.mplus(nothing, nothing).toString());
        //guard
        assertEquals("Nothing", monad.guard(false).toString());
        assertEquals("Just(())", monad.guard(true).toString());
        //mfilter
        Maybe<Integer> one = Just(1);
        assertEquals("Just(1)", monad.mfilter(Integers.odd, one).toString());
        assertEquals("Nothing", monad.mfilter(Integers.even, one).toString());
        //mplus
        List<_<Maybe.µ,String>> fooBarBaz = List.contravariant(List.of(foo, bar, baz));
        assertEquals("Just(foo)", monad.msum(fooBarBaz).toString());
        List<_<Maybe.µ,String>> fooBarNothing = List.contravariant(List.of(foo, bar, nothing));
        assertEquals("Just(foo)", monad.msum(fooBarNothing).toString());
        List<_<Maybe.µ,String>> fooNothingBaz = List.contravariant(List.of(foo, nothing, baz));
        assertEquals("Just(foo)", monad.msum(fooNothingBaz).toString());
        List<_<Maybe.µ,String>> nothingNothingBaz = List.contravariant(List.of(nothing, nothing, baz));
        assertEquals("Just(baz)", monad.msum(nothingNothingBaz).toString());
        List<_<Maybe.µ,String>> nothingBarNothing = List.contravariant(List.of(nothing, bar, nothing));
        assertEquals("Just(bar)", monad.msum(nothingBarNothing).toString());
        List<_<Maybe.µ,String>> nothingNothingNothing = List.contravariant(List.of(nothing, nothing, nothing));
        assertEquals("Nothing", monad.msum(nothingNothingNothing).toString());
    }

    @Test
    public void testJusts() throws Exception {
         F0<String> thunk = F0.constant("bar");
         List<Maybe<String>> maybes = List.of(
                 Maybe.<String>Nothing(),
                 Just("foo"),
                 Maybe.<String>Nothing(),
                 Just(thunk),
                 Maybe.<String>Nothing());
         List<String> strings = justs(maybes);
         assertEquals("List(foo,bar)", strings.toString());
    }
    
    @Test
    public void testAsList() throws Exception {
        Maybe<String> foo = Just("foo");
        assertEquals("List(foo)", foo.asList().toString());
        F0<String> thunk = F0.constant("bar");
        Maybe<String> bar = Just(thunk);
        assertEquals("List(bar)", bar.asList().toString());
        Maybe<String> nothing = Nothing();
        assertEquals("List()", nothing.asList().toString());
    }
}
