package org.highj.data.stateful;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;

import java.util.function.Function;

//see http://blog.higher-order.com/blog/2015/06/23/a-scala-comonad-tutorial/
public class Coreader<R, A> implements __2<Coreader.µ, R, A> {

    private final A extract;
    private final R ask;

    public interface µ {}

    public Coreader(R ask, A extract) {
        this.ask = ask;
        this.extract = extract;
    }

    public <B> Coreader<R,B> map(Function<A,B> fn) {
        return new Coreader<>(ask, fn.apply(extract));
    }

    public Coreader<R, Coreader<R,A>> duplicate() {
        return new Coreader<>(ask, this);
    }

    public <B> Coreader<R,B> extend(Function<Coreader<R,A>,B> fn) {
        return duplicate().map(fn);
    }

    public R ask() {
        return ask;
    }

    public A extract() {
        return extract;
    }
}
