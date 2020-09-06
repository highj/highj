/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data;

import org.derive4j.hkt.__;
import org.highj.data.coroutine.ProducerT;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T1;
import org.highj.data.tuple.T2;
import org.highj.function.Strings;

import java.util.Arrays;
import java.util.Iterator;

import static org.highj.Hkt.asProducerT;

public class HashMap<K, V> implements Iterable<T2<K, V>> {
    private static final int NUM_BRANCHING_BITS = 5;
    private static final int NUM_BRANCHES = 1 << NUM_BRANCHING_BITS;
    private static final int MASK = (1 << NUM_BRANCHING_BITS) - 1;
    private static final Node<?, ?>[] EMPTY_ARRAY = new Node<?, ?>[NUM_BRANCHES];

    static {
        Arrays.fill(EMPTY_ARRAY, Leaf.empty());
    }

    private final Node<K, V> root;

    private HashMap(Node<K, V> root) {
        this.root = root;
    }

    private static final HashMap<?, ?> EMPTY = new HashMap<>(Leaf.empty());

    @SuppressWarnings("unchecked")
    public static <K, V> HashMap<K, V> empty() {
        return (HashMap<K, V>) EMPTY;
    }

    public boolean isEmpty() {
        return root.isEmpty();
    }

    @Override
    public Iterator<T2<K, V>> iterator() {
        return ProducerT.toIterator(asProducerT(root.generator(0, 0)));
    }

    public int size() {
        return root.size();
    }

    public HashMap<K, V> insert(K key, V value) {
        return new HashMap<>(root.insert(key.hashCode(), key, value));
    }

    public Maybe<V> lookup(K key) {
        return root.lookup(key.hashCode(), key);
    }

    public HashMap<K, V> delete(K key) {
        Node<K, V> root2 = root.delete(key.hashCode(), key);
        return root2 == root ? this : new HashMap<>(root2);
    }

    @Override
    public String toString() {
        return root.toString();
    }

    private interface Node<K, V> {

        boolean isEmpty();

        int size();

        Node<K, V> insert(int hash, K key, V value);

        Maybe<V> lookup(int hash, K key);

        Node<K, V> delete(int hash, K key);

        __<__<__<ProducerT.µ, T2<K, V>>, T1.µ>, T0> generator(int shift, int hash);
    }

    private static class Leaf<K, V> implements Node<K, V> {

        private static final Leaf<?, ?> EMPTY = new Leaf<>(List.of());

        @SuppressWarnings("unchecked")
        public static <K, V> Leaf<K, V> empty() {
            return (Leaf<K, V>) EMPTY;
        }

        private final List<T2<K, V>> bucket;

        private Leaf(K key, V value) {
            this.bucket = List.of(T2.of(key, value));
        }

        private Leaf(List<T2<K, V>> bucket) {
            this.bucket = bucket;
        }

        @Override
        public boolean isEmpty() {
            return bucket.isEmpty();
        }

        @Override
        public int size() {
            return bucket.size();
        }

        @Override
        public Node<K, V> insert(int hash, K key, V value) {
            if (hash == 0) {
                return new Leaf<>(bucket.filter(p -> !p._1().equals(key)).plus(T2.of(key, value)));
            } else {
                int newHash = hash >>> NUM_BRANCHING_BITS;
                int index = hash & MASK;
                Node<K, V>[] nodes = emptyArray();
                if (newHash == 0) {
                    nodes[0] = this;
                    nodes[index] = new Leaf<>(key, value);
                } else {
                    nodes[index] = Leaf.<K, V>empty().insert(newHash, key, value);
                    if (index != 0) {
                        nodes[0] = this;
                    } else {
                        bucket.forEach(t2 -> nodes[0] = nodes[0].insert(0, t2._1(), t2._2()));
                    }
                }
                return new Branch<>(nodes);
            }
        }

        @Override
        public Maybe<V> lookup(int hash, K key) {
            return (hash == 0)
                    ? bucket.filter(p -> p._1().equals(key)).maybeHead().map(T2::_2)
                    : Maybe.Nothing();
        }

        @Override
        public String toString() {
            return "(Leaf " + Strings.mkEnclosed("", "", "", bucket) + ")";
        }

        @Override
        public Node<K, V> delete(int hash, K key) {
            if (hash != 0) {
                return this;
            }
            List<T2<K, V>> newBucket = bucket.filter(p -> !p._1().equals(key));
            return newBucket.isEmpty() ? Leaf.empty() : new Leaf<>(newBucket);
        }

        @Override
        public __<__<__<ProducerT.µ, T2<K, V>>, T1.µ>, T0> generator(int shift, int hash) {
            return ProducerT.<T2<K, V>, T1.µ>monad().sequence_(bucket.map(ProducerT::yield));
        }
    }

    private static class Branch<K, V> implements Node<K, V> {
        private final Node<K, V>[] nodes;

        private Branch(Node<K, V>[] nodes) {
            this.nodes = nodes;
        }

        @Override
        public boolean isEmpty() {
            for (Node<K, V> node : nodes) {
                if (!node.isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public int size() {
            int size = 0;
            for (Node<K, V> node : nodes) {
                size += node.size();
            }
            return size;
        }

        @Override
        public Node<K, V> insert(int hash, K key, V value) {
            int newHash = hash >>> NUM_BRANCHING_BITS;
            int index = hash & MASK;
            Node<K, V>[] newNodes = Arrays.copyOf(nodes, nodes.length);
            newNodes[index] = nodes[index].insert(newHash, key, value);
            return new Branch<>(newNodes);
        }

        @Override
        public Maybe<V> lookup(int hash, K key) {
            int newHash = hash >>> NUM_BRANCHING_BITS;
            int index = hash & MASK;
            return nodes[index].lookup(newHash, key);
        }

        @Override
        public Node<K, V> delete(int hash, K key) {
            int newHash = hash >>> NUM_BRANCHING_BITS;
            int index = hash & MASK;
            Node<K, V> node = nodes[index];
            if (node.isEmpty()) {
                return this;
            } else {
                Node<K, V> newNode = node.delete(newHash, key);
                if (newNode == node) {
                    return this;
                } else {
                    Node<K, V>[] newNodes = Arrays.copyOf(nodes, nodes.length);
                    newNodes[index] = newNode;
                    Node<K, V> branch = new Branch<>(newNodes);
                    return branch.isEmpty() ? Leaf.empty() : branch;
                }
            }
        }

        @Override
        public String toString() {
            return Strings.mkEnclosed("(Branch ", " ", ")", (Object[]) nodes);
        }

        @Override
        public __<__<__<ProducerT.µ, T2<K, V>>, T1.µ>, T0> generator(int shift, int hash) {
            return ProducerT.<T2<K, V>, T1.µ>monad().sequence_(
                    List.zip(
                            List.of(nodes),
                            List.range(0)
                    ).map((T2<Node<K, V>, Integer> x) -> {
                        int newShift = shift + NUM_BRANCHING_BITS;
                        int newHash = hash | (x._2() << shift);
                        return x._1().generator(newShift, newHash);
                    })
            );
        }
    }

    @SuppressWarnings("unchecked")
    private static <K, V> Node<K, V>[] emptyArray() {
        return Arrays.copyOf((Node<K, V>[]) EMPTY_ARRAY, NUM_BRANCHES);
    }
}
