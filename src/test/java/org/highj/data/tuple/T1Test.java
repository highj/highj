package org.highj.data.tuple;

import org.highj._;
import org.highj.typeclass.monad.Apply;
import org.highj.typeclass.monad.Functor;
import org.highj.typeclass.monad.Monad;
import org.highj.data.compare.Eq;
import org.highj.function.F0;
import org.highj.function.F1;
import org.highj.function.repo.Strings;
import org.junit.Test;

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
        assertEquals(Integer.valueOf(42), T1.narrow(fortyTwo)._1());
        //lazy version
        _<T1.µ, String> hello = stringThunk("hello");
        assertEquals("hello", T1.narrow(hello)._1());
    }

    @Test
    public void mapTest() {
        T1<String> hello = Tuple.of("hello");
        T1<Integer> five = hello.map(Strings.length);
        assertEquals(Integer.valueOf(5), five._1());
        
        //lazy version
        T1<String> helloLazy = stringThunk("hello");
        T1<Integer> fiveLazy = helloLazy.map(Strings.length);
        assertEquals(Integer.valueOf(5), fiveLazy._1());
    }

    @Test
    public void functorTest() {
        Functor<T1.µ> functor = T1.monad;
        T1<String> hello = Tuple.of("hello");
        T1<Integer> five = T1.narrow(functor.map(Strings.length, hello));
        assertEquals(Integer.valueOf(5), five._1());
    }

    @Test
    public void apTest() {
        T1<F1<String,Integer>> lengthFn = Tuple.of(Strings.length);
        T1<String> hello = Tuple.of("hello");
        T1<Integer> five = hello.ap(lengthFn);
        assertEquals(Integer.valueOf(5), five._1());
    }

    @Test
    public void applyTest() {
        Apply<T1.µ> apply = T1.monad;
        T1<F1<String,Integer>> lengthFn = Tuple.of(Strings.length);
        T1<String> hello = Tuple.of("hello");
        T1<Integer> five = T1.narrow(apply.ap(lengthFn, hello));
        assertEquals(Integer.valueOf(5), five._1());
    }

    @Test
    public void monadTest() {
        Monad<T1.µ> monad = T1.monad;
        F1<String,_<T1.µ, Integer>> lengthFn = F1.<String,Integer,_<T1.µ,Integer>>compose(Tuple.<Integer>cell(), Strings.length);
        T1<String> hello = Tuple.of("hello");
        T1<Integer> five = T1.narrow(monad.bind(hello, lengthFn));
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

    private F0<String> stringThunk(final String s) {
        return new F0<String>() {
            @Override
            public String $() {
                return s;
            }
        };
    }
}
