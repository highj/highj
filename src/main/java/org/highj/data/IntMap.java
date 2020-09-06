/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data;

import org.derive4j.hkt.__;
import org.highj.data.instance.intmap.IntMapFunctor;
import org.highj.function.Strings;
import org.highj.data.coroutine.ProducerT;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T1;
import org.highj.data.tuple.T2;
import org.highj.util.ArrayUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Function;

import static org.highj.Hkt.asProducerT;

/**
 * @author clintonselke
 */
public class IntMap<A> implements __<IntMap.µ, A>, Iterable<T2<Integer,A>>{
    private static final int NUM_BRANCHING_BITS = 5;
    private static final int NUM_BRANCHES = 1 << NUM_BRANCHING_BITS;
    private static final int MASK = (1 << NUM_BRANCHING_BITS) - 1;
    private static final Node<?>[] EMPTY_ARRAY = new Node<?>[NUM_BRANCHES];

    public interface µ{
    }

    static {
        Arrays.fill(EMPTY_ARRAY, Empty.empty());
    }

    private final Node<A> root;

    private IntMap(Node<A> root) {
        this.root = root;
    }

    private static final IntMap<?> EMPTY = new IntMap<>(Empty.empty());

    @SuppressWarnings("unchecked")
    public static <A> IntMap<A> empty() {
        return (IntMap<A>) EMPTY;
    }

    public boolean isEmpty() {
        return root.isEmpty();
    }

    @Override
    public Iterator<T2<Integer, A>> iterator() {
        return ProducerT.toIterator(asProducerT(root.generator(0, 0)));
    }

    public int size() {
        return root.size();
    }

    public IntMap<A> insert(int key, A value) {
        return new IntMap<>(root.insert(key, value));
    }

    public Maybe<A> lookup(int key) {
        return root.lookup(key);
    }

    public IntMap<A> delete(int key) {
        Node<A> root2 = root.delete(key);
        return root2 == root ? this : new IntMap<>(root2);
    }

    public <B> IntMap<B> mapValues(Function<? super A, ? extends B> fn) {
        return new IntMap<B>(root.mapValues(fn));
    }

    @Override
    public String toString() {
        return root.toString();
    }

    private interface Node<A> {

        boolean isEmpty();

        int size();

        Node<A> insert(int key, A value);

        Maybe<A> lookup(int key);

        Node<A> delete(int key);

        <B> Node<B> mapValues(Function<? super A, ? extends B> fn);

        __<__<__<ProducerT.µ,T2<Integer,A>>,T1.µ>,T0> generator(int shift, int key);
    }

    private static class Empty<A> implements Node<A> {
        private static final Empty<?> EMPTY = new Empty<>();

        @SuppressWarnings("unchecked")
        public static <A> Empty<A> empty() {
            return (Empty<A>) EMPTY;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public Node<A> insert(int key, A value) {
            if (key == 0) {
                return new Leaf<>(value);
            } else {
                int key2 = key >>> NUM_BRANCHING_BITS;
                int idx = key & MASK;
                Node<A>[] nodes = emptyArray();
                nodes[idx] = (key2 == 0) ? new Leaf<>(value) : insert(key2, value);
                return new Branch<>(nodes);
            }
        }

        @Override
        public Maybe<A> lookup(int key) {
            return Maybe.Nothing();
        }

        @Override
        public String toString() {
            return "Empty";
        }

        @Override
        public Node<A> delete(int key) {
            return this;
        }

        @Override
        public <B> Node<B> mapValues(Function<? super A, ? extends B> fn) {
            return empty();
        }

        @Override
        public __<__<__<ProducerT.µ,T2<Integer,A>>,T1.µ>,T0> generator(int shift, int key) {
            return ProducerT.<T2<Integer,A>,T1.µ>applicative().pure(T0.of());
        }
    }

    private static class Leaf<A> implements Node<A> {
        private final A value;

        private Leaf(A value) {
            this.value = value;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public Node<A> insert(int key, A value2) {
            if (key == 0) {
                return value == value2 ? this : new Leaf<>(value2);
            } else {
                int key2 = key >>> NUM_BRANCHING_BITS;
                int idx = key & MASK;
                Node<A>[] nodes = emptyArray();
                if (key2 == 0) {
                    nodes[0] = this;
                    nodes[idx] = new Leaf<>(value2);
                } else {
                    nodes[idx] = Empty.<A>empty().insert(key2, value2);
                    nodes[0] = idx == 0 ? nodes[0].insert(0, value) : this;
                }
                return new Branch<>(nodes);
            }
        }

        @Override
        public Maybe<A> lookup(int key) {
            return Maybe.JustWhenTrue(key == 0, () -> value);
        }

        @Override
        public String toString() {
            return "(Leaf " + value.toString() + ")";
        }

        @Override
        public Node<A> delete(int key) {
            return key == 0 ? Empty.empty() : this;
        }

        @Override
        public <B> Node<B> mapValues(Function<? super A, ? extends B> fn) {
            return new Leaf<B>(fn.apply(value));
        }

        @Override
        public __<__<__<ProducerT.µ, T2<Integer, A>>, T1.µ>, T0> generator(int shift, int key) {
            return ProducerT.yield(T2.of(key, value));
        }
    }

    private static class Branch<A> implements Node<A> {
        private final Node<A>[] nodes;

        private Branch(Node<A>[] nodes) {
            this.nodes = nodes;
        }

        @Override
        public boolean isEmpty() {
            for (Node<A> node : nodes) {
                if (!node.isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public int size() {
            int size = 0;
            for (Node<A> node : nodes) {
                size += node.size();
            }
            return size;
        }

        @Override
        public Node<A> insert(int key, A value) {
            int key2 = key >>> NUM_BRANCHING_BITS;
            int idx = key & MASK;
            Node<A>[] nodes2 = Arrays.copyOf(nodes, nodes.length);
            nodes2[idx] = nodes[idx].insert(key2, value);
            return new Branch<>(nodes2);
        }

        @Override
        public Maybe<A> lookup(int key) {
            int key2 = key >>> NUM_BRANCHING_BITS;
            int idx = key & MASK;
            return nodes[idx].lookup(key2);
        }

        @Override
        public Node<A> delete(int key) {
            int key2 = key >>> NUM_BRANCHING_BITS;
            int idx = key & MASK;
            Node<A> node = nodes[idx];
            if (node.isEmpty()) {
                return this;
            } else {
                Node<A> node2 = node.delete(key2);
                if (node2 == node) {
                    return this;
                } else {
                    Node<A>[] nodes2 = Arrays.copyOf(nodes, nodes.length);
                    nodes2[idx] = node2;
                    Node<A> r = new Branch<>(nodes2);
                    return r.isEmpty() ? Empty.<A>empty() : r;
                }
            }
        }

        @Override
        public <B> Node<B> mapValues(Function<? super A, ? extends B> fn) {
            return new Branch<B>(ArrayUtils.map(nodes, n -> n.mapValues(fn), emptyArray()));
        }

        @Override
        public String toString() {
            return Strings.mkEnclosed("(Branch ", " ", ")", (Object[]) nodes);
        }

        @Override
        public __<__<__<ProducerT.µ, T2<Integer, A>>, T1.µ>, T0> generator(int shift, int key) {
            return ProducerT.<T2<Integer,A>,T1.µ>monad().sequence_(
                List.zip(
                    List.of(nodes),
                    List.range(0)
                ).map((T2<Node<A>,Integer> x) -> {
                    int shift2 = shift + NUM_BRANCHING_BITS;
                    int key2 = key | (x._2() << shift);
                    return x._1().generator(shift2, key2);
                })
            );
        }
    }

    public static IntMapFunctor functor = new IntMapFunctor() {
    };

    @SuppressWarnings("unchecked")
    private static <A> Node<A>[] emptyArray() {
        return Arrays.copyOf((Node<A>[]) EMPTY_ARRAY, NUM_BRANCHES);
    }
}
