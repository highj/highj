package org.highj.data.tuple;

import org.highj._;
import org.highj.data.functions.Functions;
import org.highj.typeclass1.monad.Apply;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass0.compare.Eq;
import org.junit.Test;

import java.util.function.Function;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class T1Test {

    @Test
    public void toStringTest() {
        T1<Integer> fortyTwo = Tuple.of(42);
        assertEquals("(42)", fortyTwo.toString());
        //lazy version
        T1<String> hello = stringThunk("hello");
        assertEquals("(hello)", hello.toString());
    }

    @Test
    public void narrowTest() {
        _<T1.µ, Integer> fortyTwo = Tuple.of(42);
        assertEquals(Integer.valueOf(42), Tuple.narrow(fortyTwo)._1());
        //lazy version
        _<T1.µ, String> hello = stringThunk("hello");
        assertEquals("hello", Tuple.narrow(hello)._1());
    }

    @Test
    public void mapTest() {
        T1<String> hello = Tuple.of("hello");
        T1<Integer> five = hello.map(String::length);
        assertEquals(Integer.valueOf(5), five._1());
        
        //lazy version
        T1<String> helloLazy = stringThunk("hello");
        T1<Integer> fiveLazy = helloLazy.map(String::length);
        assertEquals(Integer.valueOf(5), fiveLazy._1());
    }

    @Test
    public void functorTest() {
        Functor<T1.µ> functor = T1.monad;
        T1<String> hello = Tuple.of("hello");
        T1<Integer> five = Tuple.narrow(functor.map(s->s.length(), hello));
        assertEquals(Integer.valueOf(5), five._1());
    }

    @Test
    public void apTest() {
        T1<Function<String,Integer>> lengthFn = Tuple.of(String::length);
        T1<String> hello = Tuple.of("hello");
        T1<Integer> five = hello.ap(lengthFn);
        assertEquals(Integer.valueOf(5), five._1());
    }

    @Test
    public void applyTest() {
        Apply<T1.µ> apply = T1.monad;
        T1<Function<String,Integer>> lengthFn = Tuple.of(String::length);
        T1<String> hello = Tuple.of("hello");
        T1<Integer> five = Tuple.narrow(apply.ap(lengthFn, hello));
        assertEquals(Integer.valueOf(5), five._1());
    }

    @Test
    public void monadTest() {
        Monad<T1.µ> monad = T1.monad;
        Function<String,_<T1.µ, Integer>> lengthFn = Functions.<String,Integer,_<T1.µ,Integer>>compose(Tuple::of, String::length);
        T1<String> hello = Tuple.of("hello");
        T1<Integer> five = Tuple.narrow(monad.bind(hello, lengthFn));
        assertEquals(Integer.valueOf(5), five._1());
    }    

    @Test
    public void eqTest() {
        Eq<T1<String>> eq = T1.eq(new Eq.JavaEq<String>());
        T1<String> one = Tuple.of("hello");
        T1<String> two = stringThunk("hello");
        T1<String> three = Tuple.of("world");
        assertTrue(eq.eq(one, two));
        assertTrue(!eq.eq(one, three));
        assertTrue(!eq.eq(two, three));
    }

    private T1<String> stringThunk(final String s) {
        return new T1<String>(){
            @Override
            public String _1() {
                return s;
            }
        };
    }
}
