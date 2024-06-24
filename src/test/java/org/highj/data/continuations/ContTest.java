package org.highj.data.continuations;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ContTest {
    @Test
    public void map() throws Exception {
    }

    @Test
    public void with() throws Exception {
    }

    @Test
    public void eval() throws Exception {
    }

    @Test
    public void resetShift() throws Exception {
        Cont<Integer, Integer> s = Cont.<Integer, Integer, Integer>shift(
                fn -> fn.apply(3).bind(fn).bind(fn));
        Cont<Integer, Integer> r = Cont.reset(s.map(x -> x * 2));
        assertThat(Cont.eval(r)).isEqualTo(24);
    }

    @Test
    public void callCC() throws Exception {
    }

    @Test
    public void functor() throws Exception {
    }

    @Test
    public void applicative() throws Exception {
    }

    @Test
    public void monad() throws Exception {
    }

}