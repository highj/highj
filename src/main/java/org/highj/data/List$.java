package org.highj.data;

import org.derive4j.hkt.__;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class List$<A> implements Iterable<Supplier<A>>, __<List$.µ,A> {

    public interface µ{
    }

    @Override
    public Iterator<Supplier<A>> iterator() {
        return new Iterator<Supplier<A>>(){
            private List$<A> current = List$.this;

            @Override
            public boolean hasNext() {
                return ! current.isEmpty().get();
            }

            @Override
            public Supplier<A> next() {
                if (! hasNext()) {
                    throw new NoSuchElementException();
                }
                Supplier<A> head = current.head();
                current = current.tail().get();
                return head;
            }
        };
    }

    private final Memo<Node$<A>> node;

    private List$(Memo<Node$<A>> node) {
        this.node = node;
    }

    public Memo<Boolean> isEmpty() {
        return node.map(Node$::isEmpty);
    }

    public Memo<A> head() throws NoSuchElementException {
        return () -> {
            if (isEmpty().get()) {
                throw new NoSuchElementException();
            }
            return node.get().head.get();
        };
    }

    public Memo<Maybe<A>> maybeHead() {
        return () -> {
            if (isEmpty().get()) {
                return Maybe.Nothing();
            }
            return Maybe.Just(node.get().head.get());
        };
    }

    public Memo<List$<A>> tail() throws NoSuchElementException {
        return () -> {
            if (isEmpty().get()) {
                throw new NoSuchElementException();
            }
            return new List$<>(node.get().tail);
        };
    }

    public Memo<Maybe<List$<A>>> maybeTail() {
        return () -> {
            if (isEmpty().get()) {
                return Maybe.Nothing();
            }
            return Maybe.Just(new List$<>(node.get().tail));
        };
    }

    public List$<A> plus(Supplier<A> a) {
        return new List$<>(() -> new Node$<>(Memo.of(a), this.node));
    }

    public <B> List$<B> map(Function<A, B> fn) {
        return new List$<>(node.map(n -> n.map(fn)));
    }

    public List$<A> filter(Predicate<A> pred) {
        return new List$<>(node.map(n -> n.filter(pred)));
    }

    public static <A> List$<A> empty() {
        return new List$<>(List$::nil);
    }

    public static <A> List$<A> singleton(Supplier<A> a) {
        return List$.<A>empty().plus(a);
    }

    public long size() {
        long count = 0;
        for(Supplier<A> head : this) {
           count ++;
        }
        return count;
    }

    static final Node$<?> NIL = new Node$<Object>(null, null) {
        @Override
        public boolean isEmpty() {
            return true;
        }
    };

    @SuppressWarnings("unchecked")
    private static <A> Node$<A> nil() {
        return (Node$<A>) NIL;
    }

    static class Node$<A> {
        private final Memo<A> head;
        private final Memo<Node$<A>> tail;

        Node$(Memo<A> head, Memo<Node$<A>> tail) {
            this.head = head;
            this.tail = tail;
        }

        public boolean isEmpty() {
            return false;
        }

        public <B> Node$<B> map(Function<A, B> fn) {
            return isEmpty() ? nil() : new Node$<>(head.map(fn), tail.map(n -> n.map(fn)));
        }

        public Node$<A> filter(Predicate<A> pred) {
            return isEmpty()
                    ? this
                    : pred.test(head.get())
                    ? new Node$<>(head, tail.map(n -> n.filter(pred)))
                    : tail.get().filter(pred);
        }
    }

}
