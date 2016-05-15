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
    
    private static abstract class Node<A> {
        public abstract Node<A> insert(int key, A value);
    }
    
    private static class Empty<A> extends Node<A> {
        private static final Empty<?> EMPTY = new Empty<>();
        
        public static <A> Empty<A> empty() {
            return (Empty<A>)EMPTY;
        }
        
        @Override
        public Node<A> insert(int key, A value) {
            return new Leaf<>(key, value);
        }
    }
    
    private static class Leaf<A> extends Node<A> {
        private final int idx;
        private final A value;
        
        public Leaf(int idx, A value) {
            this.idx = idx;
            this.value = value;
        }

        @Override
        public Node<A> insert(int key, A value2) {
            int key2 = key >>> NUM_BRANCHING_BITS;
            int idx2 = key & MASK;
            Node<A>[] nodes = new Node[NUM_BRANCHES];
            if (key2 == 0) {
                nodes[idx] = this;
                nodes[idx2] = new Leaf<>(idx2, value2);
            } else {
                if (idx == idx2) {
                    nodes[idx2] = Empty.<A>empty().insert(0, value).insert(key2, value2);
                } else {
                    nodes[idx] = this;
                    nodes[idx2] = Empty.<A>empty().insert(key2, value2);
                }
            }
            return new Branch<>(nodes);
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
                nodes2[idx] = new Leaf<>(idx, value);
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
    }
}
