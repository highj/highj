package org.highj.data.instance.list;

import org.derive4j.hkt.__;
import org.highj.data.List;
import org.highj.data.Maybe;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;
import java.util.function.Predicate;

public class Zipper<A> implements __<Zipper.µ, A> {
    final private List<A> front;
    final private List<A> back;
    final private int pos;

    public interface µ {}

    private Zipper(List<A> front, List<A> back, int pos) {
        this.front = front;
        this.back = back;
        this.pos = pos;
    }

    public static <A> Zipper<A> narrow(__<Zipper.µ, A> zipper) {
        return (Zipper<A>) zipper;
    }

    public static <A> Zipper<A> empty() {
        return fromList(List.empty());
    }

    public static <A> Zipper<A> fromList(List<A> list) {
        return new Zipper<>(List.empty(), list, 0);
    }

    public static <A> Zipper<A> fromListEnd(List<A> list) {
        return fromList(list).toEnd();
    }

    public List<A> toList() {
        return toStart().back;
    }

    public boolean isEmpty() {
        return isStart() && isEnd();
    }

    public boolean isStart() {
        return pos == 0;
    }

    public boolean isEnd() {
        return back.isEmpty();
    }

    public int position() {
        return pos;
    }

    public Zipper<A> toStart() {
        Zipper<A> result = this;
        while(! result.isStart()) {
           result = result.backwards();
        }
        return result;
    }

    public Zipper<A> toEnd() {
        Zipper<A> result = this;
        while(! result.isEnd()) {
            result = result.forwards();
        }
        return result;
    }

    public Zipper<A> toPosition(int index) {
        Zipper<A> result = this;
        while(index != result.pos) {
            result = result.pos < index ? result.forwards() : result.backwards();
        }
        return result;
    }

    public Maybe<Zipper<A>> maybeToPosition(int index) {
        Zipper<A> result = this;
        while(index != result.pos) {
            if (result.pos < index && ! result.isEnd()) {
               result = result.forwards();
            } else if (result.pos > index && ! result.isStart()) {
                result = result.backwards();
            } else {
                return Maybe.Nothing();
            }
        }
        return Maybe.Just(result);
    }

    public Zipper<A> forwards() {
        return new Zipper<>(front.plus(back.head()), back.tail(), pos+1);
    }

    public Zipper<A> backwards() {
        return new Zipper<>(front.tail(), back.plus(front.head()), pos-1);
    }

    public Maybe<Zipper<A>> maybeForwards() {
        return Maybe.JustWhenTrue(! isEnd(), this::forwards);
    }

    public Maybe<Zipper<A>> maybeBackwards() {
        return Maybe.JustWhenTrue(! isStart(), this::backwards);
    }

    public A readNext() {
        return back.head();
    }

    public A readBefore() {
        return front.head();
    }

    public Maybe<A> maybeReadNext() {
        return Maybe.JustWhenTrue(! isEnd(), back::head);
    }

    public Maybe<A> maybeReadBefore() {
        return Maybe.JustWhenTrue(! isStart(), front::head);
    }

    public Zipper<A> removeNext() {
        return new Zipper<>(front, back.tail(), pos);
    }

    public Zipper<A> removeBefore() {
        return new Zipper<>(front.tail(), back, pos-1);
    }

    public Maybe<Zipper<A>> maybeRemoveNext() {
        return Maybe.JustWhenTrue(! isEnd(), this::removeNext);
    }

    public Maybe<Zipper<A>> maybeRemoveBefore() {
        return Maybe.JustWhenTrue(! isStart(), this::removeBefore);
    }

    public Zipper<A> insertNext(A ... as) {
        return new Zipper<>(front, back.plus(as), pos);
    }

    public Zipper<A> insertBefore(A ... as) {
        return new Zipper<>(front.plus(as), back, pos + as.length);
    }

    public Zipper<A> replaceNext(A a) {
        return new Zipper<>(front, back.tail().plus(a), pos);
    }

    public Zipper<A> replaceBefore(A a) {
        return new Zipper<>(front.tail().plus(a), back, pos + 1);
    }

    public Maybe<Zipper<A>> maybeReplaceNext(A a) {
        return Maybe.JustWhenTrue(! isEnd(), () -> replaceNext(a));
    }

    public Maybe<Zipper<A>> maybeReplaceBefore(A a) {
        return Maybe.JustWhenTrue(! isStart(), () -> replaceBefore(a));
    }

    public Zipper<A> dropAfter() {
        return new Zipper<>(front, List.empty(), pos);
    }

    public Zipper<A> dropBefore() {
        return new Zipper<>(List.empty(), back, 0);
    }

    public Zipper<A> reverse() {
        return new Zipper<>(back, front, back.size());
    }

    public <B> Zipper<B> map(Function<? super A, ? extends B> fn) {
        return new Zipper<>(front.map(fn), back.map(fn), pos);
    }

    public Zipper<A> filter(Predicate<? super A> predicate) {
        return new Zipper<>(front.filter(predicate), back.filter(predicate), front.size());
    }

    public Zipper<A> filterBefore(Predicate<? super A> predicate) {
        return new Zipper<>(front.filter(predicate), back, front.size());
    }

    public Zipper<A> filterAfter(Predicate<? super A> predicate) {
        return new Zipper<>(front, back.filter(predicate), pos);
    }

    public T2<List<A>,List<A>> split() {
        return T2.of(front, back);
    }

    public static Functor<µ> functor = new Functor<µ>() {
        @Override
        public <A, B> Zipper<B> map(Function<A, B> fn, __<µ, A> nestedA) {
            return narrow(nestedA).map(fn);
        }
    };

}
