/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.collection;

import java.util.Arrays;

/**
 *
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
    
    public static <A> IntMap<A> empty() {
        return (IntMap<A>)EMPTY;
    }
    
    public IntMap<A> insert(int key, A value) {
        return new IntMap<>(root.insert(key, value));
    }
    
    public Maybe<A> lookup(int key) {
        return root.lookup(key);
    }

    @Override
    public String toString() {
        return root.toString();
    }
    
    private static abstract class Node<A> {
        
        public abstract Node<A> insert(int key, A value);
        
        public abstract Maybe<A> lookup(int key);
    }
    
    private static class Empty<A> extends Node<A> {
        private static final Empty<?> EMPTY = new Empty<>();
        
        public static <A> Empty<A> empty() {
            return (Empty<A>)EMPTY;
        }
        
        @Override
        public Node<A> insert(int key, A value) {
            if (key == 0) {
                return new Leaf<>(value);
            } else {
                int key2 = key >>> NUM_BRANCHING_BITS;
                int idx = key & MASK;
                Node<A>[] nodes = new Node[NUM_BRANCHES];
                if (key2 == 0) {
                    nodes[idx] = new Leaf<>(value);
                } else {
                    nodes[idx] = Empty.<A>empty().insert(key2, value);
                }
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
    }
    
    private static class Leaf<A> extends Node<A> {
        private final A value;
        
        public Leaf(A value) {
            this.value = value;
        }

        @Override
        public Node<A> insert(int key, A value2) {
            if (key == 0) {
                return new Leaf<>(value2);
            } else {
                int key2 = key >>> NUM_BRANCHING_BITS;
                int idx = key & MASK;
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
            return key == 0 ? Maybe.newJust(value) : Maybe.newNothing();
        }

        @Override
        public String toString() {
            return "(Leaf " + value.toString() + ")";
        }
    }
    
    private static class Branch<A> extends Node<A> {
        private final Node<A>[] nodes;
        
        public Branch(Node<A>[] nodes) {
            this.nodes = nodes;
        }

        @Override
        public Node<A> insert(int key, A value) {
            int key2 = key >>> NUM_BRANCHING_BITS;
            int idx = key & MASK;
            Node<A>[] nodes2 = Arrays.copyOf(nodes, nodes.length);
            if (key2 == 0) {
                nodes2[idx] = new Leaf<>(value);
            } else {
                nodes2[idx] = (nodes[idx] == null) ? Empty.<A>empty().insert(key2, value) : nodes[idx].insert(key2, value);
            }
            return new Branch<>(nodes2);
        }
        
        public Branch<A> set(int idx, Node<A> node) {
            Node<A>[] nodes2 = Arrays.copyOf(nodes, nodes.length);
            nodes2[idx] = node;
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
        public String toString() {
            StringBuilder sb = new StringBuilder("(Branch ");
            for (int i = 0; i < nodes.length; ++i) {
                Node node = nodes[i];
                sb.append((node == null ? Empty.<A>empty() : node).toString());
            }
            sb.append(")");
            return sb.toString();
        }
    }
}
