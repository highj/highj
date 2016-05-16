/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.collection;

import org.highj.data.functions.Strings;
import org.highj.util.ArrayUtils;

import java.util.Arrays;

/**
 * @author clintonselke
 */
public class IntMap<A> {
    private static final int NUM_BRANCHING_BITS = 5;
    private static final int NUM_BRANCHES = 1 << NUM_BRANCHING_BITS;
    private static final int MASK = (1 << NUM_BRANCHING_BITS) - 1;

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
        return this == EMPTY || root.isEmpty();
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

    @Override
    public String toString() {
        return root.toString();
    }

    private static abstract class Node<A> {

        public abstract boolean isEmpty();

        public abstract Node<A> insert(int key, A value);

        public abstract Maybe<A> lookup(int key);

        public abstract Node<A> delete(int key);
    }

    private static class Empty<A> extends Node<A> {
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
        public Node<A> insert(int key, A value) {
            if (key == 0) {
                return new Leaf<>(value);
            } else {
                int key2 = key >>> NUM_BRANCHING_BITS;
                int idx = key & MASK;
                @SuppressWarnings("unchecked")
                Node<A>[] nodes = new Node[NUM_BRANCHES];
                nodes[idx] = (key2 == 0)
                        ? new Leaf<>(value)
                        : Empty.<A>empty().insert(key2, value);
                return new Branch<>(nodes);
            }
        }

        @Override
        public Maybe<A> lookup(int key) {
            return Maybe.newNothing();
        }

        @Override
        public String toString() {
            return "Empty";
        }

        @Override
        public Node<A> delete(int key) {
            return this;
        }
    }

    private static class Leaf<A> extends Node<A> {
        private final A value;

        private Leaf(A value) {
            this.value = value;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Node<A> insert(int key, A value2) {
            if (key == 0) {
                return new Leaf<>(value2);
            } else {
                int key2 = key >>> NUM_BRANCHING_BITS;
                int idx = key & MASK;
                @SuppressWarnings("unchecked")
                Node<A>[] nodes = new Node[NUM_BRANCHES];
                if (key2 == 0) {
                    nodes[0] = this;
                    nodes[idx] = new Leaf<>(value2);
                } else {
                    if (idx == 0) {
                        nodes[0] = Empty.<A>empty().insert(0, value).insert(key2, value2);
                    } else {
                        nodes[0] = this;
                        nodes[idx] = Empty.<A>empty().insert(key2, value2);
                    }
                }
                return new Branch<>(nodes);
            }
        }

        @Override
        public Maybe<A> lookup(int key) {
            return Maybe.justWhenTrue(key == 0, () -> value);
        }

        @Override
        public String toString() {
            return "(Leaf " + value.toString() + ")";
        }

        @Override
        public Node<A> delete(int key) {
            return key == 0 ? Empty.empty() : this;
        }
    }

    private static class Branch<A> extends Node<A> {
        private final Node<A>[] nodes;

        private Branch(Node<A>[] nodes) {
            this.nodes = nodes;
        }

        @Override
        public boolean isEmpty() {
            for (Node<A> node : nodes) {
                if (node != null && !node.isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public Node<A> insert(int key, A value) {
            int key2 = key >>> NUM_BRANCHING_BITS;
            int idx = key & MASK;
            Node<A>[] nodes2 = Arrays.copyOf(nodes, nodes.length);
            if (key2 == 0) {
                nodes2[idx] = (nodes[idx] == null) ? new Leaf<>(value) : nodes[idx].insert(key2, value);
            } else {
                nodes2[idx] = (nodes[idx] == null) ? Empty.<A>empty().insert(key2, value) : nodes[idx].insert(key2, value);
            }
            return new Branch<>(nodes2);
        }

        @Override
        public Maybe<A> lookup(int key) {
            int key2 = key >>> NUM_BRANCHING_BITS;
            int idx = key & MASK;
            Node node = nodes[idx];
            return node == null ? Maybe.newNothing() : node.lookup(key2);
        }

        @Override
        public Node<A> delete(int key) {
            int key2 = key >>> NUM_BRANCHING_BITS;
            int idx = key & MASK;
            Node<A> node = nodes[idx];
            if (node == null) {
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
        public String toString() {
            return Strings.mkString("(Branch ", " ", ")",
                    ArrayUtils.map(nodes,
                            n -> n == null ? Empty.empty().toString() : n.toString(),
                            new String[nodes.length]));
        }
    }
}
